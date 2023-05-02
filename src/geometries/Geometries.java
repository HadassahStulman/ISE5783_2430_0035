package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represent A collection of geometries that can be intersected by a ray.
 *
 * @author Efrat Roth and hadassah Stulman
 */

public class Geometries implements Intersectable {

    private List<Intersectable> geometries;

    /**
     * Creates an empty collection of geometries.
     */
    public Geometries() {
        geometries = new LinkedList<Intersectable>();
    }

    /**
     * Creates a collection of geometries from the provided Intersectable objects.
     *
     * @param geometries an array of Intersectable objects to add to the collection.
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = List.of(geometries);
    }

    /**
     * Adds the provided Intersectable objects to the collection.
     *
     * @param geometries an array of Intersectable objects to add to the collection.
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(List.of(geometries));
    }


    @Override
    public List<Point> findIntersections(Ray ray) {

        int amount = 0;
        List<Point> lstIntersection;

        // Iterate over all the geometries in this composite object.
        for (Intersectable geometry : geometries) {
            lstIntersection = geometry.findIntersections(ray);
            if (lstIntersection != null)
                amount += lstIntersection.size();
        }

        // If there are no intersection points, return null.
        if (amount == 0)
            return null;

        // Collect all the intersection points in a list.
        List<Point> lstAllIntersections = new LinkedList<Point>();
        for (Intersectable geometry : geometries) {
            lstIntersection = geometry.findIntersections(ray);
            if (lstIntersection != null)
                lstAllIntersections.addAll(lstIntersection);
        }

        // Return the list of intersection points.
        return lstAllIntersections;
    }
}
