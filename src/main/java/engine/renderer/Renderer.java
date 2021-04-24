package engine.renderer;

import engine.scene.Component;
import engine.shader.Shader;

public class Renderer extends Component {

    private Shader shader;
    private RenderConfig config;
    private boolean render = true;

    public Renderer(Shader shader, RenderConfig config) {
        super();
        this.shader = shader;
        this.config = config;
    }

    public void render() {
        if (render) {
            shader.bind();
            config.enable();
            shader.updateUniforms(getParent());
            getParent().getVbo().render();
            config.disable();
            shader.unbind();
        }
    }

    public void cleanUp() {
        shader.cleanUp();
    }

    public boolean isRender() {
        return render;
    }

    public void disableRendering() {
        render = false;
    }

    public void enableRendering() {
        render = true;
    }

    public void setRender(boolean value){
        render = value;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public RenderConfig getConfig() {
        return config;
    }

    public void setConfig(RenderConfig config) {
        this.config = config;
    }
}
