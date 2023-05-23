package renderer;

import primitives.*;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;


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

        List<GeoPoint> intersections= scene.geometries.findGeoIntersections(ray);
        if(intersections == null)
            return scene.background;

        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint);
    }


    /**
     * Calculates the color at a specific point
     *
     * @param point the point to calculate the color at.
     * @return the color at the specified point.
     */
    private Color calcColor(GeoPoint point) {
        Color color = scene.ambientLight.getIntensity();
        return color.add(point.geometry.getEmission());
    }
}
