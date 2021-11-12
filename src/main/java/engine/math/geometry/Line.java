package engine.math.geometry;

import engine.math.FastMath;
import engine.math.vector.Vector3;

/**
 * Line définit une droite. Une droite a une longueur infinie le long de deux points.
 * Ces deux points sont définit par origin et direction
 */
public class Line {

    /**
     * Point A, origine
     */
    private Vector3 origin;
    /**
     * Point B, direction
     */
    private Vector3 direction;

    /**
     * Constructeur. L'origine et la direction sont définit par défaut à (0, 0, 0)
     */
    public Line() {
        this.origin = new Vector3();
        this.direction = new Vector3();
    }

    /**
     * Calcule la distance au carré depuis cette droite vers un certain point
     *
     * @param point le certain point
     * @return le carré de la distance minimum
     */
    public float distanceSquared(Vector3 point) {
        Vector3 temp = point.sub(origin);
        float lineParameter = direction.dot(temp);
        Vector3 temp1 = origin.add(direction.mul(lineParameter));
        temp = temp1.sub(point);
        return temp.lengthSquared();
    }

    /**
     * Calcul la distance depuis cette droite vers un certain point
     *
     * @param point le certain point
     * @return la distance minimum
     */
    public float distance(Vector3 point) {
        return FastMath.sqrt(distanceSquared(point));
    }

    /**
     * Détermine un point aléatoire le long de cette droite
     *
     * @return un point aléatoire sur cette droite
     */
    public Vector3 random() {
        float rand = (float) Math.random();
        float x = (origin.x * (1 - rand)) + (direction.x * rand);
        float y = (origin.y * (1 - rand)) + (direction.y * rand);
        float z = (origin.z * (1 - rand)) + (direction.z * rand);
        return new Vector3(x, y, z);
    }

}
