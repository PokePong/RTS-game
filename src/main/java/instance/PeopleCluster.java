package instance;

import engine.core.kernel.Input;
import engine.model.Mesh;
import engine.renderer.Default;
import engine.renderer.Renderer;
import engine.scene.GameObject;
import engine.util.Constants;
import engine.util.Color4;
import engine.util.Debug;
import engine.util.OBJLoader;
import module.instanced.InstancedCluster;
import module.instanced.InstancedGenericShader;
import module.instanced.InstancedMeshVBO;
import org.lwjgl.glfw.GLFW;

public class PeopleCluster extends InstancedCluster {


    public PeopleCluster(int numInstances) {
        super(numInstances);
    }

    @Override
    public void __init__() {
        setMesh(OBJLoader.loadMesh("", "cat.obj"));
        setVbo(new InstancedMeshVBO(getMesh()));

        getLocalTransform().translateTo(-0.5f, 0, -0.5f);
        getLocalTransform().scale(0.005f);
        getLocalTransform().rotateTo(0, 90, 0);

        addComponent(Constants.RENDERER_COMPONENT, new Renderer(InstancedGenericShader.getInstance(), new Default()));
    }

    @Override
    public void __update__(double delta) {
        if(Input.isKeyDown(GLFW.GLFW_KEY_L)) {
            addInstance();
        }
    }

    @Override
    public GameObject createNewInstance() {
        return new People(Color4.random());
    }
}
