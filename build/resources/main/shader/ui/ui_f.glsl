#version 430 core

layout (location = 0) out vec4 out_Color;

in vec2 f_UV;

uniform vec4 color;
uniform int textured;
uniform sampler2D texture;

vec4 res = vec4(1);

void main() {

    if (textured == 1){
        res = texture2D(texture, f_UV);
    }
    res *= color;

    out_Color = res;

}