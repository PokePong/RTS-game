package engine.math;

import java.io.Serializable;

public abstract class Vector4A<ResultType extends Vector4A<ResultType>> extends Vector<ResultType> implements Serializable {

    public static final long serialVersionUID = 1;

    protected abstract ResultType build(float x, float y, float z, float w);

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
     * the w value of the vector.
     */
    public final float w;

    /**
     * Constructor instantiates a new <code>Vector4Af</code> with provides
     * values.
     */
    public Vector4A(float s) {
        x = y = z = w = s;
    }


    /**
     * Constructor instantiates a new <code>Vector4Af</code> with provides
     * values.
     *
     * @param x the x value of the vector.
     * @param y the y value of the vector.
     * @param z the z value of the vector.
     * @param w the w value of the vector.
     */
    public Vector4A(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }


    /**
     * are these two vectors the same? they are is they both have the same x,y,
     * and z values.
     *
     * @param o the object to compare for equality
     * @return true if they are equal
     */
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Vector4A)) {
            return false;
        }

        if (this == o) {
            return true;
        }

        @SuppressWarnings("unchecked")
        Vector4A<ResultType> comp = (Vector4A<ResultType>) o;
        if (Float.compare(x, comp.x) != 0) return false;
        if (Float.compare(y, comp.y) != 0) return false;
        if (Float.compare(z, comp.z) != 0) return false;
        if (Float.compare(w, comp.w) != 0) return false;
        return true;
    }

    /**
     * <code>toString</code> returns the string representation of this vector.
     * The format is:
     * <p>
     * org.jme.math.Vector3f [X=XX.XXXX, Y=YY.YYYY, Z=ZZ.ZZZZ, W=WW.WWWW]
     *
     * @return the string representation of this vector.
     */
    @Override
    public final String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }

    public final float getX() {
        return x;
    }

    public final float getY() {
        return y;
    }

    public final float getZ() {
        return z;
    }

    public final float getW() {
        return w;
    }

    /**
     * <code>angleBetween</code> returns (in radians) the angle between two vectors.
     * It is assumed that both this vector and the given vector are unit vectors (iow, normalized).
     *
     * @param otherVector a unit vector to find the angle against
     * @return the angle in radians.
     */
    @Override
    public final float angleBetween(ResultType otherVector) {
        float dotProduct = dot(otherVector);
        float angle = FastMath.acos(dotProduct);
        return angle;
    }


    /**
     * Saves this Vector3f into the given float[] object.
     *
     * @return The array, with X, Y, Z float values in that order
     */
    @Override
    public final float[] toArray() {
        return new float[]{x, y, z, w};
    }

    /**
     * @param index
     * @return x value if index == 0, y value if index == 1 or z value if index ==
     * 2
     * @throws IllegalArgumentException if index is not one of 0, 1, 2.
     */
    public final float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            case 3:
                return w;
        }
        throw new IllegalArgumentException("index must be either 0, 1, 2 or 3");
    }

    @Override
    protected ResultType build(float[] v) {
        return build(v[0], v[1], v[2], v[3]);
    }

    @Override
    protected ResultType build(float s) {
        return build(s);
    }

}
