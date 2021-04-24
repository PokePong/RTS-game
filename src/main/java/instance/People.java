package instance;

import engine.math.Vector3;
import engine.renderer.Default;
import engine.renderer.Renderer;
import engine.scene.GameObject;
import engine.util.Constants;
import module.Color4;
import module.buffer.MeshVBO;
import module.shader.GenericShader;
import org.joml.Random;

public class People extends GameObject {

    private int terrainSize = 300;
    private float moveSpeed = 3f;

    public People(Color4 color) {
        setColor(color);
    }

    @Override
    public void __init__() {
        setVbo(MeshVBO.CUBE_VBO);

        getLocalTransform().translateTo(-0.5f, 0, -0.5f);
        getWorldTransform().rotateTo(0, new Random().nextInt(360), 0);

        int x = new Random().nextInt(2 * terrainSize) - terrainSize;
        int z = new Random().nextInt(2 * terrainSize) - terrainSize;
        getWorldTransform().translateTo(x, 0, z);

        addComponent(Constants.BOUNDING_BOX_COMPONENT, new BoundingBox(2f, 1f, 2f));
        addComponent(Constants.FRUSTUM_CULLING_COMPONENT, new FrustumCulling());
        addComponent(Constants.RENDERER_COMPONENT, new Renderer(GenericShader.getInstance(), new Default()));
    }

    @Override
    public void __update__(double delta) {
        float moveAmount = (float) (delta * moveSpeed);

        getWorldTransform().move(getWorldTransform().forward(), moveAmount);
        Vector3 pos = getWorldTransform().getTranslation();

        float x = pos.x;
        float z = pos.z;

        if (pos.x > terrainSize / 2) {
            x = pos.x - terrainSize;
        }
        if (pos.x < -terrainSize / 2) {
            x = pos.x + terrainSize;
        }

        if (pos.z > terrainSize / 2) {
            z = pos.z - terrainSize;
        }
        if (pos.z < -terrainSize / 2) {
            z = pos.z + terrainSize;
        }
        getWorldTransform().translateTo(x, pos.y, z);


    }

}
