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
     * Finds the intersections of a ray with the geometry.
     *
     * @param ray The ray to find intersections with.
     * @return A list of points representing the intersections, or null if no intersections are found.
     */
    public final List<Point> findIntersections(Ray ray) {

        // finds geo intersections
        var geoList = findGeoIntersections(ray);
        if (geoList == null) {
            return null;
        }
        // returns a list of points
        return geoList.stream()
                .map(gp -> gp.point)
                .toList();
    }

    /**
     * Finds the intersections of a ray with the geometry, considering all intersections within a maximum distance.
     * return points in GeoPoint format
     *
     * @param ray The ray to find intersections with.
     * @return A list of GeoPoints representing the intersections.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Finds the intersections of a ray with the geometry, considering all intersections within a maximum distance.
     * return points in GeoPoint format
     *
     * @param ray         The ray to find intersections with.
     * @param maxDistance The maximum distance to consider for intersections.
     * @return A list of GeoPoints representing the intersections.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Helper method to find the intersections of a ray with the geometry, considering all intersections within a maximum distance.
     * Subclasses must implement this method to provide the actual intersection logic.
     *
     * @param ray         The ray to find intersections with.
     * @param maxDistance The maximum distance to consider for intersections.
     * @return A list of GeoPoints representing the intersections.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);


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

        /**
         * constructor to initialize GeoPoint
         *
         * @param geometry the Geometry object of the GeoPoint
         * @param point    the coordinate of the point
         */
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

