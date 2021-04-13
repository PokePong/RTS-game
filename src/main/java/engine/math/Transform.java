package engine.math;

import engine.core.Window;
import engine.util.Debug;
import engine.util.Utils;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Transform {

    private Vector3f translation;
    private EulerAngle rotation;
    private Vector3f scaling;

    public Transform() {
        setTranslation(new Vector3f(0, 0, 0));
        setRotation(new EulerAngle(0, 0, 0));
        setScaling(new Vector3f(1, 1, 1));
    }

    public Transform(Vector3f translation) {
        setTranslation(translation);
        setRotation(new EulerAngle(0, 0, 0));
        setScaling(new Vector3f(1, 1, 1));
    }

    public Transform translate(float dx, float dy, float dz) {
        return translate(new Vector3f(dx, dy, dz));
    }

    public Transform translate(Vector3f translation) {
        setTranslation(getTranslation().add(translation));
        return this;
    }

    public Transform translateTo(float x, float y, float z) {
        return translateTo(new Vector3f(x, y, z));
    }

    public Transform translateTo(Vector3f translation) {
        setTranslation(new Vector3f(translation));
        return this;
    }

    public void rotate(float xAmount, float yAmount, float zAmount) {
        rotation.add(xAmount, yAmount, zAmount);
    }

    public void move(Vector3f dir, float amount) {
        Vector3f move = new Vector3f(dir).mul(amount);
        translation.add(move);
    }

    public void moveSmooth(Vector3f nPos, float delay) {
        translation.lerp(nPos, delay);
    }

    public void rotate(float pitchAmount, float yawAmount) {
        rotation.setPitch(rotation.getPitch() + pitchAmount);
        rotation.setYaw(rotation.getYaw() + yawAmount);
    }

    public void rotateSmooth(EulerAngle nAngles, float delay) {
        float pitchLerp = Utils.lerp(rotation.getPitch(), nAngles.getPitch(), delay);
        float yawLerp = Utils.lerp(rotation.getYaw(), nAngles.getYaw(), delay);
        rotation.setPitch(pitchLerp);
        rotation.setYaw(yawLerp);
    }

    public Vector3f forward() {
        return rotation.forward().normalize();
    }

    public Vector3f up() {
        Vector4f res = new Vector4f();
        getModelMatrix().getColumn(1, res);
        return new Vector3f(res.x, res.y, res.z).normalize();
    }

    public Vector3f right() {
        Vector3f res = new Vector3f();
        forward().cross(new Vector3f(0, 1, 0), res);
        return new Vector3f(res.x, res.y, res.z).normalize();
    }

    public Matrix4f getViewMatrix() {
        Matrix4f result = new Matrix4f();
        Vector3f temp = new Vector3f();
        translation.add(forward(), temp);
        result.lookAt(translation, temp, new Vector3f(0, 1, 0));
        return result;
    }

    public Matrix4f getTranslationMatrix() {
        Matrix4f ret = new Matrix4f().identity();
        ret.translate(translation);
        return ret;
    }

    public Matrix4f getRotationMatrix() {
        Matrix4f ret = new Matrix4f().identity();
        ret.rotateXYZ(rotation.getRotationRadian());
        return ret;
    }

    public Matrix4f getScalingMatrix() {
        Matrix4f ret = new Matrix4f().identity();
        ret.scale(scaling);
        return ret;
    }

    public Matrix4f getWorldMatrix() {
        return getTranslationMatrix().mul(getRotationMatrix().mul(getScalingMatrix()));
    }

    public Matrix4f getWorldMatrixTRS() {
        Matrix4f translationMatrix = new Matrix4f().translation(translation);
        Matrix4f rotationMatrix = new Matrix4f().rotateXYZ(rotation.getRotationRadian());
        Matrix4f scalingMatrix = new Matrix4f().scaling(scaling);
        return translationMatrix.mul(rotationMatrix.mul(scalingMatrix));
    }

    public Matrix4f getWorldMatrixRTS() {
        Matrix4f translationMatrix = new Matrix4f().translation(translation);
        Matrix4f rotationMatrix = new Matrix4f().rotateXYZ(rotation.getRotationRadian());
        Matrix4f scalingMatrix = new Matrix4f().scaling(scaling);
        return rotationMatrix.mul(translationMatrix.mul(scalingMatrix));
    }

    public Matrix4f getWorldMatrixSRT() {
        Matrix4f translationMatrix = new Matrix4f().translation(translation);
        Matrix4f rotationMatrix = new Matrix4f().rotateXYZ(rotation.getRotationRadian());
        Matrix4f scalingMatrix = new Matrix4f().scaling(scaling);
        return scalingMatrix.mul(rotationMatrix.mul(translationMatrix));
    }

    public Matrix4f getModelMatrix() {
        Matrix4f rotationMatrix = new Matrix4f().rotateXYZ(rotation.getRotationRadian());
        return rotationMatrix;
    }

    public Matrix4f getOrthoMatrix() {
        return Window.getOrthoMatrix().mul(getWorldMatrix());
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public void setTranslation(float x, float y, float z) {
        this.translation = new Vector3f(x, y, z);
    }

    public EulerAngle getRotation() {
        return rotation;
    }

    public void setRotation(EulerAngle rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation = new EulerAngle(x, y, z);
    }

    public Vector3f getScaling() {
        return scaling;
    }

    public void setScaling(Vector3f scaling) {
        this.scaling = scaling;
    }

    public void setScaling(float x, float y, float z) {
        this.scaling = new Vector3f(x, y, z);
    }

    public void setScaling(float s) {
        this.scaling = new Vector3f(s, s, s);
    }

}
