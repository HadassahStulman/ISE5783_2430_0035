package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * The RayTracerBasic class represents a basic implementation of the abstract class RayTracerBase.
 * It provides a method to trace a ray in the scene.
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class RayTracerBasic extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;


    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {

        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }


    /**
     * Calculates the color at the intersection point of a ray with a geometric object,
     * taking into account local and global effects.
     *
     * @param intersection The intersection point of the ray with the geometric object.
     * @param ray          The ray used for intersection calculation.
     * @param level        The recursion level for global effects calculation.
     * @param k            The reflection and transparency coefficients.
     * @return The calculated color at the intersection point.
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return level == 1 ? color
                : color.add(calcGlobalEffects(intersection, ray, level, k));
    }


    /**
     * Calculates the color at the intersection point between a ray and a geometric object in the scene.
     * The color is calculated by considering the local effects of the intersected object and adding the global effects
     * such as reflections and refractions.
     *
     * @param gp  The geometric intersection point between the ray and an object.
     * @param ray The ray that intersected the object.
     * @return The color at the intersection point, taking into account local and global effects.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }


    /**
     * Calculates the local effects of a light source at a given geometric point, taking into account
     * the material properties of the intersected object.
     *
     * @param geoPoint The geometric point at which to calculate the local effects.
     * @param ray      The ray that intersected the object.
     * @param k        The coefficient of transparency for the intersected object.
     * @return The color resulting from the local effects of the light source.
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {

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

                Double3 ktr = transparency(lightSource, l, n, geoPoint);
                // Check if the geometric point is unshaded by any objects in the scene along the light direction
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
//                if(unshaded(geoPoint,l,n,nv,lightSource)){
                    // Get the intensity of the light source at the geometric point
//                    Color iL = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    Color iL = lightSource.getIntensity(geoPoint.point).scale(ktr);

                    // Calculate the diffuse reflection component
                    Double3 diffuse = calcDiffuse(material, nl);
                    // Calculate the specular reflection component
                    Double3 specular = calcSpecular(material, n, l, nl, v);

                    // Add the scaled diffuse and specular components to the color
                    color = color.add(iL.scale(diffuse), iL.scale(specular));
                }
            }
        }

        // Return the final color with the combined local effects
        return color;
    }


    /**
     * Calculates the diffuse reflection component for a given material and the dot product between the surface normal
     * and the light vector.
     *
     * @param material The material of the intersected object.
     * @param nl       The dot product between the object normal and the light vector.
     * @return The diffuse reflection component.
     */
    private Double3 calcDiffuse(Material material, double nl) {
        // Diffuse reflection is determined by scaling the diffuse coefficient with the absolute value of the dot product
        // of the surface normal and light vector
        return material.kD.scale(Math.abs(nl));
    }


    /**
     * Calculates the specular reflection component for a given material, object normal, light vector, view vector,
     * and dot product between the object normal and light vector.
     *
     * @param material The material of the intersected object.
     * @param n        The surface normal at the intersection point.
     * @param l        The direction vector from the intersection point towards the light source.
     * @param nl       The dot product between the surface normal and the light vector.
     * @param v        The direction vector of the ray (view vector).
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


    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nv, LightSource light) {

        // Reverse the light direction to get the direction from the point towards the light source
        Vector lightDirection = l.scale(-1);

        // Create a ray from the geometric point towards the light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);

        // Find the intersections between the ray and the geometries in the scene
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));

        if (intersections == null)
            return true;

        // If there are no intersections, the point is unshaded
        return intersections.isEmpty();
    }

    /**
     * Calculates the transparency factor for a given light source, light direction, surface normal, and intersection point.
     *
     * @param lightSource The light source in the scene.
     * @param l           The direction vector from the intersection point towards the light source.
     * @param n           The surface normal at the intersection point.
     * @param gp          The geometric point of intersection.
     * @return The transparency factor.
     */
    private Double3 transparency(LightSource lightSource, Vector l, Vector n, GeoPoint gp) {
        // Create a ray from the intersection point towards the light source
        Vector lightDirection = l.scale(-1); // from point to light source

        Point point = gp.point;
        Ray lightRay = new Ray(point, lightDirection, n);

        double maxDistance = lightSource.getDistance(point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, maxDistance);

        // If there are no intersections, return full transparency
        if (intersections == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;

        for (GeoPoint geo : intersections) {
            // Check if the intersection point is closer to the light source than the current point
            if (point.distance(geo.point) < lightSource.getDistance(point)) {
                // Multiply the transparency factor by the kT value of the intersected geometry
                ktr = geo.geometry.getKt().product(ktr);
            }

            // If the transparency factor is zero, no light can pass through
            if (ktr.equals(Double3.ZERO)) {
                return Double3.ZERO;
            }
        }

        return ktr;
    }


    /**
     * Calculates the global effects (reflection and refraction) for a given intersection point, ray, recursion level, and coefficient.
     *
     * @param gp    The geometric point of intersection.
     * @param ray   The ray being traced.
     * @param level The recursion level.
     * @param k     The coefficient.
     * @return The color with the combined global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        // Calculate the color contribution from reflection
        Color reflectionColor = calcGlobalEffect(constructReflectedRay(gp, v, n), level, k, material.kR);

        // Calculate the color contribution from refraction
        Color refractionColor = calcGlobalEffect(constructRefractedRay(gp, v, n), level, k, material.kT);

        // Return the color with the combined global effects
        return reflectionColor.add(refractionColor);
    }


    /**
     * Calculates the global effect (reflection or refraction) for a given ray, recursion level, coefficient, and material coefficient.
     *
     * @param ray   The ray being traced.
     * @param level The recursion level.
     * @param k     The coefficient.
     * @param kx    The material coefficient for reflection (kR) or refraction (kT).
     * @return The color with the global effect.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);

        // Check if the product of the coefficients is lower than the minimum threshold, return black color
        if (kkx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;

        // Find the closest intersection point with the ray
        GeoPoint gp = findClosestIntersection(ray);

        // If there is no intersection, return the background color scaled by the material coefficient
        if (gp == null)
            return scene.background.scale(kx);

        // Check if the dot product between the surface normal and ray direction is approximately zero,
        // return black color
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ?
                Color.BLACK
                // Calculate the color with the combined global effect recursively by calling calcColor with updated
                // level and coefficient
                : calcColor(gp, ray, level - 1, kkx).scale(kx);

    }


    /**
     * Finds the closest intersection between a ray and the geometries in the scene.
     *
     * @param ray The ray for which to find the closest intersection.
     * @return The closest GeoPoint of intersection, or null if no intersections are found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);

        // If no intersections are found, return null
        if (intersections == null)
            return null;

        // Find and return the closest GeoPoint of intersection
        return ray.findClosestGeoPoint(intersections);
    }


    /**
     * Constructs a reflected ray based on the incoming ray, intersection point, and surface normal.
     *
     * @param gp The GeoPoint of intersection.
     * @param v  The incoming direction vector of the ray.
     * @param n  The surface normal at the intersection point.
     * @return The reflected ray, or null if an exception occurs.
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        try {
            double vn = v.dotProduct(n);
            Vector r = v.subtract(n.scale(vn * 2)).normalize();
            return new Ray(gp.point, r, n);
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * Constructs a refracted ray based on the incoming ray, intersection point, and surface normal.
     *
     * @param gp The GeoPoint of intersection.
     * @param v  The incoming direction vector of the ray.
     * @param n  The surface normal at the intersection point.
     * @return The refracted ray.
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
    }
}
