package instance;

import engine.math.vector.Vector3;
import engine.model.Mesh;
import engine.model.Vertex;
import engine.renderer.Default;
import engine.renderer.Renderer;
import engine.scene.GameObject;
import engine.util.Constants;
import engine.util.Color4;
import module.buffer.MeshVBO;
import module.shader.GenericShader;

public class Terrain extends GameObject {

    private int size;

    public Terrain(int size, Color4 color) {
        this.size = size;
        setColor(color);
    }

    @Override
    public void __init__() {
        Mesh mesh = createTerrainMesh();
        setVbo(new MeshVBO(mesh));

        getWorldTransform().translateTo(-size / 2, 0.01f, -size / 2);

        Renderer renderer = new Renderer(GenericShader.getInstance(), new Default());
        renderer.setParent(this);
        renderer.init();
        getComponents().put(Constants.RENDERER_COMPONENT, renderer);
    }

    @Override
    public void __update__(double delta) {

    }

    public Mesh createTerrainMesh() {
        Vertex[] vertices = new Vertex[size * size];
        int count = 0;
        for (int z = 0; z < size; z++) {
            for (int x = 0; x < size; x++) {
                vertices[count++] = new Vertex(new Vector3(x, 0, z));
            }
        }
        int[] indices = new int[6 * (size - 1) * (size - 1)];
        int pointer = 0;
        for (int col = 0; col < size - 1; col++) {
            for (int row = 0; row < size - 1; row++) {
                int bottomLeft = (row * size) + col;
                int bottomRight = bottomLeft + 1;
                int topLeft = ((row + 1) * size) + col;
                int topRight = topLeft + 1;

                pointer = storeQuadIndices(indices, pointer, bottomLeft, bottomRight, topLeft, topRight);
            }
        }
        return new Mesh(vertices, indices);
    }

    private int storeQuadIndices(int[] indices, int pointer, int bottomLeft, int bottomRight, int topLeft, int topRight) {
        indices[pointer++] = topLeft;
        indices[pointer++] = bottomLeft;
        indices[pointer++] = bottomRight;

        indices[pointer++] = topLeft;
        indices[pointer++] = bottomRight;
        indices[pointer++] = topRight;
        return pointer;
    }
}
