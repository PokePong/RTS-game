package engine.math.vector;

import engine.math.FastMath;

import java.io.Serializable;

public final class Vector2 extends Vector<Vector2> implements Serializable {

    public static final long serialVersionUID = 1;

    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 UNIT_X = new Vector2(1, 0);
    public static final Vector2 UNIT_Y = new Vector2(0, 1);
    public static final Vector2 UNIT = new Vector2(1, 1);

    public final float x;
    public final float y;

    /**
     * Constructor instantiates a new <code>Vector3</code> with default
     * values of (0,0,0).
     */
    public Vector2() {
        x = y = 0;
    }

    /**
     * Creates a Vector2 with the given initial x and y values.
     *
     * @param x The x value of this Vector2.
     * @param y The y value of this Vector2.
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a Vector2 with the given an initial scalar
     *
     * @param s The x and y value of this Vector2.
     */
    public Vector2(float s) {
        this.x = s;
        this.y = s;
    }

    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * <code>cross</code> calculates the cross product of this vector with a
     * parameter vector v.
     *
     * @param v the vector to take the cross product of with this.
     * @return the cross product vector.
     */
    public Vector3 cross(Vector2 v) {
        return new Vector3(0, 0, determinant(v));
    }

    public float determinant(Vector2 v) {
        return (x * v.y) - (y * v.x);
    }

    /**
     * Sets this vector to the interpolation by changeAmnt from beginVec to
     * finalVec this=(1-changeAmnt)*beginVec + changeAmnt * finalVec
     *
     * @param beginVec   The begining vector (delta=0)
     * @param finalVec   The final vector to interpolate towards (delta=1)
     * @param changeAmnt An amount between 0.0 - 1.0 representing a precentage change from
     *                   beginVec towards finalVec
     */
    public static Vector2 interpolate(Vector2 beginVec,
                                      Vector2 finalVec, float changeAmnt) {
        return beginVec.interpolate(finalVec, changeAmnt);
    }

    /**
     * <code>smallestAngleBetween</code> returns (in radians) the minimum angle
     * between two vectors. It is assumed that both this vector and the given
     * vector are unit vectors (iow, normalized).
     *
     * @param otherVector a unit vector to find the angle against
     * @return the angle in radians.
     */
    public float smallestAngleBetween(Vector2 otherVector) {
        float dotProduct = dot(otherVector);
        float angle = FastMath.acos(dotProduct);
        return angle;
    }

    /**
     * <code>getAngle</code> returns (in radians) the angle represented by this
     * Vector2 as expressed by a conversion from rectangular coordinates (
     * <code>x</code>,&nbsp;<code>y</code>) to polar coordinates
     * (r,&nbsp;<i>theta</i>).
     *
     * @return the angle in radians. [-pi, pi)
     */
    public float getAngle() {
        return FastMath.atan2(y, x);
    }


    @Override
    protected Vector2 build(float[] v) {
        return new Vector2(v[0], v[1]);
    }

    @Override
    protected Vector2 build(float s) {
        return new Vector2(s);
    }

    /**
     * Saves this Vector2 into the given float[] object.
     *
     * @return The array, with X, Y float values in that order
     */
    @Override
    public float[] toArray() {
        return new float[]{x, y};
    }

    /**
     * <code>angleBetween</code> returns (in radians) the angle required to rotate
     * a ray represented by this vector to lie colinear to a ray described by the
     * given vector. It is assumed that both this vector and the given vector are
     * unit vectors (iow, normalized).
     *
     * @param otherVector the "destination" unit vector
     * @return the angle in radians.
     */
    @Override
    public float angleBetween(Vector2 otherVector) {
        float angle = FastMath.atan2(otherVector.y, otherVector.x)
                - FastMath.atan2(y, x);
        return angle;
    }

    /**
     * are these two vectors the same? they are is they both have the same x and y
     * values.
     *
     * @param o the object to compare for equality
     * @return true if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector2)) {
            return false;
        }

        if (this == o) {
            return true;
        }

        Vector2 comp = (Vector2) o;
        if (Float.compare(x, comp.x) != 0)
            return false;
        if (Float.compare(y, comp.y) != 0)
            return false;
        return true;
    }

    /**
     * <code>toString</code> returns the string representation of this vector
     * object. The format of the string is such: com.jme.math.Vector2 [X=XX.XXXX,
     * Y=YY.YYYY]
     *
     * @return the string representation of this vector.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }



}
