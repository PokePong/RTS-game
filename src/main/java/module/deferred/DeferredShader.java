package module.deferred;

import engine.core.Window;
import engine.model.Texture2D;
import engine.scene.GameObject;
import engine.shader.Shader;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL42.*;

import engine.util.Constants;
import org.lwjgl.opengl.GL43;

public class DeferredShader extends Shader {

    private static DeferredShader instance;

    public static DeferredShader getInstance() {
        if (instance == null) {
            instance = new DeferredShader();
        }
        return instance;
    }

    public DeferredShader() {
        super("deferred");

        addComputeShader();
        validateShader();

        addUniformBlock("Camera");
        addUniform("img_Depth");
        addUniform("width");
        addUniform("height");
    }

    @Override
    public void updateUniforms(GameObject gameObject) {

    }

    public void updateUniforms(Texture2D albedo, Texture2D position, Texture2D normal, Texture2D depth, Texture2D scene) {
        glActiveTexture(GL_TEXTURE0);
        depth.bind();
        depth.linearFilter();

        setUniformBlock("Camera", Constants.CAMERA_UBO_BINDING_INDEX);
        setUniform("img_Depth", 0);
        setUniform("width", Window.width);
        setUniform("height", Window.height);

        glBindImageTexture(0, albedo.getId(), 0, false, 0, GL_READ_ONLY, albedo.getInternalFormat());
        glBindImageTexture(1, position.getId(), 0, false, 0, GL_READ_ONLY, position.getInternalFormat());
        glBindImageTexture(2, normal.getId(), 0, false, 0, GL_READ_ONLY, normal.getInternalFormat());
        glBindImageTexture(3, scene.getId(), 0, false, 0, GL_WRITE_ONLY, scene.getInternalFormat());
        GL43.glDispatchCompute(Window.width / 16, Window.height / 16, 1);
        glMemoryBarrier(GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);
    }
}
