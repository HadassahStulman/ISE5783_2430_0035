package renderer;

import primitives.*;

import static primitives.Util.*;


/**
 * The Camera class represents a virtual camera in a 3D space.
 * It provides methods to set up the camera's position, orientation, and viewport size and distance.
 *
 * @author Efrat Roth and Hadassah Stulman
 */

public class Camera {
    /**
     * The camera's position in 3D space.
     */
    private Point p0;

    /**
     * The up vector of the camera, normalized.
     */
    private Vector vUp;

    /**
     * The right vector of the camera, normalized.
     */
    private Vector vRight;

    /**
     * The direction vector the camera is facing, normalized.
     */
    private Vector vTo;

    /**
     * The height of the viewPlane.
     */
    private double height;

    /**
     * The width of the viewPlane.
     */
    private double width;

    /**
     * The distance between the camera and the viewPlane.
     */
    private double distance;

    /**
     * Constructs a new Camera object with the specified position, up vector,
     * <p>
     * and direction vector.
     *
     * @param p0  the position of the camera
     * @param vUp the up vector of the camera
     * @param vTo the direction vector of the camera
     * @throws IllegalArgumentException if vUp and vTo are not orthogonal
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {

        if (!isZero(vTo.dotProduct(vUp)))
            throw new IllegalArgumentException("vUp and vTo are not orthogonal");


        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();

        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Sets the size of the viewPlane.
     *
     * @param width  the width of the viewPlane
     * @param height the height of the viewPlane
     * @return this Camera object
     */
    public Camera setVPSize(double width, double height) {
        this.height = height;
        this.width = width;
        return this;
    }

    /**
     * Sets the distance between the camera and the viewPlane.
     *
     * @param distance the distance between the camera and the viewPlane
     * @return this Camera object
     */
    public Camera setVPDistance(double distance) {
        if(isZero(distance))
            throw new IllegalArgumentException("distance can't be 0");
        this.distance = distance;
        return this;
    }


    /**
     * Constructs a new ray from the camera's position through the specified
     * pixel coordinates in the viewPlane.
     *
     * @param nX the number of pixels along the x-axis
     * @param nY the number of pixels along the y-axis
     * @param j  the index of the pixel along the x-axis
     * @param i  the index of the pixel along the y-axis
     * @return a new Ray object from the camera's position through the
     * specified pixel coordinates in the viewPlane
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        Point Pc = p0.add(vTo.scale(distance));
        double Ry = height / nY;
        double Rx = width / nX;

        Point Pij = Pc;
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        if (!isZero(Xj))
            Pij = Pij.add(vRight.scale(Xj));
        if (!isZero(Yi))
            Pij = Pij.add(vUp.scale(Yi));

        Vector Vij = Pij.subtract(p0);
        return new Ray(p0, Vij);
    }

}
