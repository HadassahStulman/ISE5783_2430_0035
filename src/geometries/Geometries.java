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


        // Collect all the intersection points in a list.
        List<Point> lstAllIntersections = null;
        for (Intersectable geometry : geometries) {

            lstIntersection = geometry.findIntersections(ray);

            if (lstIntersection != null) {

                if (lstAllIntersections == null)
                    lstAllIntersections = new LinkedList<Point>();

                lstAllIntersections.addAll(lstIntersection);
            }
        }

        // If there are no intersection points, return null.
        if (lstAllIntersections == null)
            return null;

        // Return the list of intersection points.
        return lstAllIntersections;
    }
}
