package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.*;

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        // get the starting point of the ray
        Point P0 = ray.getP0();

        // if the ray starts from the center point of the sphere, return the point where the ray exits the sphere
        if (center.equals(P0)) {
            return List.of(ray.getPoint(radius)).stream().map(p-> new GeoPoint(this, p)).toList();
        }

        // compute the intersection between the ray and the sphere using vector math
        Vector u = center.subtract(P0);
        double tm = ray.getDir().dotProduct(u);
        double d = sqrt(u.lengthSquared() - tm * tm);

        double th = sqrt(radius * radius - d * d);
        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);

        // if there is no intersection between the ray and the sphere, return null
        if (d >= radius || t1 <= 0 && t2 <= 0)
            return null;

        // return both intersection points
        if (t1 > 0 && t2 > 0) {
            Point P1 = ray.getPoint(t1);
            Point P2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this,P1), new GeoPoint(this, P2));
        }

        // if t1 is less than or equal to 0, return P2 as the intersection point
        if (t1 <= 0) {
            Point P2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, P2));
        }

        // if t2 is less than or equal to 0, return P1 as the intersection point
        if (t2 <= 0) {
            Point P1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this, P1));
        }
        return null;
    }
}
