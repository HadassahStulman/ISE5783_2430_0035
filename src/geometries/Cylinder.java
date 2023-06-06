package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Cylinder class represents three-dimensional Cylinder in 3D Cartesian coordinate
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class Cylinder extends Tube {

    private final double height;

    /**
     * constructor to initialize cylinder with radius' axis ray and height
     *
     * @param axisRay axis ray ot cylinder
     * @param radius  radius of cylinder
     * @param height  height of cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * getter
     *
     * @return Cylinder's height
     */
    public double getHeight() {
        return height;
    }


   }
