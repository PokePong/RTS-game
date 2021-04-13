package module.buffer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;

import engine.gl.UBO;

public class BlockUBO implements UBO {

    private int ubo;
    private int bindingIndex;

    public BlockUBO() {
        this.ubo = glGenBuffers();
    }

    @Override
    public void allocate(int size) {
        bind();
        glBufferData(GL_UNIFORM_BUFFER, size, GL_DYNAMIC_DRAW);
        unbind();
    }

    @Override
    public void updateData(FloatBuffer buffer, int size) {
        glBindBuffer(GL_UNIFORM_BUFFER, ubo);
        ByteBuffer mappedBuffer = glMapBuffer(GL_UNIFORM_BUFFER, GL_READ_WRITE, size, null);
        mappedBuffer.clear();
        for (int i = 0; i < size / Float.BYTES; i++) {
            mappedBuffer.putFloat(buffer.get(i));
        }
        mappedBuffer.flip();
        glUnmapBuffer(GL_UNIFORM_BUFFER);
    }

    @Override
    public void bind() {
        glBindBuffer(GL_UNIFORM_BUFFER, ubo);
    }

    @Override
    public void unbind() {
        glBindBuffer(GL_UNIFORM_BUFFER, 0);
    }

    @Override
    public void bindBufferBase() {
        glBindBufferBase(GL_UNIFORM_BUFFER, bindingIndex, ubo);
    }

    public int getBindingIndex() {
        return bindingIndex;
    }

    public void setBindingIndex(int bindingIndex) {
        this.bindingIndex = bindingIndex;
    }

    @Override
    public void cleanUp() {
        unbind();
        glDeleteBuffers(ubo);

    }

}
