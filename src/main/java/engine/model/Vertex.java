package engine.model;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vertex {

    public static final int FLOATS = 6;
    public static final int BYTES = FLOATS * Float.BYTES;

    private Vector3f position;
    private Vector3f normal;

    public Vertex(Vector3f position) {
        this(position, new Vector3f(0, 0, 1));
    }

    public Vertex(Vector3f position, Vector3f normal) {
        this.position = position;
        this.normal = normal;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

}
