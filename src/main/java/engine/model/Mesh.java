package engine.model;

import engine.math.vector.Vector3;
import engine.util.OBJLoader;
import module.bounding.BoundingAAB;
import module.bounding.BoundingVolume;

public class Mesh {

    public static final Mesh CUBE = OBJLoader.loadMesh("", "cube.obj");

    private Vertex[] vertices;
    private int[] indices;
    private BoundingVolume boundingVolume;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
        setBoundingVolume(new BoundingAAB());
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

    public BoundingVolume getBoundingVolume() {
        return boundingVolume;
    }

    public void setBoundingVolume(BoundingVolume boundingVolume) {
        this.boundingVolume = boundingVolume;
        this.boundingVolume.generateFromPoints(getPositions());
    }

    public Vector3[] getPositions() {
        Vector3[] res = new Vector3[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            res[i] = vertices[i].getPosition();
        }
        return res;
    }

}
