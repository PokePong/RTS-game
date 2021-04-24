package module.gui;

import engine.gl.VBO;
import engine.math.Transform;
import module.Color4;

public class GuiObject {

    private Transform transform;
    private VBO vbo;
    private Color4 color;

    public GuiObject() {
        this.transform = new Transform();
        this.vbo = new GuiVBO();
        this.color = Color4.TRANSPARENT;
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

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public Color4 getColor() {
        return color;
    }

    public void setColor(Color4 color) {
        this.color = color;
    }
}
