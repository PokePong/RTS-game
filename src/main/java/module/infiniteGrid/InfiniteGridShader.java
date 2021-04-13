package module.infiniteGrid;

import engine.scene.GameObject;
import engine.shader.Shader;
import engine.util.Constants;

public class InfiniteGridShader extends Shader {

    private static InfiniteGridShader instance;

    public static InfiniteGridShader getInstance() {
        if(instance == null) {
            instance = new InfiniteGridShader();
        }
        return instance;
    }

    public InfiniteGridShader() {
        super("infiniteGrid");

        this.addVertexShader();
        this.addFragmentShader();
        this.validateShader();

        this.addUniformBlock("Camera");

    }

    @Override
    public void updateUniforms(GameObject gameObject) {
        this.setUniformBlock("Camera", Constants.CAMERA_UBO_BINDING_INDEX);

    }
}
