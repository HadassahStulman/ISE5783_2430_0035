package geometries;

import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represent A collection of geometries that can be intersected by a ray.
 *
 * @author Efrat Roth and hadassah Stulman
 */

public class Geometries extends Intersectable {

    private final List<Intersectable> geometries;

    /**
     * Creates an empty collection of geometries.
     */
    public Geometries() {
        geometries = new LinkedList<>();
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        List<GeoPoint> lstIntersection;


        // Collect all the intersection points in a list.
        List<GeoPoint> lstAllIntersections = null;
        for (Intersectable geometry : geometries) {

            lstIntersection = geometry.findGeoIntersections(ray);

            if (lstIntersection != null) {

                if (lstAllIntersections == null)
                    lstAllIntersections = new LinkedList<>();

                lstAllIntersections.addAll(lstIntersection);
            }
        }

        // Return the list of intersection points.
        return lstAllIntersections;
    }
}
