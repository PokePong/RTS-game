package engine.math;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public final class Matrix4 implements Serializable {

    public static final long serialVersionUID = 1;

    public final float m00, m01, m02, m03;
    public final float m10, m11, m12, m13;
    public final float m20, m21, m22, m23;
    public final float m30, m31, m32, m33;
    public static final Matrix4 ZERO = new Matrix4(0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0);
    public static final Matrix4 IDENTITY = new Matrix4();

    /**
     * Constructor instantiates a new <code>Matrix</code> that is set to the
     * identity matrix.
     */
    public Matrix4() {
        m01 = m02 = m03 = 0.0f;
        m10 = m12 = m13 = 0.0f;
        m20 = m21 = m23 = 0.0f;
        m30 = m31 = m32 = 0.0f;
        m00 = m11 = m22 = m33 = 1.0f;
    }

    /**
     * constructs a matrix with the given values.
     */
    public Matrix4( //
                    float m00, float m01, float m02, float m03, //
                    float m10, float m11, float m12, float m13, //
                    float m20, float m21, float m22, float m23, //
                    float m30, float m31, float m32, float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    /**
     * Create a new Matrix4, given data in column-major format.
     *
     * @param array An array of 16 floats in column-major format (translation in
     *              elements 12, 13 and 14).
     */
    public Matrix4(float[] array) {
        this(array, false);
    }

    public Matrix4(float[] matrix, boolean rowMajor) {
        if (matrix.length != 16) {
            throw new IllegalArgumentException("Array must be of size 16.");
        }

        if (rowMajor) {
            m00 = matrix[0];
            m01 = matrix[1];
            m02 = matrix[2];
            m03 = matrix[3];
            m10 = matrix[4];
            m11 = matrix[5];
            m12 = matrix[6];
            m13 = matrix[7];
            m20 = matrix[8];
            m21 = matrix[9];
            m22 = matrix[10];
            m23 = matrix[11];
            m30 = matrix[12];
            m31 = matrix[13];
            m32 = matrix[14];
            m33 = matrix[15];
        } else {
            m00 = matrix[0];
            m01 = matrix[4];
            m02 = matrix[8];
            m03 = matrix[12];
            m10 = matrix[1];
            m11 = matrix[5];
            m12 = matrix[9];
            m13 = matrix[13];
            m20 = matrix[2];
            m21 = matrix[6];
            m22 = matrix[10];
            m23 = matrix[14];
            m30 = matrix[3];
            m31 = matrix[7];
            m32 = matrix[11];
            m33 = matrix[15];
        }
    }

    /**
     * <code>get</code> retrieves the values of this object into a float array in
     * row-major order.
     *
     * @param matrix the matrix to set the values into.
     */
    public void get(float[] matrix) {
        get(matrix, true);
    }

    /**
     * <code>set</code> retrieves the values of this object into a float array.
     *
     * @param matrix   the matrix to set the values into.
     * @param rowMajor whether the outgoing data is in row or column major order.
     */
    public void get(float[] matrix, boolean rowMajor) {
        if (matrix.length != 16) {
            throw new IllegalArgumentException("Array must be of size 16.");
        }

        if (rowMajor) {
            matrix[0] = m00;
            matrix[1] = m01;
            matrix[2] = m02;
            matrix[3] = m03;
            matrix[4] = m10;
            matrix[5] = m11;
            matrix[6] = m12;
            matrix[7] = m13;
            matrix[8] = m20;
            matrix[9] = m21;
            matrix[10] = m22;
            matrix[11] = m23;
            matrix[12] = m30;
            matrix[13] = m31;
            matrix[14] = m32;
            matrix[15] = m33;
        } else {
            matrix[0] = m00;
            matrix[4] = m01;
            matrix[8] = m02;
            matrix[12] = m03;
            matrix[1] = m10;
            matrix[5] = m11;
            matrix[9] = m12;
            matrix[13] = m13;
            matrix[2] = m20;
            matrix[6] = m21;
            matrix[10] = m22;
            matrix[14] = m23;
            matrix[3] = m30;
            matrix[7] = m31;
            matrix[11] = m32;
            matrix[15] = m33;
        }
    }

    /**
     * <code>get</code> retrieves a value from the matrix at the given position.
     * If the position is invalid a <code>JmeException</code> is thrown.
     *
     * @param i the row index.
     * @param j the colum index.
     * @return the value at (i, j).
     */
    @SuppressWarnings("fallthrough")
    public float get(int i, int j) {
        switch (i) {
            case 0:
                switch (j) {
                    case 0:
                        return m00;
                    case 1:
                        return m01;
                    case 2:
                        return m02;
                    case 3:
                        return m03;
                }
            case 1:
                switch (j) {
                    case 0:
                        return m10;
                    case 1:
                        return m11;
                    case 2:
                        return m12;
                    case 3:
                        return m13;
                }
            case 2:
                switch (j) {
                    case 0:
                        return m20;
                    case 1:
                        return m21;
                    case 2:
                        return m22;
                    case 3:
                        return m23;
                }
            case 3:
                switch (j) {
                    case 0:
                        return m30;
                    case 1:
                        return m31;
                    case 2:
                        return m32;
                    case 3:
                        return m33;
                }
        }
        throw new IllegalArgumentException("Invalid indices into matrix.");
    }

    /**
     * <code>getColumn</code> returns one of three columns specified by the
     * parameter. This column is returned as a float array of length 4.
     *
     * @param i the column to retrieve. Must be between 0 and 3.
     * @return the column specified by the index.
     */
    public float[] getColumn(int i) {
        return getColumn(i, null);
    }

    /**
     * <code>getColumn</code> returns one of three columns specified by the
     * parameter. This column is returned as a float[4].
     *
     * @param i     the column to retrieve. Must be between 0 and 3.
     * @param store the float array to store the result in. if null, a new one is
     *              created.
     * @return the column specified by the index.
     */
    public float[] getColumn(int i, float[] store) {
        if (store == null) {
            store = new float[4];
        }
        switch (i) {
            case 0:
                store[0] = m00;
                store[1] = m10;
                store[2] = m20;
                store[3] = m30;
                break;
            case 1:
                store[0] = m01;
                store[1] = m11;
                store[2] = m21;
                store[3] = m31;
                break;
            case 2:
                store[0] = m02;
                store[1] = m12;
                store[2] = m22;
                store[3] = m32;
                break;
            case 3:
                store[0] = m03;
                store[1] = m13;
                store[2] = m23;
                store[3] = m33;
                break;
            default:
                throw new IllegalArgumentException("Invalid column index. " + i);
        }
        return store;
    }

    public Matrix4 transpose() {
        float[] tmp = new float[16];
        get(tmp, true);
        Matrix4 mat = new Matrix4(tmp);
        return mat;
    }

    /**
     * <code>toFloatBuffer</code> returns a FloatBuffer object that contains the
     * matrix data.
     *
     * @return matrix data as a FloatBuffer.
     */
    public FloatBuffer toFloatBuffer() {
        return toFloatBuffer(false);
    }

    /**
     * <code>toFloatBuffer</code> returns a FloatBuffer object that contains the
     * matrix data.
     *
     * @param columnMajor if true, this buffer should be filled with column major data,
     *                    otherwise it will be filled row major.
     * @return matrix data as a FloatBuffer. The position is set to 0 for
     * convenience.
     */
    public FloatBuffer toFloatBuffer(boolean columnMajor) {
        FloatBuffer fb = ByteBuffer.allocateDirect(4 * 16).asFloatBuffer();
        fillFloatBuffer(fb, columnMajor);
        fb.rewind();
        return fb;
    }

    /**
     * <code>fillFloatBuffer</code> fills a FloatBuffer object with the matrix
     * data.
     *
     * @param fb the buffer to fill, must be correct size
     * @return matrix data as a FloatBuffer.
     */
    public void fillFloatBuffer(FloatBuffer fb) {
        fillFloatBuffer(fb, true);
    }

    /**
     * <code>fillFloatBuffer</code> fills a FloatBuffer object with the matrix
     * data.
     *
     * @param fb          the buffer to fill, starting at current position. Must have room
     *                    for 16 more floats.
     * @param columnMajor if true, this buffer should be filled with column major data,
     *                    otherwise it will be filled row major.
     * @return matrix data as a FloatBuffer. (position is advanced by 16 and any
     * limit set is not changed).
     */
    public void fillFloatBuffer(FloatBuffer fb, boolean columnMajor) {
        if (columnMajor) {
            fb.put(m00).put(m10).put(m20).put(m30);
            fb.put(m01).put(m11).put(m21).put(m31);
            fb.put(m02).put(m12).put(m22).put(m32);
            fb.put(m03).put(m13).put(m23).put(m33);
        } else {
            fb.put(m00).put(m01).put(m02).put(m03);
            fb.put(m10).put(m11).put(m12).put(m13);
            fb.put(m20).put(m21).put(m22).put(m23);
            fb.put(m30).put(m31).put(m32).put(m33);
        }
    }

    public void fillFloatArray(float[] f) {
        fillFloatArray(f, false);
    }

    public void fillFloatArray(float[] f, boolean columnMajor) {
        if (columnMajor) {
            f[0] = m00;
            f[1] = m10;
            f[2] = m20;
            f[3] = m30;
            f[4] = m01;
            f[5] = m11;
            f[6] = m21;
            f[7] = m31;
            f[8] = m02;
            f[9] = m12;
            f[10] = m22;
            f[11] = m32;
            f[12] = m03;
            f[13] = m13;
            f[14] = m23;
            f[15] = m33;
        } else {
            f[0] = m00;
            f[1] = m01;
            f[2] = m02;
            f[3] = m03;
            f[4] = m10;
            f[5] = m11;
            f[6] = m12;
            f[7] = m13;
            f[8] = m20;
            f[9] = m21;
            f[10] = m22;
            f[11] = m23;
            f[12] = m30;
            f[13] = m31;
            f[14] = m32;
            f[15] = m33;
        }
    }

    /**
     * <code>readFloatBuffer</code> reads value for this matrix from a
     * FloatBuffer.
     *
     * @param fb the buffer to read from, must be correct size
     * @return this data as a FloatBuffer.
     */
    public Matrix4(FloatBuffer fb) {
        this(fb, false);
    }

    /**
     * <code>readFloatBuffer</code> reads value for this matrix from a
     * FloatBuffer.
     *
     * @param fb          the buffer to read from, must be correct size
     * @param columnMajor if true, this buffer should be filled with column major data,
     *                    otherwise it will be filled row major.
     * @return this data as a FloatBuffer.
     */
    public Matrix4(FloatBuffer fb, boolean columnMajor) {

        if (columnMajor) {
            m00 = fb.get();
            m10 = fb.get();
            m20 = fb.get();
            m30 = fb.get();
            m01 = fb.get();
            m11 = fb.get();
            m21 = fb.get();
            m31 = fb.get();
            m02 = fb.get();
            m12 = fb.get();
            m22 = fb.get();
            m32 = fb.get();
            m03 = fb.get();
            m13 = fb.get();
            m23 = fb.get();
            m33 = fb.get();
        } else {
            m00 = fb.get();
            m01 = fb.get();
            m02 = fb.get();
            m03 = fb.get();
            m10 = fb.get();
            m11 = fb.get();
            m12 = fb.get();
            m13 = fb.get();
            m20 = fb.get();
            m21 = fb.get();
            m22 = fb.get();
            m23 = fb.get();
            m30 = fb.get();
            m31 = fb.get();
            m32 = fb.get();
            m33 = fb.get();
        }
    }

    private static class Matrix4Temp {
        public float m00, m01, m02, m03;
        public float m10, m11, m12, m13;
        public float m20, m21, m22, m23;
        public float m30, m31, m32, m33;

        public Matrix4Temp() {
            m01 = m02 = m03 = 0.0f;
            m10 = m12 = m13 = 0.0f;
            m20 = m21 = m23 = 0.0f;
            m30 = m31 = m32 = 0.0f;
            m00 = m11 = m22 = m33 = 1.0f;
        }
    }

    /**
     * <code>set</code> sets the values of this matrix from an array of values.
     *
     * @param m the matrix to set the value to.
     */
    private Matrix4(Matrix4Temp m) {
        m00 = m.m00;
        m01 = m.m01;
        m02 = m.m02;
        m03 = m.m03;
        m10 = m.m10;
        m11 = m.m11;
        m12 = m.m12;
        m13 = m.m13;
        m20 = m.m20;
        m21 = m.m21;
        m22 = m.m22;
        m23 = m.m23;
        m30 = m.m30;
        m31 = m.m31;
        m32 = m.m32;
        m33 = m.m33;
    }

    /**
     * <code>fromAngleAxis</code> sets this Matrix4 to the values specified by an
     * angle and an axis of rotation. This method creates an object, so use
     * fromAngleNormalAxis if your axis is already normalized.
     *
     * @param angle the angle to rotate (in radians).
     * @param axis  the axis of rotation.
     */
    public static Matrix4 fromAngleAxis(float angle, Vector3 axis) {
        Vector3 normAxis = axis.normalize();
        return fromAngleNormalAxis(angle, normAxis);
    }

    /**
     * <code>fromAngleNormalAxis</code> sets this Matrix4 to the values specified
     * by an angle and a normalized axis of rotation.
     *
     * @param angle the angle to rotate (in radians).
     * @param axis  the axis of rotation (already normalized).
     */
    public static Matrix4 fromAngleNormalAxis(float angle, Vector3 axis) {
        Matrix4Temp m = new Matrix4Temp();

        float fCos = FastMath.cos(angle);
        float fSin = FastMath.sin(angle);
        float fOneMinusCos = ((float) 1.0) - fCos;
        float fX2 = axis.x * axis.x;
        float fY2 = axis.y * axis.y;
        float fZ2 = axis.z * axis.z;
        float fXYM = axis.x * axis.y * fOneMinusCos;
        float fXZM = axis.x * axis.z * fOneMinusCos;
        float fYZM = axis.y * axis.z * fOneMinusCos;
        float fXSin = axis.x * fSin;
        float fYSin = axis.y * fSin;
        float fZSin = axis.z * fSin;

        m.m00 = fX2 * fOneMinusCos + fCos;
        m.m01 = fXYM - fZSin;
        m.m02 = fXZM + fYSin;
        m.m10 = fXYM + fZSin;
        m.m11 = fY2 * fOneMinusCos + fCos;
        m.m12 = fYZM - fXSin;
        m.m20 = fXZM - fYSin;
        m.m21 = fYZM + fXSin;
        m.m22 = fZ2 * fOneMinusCos + fCos;
        return new Matrix4(m);
    }

    /**
     * <code>mult</code> multiplies this matrix by a scalar.
     *
     * @param scalar the scalar to multiply this matrix by.
     */

    public Matrix4 mul(float scalar) {
        return new Matrix4(m00 * scalar, m01 * scalar, m02 * scalar, m03 * scalar,
                m10 * scalar, m11 * scalar, m12 * scalar, m13 * scalar, m20 * scalar,
                m21 * scalar, m22 * scalar, m23 * scalar, m30 * scalar, m31 * scalar,
                m32 * scalar, m33 * scalar);
    }

    /**
     * <code>mult</code> multiplies this matrix with another matrix. The result
     * matrix will then be returned. This matrix will be on the left hand side,
     * while the parameter matrix will be on the right.
     *
     * @param in2 the matrix to multiply this matrix by.
     * @return the resultant matrix
     */
    public Matrix4 mul(Matrix4 in2) {
        Matrix4Temp store = new Matrix4Temp();

        float temp00, temp01, temp02, temp03;
        float temp10, temp11, temp12, temp13;
        float temp20, temp21, temp22, temp23;
        float temp30, temp31, temp32, temp33;

        temp00 = m00 * in2.m00 + m01 * in2.m10 + m02 * in2.m20 + m03 * in2.m30;
        temp01 = m00 * in2.m01 + m01 * in2.m11 + m02 * in2.m21 + m03 * in2.m31;
        temp02 = m00 * in2.m02 + m01 * in2.m12 + m02 * in2.m22 + m03 * in2.m32;
        temp03 = m00 * in2.m03 + m01 * in2.m13 + m02 * in2.m23 + m03 * in2.m33;

        temp10 = m10 * in2.m00 + m11 * in2.m10 + m12 * in2.m20 + m13 * in2.m30;
        temp11 = m10 * in2.m01 + m11 * in2.m11 + m12 * in2.m21 + m13 * in2.m31;
        temp12 = m10 * in2.m02 + m11 * in2.m12 + m12 * in2.m22 + m13 * in2.m32;
        temp13 = m10 * in2.m03 + m11 * in2.m13 + m12 * in2.m23 + m13 * in2.m33;

        temp20 = m20 * in2.m00 + m21 * in2.m10 + m22 * in2.m20 + m23 * in2.m30;
        temp21 = m20 * in2.m01 + m21 * in2.m11 + m22 * in2.m21 + m23 * in2.m31;
        temp22 = m20 * in2.m02 + m21 * in2.m12 + m22 * in2.m22 + m23 * in2.m32;
        temp23 = m20 * in2.m03 + m21 * in2.m13 + m22 * in2.m23 + m23 * in2.m33;

        temp30 = m30 * in2.m00 + m31 * in2.m10 + m32 * in2.m20 + m33 * in2.m30;
        temp31 = m30 * in2.m01 + m31 * in2.m11 + m32 * in2.m21 + m33 * in2.m31;
        temp32 = m30 * in2.m02 + m31 * in2.m12 + m32 * in2.m22 + m33 * in2.m32;
        temp33 = m30 * in2.m03 + m31 * in2.m13 + m32 * in2.m23 + m33 * in2.m33;

        store.m00 = temp00;
        store.m01 = temp01;
        store.m02 = temp02;
        store.m03 = temp03;
        store.m10 = temp10;
        store.m11 = temp11;
        store.m12 = temp12;
        store.m13 = temp13;
        store.m20 = temp20;
        store.m21 = temp21;
        store.m22 = temp22;
        store.m23 = temp23;
        store.m30 = temp30;
        store.m31 = temp31;
        store.m32 = temp32;
        store.m33 = temp33;

        return new Matrix4(store);
    }

    public Vector3 mul(Vector3 v) {
        Vector4 result = mul(new Vector4(v, 1));
        return new Vector3(result.x, result.y, result.z);
    }

    /**
     * <code>mult</code> multiplies a <code>Vector4f</code> about a rotation
     * matrix. The resulting vector is returned as a new <code>Vector4f</code>.
     *
     * @param v vec to multiply against.
     * @return the rotated vector.
     */
    public Vector4 mul(Vector4 v) {
        return new Vector4(m00 * v.x + m01 * v.y + m02 * v.z + m03 * v.w, m10
                * v.x + m11 * v.y + m12 * v.z + m13 * v.w, m20 * v.x + m21 * v.y + m22
                * v.z + m23 * v.w, m30 * v.x + m31 * v.y + m32 * v.z + m33 * v.w);
    }

    /**
     * Inverts this matrix as a new Matrix4.
     *
     * @return The new inverse matrix
     */
    public Matrix4 invert() {
        Matrix4Temp store = new Matrix4Temp();
        float fA0 = m00 * m11 - m01 * m10;
        float fA1 = m00 * m12 - m02 * m10;
        float fA2 = m00 * m13 - m03 * m10;
        float fA3 = m01 * m12 - m02 * m11;
        float fA4 = m01 * m13 - m03 * m11;
        float fA5 = m02 * m13 - m03 * m12;
        float fB0 = m20 * m31 - m21 * m30;
        float fB1 = m20 * m32 - m22 * m30;
        float fB2 = m20 * m33 - m23 * m30;
        float fB3 = m21 * m32 - m22 * m31;
        float fB4 = m21 * m33 - m23 * m31;
        float fB5 = m22 * m33 - m23 * m32;
        float fDet = fA0 * fB5 - fA1 * fB4 + fA2 * fB3 + fA3 * fB2 - fA4 * fB1
                + fA5 * fB0;

        if (FastMath.abs(fDet) <= 0f) {
            throw new ArithmeticException("This matrix cannot be inverted");
        }

        store.m00 = +m11 * fB5 - m12 * fB4 + m13 * fB3;
        store.m10 = -m10 * fB5 + m12 * fB2 - m13 * fB1;
        store.m20 = +m10 * fB4 - m11 * fB2 + m13 * fB0;
        store.m30 = -m10 * fB3 + m11 * fB1 - m12 * fB0;
        store.m01 = -m01 * fB5 + m02 * fB4 - m03 * fB3;
        store.m11 = +m00 * fB5 - m02 * fB2 + m03 * fB1;
        store.m21 = -m00 * fB4 + m01 * fB2 - m03 * fB0;
        store.m31 = +m00 * fB3 - m01 * fB1 + m02 * fB0;
        store.m02 = +m31 * fA5 - m32 * fA4 + m33 * fA3;
        store.m12 = -m30 * fA5 + m32 * fA2 - m33 * fA1;
        store.m22 = +m30 * fA4 - m31 * fA2 + m33 * fA0;
        store.m32 = -m30 * fA3 + m31 * fA1 - m32 * fA0;
        store.m03 = -m21 * fA5 + m22 * fA4 - m23 * fA3;
        store.m13 = +m20 * fA5 - m22 * fA2 + m23 * fA1;
        store.m23 = -m20 * fA4 + m21 * fA2 - m23 * fA0;
        store.m33 = +m20 * fA3 - m21 * fA1 + m22 * fA0;
        return new Matrix4(store).mul(1.0f / fDet);
    }

    /**
     * Returns a new matrix representing the adjoint of this matrix.
     *
     * @return The adjoint matrix
     */
    public Matrix4 adjoint() {
        Matrix4Temp store = new Matrix4Temp();

        float fA0 = m00 * m11 - m01 * m10;
        float fA1 = m00 * m12 - m02 * m10;
        float fA2 = m00 * m13 - m03 * m10;
        float fA3 = m01 * m12 - m02 * m11;
        float fA4 = m01 * m13 - m03 * m11;
        float fA5 = m02 * m13 - m03 * m12;
        float fB0 = m20 * m31 - m21 * m30;
        float fB1 = m20 * m32 - m22 * m30;
        float fB2 = m20 * m33 - m23 * m30;
        float fB3 = m21 * m32 - m22 * m31;
        float fB4 = m21 * m33 - m23 * m31;
        float fB5 = m22 * m33 - m23 * m32;

        store.m00 = +m11 * fB5 - m12 * fB4 + m13 * fB3;
        store.m10 = -m10 * fB5 + m12 * fB2 - m13 * fB1;
        store.m20 = +m10 * fB4 - m11 * fB2 + m13 * fB0;
        store.m30 = -m10 * fB3 + m11 * fB1 - m12 * fB0;
        store.m01 = -m01 * fB5 + m02 * fB4 - m03 * fB3;
        store.m11 = +m00 * fB5 - m02 * fB2 + m03 * fB1;
        store.m21 = -m00 * fB4 + m01 * fB2 - m03 * fB0;
        store.m31 = +m00 * fB3 - m01 * fB1 + m02 * fB0;
        store.m02 = +m31 * fA5 - m32 * fA4 + m33 * fA3;
        store.m12 = -m30 * fA5 + m32 * fA2 - m33 * fA1;
        store.m22 = +m30 * fA4 - m31 * fA2 + m33 * fA0;
        store.m32 = -m30 * fA3 + m31 * fA1 - m32 * fA0;
        store.m03 = -m21 * fA5 + m22 * fA4 - m23 * fA3;
        store.m13 = +m20 * fA5 - m22 * fA2 + m23 * fA1;
        store.m23 = -m20 * fA4 + m21 * fA2 - m23 * fA0;
        store.m33 = +m20 * fA3 - m21 * fA1 + m22 * fA0;

        return new Matrix4(store);
    }

    /**
     * <code>determinant</code> generates the determinate of this matrix.
     *
     * @return the determinate
     */
    public float determinant() {
        float fA0 = m00 * m11 - m01 * m10;
        float fA1 = m00 * m12 - m02 * m10;
        float fA2 = m00 * m13 - m03 * m10;
        float fA3 = m01 * m12 - m02 * m11;
        float fA4 = m01 * m13 - m03 * m11;
        float fA5 = m02 * m13 - m03 * m12;
        float fB0 = m20 * m31 - m21 * m30;
        float fB1 = m20 * m32 - m22 * m30;
        float fB2 = m20 * m33 - m23 * m30;
        float fB3 = m21 * m32 - m22 * m31;
        float fB4 = m21 * m33 - m23 * m31;
        float fB5 = m22 * m33 - m23 * m32;
        float fDet = fA0 * fB5 - fA1 * fB4 + fA2 * fB3 + fA3 * fB2 - fA4 * fB1
                + fA5 * fB0;
        return fDet;
    }

    public Matrix4 add(Matrix4 mat) {
        Matrix4Temp result = new Matrix4Temp();
        result.m00 = this.m00 + mat.m00;
        result.m01 = this.m01 + mat.m01;
        result.m02 = this.m02 + mat.m02;
        result.m03 = this.m03 + mat.m03;
        result.m10 = this.m10 + mat.m10;
        result.m11 = this.m11 + mat.m11;
        result.m12 = this.m12 + mat.m12;
        result.m13 = this.m13 + mat.m13;
        result.m20 = this.m20 + mat.m20;
        result.m21 = this.m21 + mat.m21;
        result.m22 = this.m22 + mat.m22;
        result.m23 = this.m23 + mat.m23;
        result.m30 = this.m30 + mat.m30;
        result.m31 = this.m31 + mat.m31;
        result.m32 = this.m32 + mat.m32;
        result.m33 = this.m33 + mat.m33;
        return new Matrix4(result);
    }

    public Vector3 toTranslationVector() {
        return new Vector3(m03, m13, m23);
    }

    @Override
    public String toString() {
        return "[(" + m00 + ", " + m01 + ", " + m02 + ", " + m03 + ")\n"
                + "(" + m10 + ", " + m11 + ", " + m12 + ", " + m13 + ")\n"
                + "(" + m20 + ", " + m21 + ", " + m22 + ", " + m23 + ")\n"
                + "(" + m30 + ", " + m31 + ", " + m32 + ", " + m33 + ")]";
    }

    /**
     * are these two matrices the same? they are is they both have the same mXX
     * values.
     *
     * @param o the object to compare for equality
     * @return true if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Matrix4) || o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }

        Matrix4 comp = (Matrix4) o;
        if (Float.compare(m00, comp.m00) != 0) {
            return false;
        }
        if (Float.compare(m01, comp.m01) != 0) {
            return false;
        }
        if (Float.compare(m02, comp.m02) != 0) {
            return false;
        }
        if (Float.compare(m03, comp.m03) != 0) {
            return false;
        }

        if (Float.compare(m10, comp.m10) != 0) {
            return false;
        }
        if (Float.compare(m11, comp.m11) != 0) {
            return false;
        }
        if (Float.compare(m12, comp.m12) != 0) {
            return false;
        }
        if (Float.compare(m13, comp.m13) != 0) {
            return false;
        }

        if (Float.compare(m20, comp.m20) != 0) {
            return false;
        }
        if (Float.compare(m21, comp.m21) != 0) {
            return false;
        }
        if (Float.compare(m22, comp.m22) != 0) {
            return false;
        }
        if (Float.compare(m23, comp.m23) != 0) {
            return false;
        }

        if (Float.compare(m30, comp.m30) != 0) {
            return false;
        }
        if (Float.compare(m31, comp.m31) != 0) {
            return false;
        }
        if (Float.compare(m32, comp.m32) != 0) {
            return false;
        }
        if (Float.compare(m33, comp.m33) != 0) {
            return false;
        }
        return true;
    }

    /**
     * @return true if this matrix is identity
     */
    public boolean isIdentity() {
        return (m00 == 1 && m01 == 0 && m02 == 0 && m03 == 0)
                && (m10 == 0 && m11 == 1 && m12 == 0 && m13 == 0)
                && (m20 == 0 && m21 == 0 && m22 == 1 && m23 == 0)
                && (m30 == 0 && m31 == 0 && m32 == 0 && m33 == 1);
    }

    static boolean equalIdentity(Matrix4 mat) {
        if (Math.abs(mat.m00 - 1) > 1e-4) {
            return false;
        }
        if (Math.abs(mat.m11 - 1) > 1e-4) {
            return false;
        }
        if (Math.abs(mat.m22 - 1) > 1e-4) {
            return false;
        }
        if (Math.abs(mat.m33 - 1) > 1e-4) {
            return false;
        }

        if (Math.abs(mat.m01) > 1e-4) {
            return false;
        }
        if (Math.abs(mat.m02) > 1e-4) {
            return false;
        }
        if (Math.abs(mat.m03) > 1e-4) {
            return false;
        }

        if (Math.abs(mat.m10) > 1e-4) {
            return false;
        }
        if (Math.abs(mat.m12) > 1e-4) {
            return false;
        }
        if (Math.abs(mat.m13) > 1e-4) {
            return false;
        }

        if (Math.abs(mat.m20) > 1e-4) {
            return false;
        }
        if (Math.abs(mat.m21) > 1e-4) {
            return false;
        }
        if (Math.abs(mat.m23) > 1e-4) {
            return false;
        }

        if (Math.abs(mat.m30) > 1e-4) {
            return false;
        }
        if (Math.abs(mat.m31) > 1e-4) {
            return false;
        }
        if (Math.abs(mat.m32) > 1e-4) {
            return false;
        }
        return true;
    }


    public static Matrix4 translateTo(Vector3 v) {
        Matrix4Temp m = new Matrix4Temp();
        m.m03 = v.x;
        m.m13 = v.y;
        m.m23 = v.z;
        return new Matrix4(m);
    }

    public static Matrix4 rotateTo(float angle, Vector3 axis) {
        return rotateTo(Quaternion.eulerAxis(angle, axis));
    }

    public static Matrix4 rotateTo(Quaternion q) {
        return q.toRotationMatrix4();
    }

    public static Matrix4 scaleTo(float f) {
        return scaleTo(new Vector3(f, f, f));
    }

    public static Matrix4 scaleTo(Vector3 v) {
        Matrix4Temp m = new Matrix4Temp();
        m.m00 *= v.x;
        m.m11 *= v.y;
        m.m22 *= v.z;
        return new Matrix4(m);
    }

    public Matrix4 translate(Vector3 v) {
        return mul(translateTo(v));
    }

    public Matrix4 translate(float x, float y, float z) {
        return translate(new Vector3(x, y, z));
    }

    public Matrix4 rotate(Quaternion q) {
        return mul(rotateTo(q));
    }

    public Matrix4 rotate(float angle, Vector3 axis) {
        return mul(rotateTo(angle, axis));
    }

    public Matrix4 scale(float f) {
        return mul(scaleTo(f));
    }

    public Matrix4 scale(Vector3 v) {
        return mul(scaleTo(v));
    }

    public Matrix4 scale(float x, float y, float z) {
        return scale(new Vector3(x, y, z));
    }

    public static Matrix4 orthographic(float left, float right, float bottom,
                                       float top, float near, float far) {
        float x_orth = 2 / (right - left);
        float y_orth = 2 / (top - bottom);
        float z_orth = -2 / (far - near);

        float tx = -(right + left) / (right - left);
        float ty = -(top + bottom) / (top - bottom);
        float tz = -(far + near) / (far - near);
        Matrix4Temp m = new Matrix4Temp();

        m.m00 = x_orth;
        m.m10 = 0;
        m.m20 = 0;
        m.m30 = 0;
        m.m01 = 0;
        m.m11 = y_orth;
        m.m21 = 0;
        m.m31 = 0;
        m.m02 = 0;
        m.m12 = 0;
        m.m22 = z_orth;
        m.m32 = 0;
        m.m03 = tx;
        m.m13 = ty;
        m.m23 = tz;
        m.m33 = 1;
        return new Matrix4(m);
    }

    public static Matrix4 lookAt(Vector3 eye, Vector3 target, Vector3 up) {
        Vector3 f = target.sub(eye).normalize();
        Vector3 s = f.cross(up).normalize();

        Matrix4Temp m = new Matrix4Temp();
        m.m00 = s.x;
        m.m01 = s.y;
        m.m02 = s.z;
        m.m10 = up.x;
        m.m11 = up.y;
        m.m12 = up.z;
        m.m20 = -f.x;
        m.m21 = -f.y;
        m.m22 = -f.z;
        m.m03 = -s.dot(eye);
        m.m13 = -up.dot(eye);
        m.m23 = f.dot(eye);
        return new Matrix4(m);
    }

    public static Matrix4 fromFrustum(float near, float far, float left, float right, float top, float bottom) {
        Matrix4Temp m = new Matrix4Temp();

        m.m00 = (2.0f * near) / (right - left);
        m.m11 = (2.0f * near) / (top - bottom);
        m.m32 = -1.0f;
        m.m33 = 0.0f;

        // A
        m.m02 = (right + left) / (right - left);

        // B
        m.m12 = (top + bottom) / (top - bottom);

        // C
        m.m22 = -(far + near) / (far - near);

        // D
        m.m23 = -(2.0f * far * near) / (far - near);

        return new Matrix4(m);
    }

    public static Matrix4 perspective(float fovy, float aspect, float zNear, float zFar) {
        Matrix4Temp ret = new Matrix4Temp();

        float tanFOV = (float) Math.tan(Math.toRadians(fovy / 2));
        float length = zFar - zNear;

        ret.m00 = 1 / (aspect * tanFOV);
        ret.m11 = 1 / tanFOV;
        ret.m22 = (-(zFar + zNear) / length);
        ret.m32 = (-1);
        ret.m23 = (-(2 * zNear * zFar) / length);
        ret.m33 = (0);

        return new Matrix4(ret);

    }

}
