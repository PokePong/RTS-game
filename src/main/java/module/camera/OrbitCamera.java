package module.camera;

import engine.core.Input;
import engine.math.EulerAngle;
import engine.math.Plane;
import engine.math.Ray;
import engine.math.Transform;
import engine.scene.Camera;
import engine.scene.CameraController;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class OrbitCamera implements CameraController {

    private Camera camera;

    private float moveSpeed = 5f;
    private float rotateSpeed = 20f;
    private float zoomSpeed = 150f;

    private float moveTime = 6f;
    private float rotateTime = 20f;
    private float zoomTime = 2f;

    private Vector3f nPos;
    private EulerAngle nAngle;
    private Vector3f nZoom;

    public OrbitCamera(Camera camera, Transform target) {
        this.camera = camera;
        this.nPos = new Vector3f(target.getTranslation());
        this.nAngle = new EulerAngle(camera.getWorldTransform().getRotation());
        this.nZoom = new Vector3f(camera.getLocalTransform().getTranslation());

        camera.setWorldTransform(target);
        camera.lookAt(camera.getWorldTransform().getTranslation());
    }

    @Override
    public void update(double delta) {
        float moveAmount = (float) (moveSpeed * delta);
        float rotateAmount = (float) (rotateSpeed * delta);
        float zoomAmount = (float) (zoomSpeed * delta);

        float moveDelay = (float) (moveTime * delta);
        float rotateDelay = (float) (rotateTime * delta);
        float zoomDelay = (float) (zoomTime * delta);

        Transform parent = camera.getWorldTransform();

        if (Input.isKeyHold(GLFW.GLFW_KEY_W)) {
            move(nPos, parent.forward(), moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_S)) {
            move(nPos, parent.forward(), -moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_A)) {
            move(nPos, parent.right(), -moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_D)) {
            move(nPos, parent.right(), moveAmount);
        }

        if (Input.isButtonDown(0)) {
            Ray ray = camera.getRay(Input.getCursorPosition());
            Plane plane = new Plane(new Vector3f(0, 1, 0), new Vector3f(0));

            float entry = plane.rayCast(ray);

            Vector3f pointOnPlane = ray.getPoint(entry);
        }

        if (Input.isButtonHold(1)) {
            float dx = Input.getDispVec().x * moveAmount;
            float dy = Input.getDispVec().y * moveAmount;
            move(nPos, parent.right(), -dx);
            move(nPos, parent.forward(), dy);
        }

        if (Input.isButtonHold(2)) {
            float yaw = Input.getDispVec().x * -rotateAmount;
            float pitch = Input.getDispVec().y * rotateAmount;
            rotate(nAngle, pitch, yaw);
        }

        if(Input.getScrollOffSet() != 0) {
            float zoom = Input.getScrollOffSet() * zoomAmount;
            move(nZoom, camera.getLocalTransform().forward(), zoom);
        }

        parent.moveSmooth(nPos, moveDelay);
        parent.rotateSmooth(new EulerAngle(0, nAngle.getYaw(), 0), rotateDelay);
        camera.getLocalTransform().moveSmooth(nZoom, zoomDelay);
    }

    private void move(Vector3f nPos, Vector3f dir, float amount) {
        Vector3f move = new Vector3f(dir).mul(amount);
        nPos.add(move);
    }

    public void rotate(EulerAngle nAngle, float pitchAmount, float yawAmount) {
        nAngle.setPitch(nAngle.getPitch() + pitchAmount);
        nAngle.setYaw(nAngle.getYaw() + yawAmount);
    }

}
