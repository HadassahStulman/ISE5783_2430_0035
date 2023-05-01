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
    public List<Point> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }
}
