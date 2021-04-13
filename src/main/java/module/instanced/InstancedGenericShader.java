package module.instanced;

import engine.scene.GameObject;
import engine.shader.Shader;
import engine.util.Constants;

public class InstancedGenericShader extends Shader {

    private static InstancedGenericShader instance;

    public static InstancedGenericShader getInstance() {
        if (instance == null) {
            instance = new InstancedGenericShader();
        }
        return instance;
    }

    protected InstancedGenericShader() {
        super("instancedGeneric");

        addVertexShader();
        addFragmentShader();
        validateShader();

        addUniform("localMatrix");
        addUniformBlock("Camera");
    }

    @Override
    public void updateUniforms(GameObject gameObject) {
        setUniform("localMatrix", gameObject.getLocalTransform().getWorldMatrix());
        setUniformBlock("Camera", Constants.CAMERA_UBO_BINDING_INDEX);
    }
}
