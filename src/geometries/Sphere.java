package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.*;

/**
 * Sphere class represents a 3D sphere in Cartesian 3D coordinate system
 *
 * @author Efrat Roth and hadassah Stulman
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor to initialize Sphere with center point and radius
     *
     * @param center center point of sphere
     * @param radius radius of sphere
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * getter
     *
     * @return center point
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(this.center).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {

        Point P0 = ray.getP0();
        Vector u = center.subtract(P0);
        double tm = ray.getDir().dotProduct(u);
        double d = sqrt(u.lengthSquared() - pow(tm, 2));

        double th = sqrt(pow(radius, 2) - pow(d, 2));
        double t1 = tm + th;
        double t2 = tm - th;

        if (d >= radius || t1 <= 0 && t2 <= 0)
            return null;

        Point P1 = P0.add(ray.getDir().scale(t1));
        Point P2 = P0.add(ray.getDir().scale(t2));

        // If t1 is less than or equal to 0, or if P1 is equal to P0.
        // then P1 will not be considered as an intersection point
        // and if P2 is not equal to P0, return a list containing only P2.
        if ((t1 <= 0 || P1 == P0) && P2 != P0)
            return List.of(P2);

        // If t2 is less than or equal to 0, or if P2 is equal to P0.
        // then P2 will not be considered as an intersection point
        // and if P1 is not equal to P0, return a list containing only P1.
        if ((t2 <= 0 || P2 == P0) && P1 != P0)
            return List.of(P1);

        return List.of(P1,P2);
    }
}
