#version 430 core

layout(location = 0) in vec3 in_Position;
layout(location = 1) in vec3 in_Normal;
layout(location = 2) in mat4 in_WorldMatrix;
layout(location = 6) in vec4 in_Color;

out vec3 out_Position;
out vec3 out_Normal;
out vec4 out_Color;

layout (std140) uniform Camera {
    mat4 c_Projection;
    mat4 c_View;
};

uniform mat4 localMatrix;

void main(void) {

    vec4 position = in_WorldMatrix * localMatrix * vec4(in_Position, 1.0);
    vec4 normal = in_WorldMatrix * localMatrix * vec4(in_Normal, 0.0);

    out_Position = position.xyz;
    out_Normal = normalize(normal.xyz);
    out_Color = in_Color;

    gl_Position = c_Projection * c_View * position;
}