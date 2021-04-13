package engine.util;

import engine.core.Window;
import module.Color4f;
import module.gui.GuiConfig;
import module.gui.GuiObject;
import module.gui.GuiShader;
import org.joml.Vector2f;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Debug {

    public static void log(String message) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss,SSS");
        System.out.println(formatter.format(new Date()) + ": " + message);
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


    public static void drawRectScreen(Vector2f p1, Vector2f p2, Color4f color) {
        GuiConfig config = new GuiConfig();
        GuiObject rect = new GuiObject();

        rect.setColor(color);
        rect.getWorldMatrix().translate(p1.x, Window.height - p1.y, 0);

        int width = (int) (p2.x - p1.x);
        int height = (int) (p2.y - p1.y);

        if (width > 0 && height < 0) {
            rect.getWorldMatrix().scale(Math.abs(width), Math.abs(height), 0);
        } else if (width < 0 && height < 0) {
            rect.getWorldMatrix().rotateZ((float) (Math.PI / 2));
            rect.getWorldMatrix().scale(Math.abs(height), Math.abs(width), 0);
        } else if (width < 0 && height > 0) {
            rect.getWorldMatrix().rotateZ((float) (Math.PI));
            rect.getWorldMatrix().scale(Math.abs(width), Math.abs(height), 0);
        } else {
            rect.getWorldMatrix().rotateZ((float) (-Math.PI / 2));
            rect.getWorldMatrix().scale(Math.abs(height), Math.abs(width), 0);
        }

        GuiShader.getInstance().bind();
        config.enable();
        GuiShader.getInstance().updateUniforms(rect);
        rect.getVbo().render();
        config.disable();
        GuiShader.getInstance().unbind();
    }

}
