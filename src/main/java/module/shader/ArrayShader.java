package module.shader;

import engine.scene.GameObject;
import engine.shader.Shader;
import engine.util.Constants;
import module.Color4;

public class ArrayShader extends Shader {

    private static ArrayShader instance;

    public static ArrayShader getInstance() {
        if (instance == null) {
            instance = new ArrayShader();
        }
        return instance;
    }

    public ArrayShader() {
        super("array");
        addVertexShader();
        addFragmentShader();
        validateShader();

        addUniformBlock("Camera");
        addUniform("color");
    }

    @Override
    public void updateUniforms(GameObject gameObject) {

    }

    public void updateUniforms(Color4 color) {
        setUniformBlock("Camera", Constants.CAMERA_UBO_BINDING_INDEX);
        setUniform("color", color.toVector4());
    }
}
