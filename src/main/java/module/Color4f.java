package module;

import org.joml.Vector4f;

import java.nio.FloatBuffer;
import java.util.Random;

public class Color4f {
    public static final Color4f TRANSPARENT = new Color4f(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color4f WHITE = new Color4f(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color4f YELLOW = new Color4f(1.0f, 1.0f, 0.0f, 1.0f);
    public static final Color4f RED = new Color4f(1.0f, 0.0f, 0.0f, 1.0f);
    public static final Color4f BLUE = new Color4f(0.0f, 0.0f, 1.0f, 1.0f);
    public static final Color4f GREEN = new Color4f(0.0f, 1.0f, 0.0f, 1.0f);
    public static final Color4f BLACK = new Color4f(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Color4f GRAY = new Color4f(0.5f, 0.5f, 0.5f, 1.0f);
    public static final Color4f CYAN = new Color4f(0.0f, 1.0f, 1.0f, 1.0f);
    public static final Color4f DARK_GRAY = new Color4f(0.3f, 0.3f, 0.3f, 1.0f);
    public static final Color4f LIGHT_GRAY = new Color4f(0.7f, 0.7f, 0.7f, 1.0f);
    public static final Color4f PINK = new Color4f(1.0f, 0.68f, 0.68f, 1.0f);
    public static final Color4f ORANGE = new Color4f(1.0f, 0.78f, 0.0f, 1.0f);
    public static final Color4f MAGENTA = new Color4f(1.0f, 0, 1.0f, 1.0f);

    public float r;
    public float g;
    public float b;
    public float a = 1.0F;

    public Color4f(Color4f Color4f) {
        this.r = Color4f.r;
        this.g = Color4f.g;
        this.b = Color4f.b;
        this.a = Color4f.a;
    }

    public Color4f(FloatBuffer buffer) {
        this.r = buffer.get();
        this.g = buffer.get();
        this.b = buffer.get();
        this.a = buffer.get();
    }

    public Color4f(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0F;
    }

    public Color4f(float r, float g, float b, float a) {
        this.r = Math.min(r, 1.0F);
        this.g = Math.min(g, 1.0F);
        this.b = Math.min(b, 1.0F);
        this.a = Math.min(a, 1.0F);
    }

    public Color4f(int r, int g, int b) {
        this.r = (r / 255.0F);
        this.g = (g / 255.0F);
        this.b = (b / 255.0F);
        this.a = 1.0F;
    }

    public Color4f(int r, int g, int b, int a) {
        this.r = (r / 255.0F);
        this.g = (g / 255.0F);
        this.b = (b / 255.0F);
        this.a = (a / 255.0F);
    }

    public Color4f(int value) {
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

    public Vector4f toVector4f() {
        return new Vector4f(r, g, b, a);
    }

    public static Color4f decode(String nm) {
        return new Color4f(Integer.decode(nm).intValue());
    }

    public int hashCode() {
        return (int) (this.r + this.g + this.b + this.a) * 255;
    }

    public boolean equals(Object other) {
        if ((other instanceof Color4f)) {
            Color4f o = (Color4f) other;
            return (o.r == this.r) && (o.g == this.g) && (o.b == this.b) && (o.a == this.a);
        }
        return false;
    }

    public String toString() {
        return "Color4f (" + this.r + "," + this.g + "," + this.b + "," + this.a + ")";
    }

    public Color4f darker() {
        return darker(0.5F);
    }

    public Color4f darker(float scale) {
        scale = 1.0F - scale;
        Color4f temp = new Color4f(this.r * scale, this.g * scale, this.b * scale, this.a);

        return temp;
    }

    public Color4f brighter() {
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

    public Color4f brighter(float scale) {
        scale += 1.0F;
        Color4f temp = new Color4f(this.r * scale, this.g * scale, this.b * scale, this.a);

        return temp;
    }

    public Color4f multiply(Color4f c) {
        return new Color4f(this.r * c.r, this.g * c.g, this.b * c.b, this.a * c.a);
    }

    public void add(Color4f c) {
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

    public Color4f addToCopy(Color4f c) {
        Color4f copy = new Color4f(this.r, this.g, this.b, this.a);
        copy.r += c.r;
        copy.g += c.g;
        copy.b += c.b;
        copy.a += c.a;

        return copy;
    }

    public Color4f scaleCopy(float value) {
        Color4f copy = new Color4f(this.r, this.g, this.b, this.a);
        copy.r *= value;
        copy.g *= value;
        copy.b *= value;
        copy.a *= value;

        return copy;
    }

    public static Color4f random() {
        float r = new Random().nextFloat();
        float g = new Random().nextFloat();
        float b = new Random().nextFloat();
        return new Color4f(r, g, b, 1.0f);
    }
}
