package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * An interface for geometric objects that can be intersected by a ray.
 */
public interface Intersectable {

    /**
     * Finds the intersections between a given ray and the geometry.
     * @param ray The ray to intersect with the geometry.
     * @return A list of points representing the intersections between the ray and the geometry.
     */
    List<Point> findIntersections(Ray ray);
}

