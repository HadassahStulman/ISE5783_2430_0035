package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

        Vector v = axisRay.getDir();
        Point O = axisRay.getP0();

        // checking if the vector between po and point is orthogonal to the axis ray
        if (!isZero(point.subtract(O).dotProduct(v))) {
            double t = v.dotProduct(point.subtract(O)); // the distance between P0 and the projection of point on the axis ray
            O = O.add(v.scale(t)); // a point on the axis ray that is also on the normal
        }

        return point.subtract(O).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }
}
