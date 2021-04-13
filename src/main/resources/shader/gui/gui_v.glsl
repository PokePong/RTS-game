#version 430 core

layout(location = 0) in vec2 in_Position;

uniform mat4 m_Ortho;

void main() {

    gl_Position = m_Ortho * vec4(in_Position, 0, 1.0);

}