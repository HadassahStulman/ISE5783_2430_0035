package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Tube
 * @author Efrat Roth and Hadassah Stulman
 */
class TubeTests {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Ray axisRay=new Ray(new Point(0,0,0), new Vector(0,0,1));
        Tube t=new Tube(axisRay, 1);
        // ============ Equivalence Partitions Tests ==============
        // TC01 simple test
        // ensure there are no exceptions
        assertDoesNotThrow(() -> t.getNormal(new Point(2,0,1)), "");
        assertEquals(new Point(1,0,0), t.getNormal(new Point(2,0,1)),
                "Tube's normal is not orthogonal to the tangent plane");

        // =============== Boundary Values Tests ==================
        //TC11 the vector that connects the given point to the head of the axis ray, is orthogonal to the axis ray
        assertEquals(new Point(1,0,0), t.getNormal(new Point(1,0,0)),
                "Couldn't find normal for a point that creates 90 degree angle with the head of the axis ray");

    }
}