package primitives;

import java.util.Objects;

/**
 * class Point is the basic class representing a point of a Euclidean geometry in Cartesian 3-Dimensional coordinate system
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class Point {

    /**
     * static Zero Point
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * 3 coordinates of the Point
     **/
    final Double3 xyz;

    /**
     * constructor to initialize the Point object with 3 given coordinates
     *
     * @param d1 First number value
     * @param d2 Second number value
     * @param d3 Third number value
     */
    public Point(double d1, double d2, double d3) {
        this.xyz = new Double3(d1, d2, d3);
    }

    /**
     * constructor to initialize the Point object with values from a given Double3 object
     *
     * @param xyz Double3 object of Point's coordinates
     */
    Point(Double3 xyz) {
        this(xyz.d1, xyz.d2, xyz.d3);
    }


    /**
     * Returns the x coordinate of the point.
     *
     * @return The x coordinate of the point.
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * Returns the y coordinate of the point.
     *
     * @return The y coordinate of the point.
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * Returns the z coordinate of the point.
     *
     * @return The z coordinate of the point.
     */
    public double getZ() {
        return xyz.d3;
    }


    /**
     * vector subtraction between two Points
     *
     * @param point2 second Point
     * @return vector between P1 and current Point
     */
    public Vector subtract(Point point2) {
        return new Vector(this.xyz.subtract(point2.xyz));
    }

    /**
     * adds vector to point to create new point with summed values of the original point and vector's coordinates
     *
     * @param vector vector to add to point
     * @return new point that
     */
    public Point add(Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
    }

    /**
     * calculates squared distance between two points
     *
     * @param point2 other point
     * @return squared distance
     */
    public double distanceSquared(Point point2) {
        // distance squared is the sum of the squares of the difference between corresponding coordinates of the two points
        return (this.xyz.d1 - point2.xyz.d1) * (this.xyz.d1 - point2.xyz.d1) + (this.xyz.d2 - point2.xyz.d2) * (this.xyz.d2 - point2.xyz.d2) + (this.xyz.d3 - point2.xyz.d3) * (this.xyz.d3 - point2.xyz.d3);
    }

    /**
     * calculates distance between two points
     *
     * @param point2 other point
     * @return distance
     */
    public double distance(Point point2) {
        return Math.sqrt(this.distanceSquared(point2));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    @Override
    public String toString() {
        return "Point: " + xyz;
    }
}

