package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class Ray is the basic class representing a ray in Cartesian 3-Dimensional coordinate system
 *
 * @author Efrat Roth and hadassah Stulman
 */
public class Ray {

    private static final double DELTA = 0.1;

    /**
     * The starting point of the ray.
     */
    private final Point p0;


    /**
     * The direction of the ray as a vector.
     */
    private final Vector dir;


    /**
     * constructor to initiate ray with given point and direction
     *
     * @param p0  base point
     * @param dir direction vector
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }


    /**
     * Constructs a Ray object with the given parameters.
     * The ray is defined by a starting point (head), a direction vector, and a surface normal vector.
     *
     * @param head The starting point of the ray as a Point object.
     * @param direction The direction vector of the ray as a Vector object.
     * @param normal The surface normal vector as a Vector object.
     */
    public Ray(Point head, Vector direction, Vector normal) {

        // Calculate the dot product between the surface normal and the direction vector of the ray
        double nv = alignZero(normal.dotProduct(direction));
        if(isZero(nv))
            this.p0 = head;
        else
            this.p0 = head.add(normal.scale(nv > 0 ? DELTA : -DELTA));
        this.dir = direction;
    }

    /**
     * getter
     *
     * @return base point of ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter
     *
     * @return direction of ray
     */
    public Vector getDir() {
        return dir;
    }


    /**
     * Calculates the position of a point on the ray at a given distance from the ray's starting point.
     *
     * @param t the distance along the ray to the new point.
     * @return a new point that represents the position of the point on the ray at the given distance.
     */
    public Point getPoint(double t) {
        if (isZero(t)) {
            return p0;
        }
        return p0.add(dir.scale(t));
    }

    /**
     * finds the closest GeoPoint to ray's base point.
     *
     * @param intersections a list of GeoPoints to search from
     * @return the closest GeoPoint to the given point
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {

        // the list is empty
        if (intersections == null || intersections.size() == 0) {
            return null;
        }

        // initialize as if the first point's is the closest
        GeoPoint closest = intersections.get(0);
        double minDistance = Double.POSITIVE_INFINITY;
        double distance;

        // run across the list of points
        for (GeoPoint geoPoint : intersections) {
            distance = p0.distanceSquared(geoPoint.point);
            if (distance < minDistance) {
                minDistance = distance;
                closest = geoPoint;
            }
        }
        return closest;
    }

    /**
     * finds the closest point to ray's base point
     *
     * @param intersections a list of Points to search from
     * @return the closest Point to the given point
     */
    public Point findClosestPoint(List<Point> intersections) {
        return intersections == null || intersections.isEmpty() ? null
                : findClosestGeoPoint(intersections.stream()
                .map(p -> new GeoPoint(null, p))
                .toList()).point;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }
}
