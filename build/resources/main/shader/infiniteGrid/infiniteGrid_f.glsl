#version 430 core

layout (location = 0) out vec4 out_Albedo;
layout (location = 1) out vec4 out_Position;
layout (location = 2) out vec4 out_Normal;

in vec3 nearPoint;
in vec3 farPoint;
in vec3 pos;
in mat4 proj;
in mat4 view;

float near = 0.1;
float far  = 100;

vec4 grid(vec3 fragPos, float scale, float intensity) {
    vec2 coord = fragPos.xz * 1 / scale;
    vec2 derivative = fwidth(coord);

    vec2 grid = abs(fract(coord - 0.5) - 0.5) / derivative;
    float line = min(grid.x, grid.y);

    float gray = intensity / 10.;
    vec4 color;

    float minimumx = min(derivative.x, 1);
    float minimumz = min(derivative.y, 1);
    float delta = 0.5;

    bool zAxis = fragPos.x > -delta * minimumx && fragPos.x < delta * minimumx;
    bool xAxis = fragPos.z > -delta * minimumz && fragPos.z < delta * minimumz;

    color = vec4(vec3(gray), 1.0 - min(line, 1.0));

    if (xAxis) {
        color.r = 1.0;
    }
    if (zAxis) {
        color.b = 1.0;
    }

    return color;
}

float computeDepth(vec3 pos) {
    vec4 clip_space_pos = proj * view * vec4(pos.xyz, 1.0);
    return clip_space_pos.z / clip_space_pos.w;
}

float linearizeDepth(vec3 pos) {
    float depth = computeDepth(pos) * 2.0 - 1.0;
    return (2.0 * near * far) / (far + near - depth * (far - near)) / far;
}

void main(void) {

    float t = -nearPoint.y / (farPoint.y - nearPoint.y);
    vec3 R = nearPoint + t * (farPoint - nearPoint);

    float depth = computeDepth(R);
    float linearDepth = linearizeDepth(R);
    float fading = max(0, (0.5 - linearDepth));

    gl_FragDepth = (depth + 1) / 2;

    out_Albedo = grid(R, 1, 1) + grid(R, 10, 3);
    out_Albedo = out_Albedo * float(t > 0);
    out_Albedo.a *= fading;

    out_Position = vec4(0.0);
    out_Normal = vec4(0.0);

}