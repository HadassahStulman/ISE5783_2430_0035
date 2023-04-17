package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
}