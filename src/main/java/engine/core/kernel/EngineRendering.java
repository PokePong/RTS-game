package engine.core.kernel;

import engine.renderer.Default;


public class EngineRendering {

    private EngineSystem engineSystem;

    public EngineRendering(EngineSystem engineSystem) {
        this.engineSystem = engineSystem;
    }

    public void init() {
        Default.init();
    }

    public void render() {
        Default.clearScreen();
        engineSystem.getContext().getScenegraph().render();
    }

    public void cleanUp() {

    }

}
