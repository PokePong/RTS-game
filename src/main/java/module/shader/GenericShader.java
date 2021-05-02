package module.shader;

import engine.math.Transform;
import engine.scene.GameObject;
import engine.shader.Shader;
import engine.util.Constants;
import engine.util.Color4;

public class GenericShader extends Shader {

    private static GenericShader instance = null;

    public static GenericShader getInstance() {
        if (instance == null)
            instance = new GenericShader();
        return instance;
    }

    protected GenericShader() {
        super("generic");

        addVertexShader();
        addFragmentShader();
        validateShader();

        addUniform("color");
        addUniform("localMatrix");
        addUniform("worldMatrix");
        addUniformBlock("Camera");
    }

    @Override
    public void updateUniforms(GameObject gameObject) {
        setUniformBlock("Camera", Constants.CAMERA_UBO_BINDING_INDEX);
        setUniform("localMatrix", gameObject.getLocalTransform().getWorldMatrix());
        setUniform("worldMatrix", gameObject.getWorldTransform().getWorldMatrix());
        setUniform("color", gameObject.getColor().toVector4());
    }

    public void updateUniforms(Transform localTransform, Transform worldTransform, Color4 color) {
        setUniform("localMatrix", localTransform.getWorldMatrix());
        setUniform("worldMatrix", worldTransform.getWorldMatrix());
        setUniformBlock("Camera", Constants.CAMERA_UBO_BINDING_INDEX);
        setUniform("color", color.toVector4());
    }
}
