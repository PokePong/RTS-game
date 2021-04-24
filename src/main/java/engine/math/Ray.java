package engine.math;


public class Ray {

    private Vector3 origin;
    private Vector3 direction;

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    // Returns a point at /distance/ units along the ray.
    public Vector3 getPoint(float distance) {
        return direction.mul(distance).add(origin);
    }

    public Vector3 getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3 origin) {
        this.origin = origin;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public void setDirection(Vector3 direction) {
        this.direction = direction;
    }

    public String toString() {
        return "o: " + origin.toString() + " - dir: " + direction.toString();
    }
}
