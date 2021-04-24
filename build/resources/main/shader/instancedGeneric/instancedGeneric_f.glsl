#version 430 core

layout (location = 0) out vec4 out_Albedo;

in vec3 out_Position;
in vec3 out_Normal;
in vec4 f_Color;

void main(void) {

    // Ambiant light
    float ambiantStrenght = 0.3;
    vec3 ambiantColor = vec3(1);
    vec3 ambiant = ambiantStrenght * ambiantColor;

    // Diffuse light
    vec3 lightPos = vec3(20);
    vec3 lightColor = vec3(1);
    vec3 lightDir = normalize(lightPos - out_Position);
    float diffuseFactor = max(dot(normalize(out_Normal), lightDir), 0.0);
    vec3 diffuse = diffuseFactor * lightColor;

    vec3 res = (ambiant + diffuse) * f_Color.rgb;
    out_Albedo = vec4(res, 1.0);

}