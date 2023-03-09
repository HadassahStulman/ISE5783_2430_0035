package primitives;

import java.util.Objects;

/**
 * class Ray is the basic class representing a ray in Cartesian 3-Dimensional coordinate system
 * @author Efrat Roth and hadassah Stulman
 */
public class Ray {
    private final Point p0;
    private final Vector dir;

    /** getter
     *
     * @return base point of ray
     */
    public Point getP0() {
        return p0;
    }

    /** getter
     *
     * @return direction of ray
     */
    public Vector getDir() {
        return dir;
    }

    /** constructor to initiate ray with given point and direction
     *
     * @param p0 base point
     * @param dir direction vector
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
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
            return this.p0.equals(ray.p0)&&this.dir.equals(ray.dir);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }
}
