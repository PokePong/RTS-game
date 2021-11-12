package engine.core.kernel;

import engine.core.Context;
import engine.core.Window;
import engine.util.Debug;
import module.deferred.DeferredRendering;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;

public class EngineSystem {

    private static final Logger logger = LogManager.getLogger(EngineSystem.class);

    private Engine engine;
    private EngineRendering engineRendering;
    private Context context;

    protected EngineSystem(Engine engine) {
        this.engine = engine;
    }

    protected void init() {
        if(context == null) {
            logger.fatal("[Engine] No context! Cannot launch the game!");
        }
        this.engineRendering = new DeferredRendering(context);
        glfwWindowSizeCallback();
        displayGameSettings();
        Input.init(Window.getWindow());
        context.init();
        engineRendering.init();
    }

    protected void render() {
        engineRendering.render();
    }

    protected void update(double delta) {
        context.update(delta);
        Input.update();
    }

    protected void cleanUp() {
        context.cleanUp();
        engineRendering.cleanUp();
        Input.cleanUp();
    }

    private void glfwWindowSizeCallback() {
        glfwSetFramebufferSizeCallback(Window.getWindow(), (window, nwidth, nheight) -> {
            Window.width = nwidth;
            Window.height = nheight;
            GL11.glViewport(0, 0, nwidth, nheight);
            context.getScenegraph().getCamera().processProjectionMatrix();
        });
    }

    private void displayGameSettings() {
        logger.info("Name: " + engine.getConfig().getWindow_title());
        logger.info("Version: " + engine.getConfig().getVersion());
        logger.info("Window dimension: " + engine.getConfig().getWindow_width() + " x "
                + engine.getConfig().getWindow_height() + " pixels");
        logger.info("Fps_cap: " + engine.getConfig().getFps_cap() + " | Ups_cap: " + engine.getConfig().getUps_cap());
        logger.info("Powered by LWJGL: " + Version.getVersion());
        logger.info("Running with OpenGL: " + GL11.glGetString(GL11.GL_VERSION));
    }

    public Context getContext() {
        return context;
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    protected void setEngineRendering(EngineRendering engineRendering) {
        this.engineRendering = engineRendering;
    }
}
