package primitives;

import java.util.List;
import java.util.Objects;

import geometries.Intersectable.GeoPoint;

/**
 * class Ray is the basic class representing a ray in Cartesian 3-Dimensional coordinate system
 *
 * @author Efrat Roth and hadassah Stulman
 */
public class Ray {

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
        if (intersections == null || intersections.size() == 0)
            return null;

        // initialize as if the first point's is the closest
        GeoPoint closest = intersections.get(0);
        double minDistance = p0.distance(closest.point);
        double distance;

        // run across the list of points
        for (int i = 1; i < intersections.size(); i++) {

            distance = p0.distance(intersections.get(i).point);
            if (distance < minDistance)
                closest = intersections.get(i);
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
                .map(p -> new GeoPoint(null, p)).toList()).point;
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
        if (o instanceof Ray ray)
            return this.p0.equals(ray.p0) && this.dir.equals(ray.dir);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }
}
