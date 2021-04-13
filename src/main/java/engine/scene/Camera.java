package engine.scene;

import engine.core.EngineConfig;
import engine.core.Window;
import engine.math.Ray;
import engine.math.Transform;
import engine.util.BufferUtils;
import engine.util.Constants;
import engine.util.Utils;
import module.buffer.BlockUBO;
import module.camera.CameraRenderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.CallbackI;

import java.nio.FloatBuffer;

public class Camera extends GameObject {

    private final Vector3f yAxis = new Vector3f(0, 1, 0);

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Matrix4f worldMatrix;

    private FloatBuffer buffer;
    private BlockUBO ubo;
    private final int bufferSize = Float.BYTES * 2 * (4 * 4);

    private CameraController controller;

    public Camera() {
        this(new Vector3f(0, 0, 0), 0, 0, 0);
    }

    public Camera(Vector3f position, float pitch, float yaw) {
        this(position, pitch, yaw, 0);
    }

    public Camera(Vector3f position, float pitch, float yaw, float roll) {
        super();
        getLocalTransform().translate(position);
        getLocalTransform().setRotation(pitch, yaw, roll);
    }

    @Override
    public void __init__() {
        getComponents().put(Constants.RENDERER_COMPONENT, new CameraRenderer());

        this.ubo = new BlockUBO();
        this.ubo.setBindingIndex(Constants.CAMERA_UBO_BINDING_INDEX);
        this.ubo.allocate(bufferSize);
        this.ubo.bindBufferBase();
        this.buffer = BufferUtils.createFloatBuffer(bufferSize);

        this.projectionMatrix = calculateProjectionMatrix();
        this.viewMatrix = calculateViewMatrix();
        this.worldMatrix = calculateWorldMatrix();
    }

    @Override
    public void __update__(double delta) {
        controller.update(delta);
        viewMatrix = calculateViewMatrix();
        worldMatrix = calculateWorldMatrix();
        updateUBO();
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        buffer.clear();
        ubo.cleanUp();
    }

    public Matrix4f calculateProjectionMatrix() {
        return Utils.generateProjectionMatrix(EngineConfig.getFov(), EngineConfig.getZ_near(), EngineConfig.getZ_far());
    }

    private Matrix4f calculateViewMatrix() {
        Matrix4f view = getLocalTransform().getViewMatrix();
        Matrix4f view1 = getWorldTransform().getViewMatrix();
        Matrix4f temp = new Matrix4f();
        view1.mul(view, temp);
        return temp;
    }

    private Matrix4f calculateWorldMatrix() {
        Matrix4f world = getLocalTransform().getWorldMatrix();
        Matrix4f world1 = getWorldTransform().getWorldMatrix();
        Matrix4f temp = new Matrix4f();
        world1.mul(world, temp);
        return temp;
    }

    private void updateUBO() {
        buffer.clear();
        buffer.put(BufferUtils.createFlippedBuffer(projectionMatrix));
        buffer.put(BufferUtils.createFlippedBuffer(viewMatrix));
        ubo.updateData(buffer, bufferSize);
    }

    public void lookTo(Vector3f direction) {
        getLocalTransform().getRotation().toAngles(direction);
    }

    public void lookAt(Vector3f point) {
        Vector3f direction = new Vector3f(getLocalTransform().getTranslation()).sub(point).normalize();
        lookTo(direction);
    }

    public Ray getRay(Vector2f mousePosition) {
        float x = (2.0f * mousePosition.x) / Window.width - 1.0f;
        float y = 1.0f - (2.0f * mousePosition.y) / Window.height;

        Vector4f ray_clip = new Vector4f(x, y, -1f, 1f);
        Vector4f ray_eye = new Vector4f();
        Vector4f ray_world = new Vector4f();

        ray_clip.mul(new Matrix4f(projectionMatrix).invert(), ray_eye);
        ray_eye.z = -1f;
        ray_eye.w = 0f;

        ray_eye.mul(new Matrix4f(viewMatrix).invert(), ray_world);
        ray_world.normalize();

        Vector3f ray_origin = getPosition();
        Vector3f ray_dir = new Vector3f(ray_world.x, ray_world.y, ray_world.z);

        return new Ray(ray_origin, ray_dir);
    }

    public Vector3f getPosition() {
        Vector3f res = new Vector3f();
        getWorldMatrix().getTranslation(res);
        return res;
    }

    public Vector3f forward() {
        Vector4f res = new Vector4f();
        getViewMatrix().getColumn(2, res);
        return new Vector3f(res.x, res.y, res.z).normalize();
    }

    public Vector3f up() {
        Vector4f res = new Vector4f();
        getViewMatrix().getColumn(1, res);
        return new Vector3f(res.x, res.y, res.z).normalize();
    }

    public Vector3f right() {
        Vector4f res = new Vector4f();
        getViewMatrix().getColumn(0, res);
        return new Vector3f(res.x, res.y, res.z).normalize();
    }

    public void setController(CameraController controller) {
        this.controller = controller;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getWorldMatrix() {
        return worldMatrix;
    }

}
