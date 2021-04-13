package engine.math;

import org.joml.Vector3f;

public class Ray {

    private Vector3f origin;
    private Vector3f direction;

    public Ray(Vector3f origin, Vector3f direction) {
        this.origin = origin;
        this.direction = direction;
    }

    // Returns a point at /distance/ units along the ray.
    public Vector3f getPoint(float distance) {
        Vector3f res = new Vector3f();
        return origin.add(new Vector3f(direction).mul(distance), res);
    }

    public Vector3f getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3f origin) {
        this.origin = origin;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public String toString() {
        return "o: " + origin.toString() + " - dir: " + direction.toString();
    }
}
