package instance;

import engine.renderer.Default;
import engine.renderer.Renderer;
import engine.scene.GameObject;
import engine.util.Constants;
import module.Color4f;
import module.buffer.MeshVBO;
import module.shader.GenericShader;
import org.joml.Random;
import org.joml.Vector3f;

public class People extends GameObject {

    private int terrainSize = 50;
    private float moveSpeed = 3f;

    public People(Color4f color) {
        setColor(color);
    }

    @Override
    public void __init__() {
        setVbo(MeshVBO.CUBE_VBO);

        getLocalTransform().translate(-0.5f, 0, -0.5f);
        getWorldTransform().rotate(0, new Random().nextInt(360), 0);

        int x = new Random().nextInt(terrainSize) - terrainSize;
        int z = new Random().nextInt(terrainSize) - terrainSize;
        getWorldTransform().translateTo(x, 0, z);

        addComponent(Constants.RENDERER_COMPONENT, new Renderer(GenericShader.getInstance(), new Default()));
        addComponent(Constants.BOUNDING_BOX_COMPONENT, new BoundingBox(1.1f, 1f, 1.1f));
    }

    @Override
    public void __update__(double delta) {
        float moveAmount = (float) (delta * moveSpeed);

        getWorldTransform().move(getWorldTransform().forward(), moveAmount);
        Vector3f pos = getWorldTransform().getTranslation();

        if(pos.x > terrainSize / 2) {
            pos.x -= terrainSize;
        }
        if(pos.x < -terrainSize / 2) {
            pos.x += terrainSize;
        }

        if(pos.z > terrainSize / 2) {
            pos.z -= terrainSize;
        }
        if(pos.z < -terrainSize / 2) {
            pos.z += terrainSize;
        }
    }

}
