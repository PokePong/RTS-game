package engine.util;

import engine.core.Window;
import engine.math.Matrix4;
import engine.math.Vector;
import engine.math.Vector2;
import engine.math.Vector3;
import module.Color4;
import module.gui.GuiConfig;
import module.gui.GuiObject;
import module.gui.GuiShader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Debug {

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
        GuiConfig config = new GuiConfig();
        GuiObject rect = new GuiObject();

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

        GuiShader.getInstance().bind();
        config.enable();
        GuiShader.getInstance().updateUniforms(rect);
        rect.getVbo().render();
        config.disable();
        GuiShader.getInstance().unbind();
    }

}
