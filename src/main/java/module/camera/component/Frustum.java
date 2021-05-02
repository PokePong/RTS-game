package module.camera.component;

import engine.core.kernel.EngineConfig;
import engine.core.kernel.Input;
import engine.core.Window;
import engine.math.FastMath;
import engine.math.geometry.Plane;
import engine.math.vector.Vector3;
import engine.scene.Camera;
import engine.scene.Component;
import engine.util.Debug;
import engine.util.Color4;
import module.bounding.BoundingAAB;
import org.lwjgl.glfw.GLFW;

public class Frustum extends Component {

    private final float distance = 25f;

    private Camera camera;
    private boolean lock;

    private Plane planes[];
    private Vector3 vertices[];

    public Frustum() {
        this.lock = false;
        this.planes = new Plane[6];
        this.vertices = new Vector3[24];
    }

    @Override
    public void init() {
        this.camera = (Camera) getParent();
        updatePlanes();
    }

    @Override
    public void update(double delta) {
        if (Input.isKeyDown(GLFW.GLFW_KEY_C)) {
            lock = !lock;
        }
        if (!lock) {
            updatePlanes();
        }
    }

    @Override
    public void render() {
        if (lock) {
            Debug.drawWire(vertices, Color4.YELLOW);
        }
    }

    private void updatePlanes() {
        Vector3 position = camera.position();
        Vector3 forward = camera.forward();
        Vector3 up = camera.up();
        Vector3 right = camera.right();

        float z_near = EngineConfig.getZ_near();
        float z_far = EngineConfig.getZ_far();
        int fov = EngineConfig.getFov();

        float normHalfWidth = FastMath.tan((float) Math.toRadians(fov * 0.5f));
        float aspectRatio = Window.getAspectRatio();

        float nearHH = normHalfWidth * z_near;
        float nearHW = nearHH * aspectRatio;
        float farHH = normHalfWidth * z_far;
        float farHW = farHH * aspectRatio;

        Vector3 nCenter = position.add(forward.mul(z_near));
        Vector3 fCenter = position.add(forward.mul(z_far * 0.5f));

        Vector3 na = getCorner(nCenter, nearHH, -nearHW, up, right);
        Vector3 nb = getCorner(nCenter, nearHH, nearHW, up, right);
        Vector3 nc = getCorner(nCenter, -nearHH, -nearHW, up, right);
        Vector3 nd = getCorner(nCenter, -nearHH, nearHW, up, right);

        Vector3 fa = getCorner(fCenter, farHH, -farHW, up, right);
        Vector3 fb = getCorner(fCenter, farHH, farHW, up, right);
        Vector3 fc = getCorner(fCenter, -farHH, -farHW, up, right);
        Vector3 fd = getCorner(fCenter, -farHH, farHW, up, right);

        planes[0] = new Plane(na, nb, nc); // near
        planes[1] = new Plane(fb, fa, fd); // far
        planes[2] = new Plane(position, nc, na); // left
        planes[3] = new Plane(position, nb, nd); // right
        planes[4] = new Plane(position, na, nb); // top
        planes[5] = new Plane(position, nd, nc); // bottom

        fa = processPoint(na, distance);
        fb = processPoint(nb, distance);
        fc = processPoint(nc, distance);
        fd = processPoint(nd, distance);

        vertices[0] = na;
        vertices[1] = nb;
        vertices[2] = nd;
        vertices[3] = nb;
        vertices[4] = nd;
        vertices[5] = nc;
        vertices[6] = na;
        vertices[7] = nc;

        vertices[8] = fa;
        vertices[9] = fb;
        vertices[10] = fd;
        vertices[11] = fb;
        vertices[12] = fd;
        vertices[13] = fc;
        vertices[14] = fa;
        vertices[15] = fc;

        vertices[16] = na;
        vertices[17] = fa;
        vertices[18] = nb;
        vertices[19] = fb;
        vertices[20] = nc;
        vertices[21] = fc;
        vertices[22] = nd;
        vertices[23] = fd;

    }

    private Vector3 getCorner(Vector3 center, float HH, float HW, Vector3 up, Vector3 right) {
        return center.add(up.mul(HH)).add(right.mul(HW));
    }

    private Vector3 processPoint(Vector3 point, float distance) {
        Vector3 dir = point.sub(camera.position()).normalize();
        return point.add(dir.mul(distance));
    }

    private boolean contains(Plane plane, Vector3 point) {
        if (plane.getDistanceToPoint(point) > 0)
            return true;
        return false;
    }

    public boolean contains(Vector3 point) {
        for (int i = 0; i < 6; i++) {
            if (!contains(planes[i], point)) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(BoundingAAB aab) {
        Plane plane;
        for (int i = 0; i < 6; i++) {
            Plane.Side side = aab.whichSide(planes[i]);
            switch (side) {
                case NEGATIVE:
                    return false;
                case POSITIVE:
                    break;
                case NONE:
                    break;
            }
        }
        return true;
    }

}
