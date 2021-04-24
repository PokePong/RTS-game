#version 430 core

layout(location = 0) in vec3 in_Position;

layout (std140) uniform Camera {
    mat4 c_Projection;
    mat4 c_View;
};

void main() {

    gl_Position = c_Projection * c_View * vec4(in_Position, 1.0);

}