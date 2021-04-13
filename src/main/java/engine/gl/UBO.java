package engine.gl;

import java.nio.FloatBuffer;

public interface UBO {

    public void allocate(int size);

    public void updateData(FloatBuffer buffer, int size);

    public void bind();

    public void unbind();

    public void bindBufferBase();

    public void cleanUp();
}
