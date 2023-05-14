package geometries;

/**
 * class RadialGeometry is the basic class representing all Euclidean geometry with radius in Cartesian 3-Dimensional coordinate system
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public abstract class RadialGeometry implements Geometry {


    /**
     * radius of the radial geometry
     */
    final protected double radius;


    /**
     * constructor to initialize the radial geometry with a given radius
     *
     * @param radius radius of geometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    /**
     * getter
     *
     * @return radius
     */
    public double getRadius() {
        return radius;
    }

}
