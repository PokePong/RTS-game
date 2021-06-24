package engine.math.geometry;

import engine.math.FastMath;
import engine.math.vector.Vector3;
import engine.util.Utils;

/**
 * Plane définit un plan où Normal vectoriel (x, y, z) = Constant
 * Cette classe fournit des méthodes pour calculer la distance à un point depuis
 * ce plan. La distance n'a pas de valeur réelle vu qu'elle peut être négative si
 * le point est sur le côté non-normal du plan
 *
 * @author Loic Besse
 */
public class Plane {

    /**
     * Décrit la relation entre un point et un plan
     */
    public enum Side {
        /**
         * Un point avec une distance positive
         */
        POSITIVE,
        /**
         * Un point avec une distance négative
         */
        NEGATIVE,
        /**
         * Un point sur le plan
         */
        NONE;
    }

    /**
     * Le vecteur normal du plan
     */
    private Vector3 normal;

    /**
     * La constante du plan
     */
    private float constant;

    /**
     * Constructeur dont le plan sera définit par une normale et un point
     *
     * @param normal la normale
     * @param pos    un point sur le plan
     */
    public Plane(Vector3 normal, Vector3 pos) {
        this.normal = normal.normalize();
        this.constant = -normal.dot(pos);
    }

    /**
     * Constructeur dont le plan sera définit par 3 points
     *
     * @param a premier point
     * @param b deuxième point
     * @param c troisième point
     */
    public Plane(Vector3 a, Vector3 b, Vector3 c) {
        Vector3 x = b.sub(a);
        Vector3 y = c.sub(a);
        this.normal = x.cross(y).normalize();
        this.constant = -normal.dot(a);
    }

    /**
     * Constructeur dont le plan sera définit par une normale et une constante
     *
     * @param normal   la normale
     * @param constant la constante
     */
    public Plane(Vector3 normal, float constant) {
        this.normal = normal.normalize();
        this.constant = constant;
    }

    /**
     * Place le plan selon une normale et une position
     *
     * @param normal la normale
     * @param pos    la position
     */
    public void set(Vector3 normal, Vector3 pos) {
        this.normal = normal.normalize();
        this.constant = -normal.dot(pos);
    }

    /**
     * Place le plan selon trois points
     *
     * @param a premier
     * @param b deuxième
     * @param c troisième
     */
    public void set(Vector3 a, Vector3 b, Vector3 c) {
        Vector3 x = b.sub(a);
        Vector3 y = c.sub(a);
        normal = x.cross(y).normalize();
        constant = -normal.dot(a);
    }

    /**
     * Retourne le plan à 180°
     */
    public void flip() {
        normal.mul(-1);
        constant *= -1f;
    }

    /**
     * Déplace le plan selon un vecteur
     *
     * @param translation le vecteur de mouvement
     */
    public void translate(Vector3 translation) {
        constant += normal.dot(translation);
    }

    /**
     * Calcul la plus proche point sur le plan depuis un point quelconque
     *
     * @param from le point quelconque
     * @return le plus proche point sur le plan
     */
    public Vector3 getClosestPointOnPlane(Vector3 from) {
        float dist = normal.dot(from) + constant;
        Vector3 res = from.sub(normal.mul(dist));
        return res;
    }

    /**
     * Calcul la distance du plan par rapport à un point.
     * Cette distance peut être négative !
     *
     * @param point un point
     * @return la distance par rapport au point
     */
    public float getDistanceToPoint(Vector3 point) {
        return normal.dot(point) + constant;
    }

    /**
     * Calcul la distance entre un Ray et ce plan.
     * Si l'intersection n'existe pas, la valeur retourner sera de 0.0
     *
     * @param ray le ray
     * @return la distance entre l'origine du ray et le plan
     */
    public float rayCast(Ray ray) {
        float vdot = normal.dot(ray.getDirection());
        float ndot = -normal.dot(ray.getOrigin()) - constant;

        if (Utils.approximately(vdot, 0.0f)) {
            return 0.0f;
        }
        return ndot / vdot;
    }

    /**
     * Détermine de quel côté se situe un point
     *
     * @param point un point
     * @return le côté
     */
    public Side whichSide(Vector3 point) {
        if (isOnPlane(point))
            return Side.NONE;
        float dist = getDistanceToPoint(point);
        if (dist > 0) {
            return Side.POSITIVE;
        } else {
            return Side.NEGATIVE;
        }
    }

    /**
     * Détermine sur un point est sur le plan.
     * Ce calcul se fait autour d'un epsilon
     *
     * @param point le point
     * @return est sur le plan
     */
    public boolean isOnPlane(Vector3 point) {
        float dist = getDistanceToPoint(point);
        if (dist < FastMath.FLT_EPSILON && dist > -FastMath.FLT_EPSILON) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Récupérer la normale
     *
     * @return la normal
     */
    public Vector3 getNormal() {
        return normal;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

    /**
     * Récupérer la constante
     *
     * @return la constante
     */
    public float getConstant() {
        return constant;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }
}
