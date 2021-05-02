package module.gui;

import engine.gl.VBO;
import engine.util.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class UiVBO implements VBO {

    public int vaoId;
    public int vboId;
    private int size;

    private float[] positions = {0f, 1f, 1f, 1f, 0f, 0f, 1f, 0};

    public UiVBO() {
        this.vaoId = glGenVertexArrays();
        this.vboId = glGenBuffers();
        this.size = positions.length / 2;
        addData();
    }

    private void addData() {
        FloatBuffer verticesBuffer;
        glBindVertexArray(vaoId);
        verticesBuffer = BufferUtils.createFlippedBuffer(positions);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(verticesBuffer);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void render() {
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, getIndicesCount());
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
}
