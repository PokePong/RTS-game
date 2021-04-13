#version 430 core

layout(location = 0) in vec3 in_Position;
layout(location = 1) in vec3 in_Normal;

out vec3 out_Position;
out vec3 out_Normal;

layout (std140) uniform Camera {
    mat4 c_Projection;
    mat4 c_View;
};

uniform mat4 localMatrix;
uniform mat4 worldMatrix;

void main(void) {

    vec4 position = worldMatrix * localMatrix * vec4(in_Position, 1.0);
    vec4 normal = worldMatrix * localMatrix * vec4(in_Normal, 0.0);

    out_Position = position.xyz;
    out_Normal = normalize(normal.xyz);

    gl_Position = c_Projection * c_View * position;

}