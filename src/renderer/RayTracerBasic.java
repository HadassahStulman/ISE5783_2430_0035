package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;


/**
 * The RayTracerBasic class represents a basic implementation of the abstract class RayTracerBase.
 * It provides a method to trace a ray in the scene.
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;


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

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return scene.background;

        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }


    /**
     * Calculates the color at a specific point
     *
     * @param intersection the point to calculate the color at.
     * @return the color at the specified point.
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        Color color = scene.ambientLight.getIntensity();
        return color.add(calcLocalEffects(intersection, ray));
    }


    /**
     * Calculates the local effects (diffuse and specular) for a given geometric point and ray in the scene.
     *
     * @param geoPoint The geometric point to calculate the local effects for.
     * @param ray      The ray used to reach the geometric point.
     * @return The color resulting from the local effects.
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {

        // Initialize the color with the emission color of the geometry at the geometric point
        Color color = geoPoint.geometry.getEmission();

        // Get the direction vector of the ray
        Vector v = ray.getDir();

        // Get the surface normal at the geometric point
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        // Calculate the dot product between the surface normal and the direction vector of the ray
        double nv = alignZero(n.dotProduct(v));

        // If the dot product is approximately zero, return the color (no local effects)
        if (nv == 0)
            return color;

        // Get the material of the geometry
        Material material = geoPoint.geometry.getMaterial();

        // Iterate over all light sources in the scene
        for (LightSource lightSource : scene.lights) {

            // Get the direction vector from the geometric point towards the light source
            Vector l = lightSource.getL(geoPoint.point);

            // Calculate the dot product between the surface normal and the light vector
            double nl = alignZero(n.dotProduct(l));

            // If the dot product of the surface normal and light vector has the same sign as the dot product of the
            // surface normal and view vector, it means the light source is on the same side as the camera
            if (nl * nv > 0) {

                // Check if the geometric point is unshaded by any objects in the scene along the light direction
//                if (unshaded(geoPoint, l)) {

                    // Get the intensity of the light source at the geometric point
                    Color iL = lightSource.getIntensity(geoPoint.point);

                    // Calculate the diffuse reflection component
                    Double3 diffuse = calcDiffuse(material, nl);
                    // Calculate the specular reflection component
                    Double3 specular = calcSpecular(material, n, l, nl, v);

                    // Add the scaled diffuse and specular components to the color
                    color = color.add(iL.scale(diffuse), iL.scale(specular));
                //}
            }
        }

        // Return the final color with the combined local effects
        return color;
    }


    /**
     * Calculates the diffuse reflection component for a given material
     * and the dot product of the surface normal and light vector.
     *
     * @param material The material of the surface.
     * @param nl       The dot product of the surface normal and light vector.
     * @return The diffuse reflection component.
     */
    private Double3 calcDiffuse(Material material, double nl) {
        // Diffuse reflection is determined by scaling the diffuse coefficient with the absolute value of the dot product of the surface normal and light vector
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular reflection component for a given material, surface normal, light vector, view vector, and the dot product of the surface normal and light vector.
     *
     * @param material The material of the surface.
     * @param n        The surface normal.
     * @param l        The light vector.
     * @param nl       The dot product of the surface normal and light vector.
     * @param v        The view vector.
     * @return The specular reflection component.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {

        // Calculate the reflection vector using the formula: r = l - 2 * (nl * n)
        Vector r = l.subtract(n.scale(2 * nl));

        // Compute the dot product between the view vector and the reflection vector
        double minusVR = v.dotProduct(r) * -1;

        // Apply the specular reflection coefficient and shininess to the dot product
        double max = Math.max(0, minusVR);

        // Calculate the specular reflection component using the formula: kS * (max ^ nShininess)
        return material.kS.scale(Math.pow(max, material.nShininess));
    }

}
