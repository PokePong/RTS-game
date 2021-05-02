package module.deferred;

import engine.model.Texture2D;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class GBuffer {

    private Texture2D albedo;
    private Texture2D position;
    private Texture2D normal;
    private Texture2D depth;

    public GBuffer(int width, int height) {
        this.albedo = new Texture2D(width, height);
        albedo.bind();
        albedo.noFilter();
        albedo.allocateImage2D(GL30.GL_RGBA32F, GL11.GL_RGBA);

        this.position = new Texture2D(width, height);
        position.bind();
        position.noFilter();
        position.allocateImage2D(GL30.GL_RGBA32F, GL11.GL_RGBA);

        this.normal = new Texture2D(width, height);
        normal.bind();
        normal.noFilter();
        normal.allocateImage2D(GL30.GL_RGBA32F, GL11.GL_RGBA);

        this.depth = new Texture2D(width, height);
        depth.bind();
        depth.noFilter();
        depth.allocateDepth();

        depth.unbind();

    }

    public void cleanUp() {
        albedo.cleanUp();
        position.cleanUp();
        normal.cleanUp();
        depth.cleanUp();
    }

    public Texture2D getAlbedo() {
        return albedo;
    }

    public Texture2D getPosition() {
        return position;
    }

    public Texture2D getNormal() {
        return normal;
    }

    public Texture2D getDepth() {
        return depth;
    }

}
