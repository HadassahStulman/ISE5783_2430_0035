package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class Plane represents two-dimensional plane in Cartesian 3D coordinate system
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class Plane extends Geometry {
    private final Point q0;
    private final Vector normal;

    /**
     * constructor to initialize plane with point and normal
     *
     * @param q0     point on plane
     * @param vector normal vector to plane
     */
    public Plane(Point q0, Vector vector) {
        this.q0 = q0;
        this.normal = vector.normalize();
    }

    /**
     * constructor to initialize Plane according to 3 given Points on the Plane
     *
     * @param point1 first point
     * @param point2 second point
     * @param point3 third point
     */
    public Plane(Point point1, Point point2, Point point3) {
        this.q0 = point1;

        if (point1.equals(point2) || point2.equals(point3) || point3.equals(point1))
            throw new IllegalArgumentException("Two of the three points are identical");

        Vector v1 = point1.subtract(point2); // vector between point1 and point2
        Vector v2 = point2.subtract(point3); // vector between point2 and point3

        if (v1.normalize().equals(v2.normalize()))
            throw new IllegalArgumentException("all three points are on one line");

        // a vector that is orthogonal to two vectors on the plane is orthogonal to the plane.
        this.normal = v1.crossProduct(v2).normalize();

    }

    /**
     * getter
     *
     * @return point on the plane
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * getter
     *
     * @return normal vector to plane
     */
    public Vector getNormal() {
        return normal;
    }


    @Override
    public Vector getNormal(Point point) {
        return this.getNormal();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {


        // Calculate the denominator of the equation
        double denom = normal.dotProduct(ray.getDir());

        // if ray is parallel to tha plans normal there are no intersections
        if (isZero(denom))
            return null;

        // If the origin of the ray is the reference point of the plane, there are no intersections
        if (q0.equals(ray.getP0()))
            return null;

        // Calculate the intersection point
        Vector QP0 = q0.subtract(ray.getP0());
        double numer = normal.dotProduct(QP0);
        double t = alignZero(numer / denom);

        if (alignZero(maxDistance - t) <= 0) {
            return null;
        }

        // If the intersection point is behind the origin of the ray, there are no intersections
        if (t <= 0)
            return null;

        // Return a list containing the intersection point
        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}

