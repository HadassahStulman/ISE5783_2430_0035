package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;


/**
 * Testing Polygons
 *
 * @author Dan
 */
public class PolygonTests {

   private  Point[] pts = {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1)};
    private Polygon pol = new Polygon(pts);

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {@link geometries.Polygon#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: intersection point is in the polygon
        Polygon polygon = new Polygon(new Point(-1, 1, 0), new Point(1, 3, 0), new Point(3, 2, 0), new Point(-1, -1, 0));
        Point p1 = new Point(0, 0, -4);
        List<Point> result = polygon.findIntersections(new Ray(p1, new Vector(0, 1, 4)));
        assertEquals(1, result.size(), "Wrong Number Of Points");
        assertEquals(List.of(new Point(0, 1, 0)), result, "Intersection Point is in the polygon");

        // TC02: intersection point is out of polygon
        assertNull(polygon.findIntersections(new Ray(p1, new Vector(0, 3, 4))),
                "intersection point is out ot the polygon");

        // TC03: intersection point is out of the polygon and between the continuation of two sides
        assertNull(polygon.findIntersections(new Ray(p1, new Vector(1, 3.5, 4))),
                "intersection point is out of the polygon and between the continuation of two of its sides");

        // =============== Boundary Values Tests ==================
        // TC04: intersection point is one of the polygon's corners
        assertNull(polygon.findIntersections(new Ray(p1, new Vector(1, 3, 4))),
                "intersection point is one of the corners");

        // TC05: intersection point is on one of the polygon's sides
        assertNull(polygon.findIntersections(new Ray(p1, new Vector(0, 2, 4))),
                "intersection point is on one of the triangle's sides");

        // TC06: intersection point is on the continuation of one of the sides
        assertNull(polygon.findIntersections(new Ray(p1, new Vector(5, 1, 4))),
                "intersection point is on the continuation of one of the sides");
    }

    /**
     * Test method for {@link geometries.Polygon#findGeoIntersections(primitives.Ray, double)}.
     */
    @Test
    void testFindGeoIntersections() {
        assertNull(pol.findGeoIntersections(new Ray(new Point(0,0,110), new Vector(0,1,-111)), 100),
                "a far intersection Point is included in intersections");
    }
}