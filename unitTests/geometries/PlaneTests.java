package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Plane
 * @author Efrat Roth and Hadassah Stulman
 */
class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01 simple test
        Point p1=new Point(1,0,0);
        Point p2=new Point(0,1,0);
        Point p3=new Point(0,0,0);
        Plane pl= new Plane(p1,p2, p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pl.getNormal(new Point(1,1,0)), "");
        // generate the test result
        Vector result = pl.getNormal(new Point(1,1,0));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        assertTrue(isZero(result.dotProduct(p1.subtract(p2))), "Plane's normal is not orthogonal to one of the Points");
        assertTrue(isZero(result.dotProduct(p2.subtract(p3))), "Plane's normal is not orthogonal to one of the Points");
        assertTrue(isZero(result.dotProduct(p3.subtract(p1))), "Plane's normal is not orthogonal to one of the Points");

        // =============== Boundary Values Tests ==================
        // there are no boundary tests
    }

    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @Test
    void testConstructor() {
        // =============== Boundary Values Tests ==================
        // TC11 first and second point are identical
        assertThrows(IllegalArgumentException.class, ()->new Plane(new Point(1,1,0), new Point(1,1,0), new Point(0,0,0)),
                "Point1 and point2 are identical");
        //TC12 the three points are on one line
        assertThrows(IllegalArgumentException.class, ()->new Plane(new Point(1,0,0), new Point(2,0,0), new Point(5,0,0)),
                "all three points are on one line");
    }

    @Test
    void testFindIntersections() {
        Point p1=new Point(-1,0,-2);
        Point p2=new Point(-1,0,0);
        Plane plane = new Plane(new Point(-2,0,0), new Vector(0,0, 2));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane
        List<Point> result = plane.findIntersections(new Ray(p1, new Vector(0,9,8)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(-1,2.25,0)),result,"Ray intersect the plane" );

        //TC02: Ray does not intersect the plane
        assertNull(plane.findIntersections(new Ray(new Point(-1,0,2), new Vector(0,9,4))),"Ray does not intersect the plane");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane
        // TC03: ray is included in the plane
        assertNull(plane.findIntersections(new Ray(p2, new Vector(3,0,0))),"Ray is included in plane");

        // TC04: ray id not included in the plane
        assertNull(plane.findIntersections(new Ray(p2, new Vector(10,0,0))),"Ray is not included in plane");

        // **** Group: Ray is orthogonal to the plane
        //TC05: Ray starts before the plane
        result=plane.findIntersections(new Ray(p1, new Vector(0,0,4)));
        assertEquals(1, result.size(),"wrong number of points");
        assertEquals(List.of(p2), result,"Ray starts before the plane");

        // TC06: Ray starts at the plane
        assertNull(plane.findIntersections(new Ray(p2, new Vector(0,0,2))),"Ray starts in the plane");

        //TC07: Ray starts after plane
        assertNull(plane.findIntersections(new Ray(new Point(-1,0,1), new Vector(0,0,1))),
                "Ray starts after the plane");

        // **** Group: special cases
        // TC08: Ray begins at the plane
        assertNull(plane.findIntersections(new Ray(p2, new Vector(0,5,2))),
                "Ray starts in plane and isn't orthogonal or parallel to the plane");

        // TC09: Ray begins in the same point that appears as the reference point of plane
        assertNull(plane.findIntersections(new Ray(plane.getQ0(), new Vector(0,5,2))), "Ray starts at Q0");
    }
}