package module.camera;

import engine.core.EngineConfig;
import engine.core.Input;
import engine.core.Window;
import engine.math.Plane;
import engine.renderer.Default;
import engine.renderer.PolygonModeLine;
import engine.renderer.RenderConfig;
import engine.scene.Camera;
import engine.scene.Component;
import engine.util.Debug;
import module.Color4f;
import module.buffer.ArrayVBO;
import module.shader.ArrayShader;
import module.shader.GenericShader;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Frustum extends Component {

    private Camera camera;
    private boolean lock;

    private Plane planes[];
    private Vector3f vertices[];
    private ArrayVBO vbo;

    public Frustum() {
        this.lock = false;
        this.planes = new Plane[6];
        this.vertices = new Vector3f[24];
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
            drawWire();
        }
    }

    private void updatePlanes() {
        Vector3f position = camera.getPosition();
        Vector3f forward = camera.forward();
        Vector3f up = camera.up();
        Vector3f right = camera.right();
        float z_near = EngineConfig.getZ_near();
        float z_far = EngineConfig.getZ_far();
        int fov = EngineConfig.getFov();

        float normHalfWidth = (float) Math.tan(fov);
        float aspectRatio = Window.getAspectRatio();

        float nearHW = normHalfWidth * z_near;
        float nearHH = nearHW / aspectRatio;
        float farHW = normHalfWidth * z_far * 0.5f;
        float farHH = farHW / aspectRatio;

        Vector3f nCenter = new Vector3f(position).add(new Vector3f(forward).mul(z_near));
        Vector3f fCenter = new Vector3f(position).add(new Vector3f(forward).mul(z_far));

        Vector3f na = getCorner(nCenter, nearHH, -nearHW, up, right);
        Vector3f nb = getCorner(nCenter, nearHH, nearHW, up, right);
        Vector3f nc = getCorner(nCenter, -nearHH, -nearHW, up, right);
        Vector3f nd = getCorner(nCenter, -nearHH, nearHW, up, right);

        Vector3f fa = getCorner(fCenter, farHH, -farHW, up, right);
        Vector3f fb = getCorner(fCenter, farHH, farHW, up, right);
        Vector3f fc = getCorner(fCenter, -farHH, -farHW, up, right);
        Vector3f fd = getCorner(fCenter, -farHH, farHW, up, right);

        planes[0] = new Plane(na, nb, nc); // near
        planes[1] = new Plane(fb, fa, fd); // far
        planes[2] = new Plane(fa, na, fc); // left
        planes[3] = new Plane(nb, fb, nd); // right
        planes[4] = new Plane(fa, fb, na); // top
        planes[5] = new Plane(nc, nd, fc); // bottom

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

        updateVBO();
    }

    private void updateVBO() {
        if (vbo == null)
            this.vbo = new ArrayVBO(vertices);
        vbo.addData(vertices);
    }

    private Vector3f getCorner(Vector3f center, float HH, float HW, Vector3f up, Vector3f right) {
        Vector3f ret = new Vector3f(center).add(new Vector3f(up).mul(HH)).add(new Vector3f(right).mul(HW));
        return ret;
    }

    private void drawWire() {
        RenderConfig config = new Default();

        ArrayShader.getInstance().bind();
        config.enable();
        ArrayShader.getInstance().updateUniforms(Color4f.YELLOW);
        vbo.render();
        config.disable();
        ArrayShader.getInstance().bind();
    }

    /*
    public boolean contains(Vector3f point, FrustumPlane plane) {
        float dot = plane.getN().dot(new Vector3f(point).add(new Vector3f(plane.getD()).mul(-1f)));
        boolean ret = dot < 0 ? false : true;
        return ret;
    }

    public boolean contains(Vector3f point) {
        for (int i = 0; i < 6; i++) {
            FrustumPlane plane = planes[i];
            if (!contains(point, plane)) {
                return false;
            }
        }
        return true;
    }

    public FrustumType containsTriangle(Vector3f a, Vector3f b, Vector3f c) {
        FrustumType ret = FrustumType.CONTAINS;
        for (int i = 0; i < 6; i++) {
            FrustumPlane plane = planes[i];
            int reject = 0;
            if (!contains(a, plane))
                reject++;
            if (!contains(b, plane))
                reject++;
            if (!contains(c, plane))
                reject++;
            if (reject == 3) {
                return FrustumType.OUTSIDE;
            } else if (reject > 0) {
                ret = FrustumType.INTERSECT;
            }
        }
        return ret;
    }

    public Vector3f getTranslationFrom(GameObject object) {
        Vector3f ret = new Vector3f(position).add(new Vector3f(object.getWorldTransform().getTranslation()).mul(-1f));
        return ret;
    }
    */
}
