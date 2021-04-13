package module.gui;

import engine.core.Window;
import engine.gl.VBO;
import engine.math.Transform;
import module.Color4f;
import org.joml.Matrix4f;

public class GuiObject {

    private Matrix4f worldMatrix;
    private VBO vbo;
    private Color4f color;

    public GuiObject() {
        this.worldMatrix = new Matrix4f();
        this.vbo = new GuiVBO();
        this.color = Color4f.TRANSPARENT;
    }

    public void init() {

    }

    public void update(double delta) {

    }

    public void render() {

    }

    public void cleanUp() {
        vbo.cleanUp();
    }

    public VBO getVbo() {
        return vbo;
    }

    public Matrix4f getOrthoMatrix() {
        return Window.getOrthoMatrix().mul(worldMatrix);
    }

    public Matrix4f getWorldMatrix() {
        return worldMatrix;
    }

    public void setWorldMatrix(Matrix4f worldMatrix) {
        this.worldMatrix = worldMatrix;
    }

    public Color4f getColor() {
        return color;
    }

    public void setColor(Color4f color) {
        this.color = color;
    }
}
