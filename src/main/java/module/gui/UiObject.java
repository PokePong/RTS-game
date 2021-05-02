package module.gui;

import engine.gl.VBO;
import engine.math.Matrix4;
import engine.math.Transform;
import engine.model.Texture2D;
import engine.util.Color4;
import engine.util.Debug;
import org.lwjgl.opengl.GL13;

public class UiObject {

    private Transform transform;
    private VBO vbo;
    private Texture2D texture;
    private Color4 color;

    public UiObject() {
        this.transform = new Transform();
        this.vbo = new UiVBO();
        this.color = Color4.TRANSPARENT;
    }

    public void init() {

    }

    public void update(double delta) {

    }

    public void render() {
        if (texture != null) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            texture.bind();
        }
        vbo.render();
    }

    public void cleanUp() {
        vbo.cleanUp();
        if (texture != null)
            texture.cleanUp();
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

    public Texture2D getTexture() {
        return texture;
    }

    public void setTexture(Texture2D texture) {
        if (color == Color4.TRANSPARENT)
            color = Color4.WHITE;
        this.texture = texture;
    }
}
