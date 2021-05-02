package module.gui;

import engine.scene.GameObject;
import engine.shader.Shader;

public class UiShader extends Shader {

    private static UiShader instance;

    public static UiShader getInstance() {
        if (instance == null)
            instance = new UiShader();
        return instance;
    }

    public UiShader() {
        super("ui");

        addVertexShader();
        addFragmentShader();
        validateShader();

        addUniform("m_Ortho");
        addUniform("color");
        addUniform("textured");
    }

    @Override
    public void updateUniforms(GameObject gameObject) {

    }

    public void updateUniforms(UiObject object) {
        setUniform("m_Ortho", object.getTransform().getOrthographicMatrix());
        setUniform("color", object.getColor().toVector4());
        setUniform("textured", object.getTexture() != null);
    }
}
