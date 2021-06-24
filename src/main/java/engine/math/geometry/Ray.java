package engine.math.geometry;


import engine.math.vector.Vector3;

/**
 * Ray définit un segment qui a une origine et une direction.
 * C'est une demi-droite.
 * Une ray est définit par l'équation suivante : {@literal
 * R(t) = origin + t * direction for t >= 0
 * }
 *
 * @author Loic Besse
 */
public class Ray {

    /**
     * Le point d'origine du ray
     */
    private Vector3 origin;

    /**
     * La direction du ray
     */
    private Vector3 direction;

    /**
     * Constructeur
     *
     * @param origin    l'origine
     * @param direction la direction
     */
    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    // Returns a point at /distance/ units along the ray.

    /**
     * Récupère le point à la /distance/ le long du ray
     *
     * @param distance la distance
     * @return un point situé sur le ray à distance /distance/ de l'origine
     */
    public Vector3 getPoint(float distance) {
        return direction.mul(distance).add(origin);
    }

    /**
     * Récupère le point d'origine
     *
     * @return l'origine
     */
    public Vector3 getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3 origin) {
        this.origin = origin;
    }

    /**
     * Récupère la direction
     *
     * @return la direction
     */
    public Vector3 getDirection() {
        return direction;
    }

    public void setDirection(Vector3 direction) {
        this.direction = direction;
    }

    public String toString() {
        return "ori: " + origin.toString() + " - dir: " + direction.toString();
    }
}
