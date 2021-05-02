package engine.core.kernel;

import engine.core.Context;
import engine.renderer.Default;


public abstract class EngineRendering {

    public Context context;

    public EngineRendering(Context context) {
        this.context = context;
    }

    protected void init() {
        Default.init();
        clearScene();
        __init__();
    }

    protected void render() {
        clearScene();
        __render__();
    }

    protected void cleanUp() {
        __cleanUp__();
    }

    public abstract void __init__();

    public abstract void __render__();

    public abstract void __cleanUp__();

    public void clearScene() {
        Default.clearScreen();
    }

}
