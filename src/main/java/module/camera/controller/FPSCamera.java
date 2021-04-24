package module.camera.controller;

import engine.core.Input;
import engine.math.*;
import engine.scene.Camera;
import engine.scene.CameraController;
import org.lwjgl.glfw.GLFW;

public class FPSCamera implements CameraController {

    private Camera camera;

    private float moveSpeed = 10f;
    private float moveTime = 6f;

    private float rotateSpeed = 35f;
    private float rotateTime = 20f;

    private Vector3 nPos;
    private Quaternion nRot;

    public FPSCamera(Camera camera) {
        this.camera = camera;
        this.nPos = new Vector3(camera.getWorldTransform().getTranslation());
        this.nRot = new Quaternion(camera.getWorldTransform().getRotation());
    }

    @Override
    public void update(double delta) {
        float moveAmount = (float) (moveSpeed * delta);
        float moveDelay = (float) (moveTime * delta);

        float rotateAmount = (float) (rotateSpeed * delta);
        float rotateDelay = (float) (rotateTime * delta);

        Transform world = camera.getWorldTransform();

        if (Input.isKeyHold(GLFW.GLFW_KEY_W)) {
            nPos = move(nPos, camera.forward(), moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_S)) {
            nPos = move(nPos, camera.forward(), -moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_A)) {
            nPos = move(nPos, camera.right(), -moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_D)) {
            nPos = move(nPos, camera.right(), moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_LEFT_CONTROL)) {
            nPos = move(nPos, camera.up(), -moveAmount);
        }

        if (Input.isKeyHold(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            nPos = move(nPos, camera.up(), moveAmount);
        }

        if (Input.isButtonHold(1)) {
            float rotX = Input.getDispVec().x * -rotateAmount;
            float rotY = Input.getDispVec().y * -rotateAmount;
            nRot = rotate(nRot, rotX, rotY);
        }

        if (Input.isButtonDown(0)) {
            Ray ray = camera.getRay(Input.getCursorPosition());
            Plane plane = new Plane(new Vector3(0, 1, 0), 0);

            float entry = plane.rayCast(ray);

            Vector3 pointOnPlane = ray.getPoint(entry);
        }

        world.moveSmooth(nPos, moveDelay);
        world.rotateSmooth(nRot, rotateDelay);
    }

    private Vector3 move(Vector3 nPos, Vector3 dir, float amount) {
        return nPos.add(dir.mul(amount));
    }

    private Quaternion rotate(Quaternion nRot, float amountX, float amountY) {
        Quaternion qx = Quaternion.eulerAxis(amountX, Vector3.UP);
        Quaternion qy = Quaternion.eulerAxis(amountY, Vector3.RIGHT);
        return qx.mul(nRot).mul(qy);
    }

}
