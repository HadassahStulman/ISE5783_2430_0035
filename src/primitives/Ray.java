package primitives;

import java.util.List;
import java.util.Objects;

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
     * finds the closest point to ray's base point.
     *
     * @param points a list of points to search from
     * @return the closest point to the given point
     */
    public Point findClosestPoint(List<Point> points) {

        // the list is empty
        if(points == null || points.size()==0)
            return null;

        // initialize as if the first point's is the closest
        Point closest = points.get(0);
        double minDistance = p0.distance(closest);
        double distance;

        // run across the list of points
        for (int i=1; i< points.size(); i++) {

            distance = p0.distance(points.get(i));
            if (distance < minDistance)
                closest = points.get(i);
        }

        return closest;
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
