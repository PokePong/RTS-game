package engine.model;


import engine.math.Vector3;

public class Vertex {

    public static final int FLOATS = 6;
    public static final int BYTES = FLOATS * Float.BYTES;

    private Vector3 position;
    private Vector3 normal;

    public Vertex(Vector3 position) {
        this(position, new Vector3(0, 0, 1));
    }

    public Vertex(Vector3 position, Vector3 normal) {
        this.position = position;
        this.normal = normal;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

}
