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



    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return level == 1 ? color
                : color.add(calcGlobalEffects(intersection, ray, level, k));

    }

    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }


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
                    Color iL = lightSource.getIntensity(geoPoint.point);

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


    private Double3 calcDiffuse(Material material, double nl) {
        // Diffuse reflection is determined by scaling the diffuse coefficient with the absolute value of the dot product of the surface normal and light vector
        return material.kD.scale(Math.abs(nl));
    }


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

    private Double3 transparency(LightSource lightSource, Vector l, Vector n, GeoPoint gp) {
        // Pay attention to your method of distance screening
        Vector lightDirection = l.scale(-1); // from point to light source
        Point point = gp.point;
        Ray lightRay = new Ray(point, lightDirection, n);

        double maxDistance = lightSource.getDistance(point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, maxDistance);

        if (intersections == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;

        for (GeoPoint geo : intersections) {
            if (point.distance(geo.point) < lightSource.getDistance(point)) {
                ktr = geo.geometry.getKt().product(ktr);
            }

            if (ktr.equals(Double3.ZERO)) {
                return Double3.ZERO;
            }
        }
        return ktr;
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(gp, v, n), level, k, material.kR)
                .add(calcGlobalEffect(constructRefractedRay(gp, v, n), level, k, material.kT));
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK
                : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    private GeoPoint findClosestIntersection(Ray ray) {

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return null;

        return ray.findClosestGeoPoint(intersections);
    }

    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        try {
            double vn = v.dotProduct(n);
            Vector r = v.subtract(n.scale(vn * 2)).normalize();
            return new Ray(gp.point, r, n);
        } catch (Exception ex) {
            return null;
        }
    }

    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
    }
}
