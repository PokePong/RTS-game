package instance;

import engine.math.Transform;
import engine.renderer.Renderer;
import engine.scene.Camera;
import engine.scene.Component;
import engine.util.Constants;
import module.camera.component.Frustum;
import module.instanced.InstancedCluster;

public class FrustumCulling extends Component {

    private Frustum frustum;
    private Transform transform;
    private boolean cull;

    public FrustumCulling() {
        super();
        this.cull = true;
    }

    @Override
    public void init() {
        this.frustum = (Frustum) Camera.getInstance().getComponent("Frustum");
        this.transform = getParent().getWorldTransform();
    }

    @Override
    public void update(double delta) {
        processCulling();
        if (getParent().isInstanced()) {
            ((InstancedCluster) getParent().getParent()).processCullingInstance(isCull(), getParent());
        } else {
            ((Renderer) getParent().getComponent(Constants.RENDERER_COMPONENT)).setRender(!isCull());
        }
    }

    private void processCulling() {
        if (frustum.contains(transform.getTranslation())) {
            this.cull = false;
        } else {
            this.cull = true;
        }
    }

    public boolean isCull() {
        return cull;
    }

}
