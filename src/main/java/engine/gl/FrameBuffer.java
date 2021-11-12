package engine.gl;

import static org.lwjgl.opengl.GL30.*;

import engine.core.Window;
import engine.util.Debug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import java.nio.IntBuffer;

public class FrameBuffer {

    private static final Logger logger = LogManager.getLogger(FrameBuffer.class);

    private int id;
    private int width;
    private int height;

    public FrameBuffer() {
        this(Window.width, Window.height);
    }

    public FrameBuffer(int width, int height) {
        this.id = glGenFramebuffers();
        this.width = width;
        this.height = height;
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void cleanUp() {
        glDeleteFramebuffers(id);
    }

    public void createColorTextureAttachment(int textureId, int channel) {
        glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + channel, GL11.GL_TEXTURE_2D, textureId, 0);
    }

    public void createDepthTextureAttachment(int textureId) {
        GL32.glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, textureId, 0);
    }

    public void createDepthBufferAttachment() {
        int depthBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32F, width, height);
        glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
    }

    public void createColorTextureMultisampleAttachment(int textureId, int channel) {
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + channel, GL32.GL_TEXTURE_2D_MULTISAMPLE,
                textureId, 0);
    }

    public void createDepthTextureMultisampleAttachment(int textureId) {
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL32.GL_TEXTURE_2D_MULTISAMPLE, textureId, 0);
    }

    public void setDrawBuffers(IntBuffer buffer) {
        GL20.glDrawBuffers(buffer);
    }

    public void blitFrameBuffer(int sourceAttachment, int destinationAttachment, int writeFBO, int width, int height) {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, writeFBO);
        glBindFramebuffer(GL_READ_FRAMEBUFFER, id);
        glReadBuffer(GL_COLOR_ATTACHMENT0 + sourceAttachment);
        glDrawBuffer(GL_COLOR_ATTACHMENT0 + destinationAttachment);
        glBlitFramebuffer(0, 0, width, height, 0, 0, width, height,
                GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT, GL_NEAREST);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
    }

    /**
     * Check all openGL framebuffer errors
     */
    public void checkStatus() {
        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE) {
            return;
        } else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_UNDEFINED) {
            logger.error("Framebuffer creation failed with GL_FRAMEBUFFER_UNDEFINED error");
            System.exit(1);
        } else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT) {
            logger.error("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT error");
            System.exit(1);
        } else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT) {
            logger.error("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT error");
            System.exit(1);
        } else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER) {
            logger.error("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER error");
            System.exit(1);
        } else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER) {
            logger.error("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER error");
            System.exit(1);
        } else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_UNSUPPORTED) {
            logger.error("Framebuffer creation failed with GL_FRAMEBUFFER_UNSUPPORTED error");
            System.exit(1);
        } else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE) {
            logger.error("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE error");
            System.exit(1);
        } else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL32.GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS) {
            logger.error("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS error");
            System.exit(1);
        }
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
