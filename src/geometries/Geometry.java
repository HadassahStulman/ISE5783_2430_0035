package geometries;

import primitives.*;

/**
 * interface geometry is the basic behavior of all Geometries in Cartesian 3-Dimensional coordinate system
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public abstract class Geometry extends Intersectable {

    private Color emission = Color.BLACK;
    private Material material = new Material();

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


    /**
     * Retrieves the material associated with an object.
     *
     * @return The material associated with the object.
     */
    public Material getMaterial() {
        return material;
    }


    /**
     * Sets the material of the geometry.
     *
     * @param material The material to set.
     * @return The updated Geometry object.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Retrieves the reflection coefficient of the material.
     *
     * @return The reflection coefficient as a Double3 object.
     */
    public Double3 getKr(){
        return material.kR;
    }

    /**
     * Retrieves the transmission coefficient of the material.
     *
     * @return The transmission coefficient as a Double3 object.
     */
    public Double3 getKt(){
        return material.kT;
    }

}
