package module.deferred;

import engine.core.Context;
import engine.core.Window;
import engine.core.kernel.EngineRendering;
import engine.gl.FrameBuffer;
import engine.model.Texture2D;
import engine.util.BufferUtils;
import engine.util.Debug;
import module.camera.component.Picking;
import module.gui.UiConfig;
import module.gui.UiObject;
import module.gui.UiShader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL30.*;


public class DeferredRendering extends EngineRendering {

    private static final Logger logger = LogManager.getLogger(DeferredRendering.class);

    private FrameBuffer fbo;
    private GBuffer gbuffer;
    private Texture2D scene;

    private DeferredShader deferredShader;
    private DeferredConfig deferredConfig;

    private UiObject ui;
    private UiConfig uiConfig;

    public DeferredRendering(Context context) {
        super(context);
        this.fbo = new FrameBuffer();
        this.gbuffer = new GBuffer(Window.width, Window.height);
        this.scene = new Texture2D(Window.width, Window.height);
    }

    @Override
    public void __init__() {
        this.deferredShader = DeferredShader.getInstance();
        this.deferredConfig = new DeferredConfig();

        scene.bind();
        scene.allocateImage2D(GL_RGBA32F, GL11.GL_RGBA);
        scene.noFilter();
        scene.unbind();

        fbo.bind();
        fbo.createColorTextureAttachment(gbuffer.getAlbedo().getId(), 0);
        fbo.createColorTextureAttachment(gbuffer.getPosition().getId(), 1);
        fbo.createColorTextureAttachment(gbuffer.getNormal().getId(), 2);
        fbo.createColorTextureAttachment(scene.getId(), 3);
        fbo.createDepthTextureAttachment(gbuffer.getDepth().getId());
        fbo.setDrawBuffers(BufferUtils.createFlippedBuffer(GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1, GL_COLOR_ATTACHMENT2, GL_COLOR_ATTACHMENT3));
        fbo.checkStatus();
        fbo.unbind();
        logger.debug("[DeferredRendering] Successful FBO created");

        uiConfig = new UiConfig();
        ui = new UiObject();
        ui.setTexture(scene);
        ui.getTransform().translateTo(0, 0, 0);
        ui.getTransform().scaleTo(Window.width, Window.height, 0);

        logger.info("[DeferredRendering] Enable");
    }

    @Override
    public void __render__() {
        // DRAW TO FBO
        fbo.bind();
        clearScene();
        context.getScenegraph().render();
        fbo.unbind();

        // PROCESS FBO SCENE
        clearScene();
        deferredConfig.enable();
        deferredShader.bind();
        deferredShader.updateUniforms(gbuffer.getAlbedo(), gbuffer.getPosition(), gbuffer.getNormal(), gbuffer.getDepth(), scene);
        deferredShader.unbind();
        deferredConfig.disable();

        // DRAW SCENE
        clearScene();
        UiShader.getInstance().bind();
        uiConfig.enable();
        UiShader.getInstance().updateUniforms(ui);
        ui.render();
        uiConfig.disable();
        UiShader.getInstance().unbind();

    }

    @Override
    public void __cleanUp__() {
        scene.cleanUp();
        fbo.cleanUp();
        deferredShader.cleanUp();
    }
}
