package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;


/**
 * Triangle class represents two-dimensional triangles in 3D coordinate system
 *
 * @author Efrat Roth and hadassah Stulman
 */
public class Triangle extends Polygon {

    /**
     * constructor to initialize Triangle with 3 points
     *
     * @param point1 first point
     * @param point2 second point
     * @param point3 third point
     */
    public Triangle(Point point1, Point point2, Point point3) {
        super(point1, point2, point3);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
/**
 // Get the origin and direction of the ray
 Point p0 = ray.getP0();
 Vector v = ray.getDir();

 // Get the vertices of the triangle
 Point p1 = vertices.get(0);
 Point p2 = vertices.get(1);
 Point p3 = vertices.get(2);

 // Calculate the vectors between the triangles vertices and the ray's origin
 Vector v1 = p1.subtract(p0);
 Vector v2 = p2.subtract(p0);
 Vector v3 = p3.subtract(p0);

 // normal vector to the vectors calculated above
 Vector n1 = v1.crossProduct(v2).normalize();
 Vector n2 = v2.crossProduct(v3).normalize();
 Vector n3 = v3.crossProduct(v1).normalize();

 // Calculate the dot products of the ray direction and the normal vectors
 double vn1 = v.dotProduct(n1);
 double vn2 = v.dotProduct(n2);
 double vn3 = v.dotProduct(n3);

 // If the dot product of any of the normal vectors and the ray direction is zero, there are no intersections
 if (isZero(vn1) || isZero(vn2) || isZero(vn3))
 return null;

 // If the dot products of all the normal vectors and the ray direction have the same sign
 // the ray is intersects the triangle
 if (vn1 > 0 && vn2 > 0 && vn3 > 0 || vn1 < 0 && vn2 < 0 && vn3 < 0)
 return plane.findIntersections(ray);

 // Otherwise, there are no intersections
 return null;
 */
        List<GeoPoint> intersections = super.findGeoIntersectionsHelper(ray);
        return intersections == null ? null
                : intersections.stream().map(p -> new GeoPoint(this, p.point)).toList();
    }
}
