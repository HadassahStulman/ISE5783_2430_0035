package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * class Plane represents two-dimensional plane in Cartesian 3D coordinate system
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class Plane implements Geometry {
    private final Point q0;
    private final Vector normal;

    /**
     * constructor to initialize plane with point and normal
     *
     * @param q0     point on plane
     * @param vector normal vector to plane
     */
    public Plane(Point q0, Vector vector) {
        this.q0 = q0;
        this.normal = vector.normalize();
    }

    /**
     * constructor to initialize Plane according to 3 given Points on the Plane
     *
     * @param point1 first point
     * @param point2 second point
     * @param point3 third point
     */
    public Plane(Point point1, Point point2, Point point3) {
        this.q0 = point1;
        // check that
        this.normal = null;
        /**
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
         */
    }

    /**
     * getter
     *
     * @return point on the plane
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * getter
     *
     * @return normal vector to plane
     */
    public Vector getNormal() {
        return normal;
    }


    @Override
    public Vector getNormal(Point point) {
        return this.getNormal();
    }
}
