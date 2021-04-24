package module.buffer;

import engine.gl.VBO;
import engine.math.Vector3;
import engine.util.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class ArrayVBO implements VBO {

    private int vaoId;
    private int vboId;
    private int size;

    public ArrayVBO(Vector3[] vertices) {
        this.vaoId = glGenVertexArrays();
        this.vboId = glGenBuffers();
        addData(vertices);
    }

    public void addData(Vector3[] vertices) {
        this.size = vertices.length;

        glBindVertexArray(vaoId);

        FloatBuffer buffer = createFlippedBuffer(vertices);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
        MemoryUtil.memFree(buffer);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 3, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void render() {
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glDrawArrays(GL_LINES, 0, getIndicesCount());
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    @Override
    public void cleanUp() {
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);
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

    public static FloatBuffer createFlippedBuffer(Vector3[] vertices) {
        FloatBuffer ret = BufferUtils.createFloatBuffer(vertices.length * 3);
        for (int i = 0; i < vertices.length; i++) {
            ret.put(vertices[i].x);
            ret.put(vertices[i].y);
            ret.put(vertices[i].z);
        }
        ret.flip();
        return ret;
    }
}
