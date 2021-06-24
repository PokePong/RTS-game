package engine.math;

import engine.core.Window;
import engine.math.vector.Quaternion;
import engine.math.vector.Vector3;

/**
 * Représente la translation, la rotation et le scale en un objet
 *
 * @author Loic Besse
 */
public final class Transform {

    /**
     * Le vecteur de position
     */
    private Vector3 translation;

    /**
     * Le quaternion de rotation
     */
    private Quaternion rotation;

    /**
     * Le vecteur de scaling
     */
    private Vector3 scaling;

    /**
     * Constructeur
     * L'ensemble des éléments sont initialisés à 0 sauf scaling à 1.
     */
    public Transform() {
        this(new Vector3(0));
    }

    /**
     * Constructeur selon une translation
     *
     * @param translation
     */
    public Transform(Vector3 translation) {
        this(translation, new Quaternion(), new Vector3(1));
    }

    /**
     * Constructeur
     *
     * @param translation
     * @param rotation
     * @param scaling
     */
    public Transform(Vector3 translation, Quaternion rotation, Vector3 scaling) {
        this.translation = translation;
        this.rotation = rotation;
        this.scaling = scaling;
    }

    /**
     * Déplace selon une direction et une distance
     *
     * @param dir    la direction
     * @param amount la distance
     */
    public void move(Vector3 dir, float amount) {
        setTranslation(translation.add(dir.mul(amount)));
    }

    /**
     * Déplace vers un point selon une interpolation au paramètre delay compris dans [0, 1]
     * Si delay = 0, translation = translation
     * Si delay = 1, translation = nPos
     *
     * @param nPos  le point
     * @param delay le paramètre
     */
    public void moveSmooth(Vector3 nPos, float delay) {
        setTranslation(translation.slerp(nPos, delay));
    }

    /**
     * Déplace d'une distance sur les axe X, Y, Z
     *
     * @param x la distance X
     * @param y la distance Y
     * @param z la distance Z
     */
    public void translate(float x, float y, float z) {
        setTranslation(translation.add(new Vector3(x, y, z)));
    }

    /**
     * Déplace d'une distance sur les axe X, Y, Z
     *
     * @param translation le vecteur quantité
     */
    public void translate(Vector3 translation) {
        setTranslation(translation.add(translation));
    }

    /**
     * Déplace aux coordonnées
     *
     * @param x coord X
     * @param y coord Y
     * @param z coord Z
     */
    public void translateTo(float x, float y, float z) {
        setTranslation(new Vector3(x, y, z));
    }

    /**
     * Déplace au point
     *
     * @param translation le point
     */
    public void translateTo(Vector3 translation) {
        setTranslation(translation);
    }

    /**
     * Rotation par les angles d'euler (en degrée)
     *
     * @param pitch (en degrée)
     * @param yaw   (en degrée)
     * @param roll  (en degrée)
     */
    public void rotate(float pitch, float yaw, float roll) {
        setRotation(rotation.mul(Quaternion.euler(pitch, yaw, roll)));
    }

    /**
     * Rotation selon un angle (en degrée) et un axe
     *
     * @param angle l'angle (en degrée)
     * @param axis  l'axe
     */
    public void rotate(float angle, Vector3 axis) {
        setRotation(rotation.mul(Quaternion.eulerAxis(angle, axis)));
    }

    /**
     * Rotation vers une nouvelle rotation selon une interpolation au paramètre delay [0, 1]
     * Si delay = 0, rotation = rotation
     * Si delay = 1, rotation = nRot
     *
     * @param nRot
     * @param delay
     */
    public void rotateSmooth(Quaternion nRot, float delay) {
        setRotation(rotation.lerp(nRot, delay));
    }

    /**
     * Rotation aux angles d'euler (en degrée)
     *
     * @param pitch (en degrée)
     * @param yaw   (en degrée)
     * @param roll  (en degrée)
     */
    public void rotateTo(float pitch, float yaw, float roll) {
        setRotation(Quaternion.euler(pitch, yaw, roll));
    }

    /**
     * Scale d'une quantité selon les direction X, Y, Z
     *
     * @param scaling
     */
    public void scale(Vector3 scaling) {
        setScaling(scaling.add(scaling));
    }

    /**
     * Scale d'une quantité selon les direction X, Y, Z
     *
     * @param x
     * @param y
     * @param z
     */
    public void scale(float x, float y, float z) {
        scale(new Vector3(x, y, z));
    }

    /**
     * Scale d'une quantité selon un facteur sur les trois directions
     *
     * @param s
     */
    public void scale(float s) {
        scale(s, s, s);
    }

    /**
     * Scale à une quantité selon les direction X, Y, Z
     *
     * @param x
     * @param y
     * @param z
     */
    public void scaleTo(float x, float y, float z) {
        setScaling(new Vector3(x, y, z));
    }

    /**
     * Scale à une quantité
     *
     * @param s
     */
    public void scaleTo(float s) {
        setScaling(new Vector3(s, s, s));
    }

    /**
     * Récupère le vecteur de droite
     *
     * @return le vecteur droit
     */
    public Vector3 right() {
        return rotation.mul(Vector3.RIGHT).normalize();
    }

    /**
     * Récupère le vecteur du haut
     *
     * @return le vecteur haut
     */
    public Vector3 up() {
        return rotation.mul(Vector3.UP).normalize();
    }

    /**
     * Récupère le vecteur vers l'avant
     *
     * @return le vecteur avant
     */
    public Vector3 forward() {
        return rotation.mul(Vector3.FORWARD).normalize();
    }

    /**
     * Récupère le vecteur de translation
     *
     * @return
     */
    public Vector3 getTranslation() {
        return translation;
    }

    /**
     * Récupère la matrice de translation
     */
    public Matrix4 getTranslationMatrix() {
        return Matrix4.translateTo(translation);
    }

    /**
     * Récupère la matrice de rotation
     */
    public Matrix4 getRotationMatrix() {
        return Matrix4.rotateTo(rotation);
    }

    /**
     * Récupère la matrice de scaling
     */
    public Matrix4 getScalingMatrix() {
        return Matrix4.scaleTo(scaling);
    }

    /**
     * Récupère la matrice monde au format TRS
     */
    public Matrix4 getWorldMatrix() {
        return getTranslationMatrix().mul(getRotationMatrix()).mul(getScalingMatrix());
    }

    /**
     * Récupère la matrice de vue
     */
    public Matrix4 getViewMatrix() {
        return Matrix4.lookAt(translation, translation.add(forward()), up());
    }

    /**
     * Récupère la matrice orthographique calculée selon la taille de la fenetre
     */
    public Matrix4 getOrthographicMatrix() {
        return Window.getOrthoMatrix().mul(getWorldMatrix());
    }

    public void setTranslation(Vector3 translation) {
        this.translation = translation;
    }

    /**
     * Récupère la rotation
     */
    public Quaternion getRotation() {
        return rotation;
    }

    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }

    /**
     * Récupère le scaling
     */
    public Vector3 getScaling() {
        return scaling;
    }

    public void setScaling(Vector3 scaling) {
        this.scaling = scaling;
    }

}
