package engine.util;

import engine.math.Vector3;
import engine.model.Mesh;
import engine.model.Vertex;

public class Utils {

    public static Mesh generateQuad() {
        return generateQuad(1f);
    }

    public static Mesh generateQuad(float length) {
        Vertex[] vertices = new Vertex[4];
        int[] indices = {0, 1, 2, 2, 1, 3};
        float value = length / 2f;
        vertices[0] = new Vertex(new Vector3(-value, value, 0));
        vertices[1] = new Vertex(new Vector3(value, value, 0));
        vertices[2] = new Vertex(new Vector3(-value, -value, 0));
        vertices[3] = new Vertex(new Vector3(value, -value, 0));
        return new Mesh(vertices, indices);
    }

    public static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }

    public static boolean approximately(float a, float b) {
        return Math.abs(b - a) < Math.max(0.000001f * Math.max(Math.abs(a), Math.abs(b)), Float.MIN_VALUE * 8);
    }

}
