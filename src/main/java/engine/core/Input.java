package engine.core;

import engine.math.Vector2;
import org.lwjgl.glfw.*;

import java.util.ArrayList;

public class Input {

    private static GLFWKeyCallback keyboard;
    private static GLFWMouseButtonCallback mouse;
    private static GLFWScrollCallback scroll;
    private static GLFWCursorPosCallback cursor;

    private static ArrayList<Integer> keysDown;
    private static ArrayList<Integer> keysHold;
    private static ArrayList<Integer> keysUp;

    private static ArrayList<Integer> buttonsDown;
    private static ArrayList<Integer> buttonsHold;
    private static ArrayList<Integer> buttonsUp;

    private static float scrollOffSet;

    private static Vector2 cursorPosition;
    private static Vector2 dispVec;

    protected static void init(long window) {

        keysDown = new ArrayList<Integer>();
        keysHold = new ArrayList<Integer>();
        keysUp = new ArrayList<Integer>();

        buttonsDown = new ArrayList<Integer>();
        buttonsHold = new ArrayList<Integer>();
        buttonsUp = new ArrayList<Integer>();

        cursorPosition = new Vector2();
        dispVec = new Vector2();

        GLFW.glfwSetKeyCallback(window, (keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW.GLFW_PRESS) {
                    if (!keysDown.contains(key)) {
                        keysDown.add(key);
                        keysHold.add(key);
                    }
                }
                if (action == GLFW.GLFW_RELEASE) {
                    keysHold.remove(new Integer(key));
                    keysUp.add(key);
                }

            }
        }));

        GLFW.glfwSetMouseButtonCallback(window, (mouse = new GLFWMouseButtonCallback() {

            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW.GLFW_PRESS) {
                    if (!buttonsDown.contains(button)) {
                        buttonsDown.add(button);
                        buttonsHold.add(button);
                    }
                }
                if (action == GLFW.GLFW_RELEASE) {
                    buttonsHold.remove(new Integer(button));
                    buttonsUp.add(button);
                }
            }
        }));

        GLFW.glfwSetCursorPosCallback(window, (cursor = new GLFWCursorPosCallback() {

            @Override
            public void invoke(long window, double x, double y) {
                float mouseX = (float) x;
                float mouseY = (float) y;

                dispVec = new Vector2(mouseX - cursorPosition.x, mouseY - cursorPosition.y);
                cursorPosition = new Vector2(mouseX, mouseY);
            }
        }));


        GLFW.glfwSetScrollCallback(window, (scroll = new GLFWScrollCallback() {

            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                setScrollOffSet((float) yoffset);
            }
        }));
    }

    protected static void update() {
        setScrollOffSet(0);
        keysDown.clear();
        keysUp.clear();

        buttonsDown.clear();
        buttonsUp.clear();

        dispVec = new Vector2(0);
    }

    public static boolean isKeyDown(int key) {
        return keysDown.contains(key);
    }

    public static boolean isKeyUp(int key) {
        return keysUp.contains(key);
    }

    public static boolean isKeyHold(int key) {
        return keysHold.contains(key);
    }

    public static boolean isButtonDown(int key) {
        return buttonsDown.contains(key);
    }

    public static boolean isButtonUp(int key) {
        return buttonsUp.contains(key);
    }

    public static boolean isButtonHold(int key) {
        return buttonsHold.contains(key);
    }

    protected static void cleanUp() {
        keyboard.free();
        mouse.free();
        scroll.free();
        cursor.free();

        keysDown.clear();
        keysHold.clear();
        keysUp.clear();

        buttonsDown.clear();
        buttonsHold.clear();
        buttonsUp.clear();
    }

    public static float getScrollOffSet() {
        return scrollOffSet;
    }

    public static void setScrollOffSet(float scrollOffSet) {
        Input.scrollOffSet = scrollOffSet;
    }

    public static Vector2 getCursorPosition() {
        return cursorPosition;
    }

    public static Vector2 getNormalizedCursorPosition() {
        float x = (2f * cursorPosition.x) / Window.width - 1;
        float y = (2f * cursorPosition.y) / Window.height - 1;
        return new Vector2(x, y);
    }

    public static void setCursorPosition(Vector2 cursorPosition, long window) {
        Input.cursorPosition = cursorPosition;
        GLFW.glfwSetCursorPos(window, cursorPosition.x, cursorPosition.y);
    }

    public static Vector2 getDispVec() {
        return dispVec;
    }

    public static ArrayList<Integer> getKeysDown() {
        return keysDown;
    }

    public static ArrayList<Integer> getKeysHold() {
        return keysHold;
    }

    public static ArrayList<Integer> getKeysUp() {
        return keysUp;
    }

    public static ArrayList<Integer> getButtonsDown() {
        return buttonsDown;
    }

    public static ArrayList<Integer> getButtonsHold() {
        return buttonsHold;
    }

    public static ArrayList<Integer> getButtonsUp() {
        return buttonsUp;
    }
}
