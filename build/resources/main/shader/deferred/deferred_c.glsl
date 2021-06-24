#version 430 core

layout (local_size_x = 16, local_size_y = 16) in;
layout (rgba32f, binding = 0) uniform readonly image2D img_Albedo;
layout (rgba32f, binding = 1) uniform readonly image2D img_Position;
layout (rgba32f, binding = 2) uniform readonly image2D img_Normal;
layout (rgba32f, binding = 3) uniform writeonly image2D img_Output;

layout (std140) uniform Camera {
    mat4 c_Projection;
    mat4 c_View;
    vec4 c_Eye;
};

uniform sampler2D img_Depth;
uniform int width;
uniform int height;

// Edge detection sobel algorithm
vec2 sobelSamplePoints[9] = {
vec2(-1, 1), vec2(0, 1), vec2(1, 1),
vec2(-1, 0), vec2(0, 0), vec2(1, 0),
vec2(-1, -1), vec2(0, -1), vec2(1, -1),
};

float sobelXMatrix[9] = {
1, 0, -1,
2, 0, -2,
1, 0, -1
};

float sobelYMatrix[9] = {
1, 2, 1,
0, 0, 0,
-1, -2, -1
};

// Sun
vec3 lightPos = vec3(20);
vec3 lightColor = vec3(1);

// Level
int level = 5;
float scaleFactor = 1.0 / level;

// Ambiant light
float ambiantStrenght = 0.3;
vec3 ambiantColor = vec3(1);
vec3 ambiant = ambiantStrenght * ambiantColor;

float depthLoad(vec2 coords) {
    return 1 - texture(img_Depth, coords / vec2(width, height)).r;
}

float processEdge(ivec2 coords, float thickness) {
    if (coords.x == 0 || coords.x == width || coords.y == 0 || coords.y == height){
        return 0;
    }
    vec2 sobel = vec2(0);
    for (int i = 0; i < 9; i++) {
        float depth = depthLoad(coords + sobelSamplePoints[i] * thickness);
        sobel += depth * vec2(sobelXMatrix[i], sobelYMatrix[i]);
    }
    return length(sobel);
}

vec3 depthNormalLoad(ivec2 coords) {
    vec3 normal = imageLoad(img_Normal, coords).xyz;
    float depth = depthLoad(coords);
    vec3 res = vec3(normal.x * 2 - depth, normal.y + depth, normal.z * 2 - depth);
    normalize(res);
    return res;
}

void main() {

    ivec2 coords = ivec2(gl_GlobalInvocationID.xy);

    vec3 albedo = imageLoad(img_Albedo, coords).rgb;
    vec3 position = imageLoad(img_Position, coords).xyz;
    vec3 normal = imageLoad(img_Normal, coords).xyz;
    float depth = depthLoad(coords);

    if (position == vec3(0.0) && normal == vec3(0.0)) {
        vec4 pixel = vec4(albedo, 1.0);
        imageStore(img_Output, coords, pixel);
        return;
    }

    //Blinn-Phong
    vec3 L = normalize(lightPos - position);
    vec3 V = normalize(c_Eye.xyz - position);
    vec3 H = normalize(L + V);

    // Diffuse light
    float diffuseFactor = max(0.0, dot(L, normal));
    vec3 diffuse = floor(diffuseFactor * level) * scaleFactor * lightColor;

    vec3 res = (ambiant + diffuse) * albedo;

    // Edge sobel
    float sobel = processEdge(coords, 1);
    if (sobel >= 1) {
        res = mix(res, vec3(0), sobel / 2.5);
    }

    vec4 pixel = vec4(res, 1.0);
    imageStore(img_Output, coords, pixel);

}