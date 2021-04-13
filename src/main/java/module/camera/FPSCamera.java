package module.camera;

import engine.core.Input;
import engine.math.EulerAngle;
import engine.scene.Camera;
import engine.scene.CameraController;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class FPSCamera implements CameraController {

    private Camera camera;

    private float moveSpeed = 20f;
    private float moveTime = 6f;

    private float rotateSpeed = 35f;
    private float rotateTime = 10f;

    private Vector3f nPos;
    private EulerAngle nAngle;

    public FPSCamera(Camera camera) {
        this.camera = camera;
        this.nPos = new Vector3f(camera.getLocalTransform().getTranslation());
        this.nAngle = new EulerAngle(camera.getLocalTransform().getRotation());
    }

    @Override
    public void update(double delta) {
        float moveAmount = (float) (moveSpeed * delta);
        float moveDelay = (float) (moveTime * delta);

        float rotateAmount = (float) (rotateSpeed * delta);
        float rotateDelay = (float) (rotateTime * delta);

        if (Input.isKeyHold(GLFW.GLFW_KEY_W)) {
            move(nPos, camera.getLocalTransform().forward(), moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_S)) {
            move(nPos, camera.getLocalTransform().forward(), -moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_A)) {
            move(nPos,  camera.getLocalTransform().right(), -moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_D)) {
            move(nPos,  camera.getLocalTransform().right(), +moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_LEFT_CONTROL)) {
            move(nPos,  new Vector3f(0, 1, 0), -moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            move(nPos, new Vector3f(0, 1, 0), +moveAmount);
        }

        if (Input.isButtonHold(1)) {
            rotate(nAngle, Input.getDispVec().y * -rotateAmount, Input.getDispVec().x * rotateAmount);
        }

        camera.getLocalTransform().moveSmooth(nPos, moveDelay);
        camera.getLocalTransform().rotateSmooth(nAngle, rotateDelay);
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
