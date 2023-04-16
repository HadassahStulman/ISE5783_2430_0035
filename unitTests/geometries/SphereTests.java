package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
        assertEquals(new Vector(0,0,2), sp.getNormal(new Point(0,0,1)),"Sphere's normal is not orthogonal to the tangent plane");

        // =============== Boundary Values Tests ==================
        // there are no boundary tests
    }
}