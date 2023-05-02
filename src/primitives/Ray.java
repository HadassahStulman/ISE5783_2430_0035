package primitives;

import java.util.Objects;

/**
 * class Ray is the basic class representing a ray in Cartesian 3-Dimensional coordinate system
 * @author Efrat Roth and hadassah Stulman
 */
public class Ray {
    private final Point p0;
    private final Vector dir;


    /**
     * constructor to initiate ray with given point and direction
     * @param p0  base point
     * @param dir direction vector
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * getter
     * @return base point of ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter
     * @return direction of ray
     */
    public Vector getDir() {
        return dir;
    }


    /**
     * Calculates the position of a point on the ray at a given distance from the ray's starting point.
     * @param t the distance along the ray to the new point.
     * @return a new point that represents the position of the point on the ray at the given distance.
     */
    public Point getPoint(double t) {
        return p0.add(dir.scale(t));
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
