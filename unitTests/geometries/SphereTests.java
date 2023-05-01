package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Triangle
 * @author Efrat Roth and Hadassah Stulman
 */
class SphereTests {
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01 simple test
        Sphere sp=new Sphere(new Point(0,0,0), 1);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> sp.getNormal(new Point(0,0,1)), "");
        assertEquals(new Vector(0,0,1), sp.getNormal(new Point(0,0,1)),"Sphere's normal is not orthogonal to the tangent plane");

        // =============== Boundary Values Tests ==================
        // there are no boundary tests
    }


    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point (1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result=sphere.findIntersections(new Ray(new Point(1,0.5,0), new Vector(1,1,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(1.411437827766147, 0.9114378277661477,0), result.get(0), "Ray starts inside sphere, 1 point");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-0.5, -0.5, 0), new Vector(-0.5, 0, 0))),
                "Ray's starts after sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC05: Ray starts at sphere and goes inside (1 point)
        p1 = new Point(0.29443776,0.708647956,0);
        result = sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(-2.8942938268, 1.2025567618,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(p1, result.get(0), "Ray starts at sphere and goes inside, 1 point");

        // TC06: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0.71252199,1.3782993243,0))),
                "Ray's starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC07: Ray starts before the sphere (2 points)
        p1 = new Point(1,-1,0);
        p2 = new Point(1,1,0);
        result = sphere.findIntersections(new Ray(new Point(1, -3, 0), new Vector(0,6,0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray starts before sphere");

        // TC08: Ray starts at sphere and goes inside (1 point)
        result = sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(0,4,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(1,1,0), result.get(0), "Ray starts at sphere and goes inside");

        // TC09: Ray starts inside (1 point)
        result = sphere.findIntersections(new Ray(new Point(1, -0.5, 0), new Vector(0,3.5,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(1,1,0), result.get(0),"Ray starts inside");

        // TC10: Ray starts at the center (1 point)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0,3,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(1,1,0), result.get(0),"Ray starts at the center");

        // TC11: Ray starts at sphere and goes outside (0 point)
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(0,2,0))),
                "Ray's starts at sphere and goes outside");

        // TC12: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1, 2, 0), new Vector(0,1,0))),
                "Ray's starts after sphere and goes outside");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC13: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, 1, 0), new Vector(3,0,0))),
                "Ray's starts before the tangent point");

        // TC14: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(2,0,0))),
                "Ray's starts at the tangent point");

        // TC15: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1.5, 1, 0), new Vector(1.5,0,0))),
                "Ray's starts after the tangent point");

        // **** Group: Special cases
        // TC16: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(1, 2, 0), new Vector(2,0,0))),
                "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
    }
}