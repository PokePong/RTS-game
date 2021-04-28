package module;


import engine.math.vector.Vector4;

import java.nio.FloatBuffer;
import java.util.Random;

public class Color4 {
    public static final Color4 TRANSPARENT = new Color4(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color4 WHITE = new Color4(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color4 YELLOW = new Color4(1.0f, 1.0f, 0.0f, 1.0f);
    public static final Color4 RED = new Color4(1.0f, 0.0f, 0.0f, 1.0f);
    public static final Color4 BLUE = new Color4(0.0f, 0.0f, 1.0f, 1.0f);
    public static final Color4 GREEN = new Color4(0.0f, 1.0f, 0.0f, 1.0f);
    public static final Color4 BLACK = new Color4(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Color4 GRAY = new Color4(0.5f, 0.5f, 0.5f, 1.0f);
    public static final Color4 CYAN = new Color4(0.0f, 1.0f, 1.0f, 1.0f);
    public static final Color4 DARK_GRAY = new Color4(0.3f, 0.3f, 0.3f, 1.0f);
    public static final Color4 LIGHT_GRAY = new Color4(0.7f, 0.7f, 0.7f, 1.0f);
    public static final Color4 PINK = new Color4(1.0f, 0.68f, 0.68f, 1.0f);
    public static final Color4 ORANGE = new Color4(1.0f, 0.78f, 0.0f, 1.0f);
    public static final Color4 MAGENTA = new Color4(1.0f, 0, 1.0f, 1.0f);

    public float r;
    public float g;
    public float b;
    public float a = 1.0F;

    public Color4(Color4 Color4) {
        this.r = Color4.r;
        this.g = Color4.g;
        this.b = Color4.b;
        this.a = Color4.a;
    }

    public Color4(FloatBuffer buffer) {
        this.r = buffer.get();
        this.g = buffer.get();
        this.b = buffer.get();
        this.a = buffer.get();
    }

    public Color4(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0F;
    }

    public Color4(float r, float g, float b, float a) {
        this.r = Math.min(r, 1.0F);
        this.g = Math.min(g, 1.0F);
        this.b = Math.min(b, 1.0F);
        this.a = Math.min(a, 1.0F);
    }

    public Color4(int r, int g, int b) {
        this.r = (r / 255.0F);
        this.g = (g / 255.0F);
        this.b = (b / 255.0F);
        this.a = 1.0F;
    }

    public Color4(int r, int g, int b, int a) {
        this.r = (r / 255.0F);
        this.g = (g / 255.0F);
        this.b = (b / 255.0F);
        this.a = (a / 255.0F);
    }

    public Color4(int value) {
        int r = (value & 0xFF0000) >> 16;
        int g = (value & 0xFF00) >> 8;
        int b = value & 0xFF;
        int a = (value & 0xFF000000) >> 24;
        if (a < 0) {
            a += 256;
        }
        if (a == 0) {
            a = 255;
        }
        this.r = (r / 255.0F);
        this.g = (g / 255.0F);
        this.b = (b / 255.0F);
        this.a = (a / 255.0F);
    }

    public Vector4 toVector4() {
        return new Vector4(r, g, b, a);
    }

    public static Color4 decode(String nm) {
        return new Color4(Integer.decode(nm).intValue());
    }

    public int hashCode() {
        return (int) (this.r + this.g + this.b + this.a) * 255;
    }

    public boolean equals(Object other) {
        if ((other instanceof Color4)) {
            Color4 o = (Color4) other;
            return (o.r == this.r) && (o.g == this.g) && (o.b == this.b) && (o.a == this.a);
        }
        return false;
    }

    public String toString() {
        return "Color4f (" + this.r + "," + this.g + "," + this.b + "," + this.a + ")";
    }

    public Color4 darker() {
        return darker(0.5F);
    }

    public Color4 darker(float scale) {
        scale = 1.0F - scale;
        Color4 temp = new Color4(this.r * scale, this.g * scale, this.b * scale, this.a);

        return temp;
    }

    public Color4 brighter() {
        return brighter(0.2F);
    }

    public int getRed() {
        return (int) (this.r * 255.0F);
    }

    public int getGreen() {
        return (int) (this.g * 255.0F);
    }

    public int getBlue() {
        return (int) (this.b * 255.0F);
    }

    public int getAlpha() {
        return (int) (this.a * 255.0F);
    }

    public int getRedByte() {
        return (int) (this.r * 255.0F);
    }

    public int getGreenByte() {
        return (int) (this.g * 255.0F);
    }

    public int getBlueByte() {
        return (int) (this.b * 255.0F);
    }

    public int getAlphaByte() {
        return (int) (this.a * 255.0F);
    }

    public Color4 brighter(float scale) {
        scale += 1.0F;
        Color4 temp = new Color4(this.r * scale, this.g * scale, this.b * scale, this.a);

        return temp;
    }

    public Color4 multiply(Color4 c) {
        return new Color4(this.r * c.r, this.g * c.g, this.b * c.b, this.a * c.a);
    }

    public void add(Color4 c) {
        this.r += c.r;
        this.g += c.g;
        this.b += c.b;
        this.a += c.a;
    }

    public void scale(float value) {
        this.r *= value;
        this.g *= value;
        this.b *= value;
        this.a *= value;
    }

    public Color4 addToCopy(Color4 c) {
        Color4 copy = new Color4(this.r, this.g, this.b, this.a);
        copy.r += c.r;
        copy.g += c.g;
        copy.b += c.b;
        copy.a += c.a;

        return copy;
    }

    public Color4 scaleCopy(float value) {
        Color4 copy = new Color4(this.r, this.g, this.b, this.a);
        copy.r *= value;
        copy.g *= value;
        copy.b *= value;
        copy.a *= value;

        return copy;
    }

    public static Color4 random() {
        float r = new Random().nextFloat();
        float g = new Random().nextFloat();
        float b = new Random().nextFloat();
        return new Color4(r, g, b, 1.0f);
    }
}
