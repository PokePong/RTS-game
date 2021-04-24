package module.gui;

import engine.scene.GameObject;
import engine.shader.Shader;

public class GuiShader extends Shader {

    private static GuiShader instance;

    public static GuiShader getInstance() {
        if (instance == null)
            instance = new GuiShader();
        return instance;
    }

    public GuiShader() {
        super("gui");

        addVertexShader();
        addFragmentShader();
        validateShader();

        addUniform("m_Ortho");
        addUniform("color");
    }

    @Override
    public void updateUniforms(GameObject gameObject) {

    }

    public void updateUniforms(GuiObject object) {
        setUniform("m_Ortho", object.getTransform().getOrthographicMatrix());
        setUniform("color", object.getColor().toVector4());
    }
}
