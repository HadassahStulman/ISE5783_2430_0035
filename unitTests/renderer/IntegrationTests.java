package renderer;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Efrat Roth and Hadassah Stulman
 */
class IntegrationTests {
    Camera cam0 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1),new Vector(0, 1, 0))
            .setVPDistance(1).setVPSize(3,3);
    Camera cam5 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPDistance(1).setVPSize(3,3);

    /**
     * This method test the amount of integrations between camera's 3x3 grid of rays and a sphere.
     */
    @Test
    void CameraSphereIntegrations() {

        assertEquals(2, countIntegration(cam0, new Sphere(new Point(0,0,-3), 1)),
                "Sphere TC01 failed");
        assertEquals(18, countIntegration(cam5, new Sphere(new Point(0,0,-2.5),2.5)),
        "Sphere TC02 failed");
        assertEquals(10, countIntegration(cam5, new Sphere(new Point(0,0,-2), 2)),
                " Sphere TC03 failed");
        assertEquals(9, countIntegration(cam5, new Sphere(new Point(0,0,0),4)),
                "Sphere TC04 failed");
        assertEquals(0, countIntegration(cam0, new Sphere(new Point(0,0,1),0.5)),
                "Sphere TC05 failed");
    }


    /**
     * This method test the amount of integrations between camera's 3x3 grid of rays and a Plane.
     */
    @Test
    void CameraPlaneIntegrations() {
        Camera cam = new Camera(new Point(0,0,0), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPDistance(1).setVPSize(9,9);
        assertEquals(9, countIntegration(cam, new Plane(new Point(0, 0, -5), new Vector(0, 0, 1))),
                "Plane TC01 failed");
        assertEquals(9, countIntegration(cam,new Plane(new Point(0, 0, -5), new Vector(0, 1, 7))),
                "Plane TC02 failed");
        assertEquals(6, countIntegration(cam,new Plane(new Point(0, 0, -5), new Vector(0, 1, 1))),
                "Plane TC03 failed");

    }

    /**
     * This method test the amount of integrations between camera's 3x3 grid of rays and a Triangle.
     */
    @Test
    void CameraTriangleIntegrations() {
        assertEquals(1, countIntegration(cam0, new Triangle(new Point(0,1,-2), new Point(1,-1,-2), new Point(-1,-1,-2))),
                "Triangle TC01 failed");
        assertEquals(2, countIntegration(cam0, new Triangle(new Point(0,20,-2), new Point(1,-1,-2), new Point(-1,-1,-2))),
                "Triangle TC02 failed");
    }


    /**
     * Counts the total number of intersections between a camera's 3x3 grid of rays and a given geometry.
     *
     * @param cam the camera used to generate the rays
     * @param geo the geometry to find intersections with
     * @return the total number of intersection points found
     */
    public int countIntegration(Camera cam, Geometry geo) {
        Ray ray;
        List<Point> points;
        int amount = 0;

        // for a 3x3 grid of rays
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                // Generate a ray for each pixel in the grid
                ray = cam.constructRay(3, 3, i, j);

                // Find the intersection points of the ray with the geometry
                    points = geo.findIntersections(ray);
                    if (points != null) {
                        amount += points.size();
                    }
            }
        }
        return amount;
    }
}