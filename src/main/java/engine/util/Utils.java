package engine.util;

import engine.core.Window;
import engine.model.Mesh;
import engine.model.Vertex;
import module.gui.GuiConfig;
import module.gui.GuiObject;
import module.gui.GuiShader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Utils {

    public static Mesh generateQuad() {
        return generateQuad(1f);
    }

    public static Mesh generateQuad(float length) {
        Vertex[] vertices = new Vertex[4];
        int[] indices = {0, 1, 2, 2, 1, 3};
        float value = length / 2f;
        vertices[0] = new Vertex(new Vector3f(-value, value, 0));
        vertices[1] = new Vertex(new Vector3f(value, value, 0));
        vertices[2] = new Vertex(new Vector3f(-value, -value, 0));
        vertices[3] = new Vertex(new Vector3f(value, -value, 0));
        return new Mesh(vertices, indices);
    }

    public static Matrix4f generateProjectionMatrix(int fovY, float zNear, float zFar) {
        Matrix4f ret = new Matrix4f();
        ret.identity();

        float tanFOV = (float) Math.tan(Math.toRadians(fovY / 2));
        float aspectRatio = Window.getAspectRatio();
        float length = zFar - zNear;

        ret.m00(1 / (aspectRatio * tanFOV));
        ret.m11(1 / tanFOV);
        ret.m22(-(zFar + zNear) / length);
        ret.m23(-1);
        ret.m32(-(2 * zNear * zFar) / length);
        ret.m33(0);
        return ret;
    }

    public static Matrix4f generateViewMatrix(Vector3f forward, Vector3f up, Vector3f position) {
        Vector3f right = new Vector3f(forward).cross(up);

        Matrix4f ret = new Matrix4f();
        ret.identity();

        ret.m00(right.x);
        ret.m01(right.y);
        ret.m02(right.z);
        ret.m10(up.x);
        ret.m11(up.y);
        ret.m12(up.z);
        ret.m20(-forward.x);
        ret.m21(-forward.y);
        ret.m22(-forward.z);
        ret.m33(1);
        ret.mul(new Matrix4f().translate(new Vector3f(position).negate()));

        return ret;
    }

    public static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }

    public static boolean approximately(float a, float b) {
        return Math.abs(b - a) < Math.max(0.000001f * Math.max(Math.abs(a), Math.abs(b)), Float.MIN_VALUE * 8);
    }

}
