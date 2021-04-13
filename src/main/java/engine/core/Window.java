package engine.core;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {

    public static int width;
    public static int height;
    public static String title;
    private static long window;

    public static void create(int width, int height, String title) {
        Window.width = width;
        Window.height = height;
        Window.title = title;
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("[GLFW] Unable to initialize GLFW!");
        }

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) {
            throw new RuntimeException("[GLFW] Failed to create window!");
        }

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwShowWindow(window);
    }

    public static void setTitle(String nTitle) {
        glfwSetWindowTitle(window, nTitle);
    }

    public static void addTitle(String addon){
        setTitle(title + " - " + addon);
    }

    protected static void draw() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    protected static void destroy() {
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    protected static boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public static float getAspectRatio() {
        return (float) width / height;
    }

    public static long getWindow() {
        return window;
    }

    public static Matrix4f getOrthoMatrix() {
        return new Matrix4f().ortho2D(0, width, 0, height);
    }


}
