package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Triangle
 *
 * @author Efrat Roth and Hadassah Stulman
 */
class TriangleTests {

    private Triangle triangle = new Triangle(new Point(0, 2, 0), new Point(2, 0, 0), new Point(-2, 0, 0));

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);
        Triangle pol = new Triangle(p1, p2, p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        assertTrue(isZero(result.dotProduct(p1.subtract(p2))),
                "Triangle's normal is not orthogonal to one of the Edges");
        assertTrue(isZero(result.dotProduct(p2.subtract(p3))),
                "Triangle's normal is not orthogonal to one of the Edges");
        assertTrue(isZero(result.dotProduct(p3.subtract(p1))),
                "Triangle's normal is not orthogonal to one of the Edges");

        // =============== Boundary Values Tests ==================
        // there are no boundary tests
    }


    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        Point p1 = new Point(1, -1, -1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: intersection point is in the triangle
        List<Point> result = triangle.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 1, -2)));
        assertEquals(1, result.size(), "Wrong Number Of Points");
        assertEquals(List.of(new Point(0, 1, 0)), result, "Intersection Point is in the triangle");

        // TC02: intersection point is out of triangle
        assertNull(triangle.findIntersections(new Ray(p1, new Vector(0, 2, 4))),
                "intersection point is out ot the triangle");

        // TC03: intersection point is out of the triangle and between the continuation of two sides
        assertNull(triangle.findIntersections(new Ray(p1, new Vector(-1, 4, 1))),
                "intersection point is out of the triangle and between the continuation of two of its sides");



        // =============== Boundary Values Tests ==================
        // TC04: intersection point is one of the triangle's corners
        assertNull(triangle.findIntersections(new Ray(p1, new Vector(-1, 3, 1))),
                "intersection point is one of the corners");

        // TC05: intersection point is on one of the triangle's sides
        assertNull(triangle.findIntersections(new Ray(p1, new Vector(-2, 1, 1))),
                "intersection point is on one of the triangle's sides");

        // TC06: intersection point is on the continuation of one of the sides
        assertNull(triangle.findIntersections(new Ray(p1, new Vector(-4, 1, 1))),
                "intersection point is on the continuation of one of the sides");
    }

    /**
     * Test method for {@link geometries.Triangle#findGeoIntersections(primitives.Ray, double)}.
     */
    @Test
    void testFindGeoIntersections() {
        assertNull(triangle.findGeoIntersections(new Ray(new Point(0,0,110), new Vector(0,1,-111)), 100),
                "a far intersection Point is included in intersections");
    }
}
