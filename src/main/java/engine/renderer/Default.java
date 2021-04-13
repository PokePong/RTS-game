package engine.renderer;

import static org.lwjgl.opengl.GL11.*;

import engine.renderer.RenderConfig;

public class Default implements RenderConfig {

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    public static void init() {
        glFrontFace(GL_CW);

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_TEXTURE_2D);
        //glEnable(GL30.GL_FRAMEBUFFER_SRGB);
    }

    public static void clearScreen() {
        glClearColor(0 / 255f, 0 / 255f, 0 / 255f, 1.0f);
        glClearDepth(1.0);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
