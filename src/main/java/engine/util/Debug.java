package engine.util;

import engine.core.Window;
import engine.math.Matrix4;
import engine.math.vector.Vector;
import engine.math.vector.Vector2;
import engine.math.vector.Vector3;
import engine.model.Texture2D;
import engine.renderer.Default;
import module.buffer.ArrayVBO;
import module.gui.UiObject;
import module.gui.UiShader;
import module.shader.ArrayShader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Debug {

    private static ArrayVBO arrayVBO;
    private static Default config = new Default();

    enum Level {
        FATAL,
        ERROR,
        WARNING,
        INFO,
        DEBUG
    }

    private static void print(Level level, String message) {
        StringBuilder builder = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss,SSS");
        builder.append(formatter.format(new Date()) + ": ");
        builder.append("[" + level.toString() + "] ");
        builder.append(message);
        System.out.println(builder.toString());
    }

    public static void fatal(String message) {
        print(Level.FATAL, message);
    }

    public static void err(String message) {
        print(Level.ERROR, message);
    }

    public static void warn(String message) {
        print(Level.WARNING, message);
    }

    public static void info(String message) {
        print(Level.INFO, message);
    }

    public static void log(String message) {
        print(Level.DEBUG, message);
    }

    public static void log(int value) {
        log(value + "");
    }

    public static void log(float value) {
        log(value + "");
    }

    public static void log(boolean value) {
        log(value + "");
    }

    public static void log(Vector value) {
        log(value.toString());
    }

    public static void log(Matrix4 value) {
        log(value.toString());
    }


    public static void drawRectScreen(Vector2 p1, Vector2 p2, Color4 color) {
        UiObject rect = new UiObject();

        rect.setColor(color);
        rect.getTransform().translateTo(p1.x, Window.height - p1.y, 0);

        int width = (int) (p2.x - p1.x);
        int height = (int) (p2.y - p1.y);

        if (width > 0 && height < 0) {
            rect.getTransform().scaleTo(Math.abs(width), Math.abs(height), 0);
        } else if (width < 0 && height < 0) {
            rect.getTransform().rotate(90, Vector3.UNIT_Z);
            rect.getTransform().scaleTo(Math.abs(height), Math.abs(width), 0);
        } else if (width < 0 && height > 0) {
            rect.getTransform().rotate(180, Vector3.UNIT_Z);
            rect.getTransform().scaleTo(Math.abs(width), Math.abs(height), 0);
        } else {
            rect.getTransform().rotate(-90, Vector3.UNIT_Z);
            rect.getTransform().scaleTo(Math.abs(height), Math.abs(width), 0);
        }

        UiShader.getInstance().bind();
        config.enable();
        UiShader.getInstance().updateUniforms(rect);
        rect.render();
        config.disable();
        UiShader.getInstance().unbind();

        rect.cleanUp();
    }

    public static void drawWire(Vector3[] positions, Color4 color) {
        if(arrayVBO == null) {
            arrayVBO = new ArrayVBO(positions);
        } else {
            arrayVBO.addData(positions);
        }
        ArrayShader.getInstance().bind();
        config.enable();
        ArrayShader.getInstance().updateUniforms(color);
        arrayVBO.render();
        config.disable();
        ArrayShader.getInstance().bind();
    }



}
