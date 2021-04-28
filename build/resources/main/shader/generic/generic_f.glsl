#version 430 core

layout (location = 0) out vec4 out_Albedo;

in vec3 f_Position;
in vec3 f_Normal;

uniform vec4 color;

void main(void) {

    // Ambiant light
    float ambiantStrenght = 0.3;
    vec3 ambiantColor = vec3(1);
    vec3 ambiant = ambiantStrenght * ambiantColor;

    // Diffuse light
    vec3 lightPos = vec3(20);
    vec3 lightColor = vec3(1);
    vec3 lightDir = normalize(lightPos - f_Position);
    float diffuseFactor = max(dot(normalize(f_Normal), lightDir), 0.0);
    vec3 diffuse = diffuseFactor * lightColor;

    vec3 res = (ambiant + diffuse) * vec3(color.rgb);
    out_Albedo = vec4(res, 1.0);

}