package engine.util;

import engine.model.Vertex;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {

    public static FloatBuffer createFloatBuffer(int size) {
        return MemoryUtil.memAllocFloat(size);
    }

    public static IntBuffer createIntBuffer(int size) {
        return MemoryUtil.memAllocInt(size);
    }

    public static FloatBuffer createFlippedBuffer(float... values) {
        FloatBuffer ret = createFloatBuffer(values.length);
        ret.put(values);
        ret.flip();
        return ret;
    }

    public static IntBuffer createFlippedBuffer(int... values) {
        IntBuffer ret = createIntBuffer(values.length);
        ret.put(values);
        ret.flip();
        return ret;
    }

    public static FloatBuffer createFlippedBuffer(Vector3f vector) {
        FloatBuffer buffer = createFloatBuffer(Float.BYTES * 3);
        buffer.put(vector.x);
        buffer.put(vector.y);
        buffer.put(vector.z);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Vector4f vector) {
        FloatBuffer buffer = createFloatBuffer(Float.BYTES * 4);
        buffer.put(vector.x);
        buffer.put(vector.y);
        buffer.put(vector.z);
        buffer.put(vector.w);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Matrix4f matrix) {
        FloatBuffer buffer = createFloatBuffer(4 * 4);
        matrix.get(buffer);
        return buffer;
    }

}
