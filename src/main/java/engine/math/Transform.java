package engine.math;

import engine.core.Window;
import engine.util.Debug;

public final class Transform {

    private Vector3 translation;
    private Quaternion rotation;
    private Vector3 scaling;

    public Transform() {
        this(new Vector3(0));
    }

    public Transform(Vector3 translation) {
        this(translation, new Quaternion(), new Vector3(1));
    }

    public Transform(Vector3 translation, Quaternion rotation, Vector3 scaling) {
        this.translation = translation;
        this.rotation = rotation;
        this.scaling = scaling;
    }

    public void move(Vector3 dir, float amount) {
        setTranslation(translation.add(dir.mul(amount)));
    }

    public void moveSmooth(Vector3 nPos, float delay) {
        setTranslation(translation.slerp(nPos, delay));
    }

    public void translate(float x, float y, float z) {
        setTranslation(translation.add(new Vector3(x, y, z)));
    }

    public void translate(Vector3 translation) {
        setTranslation(translation.add(translation));
    }

    public void translateTo(float x, float y, float z) {
        setTranslation(new Vector3(x, y, z));
    }

    public void translateTo(Vector3 translation) {
        setTranslation(translation);
    }

    public void rotate(float pitch, float yaw, float roll) {
        setRotation(rotation.mul(Quaternion.euler(pitch, yaw, roll)));
    }

    public void rotate(float angle, Vector3 axis) {
        setRotation(rotation.mul(Quaternion.eulerAxis(angle, axis)));
    }

    public void rotateSmooth(Quaternion nRot, float delay) {
        setRotation(rotation.lerp(nRot, delay));
    }

    public void rotateTo(float pitch, float yaw, float roll) {
        setRotation(Quaternion.euler(pitch, yaw, roll));
    }

    public void scale(Vector3 scaling) {
        setScaling(scaling.add(scaling));
    }

    public void scale(float x, float y, float z) {
        scale(new Vector3(x, y, z));
    }

    public void scale(float s) {
        scale(s, s, s);
    }

    public void scaleTo(float x, float y, float z) {
        setScaling(new Vector3(x, y, z));
    }

    public void scaleTo(float s) {
        setScaling(new Vector3(s, s, s));
    }

    public Vector3 right() {
        return rotation.mul(Vector3.RIGHT).normalize();
    }

    public Vector3 up() {
        return rotation.mul(Vector3.UP).normalize();
    }

    public Vector3 forward() {
        return rotation.mul(Vector3.FORWARD).normalize();
    }

    public Vector3 getTranslation() {
        return translation;
    }

    public Matrix4 getTranslationMatrix() {
        return Matrix4.translateTo(translation);
    }

    public Matrix4 getRotationMatrix() {
        return Matrix4.rotateTo(rotation);
    }

    public Matrix4 getScalingMatrix() {
        return Matrix4.scaleTo(scaling);
    }

    public Matrix4 getWorldMatrix() {
        return getTranslationMatrix().mul(getRotationMatrix()).mul(getScalingMatrix());
    }

    public Matrix4 getViewMatrix() {
        return Matrix4.lookAt(translation, translation.add(forward()), up());
    }

    public Matrix4 getOrthographicMatrix() {
        return Window.getOrthoMatrix().mul(getWorldMatrix());
    }

    public void setTranslation(Vector3 translation) {
        this.translation = translation;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }

    public Vector3 getScaling() {
        return scaling;
    }

    public void setScaling(Vector3 scaling) {
        this.scaling = scaling;
    }

}
