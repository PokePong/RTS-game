package engine.model;

import engine.util.OBJLoader;
import org.joml.Vector4f;

public class Mesh {

    public static final Mesh CUBE = OBJLoader.loadMesh("", "cube.obj");

    private Vertex[] vertices;
    private int[] indices;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public void setVertices(Vertex[] vertices) {
        this.vertices = vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

}
