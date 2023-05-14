package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.lang.reflect.Array;
import java.util.List;


/**
 * The RayTracerBasic class represents a basic implementation of the abstract class RayTracerBase.
 * It provides a method to trace a ray in the scene.
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructs a RayTracerBasic object with the given scene.
     *
     * @param scene the scene to trace rays in
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {

        List<Point> intersections= scene.geometries.findIntersections(ray);
        if(intersections==null)
            return scene.background;

        Point closestPoint = ray.findClosestPoint(intersections);
        return calaColor(closestPoint);
    }


    /**
     * Calculates the color at a specific point
     *
     * @param point the point to calculate the color at.
     * @return the color at the specified point.
     */
    private Color calaColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
