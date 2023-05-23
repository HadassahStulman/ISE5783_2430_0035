package renderer;

import primitives.*;

import java.util.MissingResourceException;

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
     The ImageWriter object used to write the image of the scene captured by the camera.
     */
    private ImageWriter imageWriter;

    /**
     The RayTracerBase object used to trace the rays in the scene captured by the camera.
     */
    private RayTracerBase rayTracer;


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
        if (isZero(distance))
            throw new IllegalArgumentException("distance can't be 0");
        this.distance = distance;
        return this;
    }


    /**
     sets the ImageWriter of the Camera
     @param imageWriter the ImageWriter to be set
     @return this Camera object
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }
    /**

     sets the RayTracerBase of the Camera
     @param rayTracer the RayTracerBase to be set
     @return this Camera object
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
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


    /**
     * Casts a ray through a pixel at position (j, i) on the view plane,
     * and returns the color of the closest intersected geometry, if any.
     *
     * @param nX the number of pixels along the width of the image plane
     * @param nY the number of pixels along the height of the image plane
     * @param j  the horizontal index of the pixel being casted
     * @param i  the vertical index of the pixel being casted
     * @return the color of the closest intersected geometry, if any
     */
    private Color castRay(int nX, int nY, int j, int i) {
        Ray ray = constructRay(nX, nY, j, i);
        return rayTracer.traceRay(ray);
    }


    /**
     * Renders an image using the camera's settings.
     *
     * @throws MissingResourceException if one of the camera's fields is missing
     */
    public void renderImage() {
        if (p0 == null)
            throw new MissingResourceException("missing the camera's position in 3D space", "Point", "p0");
        if (vTo == null)
            throw new MissingResourceException("missing the direction vector the camera is facing", "Vector", "vTo");
        if (vUp == null)
            throw new MissingResourceException("missing the up vector of the camera", "Vector", "vUp");
        if (height == 0)
            throw new MissingResourceException("missing the height of the camera's view plane", "double", "height");
        if (width == 0)
            throw new MissingResourceException("missing the width of the camera's view plane", "double", "width");

        //// !!!!!!!!! check the doubles????????????? they cant be null... !!!!!!!!!!!!!!!

        if (imageWriter == null)
            throw new MissingResourceException("missing the image writer of the camera", "ImageWriter", "imageWriter");
        if (rayTracer == null)
            throw new MissingResourceException("missing the ray tracer of the camera", "RayTracerBase", "rayTracer");


        int nX=imageWriter.getNx();
        int nY=imageWriter.getNy();
        // Iterate over the width of the image (columns)
        for (int j = 0; j< nX; j++) {

            // Iterate over the height of the image (rows)
            for (int i = 0; i < nY; i++) {

                Color color=castRay(nX, nY, j,i);
                imageWriter.writePixel(j,i,color);
            }
        }
    }


    /**
     * Prints a grid of a given interval and color on the image writer.
     *
     * @param interval the distance between the lines of the grid.
     * @param color    the color of the lines.
     * @throws MissingResourceException if the image writer of the camera is missing.
     */
    public void printGrid(int interval, Color color) {

        if (imageWriter == null)
            throw new MissingResourceException("missing the image writer of the camera", "ImageWriter", "imageWriter");

        // write grids rows
        for (int row = 0; row < imageWriter.getNy(); row+=interval) {

            for (int col = 0; col < imageWriter.getNy(); col++) {
                imageWriter.writePixel(col, row, color);
            }
        }

        // write grids columns
        for (int col = 0; col < imageWriter.getNx(); col+=interval) {

            for (int row = 0; row < imageWriter.getNy(); row++) {
                    imageWriter.writePixel(col, row, color);
            }
        }
    }

    /**
     * Writes the image to the image writer (delegation).
     *
     * @throws MissingResourceException if the image writer of the camera is missing.
     */
    public void writeToImage() {

        if (imageWriter == null)
            throw new MissingResourceException("missing the image writer of the camera", "ImageWriter", "imageWriter");

        imageWriter.writeToImage();

    }

}
