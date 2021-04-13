package module.infiniteGrid;

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

        Renderer renderer = new Renderer(InfiniteGridShader.getInstance(), new Default());
        renderer.setParent(this);
        renderer.init();
        this.addComponent(Constants.RENDERER_COMPONENT, renderer);
    }

    @Override
    public void __update__(double delta) {

    }
}
