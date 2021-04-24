package instance;

import engine.core.Input;
import engine.math.Transform;
import engine.renderer.PolygonModeLine;
import engine.renderer.RenderConfig;
import engine.scene.Camera;
import engine.scene.Component;
import engine.util.Debug;
import module.Color4;
import module.shader.GenericShader;
import org.lwjgl.glfw.GLFW;

public class BoundingBox extends Component {

    private float width;
    private float height;
    private float depth;

    private Transform transform;
    private Color4 color = Color4.GREEN.brighter();

    public BoundingBox(float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.transform = new Transform();
    }

    public void init() {
        transform.translateTo(getParent().getLocalTransform().getTranslation());
        transform.translate((1 - width) / 2, (1 - height) / 2, (1 - depth) / 2);
        transform.scaleTo(width, height, depth);
    }

    public void update(double delta) {

    }

    public void render() {
        if (Input.isKeyHold(GLFW.GLFW_KEY_B)) {
            drawBox();
        }
    }

    public void cleanUp() {

    }

    private void drawBox() {
        RenderConfig config = new PolygonModeLine();

        GenericShader.getInstance().bind();
        config.enable();
        GenericShader.getInstance().updateUniforms(transform, getParent().getWorldTransform(), color);
        getParent().getVbo().render();
        config.disable();
        GenericShader.getInstance().bind();
    }

}
