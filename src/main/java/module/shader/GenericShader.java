package module.shader;

import engine.math.Transform;
import engine.scene.GameObject;
import engine.shader.Shader;
import engine.util.Constants;
import module.Color4f;

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

        addUniform("localMatrix");
        addUniform("worldMatrix");
        addUniformBlock("Camera");
        addUniform("color");
    }

    @Override
    public void updateUniforms(GameObject gameObject) {
        setUniform("localMatrix", gameObject.getLocalTransform().getWorldMatrix());
        setUniform("worldMatrix", gameObject.getWorldTransform().getWorldMatrix());
        setUniformBlock("Camera", Constants.CAMERA_UBO_BINDING_INDEX);
        setUniform("color", gameObject.getColor().toVector4f());
    }

    public void updateUniforms(Transform localTransform, Transform worldTransform, Color4f color) {
        setUniform("localMatrix", localTransform.getWorldMatrix());
        setUniform("worldMatrix", worldTransform.getWorldMatrix());
        setUniformBlock("Camera", Constants.CAMERA_UBO_BINDING_INDEX);
        setUniform("color", color.toVector4f());
    }
}
