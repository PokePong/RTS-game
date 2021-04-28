package module.instanced;

import engine.scene.GameObject;
import engine.util.Constants;

import java.util.*;

public abstract class InstancedCluster extends GameObject {

    private Map<UUID, GameObject> renders;
    private int numInstances;

    public InstancedCluster(int numInstances) {
        super();
        this.numInstances = numInstances;
        this.renders = new HashMap<>();
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

    @Override
    public void cleanUp() {
        super.cleanUp();
        renders.clear();
    }

    public void addInstance() {
        GameObject object = createNewInstance();
        object.setInstanced(true);
        object.init();
        object.removeComponent(Constants.RENDERER_COMPONENT);
        addChild(object);
    }

    private void uploadInstancesData() {
        ((InstancedMeshVBO) getVbo()).uploadInstancedData(new ArrayList<>(renders.values()));
    }

    private void createInstances() {
        for (int i = 0; i < numInstances; i++) {
            addInstance();
        }
    }

    public abstract GameObject createNewInstance();

    public int getInstancesSize() {
        return getChildren().size();
    }

    public int getRendersSize() {
        return renders.size();
    }

    public void processCullingInstance(boolean cull, GameObject child) {
        if (cull) {
            if (renders.containsKey(child.getUuid())) {
                renders.remove(child.getUuid());
            }
        } else {
            if (!renders.containsKey(child.getUuid())) {
                renders.put(child.getUuid(), child);
            }
        }
    }

}
