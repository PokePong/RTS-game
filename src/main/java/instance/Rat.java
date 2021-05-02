package instance;

import engine.renderer.Default;
import engine.renderer.Renderer;
import engine.scene.GameObject;
import engine.util.Color4;
import engine.util.Constants;
import engine.util.OBJLoader;
import module.buffer.MeshVBO;
import module.shader.GenericShader;

public class Rat extends GameObject {

    public Rat(Color4 color) {
        setColor(color);
    }

    @Override
    public void __init__() {
        setMesh(OBJLoader.loadMesh("", "rat.obj"));
        setVbo(new MeshVBO(getMesh()));

        getLocalTransform().scaleTo(0.2f);
        getLocalTransform().translate(0, -1.1f, 0);

        addComponent(Constants.BOUNDING_BOX_COMPONENT, new BoundingBox());
        addComponent(Constants.FRUSTUM_CULLING_COMPONENT, new FrustumCulling());
        addComponent(Constants.RENDERER_COMPONENT, new Renderer(GenericShader.getInstance(), new Default()));
    }

    @Override
    public void __update__(double delta) {
        getWorldTransform().rotate(0, 0.5f, 0);
    }
}
