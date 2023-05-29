package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;


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
     * Calculates the local effects of lighting at a given geometric point on a ray.
     *
     * @param geoPoint The geometric point at which to calculate the local effects.
     * @param ray      The ray for which to calculate the local effects.
     * @return The color resulting from the local lighting effects at the given geometric point and ray.
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {

        Color color = geoPoint.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        double nv = alignZero(n.dotProduct(v));

        // If the surface is perpendicular to the view direction, no contribution to the color
        if (nv == 0)
            return color;

        Material material = geoPoint.geometry.getMaterial();

        // iterate over the lights, and adds to the final color the effect of each light source
        for (LightSource lightSource : scene.lights) {

            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));

            // if the camera and the light are on the same side of the geometry
            if (nl * nv > 0) {
                Color iL = lightSource.getIntensity(geoPoint.point);

                // Calculate the diffuse and specular components
                Double3 diffuse = calcDiffuse(material, nl);
                Double3 specular = calcSpecular(material, n, l, nl, v);

                // Add the diffuse and specular components to the color
                color = color.add(iL.scale(diffuse), iL.scale(specular));
            }
        }
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
        return material.kS.scale(Math.pow(max, material.nShininess));
    }

}
