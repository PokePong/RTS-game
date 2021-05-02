package instance;

import engine.core.kernel.Input;
import engine.math.vector.Vector3;
import engine.scene.Component;
import engine.util.Constants;
import engine.util.Debug;
import engine.util.Color4;
import module.bounding.BoundingAAB;
import org.lwjgl.glfw.GLFW;

public class BoundingBox extends Component {

    private Vector3 vertices[];

    private BoundingAAB aabLocal;
    private BoundingAAB boundingAAB;

    public BoundingBox() {
        this.vertices = new Vector3[24];
    }

    @Override
    public void init() {
        this.aabLocal = (BoundingAAB) getParent().getMesh().getBoundingVolume().transform(getLocalTransform());
        this.boundingAAB = (BoundingAAB) aabLocal.transform(getWorldTransform());
        updateVertices();
    }

    @Override
    public void update(double delta) {
        this.boundingAAB = (BoundingAAB) aabLocal.transform(getWorldTransform());
        updateVertices();
    }

    @Override
    public void render() {
        boolean isCull = ((FrustumCulling) getParent().getComponent(Constants.FRUSTUM_CULLING_COMPONENT)).isCull();
        if (Input.isKeyHold(GLFW.GLFW_KEY_B) && !isCull) {
            Debug.drawWire(vertices, Color4.GREEN);
        }
    }

    public void updateVertices() {
        float xExtent = boundingAAB.getxExtent();
        float yExtent = boundingAAB.getyExtent();
        float zExtent = boundingAAB.getzExtent();

        Vector3 na = getCorner(xExtent, yExtent, -zExtent);
        Vector3 nb = getCorner(-xExtent, yExtent, -zExtent);
        Vector3 nc = getCorner(xExtent, -yExtent, -zExtent);
        Vector3 nd = getCorner(-xExtent, -yExtent, -zExtent);

        Vector3 fa = getCorner(xExtent, yExtent, zExtent);
        Vector3 fb = getCorner(-xExtent, yExtent, zExtent);
        Vector3 fc = getCorner(xExtent, -yExtent, zExtent);
        Vector3 fd = getCorner(-xExtent, -yExtent, zExtent);

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

    private Vector3 getCorner(float x, float y, float z) {
        return boundingAAB.getCenter().add(Vector3.UP.mul(y)).add(Vector3.RIGHT.mul(x)).add(Vector3.FORWARD.mul(z));
    }

    public BoundingAAB getBoundingAAB() {
        return boundingAAB;
    }

}
