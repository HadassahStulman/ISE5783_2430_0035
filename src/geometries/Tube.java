package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

/**
 * Tube class represents three-dimensional Tube in 3D Cartesian coordinate
 *
 * @author Efrat Roth and hadassah Stulman
 */
public class Tube extends RadialGeometry {

    /**
     * the axis of the tube
     */
    protected final Ray axisRay;

    /**
     * constructor to initialize Tube with radius and axis ray
     *
     * @param axisRay axis ray of tube
     * @param radius  radius of tube
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * getter
     *
     * @return axis ray of tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
