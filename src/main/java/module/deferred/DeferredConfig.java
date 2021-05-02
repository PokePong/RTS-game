package module.deferred;

import engine.renderer.RenderConfig;
import static org.lwjgl.opengl.GL11.*;

public class DeferredConfig implements RenderConfig {

    @Override
    public void enable() {
        glDisable(GL_DEPTH_TEST);
    }

    @Override
    public void disable() {
        glEnable(GL_DEPTH_TEST);
    }

}
