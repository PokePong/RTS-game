#version 430 core

layout (location = 0) out vec4 out_Albedo;
layout (location = 1) out vec4 out_Position;
layout (location = 2) out vec4 out_Normal;

in vec3 f_Position;
in vec3 f_Normal;

uniform vec4 color;

float near = 0.1;
float far  = 100;

float linearizeDepth(float depth) {
    float z = depth * 2.0 - 1.0;// back to NDC
    return (2.0 * near * far) / (far + near - z * (far - near)) / far;
}

void main(void) {

    gl_FragDepth = linearizeDepth(gl_FragCoord.z);

    out_Albedo = color;
    out_Position = vec4(f_Position, 1.0);
    out_Normal = vec4(f_Normal, 1.0);

}