package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * interface geometry is the basic behavior of all Geometries in Cartesian 3-Dimensional coordinate system
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public interface Geometry extends Intersectable {

    /**
     * return a normal vector to the geometry at the given point
     *
     * @param point point on the geometry
     * @return normal vector
     */
    public Vector getNormal(Point point);
}
