package engine.scene;

import engine.core.EngineConfig;
import engine.core.Window;
import engine.math.*;
import engine.util.BufferUtils;
import engine.util.Constants;
import module.buffer.BlockUBO;

import java.nio.FloatBuffer;

public class Camera extends GameObject {

    private static Camera instance;

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    private Matrix4 projectionMatrix;
    private Matrix4 viewMatrix;
    private Matrix4 worldMatrix;

    private FloatBuffer buffer;
    private BlockUBO ubo;
    private final int bufferSize = Float.BYTES * 2 * (4 * 4);

    private CameraController controller;

    public Camera() {
        this(new Vector3(0, 0, 0), 0, 0);
    }

    public Camera(Vector3 position, float pitch, float yaw) {
        this(position, pitch, yaw, 0);
    }

    public Camera(Vector3 position, float pitch, float yaw, float roll) {
        super();
        getWorldTransform().translateTo(position);
        getWorldTransform().rotateTo(pitch, yaw, roll);
        instance = this;
    }

    @Override
    public void __init__() {
        this.ubo = new BlockUBO();
        this.ubo.setBindingIndex(Constants.CAMERA_UBO_BINDING_INDEX);
        this.ubo.allocate(bufferSize);
        this.ubo.bindBufferBase();
        this.buffer = BufferUtils.createFloatBuffer(bufferSize);

        this.projectionMatrix = processProjectionMatrix();
        this.viewMatrix = processViewMatrix();
        this.worldMatrix = processWorldMatrix();
    }

    @Override
    public void __update__(double delta) {
        this.controller.update(delta);
        this.viewMatrix = processViewMatrix();
        this.worldMatrix = processWorldMatrix();
        updateUBO();
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        this.buffer.clear();
        this.ubo.cleanUp();
    }

    public Vector3 forward() {
        return up().cross(right());
    }

    public Vector3 up() {
        float x = viewMatrix.get(1, 0);
        float y = viewMatrix.get(1, 1);
        float z = viewMatrix.get(1, 2);
        return new Vector3(x, y, z).normalize();
    }

    public Vector3 right() {
        float x = viewMatrix.get(0, 0);
        float y = viewMatrix.get(0, 1);
        float z = viewMatrix.get(0, 2);
        return new Vector3(x, y, z).normalize();
    }

    public Vector3 position() {
        return worldMatrix.toTranslationVector();
    }

    public Matrix4 processProjectionMatrix() {
        return Matrix4.perspective((float) EngineConfig.getFov(), Window.getAspectRatio(), EngineConfig.getZ_near(), EngineConfig.getZ_far());
    }

    private Matrix4 processViewMatrix() {
        Matrix4 local = getLocalTransform().getViewMatrix();
        Matrix4 world = getWorldTransform().getViewMatrix();
        return local.mul(world);
    }

    private Matrix4 processWorldMatrix() {
        Matrix4 local = getLocalTransform().getWorldMatrix();
        Matrix4 world = getWorldTransform().getWorldMatrix();
        return world.mul(local);
    }

    private void updateUBO() {
        this.buffer.clear();
        this.projectionMatrix.fillFloatBuffer(buffer);
        this.viewMatrix.fillFloatBuffer(buffer);
        this.ubo.updateData(buffer, bufferSize);
    }

    public Ray getRay(Vector2 mousePosition) {
        float x = (2.0f * mousePosition.x) / Window.width - 1.0f;
        float y = 1.0f - (2.0f * mousePosition.y) / Window.height;

        Vector4 ray_clip = new Vector4(x, y, -1.0f, 1.0f);

        Vector4 ray_eye = projectionMatrix.invert().mul(ray_clip);
        ray_eye = new Vector4(ray_eye.x, ray_eye.y, -1.0f, 0.0f);

        Vector4 ray_world = viewMatrix.invert().mul(ray_eye).normalize();

        Vector3 ray_origin = position();
        Vector3 ray_dir = new Vector3(ray_world.x, ray_world.y, ray_world.z);

        return new Ray(ray_origin, ray_dir);
    }

    public void setController(CameraController controller) {
        this.controller = controller;
    }

    public Matrix4 getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4 getViewMatrix() {
        return viewMatrix;
    }

}
