package module.buffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import engine.gl.VBO;
import engine.model.Mesh;
import engine.model.Vertex;
import engine.util.BufferUtils;
import engine.util.OBJLoader;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MeshVBO implements VBO {

    public static final MeshVBO CUBE_VBO = new MeshVBO(Mesh.CUBE);

    public int vaoId;
    public int vboId;
    public int iboId;
    private int size;

    public MeshVBO(Mesh mesh) {
        this.vaoId = glGenVertexArrays();
        this.vboId = glGenBuffers();
        this.iboId = glGenBuffers();
        addData(mesh);
    }

    private void addData(Mesh mesh) {
        this.size = mesh.getIndices().length;

        glBindVertexArray(vaoId);

        FloatBuffer verticesBuffer = createFlippedBuffer(mesh.getVertices());
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(verticesBuffer);

        IntBuffer indicesBuffer = BufferUtils.createFlippedBuffer(mesh.getIndices());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(indicesBuffer);

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.BYTES, 0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, Vertex.BYTES, Float.BYTES * 3);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void render() {
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, getIndicesCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }

    @Override
    public void cleanUp() {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);
        glDeleteBuffers(iboId);
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    @Override
    public int getIndicesCount() {
        return size;
    }

    @Override
    public int getVaoId() {
        return vaoId;
    }

    public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        FloatBuffer ret = BufferUtils.createFloatBuffer(vertices.length * Vertex.FLOATS);
        for (int i = 0; i < vertices.length; i++) {
            ret.put(vertices[i].getPosition().x);
            ret.put(vertices[i].getPosition().y);
            ret.put(vertices[i].getPosition().z);

            ret.put(vertices[i].getNormal().x);
            ret.put(vertices[i].getNormal().y);
            ret.put(vertices[i].getNormal().z);
        }
        ret.flip();
        return ret;
    }

}
