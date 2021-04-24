package engine.util;

import engine.math.Matrix4;
import engine.math.Vector3;
import engine.math.Vector4;
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

    public static FloatBuffer createFlippedBuffer(Vector3 vector) {
        FloatBuffer buffer = createFloatBuffer(Float.BYTES * 3);
        buffer.put(vector.x);
        buffer.put(vector.y);
        buffer.put(vector.z);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Vector4 vector) {
        FloatBuffer buffer = createFloatBuffer(Float.BYTES * 4);
        buffer.put(vector.x);
        buffer.put(vector.y);
        buffer.put(vector.z);
        buffer.put(vector.w);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Matrix4 matrix) {
        FloatBuffer buffer = createFloatBuffer(4 * 4);
        matrix.fillFloatBuffer(buffer);
        buffer.flip();
        return buffer;
    }

}
