package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Sphere class represents a 3D sphere in Cartesian 3D coordinate system
 *
 * @author Efrat Roth and hadassah Stulman
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor to initialize Sphere with center point and radius
     *
     * @param center center point of sphere
     * @param radius radius of sphere
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * getter
     *
     * @return center point
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

}
