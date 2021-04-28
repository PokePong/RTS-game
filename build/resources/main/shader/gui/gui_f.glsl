#version 430 core

layout (location = 0) out vec4 out_Color;

uniform vec4 color;

void main() {

    out_Color = color;

}