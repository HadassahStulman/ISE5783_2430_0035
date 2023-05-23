package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * An interface for geometric objects that can be intersected by a ray.
 *
 * @author Efrat Roth and hadassah Stulman
 */
public abstract class Intersectable {

    /**
     * Finds the intersections between a given ray and the geometry.
     *
     * @param ray The ray to intersect with the geometry.
     * @return A list of points representing the intersections between the ray and the geometry.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the geometric intersections between the geometry and a given ray.
     *
     * @param ray The ray to intersect with the geometry.
     * @return A list of GeoPoint objects representing the geometric intersections.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }


    /**
     * Helper method for finding geometric intersections between the geometry and a given ray.
     * This method should be implemented by subclasses.
     *
     * @param ray The ray to intersect with the geometry.
     * @return A list of GeoPoint objects representing the geometric intersections.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);


    /**
     * The GeoPoint class represents a geographic point with associated geometry information.
     *
     * @author Efrat Roth and Hadassah Stulman
     */
    public static class GeoPoint {

        /**
         * The Geometry object representing the geometric properties of the point.
         */
        public Geometry geometry;

        /**
         * The Point object representing the coordinates of the point.
         */
        public Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof GeoPoint geoPoint)
                return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
            return false;
        }


        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}

