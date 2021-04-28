package engine.math.geometry;

import engine.math.vector.Vector3;
import engine.util.Utils;

public class Plane {

    private Vector3 normal;
    private float constant;

    public Plane(Vector3 normal, Vector3 pos) {
        this.normal = normal.normalize();
        this.constant = -normal.dot(pos);
    }

    public Plane(Vector3 a, Vector3 b, Vector3 c) {
        Vector3 x = b.sub(a);
        Vector3 y = c.sub(a);
        this.normal = x.cross(y).normalize();
        this.constant = -normal.dot(a);
    }

    public Plane(Vector3 normal, float constant) {
        this.normal = normal.normalize();
        this.constant = constant;
    }

    public void set(Vector3 normal, Vector3 pos) {
        this.normal = normal.normalize();
        this.constant = -normal.dot(pos);
    }

    public void set(Vector3 a, Vector3 b, Vector3 c) {
        Vector3 x = b.sub(a);
        Vector3 y = c.sub(a);
        normal = x.cross(y).normalize();
        constant = -normal.dot(a);
    }

    public void flip() {
        normal.mul(-1);
        constant *= -1f;
    }

    public void translate(Vector3 translation) {
        constant += normal.dot(translation);
    }

    public Vector3 getClosestPointOnPlane(Vector3 from) {
        float dist = normal.dot(from) + constant;
        Vector3 res = from.sub(normal.mul(dist));
        return res;
    }

    public float getDistanceToPoint(Vector3 point) {
        return normal.dot(point) + constant;
    }

    public float rayCast(Ray ray) {
        float vdot = normal.dot(ray.getDirection());
        float ndot = -normal.dot(ray.getOrigin()) - constant;

        if (Utils.approximately(vdot, 0.0f)) {
            return 0.0f;
        }
        return ndot / vdot;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

    public float getConstant() {
        return constant;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }
}
