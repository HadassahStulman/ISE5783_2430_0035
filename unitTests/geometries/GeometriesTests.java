package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {


    /**
     * Test method for {@link geometries.Geometry#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        Sphere sphere = new Sphere(new Point(1, 0, 0), 1);
        Triangle triangle = new Triangle(new Point(0, 2, 0), new Point(2, 0, 0), new Point(-2, 0, 0));
        Plane plane = new Plane(new Point(2, 0, 0), new Point(0, 2, 0), new Point(0, 0, 2));
        Geometries geometry = new Geometries(sphere, triangle, plane);

        // ============ Equivalence Partitions Tests ==============
        // TC01: not all geometries has intersection points
        List<Point> result = geometry.findIntersections(new Ray(new Point(1, -2, 0.5), new Vector(0, 7, 1.5)));
        assertEquals(3, result.size(), "wrong number of intersection points");

        // =============== Boundary Values Tests ==================
        // TC02: geometry composite does not contain geometries
        Geometries geometry2 = new Geometries();
        assertNull(geometry2.findIntersections(new Ray(new Point(1, 0, 0), new Vector(2, 0, 0))),
                "composite does not contain geometries");

        // TC03: ray does not intersect any geometry in composite
        assertNull(geometry.findIntersections(new Ray(new Point(1, -2, 0.5), new Vector(-5, 0, -0.5))),
                "ray does not intersect any geometry");

        // TC04: ray intersect only one geometry
        result = geometry.findIntersections(new Ray(new Point(1, -2, 0.5), new Vector(-1, -1, 5.5)));
        assertEquals(1, result.size(), "ray intersect only one geometry");

        //TC05: ray intersect all geometries in composite
        result = geometry.findIntersections(new Ray(new Point(-2,0,-3), new Vector(6,1,6)));
        assertEquals(4,result.size(),"ray intersect all geometries in composite");
    }
}