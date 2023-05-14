package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 * @author Efrat Roth and Hadassah Stulman
 */
class RayTests {

    Ray ray = new Ray(new Point(-1,0,0), new Vector(0,0,2));

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void testGetPoint() {
        
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Point(-1,0,1), ray.getPoint(1), "failed to calculate the point on ray");

        // =============== Boundary Values Tests ==================
        // there are no boundary tests
    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void testFindClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: The closest Point is in the middle of the list
        assertEquals(new Point(-1,0,1), 
                ray.findClosestPoint(List.of(new Point(-1,0,3), new Point(-1,0,1), new Point(-1,0,5))),
                "failed to find the closest point");

        // =============== Boundary Values Tests ==================
        // TC02: Empty List
        assertNull(ray.findClosestPoint(List.of()));

        // TC03: The first Point in the list is the closest
        assertEquals(new Point(-1,0,1),ray.findClosestPoint(List.of(new Point(-1,0,1), new Point(-1,0,3), new Point(-1,0,5))),
                "failed to recognize the first Point as the closest");

        // TC04: The last Point in the list is the closest
        assertEquals(new Point(-1,0,1) ,ray.findClosestPoint(List.of(new Point(-1,0,3), new Point(-1,0,5), new Point(-1,0,1))),
                "failed to recognize the last Point as the closest");
    }
}