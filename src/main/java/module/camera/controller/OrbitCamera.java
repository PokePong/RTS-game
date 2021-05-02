package module.camera.controller;

import engine.core.kernel.Input;
import engine.math.vector.Quaternion;
import engine.math.Transform;
import engine.math.vector.Vector3;
import engine.scene.Camera;
import engine.scene.CameraController;
import org.lwjgl.glfw.GLFW;

public class OrbitCamera implements CameraController {

    private Camera camera;
    private Transform target;

    private float moveSpeed = 5f;
    private float moveTime = 6f;

    private float rotateSpeed = 20f;
    private float rotateTime = 20f;

    private float zoomSpeed = 150f;
    private float zoomTime = 2f;

    private Vector3 nPos;
    private Quaternion nRot;
    private Vector3 nZoom;


    public OrbitCamera(Camera camera) {
        this(camera, 30, -45);
    }

    public OrbitCamera(Camera camera, float dist, float pitch) {
        this.camera = camera;

        camera.setWorldTransform(new Transform());
        camera.getLocalTransform().translateTo(Vector3.FORWARD.mul(-dist));
        camera.getWorldTransform().rotateTo(pitch, 0, 0);

        this.nPos = new Vector3(camera.getWorldTransform().getTranslation());
        this.nRot = new Quaternion(camera.getWorldTransform().getRotation());
        this.nZoom = new Vector3(camera.getLocalTransform().getTranslation());
    }

    @Override
    public void update(double delta) {
        float moveAmount = (float) (moveSpeed * delta);
        float moveDelay = (float) (moveTime * delta);

        float rotateAmount = (float) (rotateSpeed * delta);
        float rotateDelay = (float) (rotateTime * delta);

        float zoomAmount = (float) (zoomSpeed * delta);
        float zoomDelay = (float) (zoomTime * delta);

        Transform world = camera.getWorldTransform();
        Transform local = camera.getLocalTransform();

        Vector3 p1 = world.forward().project(Vector3.FORWARD);
        Vector3 p2 = world.forward().project(Vector3.RIGHT);
        Vector3 forward = p1.add(p2).normalize();

        if (target == null) {
            if (Input.isKeyHold(GLFW.GLFW_KEY_W)) {
                nPos = move(nPos, forward, moveAmount);
            }

            if (Input.isKeyHold(GLFW.GLFW_KEY_S)) {
                nPos = move(nPos, forward, -moveAmount);
            }

            if (Input.isKeyHold(GLFW.GLFW_KEY_A)) {
                nPos = move(nPos, world.right(), -moveAmount);
            }

            if (Input.isKeyHold(GLFW.GLFW_KEY_D)) {
                nPos = move(nPos, world.right(), moveAmount);
            }

            if (Input.isButtonHold(1)) {
                float dx = Input.getDispVec().x * moveAmount;
                float dy = Input.getDispVec().y * moveAmount;
                nPos = move(nPos, world.right(), -dx);
                nPos = move(nPos, forward, dy);
            }
        } else {
            nPos = target.getTranslation();
            if (Input.isKeyDown(GLFW.GLFW_KEY_L))
                unlockTarget();
        }


        if (Input.isButtonHold(2)) {
            float rotX = Input.getDispVec().x * -rotateAmount;
            float rotY = Input.getDispVec().y * rotateAmount;
            nRot = rotate(nRot, rotX, rotY);
        }

        if (Input.getScrollOffSet() != 0) {
            float zoom = Input.getScrollOffSet() * zoomAmount;
            nZoom = move(nZoom, local.forward(), zoom);
        }

        world.moveSmooth(nPos, moveDelay);
        world.rotateSmooth(nRot, rotateDelay);
        local.moveSmooth(nZoom, zoomDelay);
    }

    private Vector3 move(Vector3 nPos, Vector3 dir, float amount) {
        return nPos.add(dir.mul(amount));
    }

    private Quaternion rotate(Quaternion nRot, float amountX, float amountY) {
        Quaternion qx = Quaternion.eulerAxis(amountX, Vector3.UP);
        Quaternion qy = Quaternion.eulerAxis(amountY, Vector3.RIGHT);
        return qx.mul(nRot).mul(qy);
    }

    public void lockTarget(Transform transform) {
        this.target = transform;
    }

    public void unlockTarget() {
        this.target = null;
    }
}
