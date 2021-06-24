package module.bounding;

import engine.math.FastMath;
import engine.math.Matrix4;
import engine.math.Transform;
import engine.math.geometry.Plane;
import engine.math.geometry.Ray;
import engine.math.vector.Vector3;
import engine.util.Debug;

/**
 * <code>BoundingBox</code> describes a bounding volume as an axis-aligned bounding box (AABB).
 * <p>
 * Instances may be initialized by invoking the <code>containAABB</code> method.
 */
public class BoundingAAB extends BoundingVolume {

    private float xExtent;
    private float yExtent;
    private float zExtent;

    public BoundingAAB() {
        this(new Vector3(0), 0, 0, 0);
    }

    public BoundingAAB(Vector3 center, float x, float y, float z) {
        super(center, Type.AAB);
        this.xExtent = x;
        this.yExtent = y;
        this.zExtent = z;
    }

    public BoundingAAB(BoundingAAB source) {
        this(source.getCenter(), source.xExtent, source.yExtent, source.zExtent);
    }

    @Override
    public BoundingVolume transform(Transform transform) {
        BoundingAAB box = new BoundingAAB();

        box.setCenter(getCenter().mul(transform.getScaling()));
        box.setCenter(transform.getRotation().mul(box.getCenter()));
        box.setCenter(box.getCenter().add(transform.getTranslation()));

        Matrix4 rotMatrix = transform.getRotationMatrix().abs();
        Vector3 scale = transform.getScaling();
        Vector3 v1 = new Vector3(
                xExtent * FastMath.abs(scale.x),
                yExtent * FastMath.abs(scale.y),
                zExtent * FastMath.abs(scale.z)
        );
        Vector3 v2 = rotMatrix.mul(v1);
        box.setxExtent(FastMath.abs(v2.x));
        box.setyExtent(FastMath.abs(v2.y));
        box.setzExtent(FastMath.abs(v2.z));
        return box;
    }

    @Override
    public void generateFromPoints(Vector3[] points) {
        float minX = points[0].x;
        float minY = points[0].y;
        float minZ = points[0].z;

        float maxX = points[0].x;
        float maxY = points[0].y;
        float maxZ = points[0].z;

        Vector3 temp;
        for (int i = 1; i < points.length; i++) {
            temp = points[i];

            if (temp.x < minX) {
                minX = temp.x;
            }

            if (temp.x > maxX) {
                maxX = temp.x;
            }

            if (temp.y < minY) {
                minY = temp.y;
            }

            if (temp.y > maxY) {
                maxY = temp.y;
            }

            if (temp.z < minZ) {
                minZ = temp.z;
            }

            if (temp.z > maxZ) {
                maxZ = temp.z;
            }
        }

        setCenter(new Vector3(minX + maxX, minY + maxY, minZ + maxZ).mul(0.5f));

        xExtent = maxX - getCenter().x;
        yExtent = maxY - getCenter().y;
        zExtent = maxZ - getCenter().z;
    }

    @Override
    public Plane.Side whichSide(Plane plane) {
        float radius = FastMath.abs(xExtent * plane.getNormal().getX())
                + FastMath.abs(yExtent * plane.getNormal().getY())
                + FastMath.abs(zExtent * plane.getNormal().getZ());
        float distance = plane.getDistanceToPoint(getCenter());

        if (distance < -radius) {
            return Plane.Side.NEGATIVE;
        } else if (distance > radius) {
            return Plane.Side.POSITIVE;
        } else {
            return Plane.Side.NONE;
        }
    }

    @Override
    public float distanceToEdge(Vector3 point) {
        return 0;
    }

    public boolean intersects(BoundingVolume bv) {
        switch (bv.getType()) {
            case AAB:
                return intersectsBoundingAAB((BoundingAAB) bv);
        }
        return false;
    }

    public boolean intersectsBoundingAAB(BoundingAAB bb) {
        if (getCenter().x + xExtent < bb.getCenter().x - bb.xExtent
                || getCenter().x - xExtent > bb.getCenter().x + bb.xExtent) {
            return false;
        } else if (getCenter().y + yExtent < bb.getCenter().y - bb.yExtent
                || getCenter().y - yExtent > bb.getCenter().y + bb.yExtent) {
            return false;
        } else if (getCenter().z + zExtent < bb.getCenter().z - bb.zExtent
                || getCenter().z - zExtent > bb.getCenter().z + bb.zExtent) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean intersects(Ray ray) {
        return false;
    }

    @Override
    public boolean contains(Vector3 point) {
        return FastMath.abs(getCenter().x - point.x) < xExtent
                && FastMath.abs(getCenter().y - point.y) < yExtent
                && FastMath.abs(getCenter().z - point.z) < zExtent;
    }

    @Override
    public boolean intersects(Vector3 point) {
        return FastMath.abs(getCenter().x - point.x) <= xExtent
                && FastMath.abs(getCenter().y - point.y) <= yExtent
                && FastMath.abs(getCenter().z - point.z) <= zExtent;
    }

    @Override
    public float getVolume() {
        return 8 * xExtent * yExtent * zExtent;
    }

    public float getxExtent() {
        return xExtent;
    }

    public void setxExtent(float xExtent) {
        this.xExtent = xExtent;
    }

    public float getyExtent() {
        return yExtent;
    }

    public void setyExtent(float yExtent) {
        this.yExtent = yExtent;
    }

    public float getzExtent() {
        return zExtent;
    }

    public void setzExtent(float zExtent) {
        this.zExtent = zExtent;
    }
}
