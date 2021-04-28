package module.bounding;

import engine.math.Transform;
import engine.math.geometry.Ray;
import engine.math.vector.Vector3;

public abstract class BoundingVolume {

    private Vector3 center;

    public BoundingVolume(Vector3 center) {
        this.center = center;
    }

    /**
     * <code>transform</code> alters the location of the bounding volume by a
     * rotate, translation and a scallar.
     *
     * @param transform the transform to affect the bound
     * @return the new bounding volume
     */
    public abstract BoundingVolume transform(Transform transform);

    /**
     * <code>generateFromPoints</code> generates this bounding volume that
     * encompasses a collection of points
     *
     * @param points the points to contain
     */
    public abstract void generateFromPoints(Vector3[] points);


    /**
     * <code>distanceToEdge</code> finds the distance from the nearest edge of this Bounding Volume to the given
     * point.
     *
     * @param point The point to get the distance to
     * @return distance
     */
    public abstract float distanceToEdge(Vector3 point);

    /**
     * <code>intersects</code> determines if this bounding volume and a second given volume are
     * intersecting. Intersecting being: one volume contains another, one volume
     * overlaps another or one volume touches another.
     *
     * @param bv the second volume to test against.
     * @return true if this volume intersects the given volume.
     */
    public abstract boolean intersects(BoundingVolume bv);

    /**
     * Determines if a ray intersects this bounding volume.
     *
     * @param ray the ray to test.
     * @return true if this volume is intersected by a given ray.
     */
    public abstract boolean intersects(Ray ray);

    /**
     * Determines if a given point is contained within this bounding volume.
     * If the point is on the edge of the bounding volume, this method will
     * return false. Use intersects(Vector3f) to check for edge intersection.
     *
     * @param point the point to check
     * @return true if the point lies within this bounding volume.
     */
    public abstract boolean contains(Vector3 point);

    /**
     * Get volume of the bounding volume
     *
     * @return the volume
     */
    public abstract float getVolume();

    public float distanceTo(Vector3 point) {
        return center.distance(point);
    }

    public float distanceSquaredTo(Vector3 point) {
        return center.distanceSquared(point);
    }

    public Vector3 getCenter() {
        return center;
    }

    public void setCenter(Vector3 center) {
        this.center = center;
    }

    public void setCenter(float x, float y, float z) {
        setCenter(new Vector3(x, y, z));
    }
}
