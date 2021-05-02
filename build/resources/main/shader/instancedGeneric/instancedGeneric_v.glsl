#version 430 core

layout(location = 0) in vec3 in_Position;
layout(location = 1) in vec3 in_Normal;
layout(location = 2) in mat4 in_WorldMatrix;
layout(location = 6) in vec4 in_Color;

out vec3 f_Position;
out vec3 f_Normal;
out vec4 f_Color;

layout (std140) uniform Camera {
    mat4 c_Projection;
    mat4 c_View;
    vec4 c_Eye;
};

uniform mat4 localMatrix;

void main(void) {

    vec4 position = in_WorldMatrix * localMatrix * vec4(in_Position, 1.0);
    vec4 normal = in_WorldMatrix * localMatrix * vec4(in_Normal, 0.0);

    f_Position = position.xyz;
    f_Normal = normalize(normal.xyz);
    f_Color = in_Color;

    gl_Position = c_Projection * c_View * position;
}