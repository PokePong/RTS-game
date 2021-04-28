package engine.math.vector;

import java.io.Serializable;

public final class Vector4 extends Vector4A<Vector4> implements Serializable {

    public static final long serialVersionUID = 1;

    public final static Vector4 ZERO = new Vector4(0, 0, 0, 0);
    public final static Vector4 NAN = new Vector4(Float.NaN, Float.NaN, Float.NaN, Float.NaN);
    public final static Vector4 UNIT_X = new Vector4(1, 0, 0, 0);
    public final static Vector4 UNIT_Y = new Vector4(0, 1, 0, 0);
    public final static Vector4 UNIT_Z = new Vector4(0, 0, 1, 0);
    public final static Vector4 UNIT_W = new Vector4(0, 0, 0, 1);
    public final static Vector4 UNIT_XYZW = new Vector4(1, 1, 1, 1);


    public final static Vector4 POSITIVE_INFINITY = new Vector4(
            Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY);
    public final static Vector4 NEGATIVE_INFINITY = new Vector4(
            Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY);

    /**
     * Constructor instantiates a new <code>Vector3f</code> with default
     * values of (0,0,0).
     */
    public Vector4() {
        this(0);
    }

    /**
     * Constructor instantiates a new <code>Vector3f</code> with default
     * values of (0,0,0).
     */
    public Vector4(float s) {
        super(s);
    }

    /**
     * Constructor instantiates a new <code>Vector4</code> with provides
     * values.
     *
     * @param x the x value of the vector.
     * @param y the y value of the vector.
     * @param z the z value of the vector.
     * @param w the w value of the vector.
     */
    public Vector4(float x, float y, float z, float w) {
        super(x, y, z, w);
    }

    /**
     * Constructor instantiates a new <code>Vector3f</code> that is a copy
     * of the provided vector
     *
     * @param v The Vector3f to copy
     */
    public Vector4(Vector2 v, float z, float w) {
        super(v.x, v.y, z, w);
    }

    /**
     * Constructor instantiates a new <code>Vector3f</code> that is a copy
     * of the provided vector
     *
     * @param v The Vector3f to copy
     */
    public Vector4(Vector2 v, float z) {
        super(v.x, v.y, z, 1);
    }

    /**
     * Constructor instantiates a new <code>Vector3f</code> that is a copy
     * of the provided vector
     *
     * @param v The Vector3f to copy
     */
    public Vector4(Vector2 v) {
        super(v.x, v.y, 0, 1);
    }

    /**
     * Constructor instantiates a new <code>Vector3f</code> that is a copy
     * of the provided vector
     *
     * @param v The Vector3f to copy
     */
    public Vector4(Vector3 v, float w) {
        super(v.x, v.y, v.z, w);
    }

    /**
     * Constructor instantiates a new <code>Vector3f</code> that is a copy
     * of the provided vector
     *
     * @param v The Vector3f to copy
     */
    public Vector4(Vector3 v) {
        super(v.x, v.y, v.z, 1);
    }

    @Override
    protected Vector4 build(float x, float y, float z, float w) {
        return new Vector4(x, y, z, w);
    }

    @Override
    protected Vector4 build(float s) {
        return new Vector4(s);
    }
}
