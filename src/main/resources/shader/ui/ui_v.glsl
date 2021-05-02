#version 430 core

layout(location = 0) in vec2 in_Position;

out vec2 f_UV;

uniform mat4 m_Ortho;

void main() {

    f_UV = vec2(in_Position.x, in_Position.y);
    gl_Position = m_Ortho * vec4(in_Position, 0, 1.0);

}