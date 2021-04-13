package module.instanced;

import engine.renderer.Renderer;
import engine.scene.GameObject;
import engine.scene.Node;
import engine.util.Constants;


public abstract class InstancedCluster extends GameObject {

    private int numInstances;

    public InstancedCluster(int numInstances) {
        this.numInstances = numInstances;
    }

    @Override
    public void init() {
        super.init();
        createInstances();
    }

    @Override
    public void render() {
        uploadInstancesData();
        super.render();
    }

    public void addInstance() {
        GameObject object = createNewInstance();
        object.init();
        processInstance(object);
        addChild(object);
    }

    private void uploadInstancesData() {
        ((InstancedMeshVBO) getVbo()).uploadInstancedData(getChildren());
    }

    private void createInstances() {
        for (int i = 0; i < numInstances; i++) {
            addInstance();
        }
    }

    private void processInstance(GameObject object) {
        ((Renderer) object.getComponent(Constants.RENDERER_COMPONENT)).disableRendering();
    }

    public abstract GameObject createNewInstance();

    public int getInstancesSize() {
        return getChildren().size();
    }

}
