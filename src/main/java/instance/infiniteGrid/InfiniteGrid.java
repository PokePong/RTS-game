package instance.infiniteGrid;

import engine.renderer.Default;
import engine.renderer.Renderer;
import engine.scene.GameObject;
import engine.util.Constants;
import engine.util.Utils;
import module.buffer.MeshVBO;

public class InfiniteGrid extends GameObject {


    @Override
    public void __init__() {
        this.setVbo(new MeshVBO(Utils.generateQuad(2)));


        this.addComponent(Constants.RENDERER_COMPONENT, new Renderer(InfiniteGridShader.getInstance(), new Default()));
    }

    @Override
    public void __update__(double delta) {

    }
}
