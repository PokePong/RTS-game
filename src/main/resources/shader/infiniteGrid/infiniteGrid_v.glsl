#version 430 core

layout(location = 0) in vec3 in_Position;
layout(location = 1) in vec3 in_Normal;

layout (std140) uniform Camera {
    mat4 c_Projection;
    mat4 c_View;
};

out vec3 nearPoint;
out vec3 farPoint;
out vec3 pos;
out mat4 proj;
out mat4 view;

vec3 UnprojectPoint(float x, float y, float z, mat4 view, mat4 projection) {
    mat4 viewInv = inverse(view);
    mat4 projInv = inverse(projection);
    vec4 unprojectedPoint =  viewInv * projInv * vec4(x, y, z, 1.0);
    return unprojectedPoint.xyz / unprojectedPoint.w;
}

void main(void) {

    nearPoint = UnprojectPoint(in_Position.x, in_Position.y, 0.0, c_View, c_Projection);
    farPoint = UnprojectPoint(in_Position.x, in_Position.y, 1.0, c_View, c_Projection);

    pos = in_Position;
    proj = c_Projection;
    view = c_View;

    gl_Position = vec4(in_Position, 1.0);

}