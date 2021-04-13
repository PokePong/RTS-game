package engine.math;

import engine.util.Utils;
import org.joml.Vector3f;

public class Plane {

    private Vector3f normal;
    private float constant;

    public Plane(Vector3f normal, Vector3f pos) {
        this.normal = new Vector3f(normal).normalize();
        this.constant = -normal.dot(pos);
    }

    public Plane(Vector3f a, Vector3f b, Vector3f c) {
        Vector3f x = new Vector3f(b).sub(a);
        Vector3f y = new Vector3f(c).sub(a);
        this.normal = new Vector3f(x).cross(y).normalize();
        this.constant = normal.dot(a);

    }

    public Plane(Vector3f normal, float constant) {
        this.normal = new Vector3f(normal).normalize();
        this.constant = constant;
    }

    public void set(Vector3f normal, Vector3f pos) {
        normal = new Vector3f(normal).normalize();
        constant = -normal.dot(pos);
    }

    public void set(Vector3f a, Vector3f b, Vector3f c) {
        Vector3f x = new Vector3f(b).sub(a);
        Vector3f y = new Vector3f(c).sub(a);
        normal = new Vector3f(x).cross(y).normalize();
        constant = normal.dot(a);
    }

    public void flip() {
        normal.mul(-1);
        constant *= -1f;
    }

    public void translate(Vector3f translation) {
        constant += normal.dot(translation);
    }

    public Vector3f getClosestPointOnPlane(Vector3f from) {
        float dist = normal.dot(from) + constant;
        Vector3f res = new Vector3f(from);
        res.sub(new Vector3f(normal).mul(dist));
        return res;
    }

    public float getDistanceToPoint(Vector3f point) {
        return normal.dot(point) + constant;
    }

    public float rayCast(Ray ray) {
        float vdot = ray.getDirection().dot(normal);
        float ndot = -ray.getOrigin().dot(normal) - constant;

        if(Utils.approximately(vdot, 0.0f)) {
            return 0.0f;
        }
        return ndot / vdot;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    public float getConstant() {
        return constant;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }
}
