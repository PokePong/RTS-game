package module.instanced;

import engine.model.Mesh;
import engine.scene.GameObject;
import engine.scene.Node;
import engine.util.BufferUtils;
import module.Color4;
import module.buffer.MeshVBO;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL33.*;

public class InstancedMeshVBO extends MeshVBO {

    public int instanceId;

    private int size;
    private int instancesCount;

    public InstancedMeshVBO(Mesh mesh) {
        super(mesh);
        this.instanceId = glGenBuffers();
    }

    public void uploadInstancedData(List<GameObject> instances) {
        this.instancesCount = instances.size();

        glBindVertexArray(vaoId);

        FloatBuffer buffer = createFlippedDataBuffer(instances);
        glBindBuffer(GL_ARRAY_BUFFER, instanceId);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
        MemoryUtil.memFree(buffer);

        int sizeVec4 = 4 * Float.BYTES;
        int sizeData = (4 * 4 + 4) * Float.BYTES;
        glVertexAttribPointer(2, 4, GL_FLOAT, false, sizeData, 0 * sizeVec4);
        glVertexAttribPointer(3, 4, GL_FLOAT, false, sizeData, 1 * sizeVec4);
        glVertexAttribPointer(4, 4, GL_FLOAT, false, sizeData, 2 * sizeVec4);
        glVertexAttribPointer(5, 4, GL_FLOAT, false, sizeData, 3 * sizeVec4);
        glVertexAttribPointer(6, 4, GL_FLOAT, false, sizeData, 4 * sizeVec4);
        glVertexAttribDivisor(2, 1);
        glVertexAttribDivisor(3, 1);
        glVertexAttribDivisor(4, 1);
        glVertexAttribDivisor(5, 1);
        glVertexAttribDivisor(6, 1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void render() {
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);
        glEnableVertexAttribArray(4);
        glEnableVertexAttribArray(5);
        glEnableVertexAttribArray(6);
        glDrawElementsInstanced(GL_TRIANGLES, getIndicesCount(), GL_UNSIGNED_INT, 0, getInstancesCount());
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
        glDisableVertexAttribArray(4);
        glDisableVertexAttribArray(5);
        glDisableVertexAttribArray(6);
        glBindVertexArray(0);
    }

    @Override
    public void cleanUp() {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
        glDisableVertexAttribArray(4);
        glDisableVertexAttribArray(5);
        glDisableVertexAttribArray(6);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);
        glDeleteBuffers(iboId);
        glDeleteBuffers(instanceId);
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    public int getInstancesCount() {
        return instancesCount;
    }

    public FloatBuffer createFlippedDataBuffer(List<GameObject> instances) {
        int dataSize = 4 * 4 + 4;
        int bufferSize = instancesCount * dataSize;
        FloatBuffer buffer = BufferUtils.createFloatBuffer(bufferSize);
        for (int i = 0; i < instancesCount; i++) {
            GameObject object = instances.get(i);
            Color4 color = object.getColor();
            object.getWorldTransform().getWorldMatrix().fillFloatBuffer(buffer);
            color.toVector4().fillFloatBuffer(buffer);
        }
        buffer.flip();
        return buffer;
    }


}
