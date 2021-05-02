#version 430 core

layout (location = 0) out vec4 out_Albedo;
layout (location = 1) out vec4 out_Position;
layout (location = 2) out vec4 out_Normal;

uniform vec4 color;

void main() {

    out_Albedo =  color;
    out_Position = vec4(0.0);
    out_Normal = vec4(0.0);

}