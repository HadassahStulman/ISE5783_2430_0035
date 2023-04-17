package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import static primitives.Util.isZero;

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
        double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0())); // the distance between P0 and the projection of point on the axis ray
        Point O = axisRay.getP0().add(axisRay.getDir().scale(t)); // a point on the axis ray that is also on the normal


        // checking if the vector between po and point is orthogonal to the axis ray
        Vector temp = point.subtract(axisRay.getP0()); // a vector between po and the given point
        if(isZero(temp.dotProduct(axisRay.getDir())))
            throw new IllegalArgumentException("Can't find normal for a point that creates 90 degree angle with the head of the axis ray");

        return point.subtract(O).normalize();
    }
}
