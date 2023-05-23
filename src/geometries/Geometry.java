package geometries;

import primitives.*;

/**
 * interface geometry is the basic behavior of all Geometries in Cartesian 3-Dimensional coordinate system
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;

    /**
     * Retrieves the emission color of the geometry.
     *
     * @return The emission color.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The emission color to set.
     * @return The updated Geometry object.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }



    /**
     * return a normal vector to the geometry at the given point
     *
     * @param point point on the geometry
     * @return normal vector
     */
    public abstract Vector getNormal(Point point);
}
