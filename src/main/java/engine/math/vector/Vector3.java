package engine.math.vector;

import engine.math.FastMath;

import java.io.Serializable;

public final class Vector3 extends Vector<Vector3> implements Serializable {

    public static final long serialVersionUID = 1;

    public final static Vector3 ZERO = new Vector3(0, 0, 0);
    public final static Vector3 UNIT_X = new Vector3(1, 0, 0);
    public final static Vector3 UNIT_Y = new Vector3(0, 1, 0);
    public final static Vector3 UNIT_Z = new Vector3(0, 0, 1);
    public final static Vector3 UNIT = new Vector3(1, 1, 1);

    public final static Vector3 RIGHT = new Vector3(1, 0, 0);
    public final static Vector3 UP = new Vector3(0, 1, 0);
    public final static Vector3 FORWARD = new Vector3(0, 0, -1);

    public final static Vector3 POSITIVE_INFINITY = new Vector3(
            Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY);
    public final static Vector3 NEGATIVE_INFINITY = new Vector3(
            Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY);

    /**
     * the x value of the vector.
     */
    public final float x;

    /**
     * the y value of the vector.
     */
    public final float y;

    /**
     * the z value of the vector.
     */
    public final float z;

    /**
     * Constructor instantiates a new <code>Vector3</code> with default
     * values of (0,0,0).
     */
    public Vector3() {
        this(0);
    }

    /**
     * Constructor instantiates a new <code>Vector3</code> with default
     * values of (0,0,0).
     */
    public Vector3(float s) {
        x = y = z = s;
    }

    /**
     * Constructor instantiates a new <code>Vector3</code> with provides
     * values.
     *
     * @param x the x value of the vector.
     * @param y the y value of the vector.
     * @param z the z value of the vector.
     */
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * Promote a 2 vec to a 3 vec
     */
    public Vector3(Vector2 v, float z) {
        this(v.x, v.y, z);
    }

    /**
     * Promote a 2 vec to a 3 vec
     */
    public Vector3(Vector2 v) {
        this(v, 0);
    }

    /**
     * <code>cross</code> calculates the cross product of this vector with a
     * parameter vector v.
     *
     * @param v the vector to take the cross product of with this.
     * @return the cross product vector.
     */
    public Vector3 cross(Vector3 v) {
        float resX = ((y * v.z) - (z * v.y));
        float resY = ((z * v.x) - (x * v.z));
        float resZ = ((x * v.y) - (y * v.x));
        return new Vector3(resX, resY, resZ);
    }

    /**
     * @param index
     * @return x value if index == 0, y value if index == 1 or z value if index ==
     * 2
     * @throws IllegalArgumentException if index is not one of 0, 1, 2.
     */
    public float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
        }
        throw new IllegalArgumentException("Index must be either 0, 1 or 2");
    }

    @Override
    protected Vector3 build(float[] v) {
        return new Vector3(v[0], v[1], v[2]);
    }

    @Override
    protected Vector3 build(float s) {
        return new Vector3(s);
    }

    /**
     * Saves this Vector3 into the given float[] object.
     *
     * @return The array, with X, Y, Z float values in that order
     */
    @Override
    public float[] toArray() {
        return new float[]{x, y, z};
    }

    /**
     * <code>angleBetween</code> returns (in radians) the angle between two vectors.
     * It is assumed that both this vector and the given vector are unit vectors (iow, normalized).
     *
     * @param otherVector a unit vector to find the angle against
     * @return the angle in radians.
     */
    @Override
    public float angleBetween(Vector3 otherVector) {
        float dotProduct = dot(otherVector);
        float angle = FastMath.acos(dotProduct);
        return angle;
    }

    /**
     * are these two vectors the same? they are is they both have the same x,y,
     * and z values.
     *
     * @param o the object to compare for equality
     * @return true if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector3)) {
            return false;
        }

        if (this == o) {
            return true;
        }

        Vector3 comp = (Vector3) o;
        if (Float.compare(x, comp.x) != 0) return false;
        if (Float.compare(y, comp.y) != 0) return false;
        if (Float.compare(z, comp.z) != 0) return false;
        return true;
    }

    /**
     * <code>toString</code> returns the string representation of this vector.
     * The format is:
     * <p>
     * org.jme.math.Vector3 [X=XX.XXXX, Y=YY.YYYY, Z=ZZ.ZZZZ]
     *
     * @return the string representation of this vector.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
