package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


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

    private Point centerPoint;


    /**
     * The ImageWriter object used to write the image of the scene captured by the camera.
     */
    private ImageWriter imageWriter;

    /**
     * The RayTracerBase object used to trace the rays in the scene captured by the camera.
     */
    private RayTracerBase rayTracer;

    private int superSampling = 0;

    private boolean adaptive;

    private int threadsCount = 0;


    /**
     * Pixel manager for supporting:
     * <ul>
     * <li>multi-threading</li>
     * <li>debug print of progress percentage in Console window/tab</li>
     * <ul>
     */
    private PixelManager pixelManager;

    /**
     * The interval (in seconds) between printing camera updates for multithreading.
     *
     * <p>This field represents the time interval at which the camera updates are printed
     * when the camera is being used in a multithreading context. The value is measured in
     * seconds and defaults to 5 seconds.</p>
     */
    private double printInterval = 5;

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

        // calculates the center point of the image
        centerPoint = p0.add(vTo.scale(this.distance));

        return this;
    }


    /**
     * sets the ImageWriter of the Camera
     *
     * @param imageWriter the ImageWriter to be set
     * @return this Camera object
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * sets the RayTracerBase of the Camera
     *
     * @param rayTracer the RayTracerBase to be set
     * @return this Camera object
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * sets the superSampling flag of the Camera.
     *
     * @param superSampling the SuperSampling flag and amount of rays in beam
     * @return this Camera object
     */
    public Camera setSuperSampling(int superSampling) {
        this.superSampling = superSampling;
        return this;
    }

    /**
     * set the adaptive flag.
     *
     * @param adaptive the adaptive flag to be set
     * @return the Camera object
     */
    public Camera setAdaptive(boolean adaptive) {
        this.adaptive = adaptive;
        return this;
    }

    /**
     * The setter initialize rendering-progress printing time interval in seconds
     *
     * @param printInterval - the interval of printing
     * @return This Camera object
     */
    public Camera setDebugPrint(double printInterval) {
        this.printInterval = printInterval;
        return this;
    }

    /**
     * Set multi threading functionality for accelerating the rendering speed.
     * Initialize the number of threads.
     * The default value is 0 (no threads).
     * The recommended value for the multithreading is 3.
     *
     * @param threadsCount the threads amount
     * @return This Camera object
     */
    public Camera setMultithreading(int threadsCount) {
        if (threadsCount < 0)
            throw new IllegalArgumentException("Threads parameter must be 0 or higher");
        if (threadsCount != 0)
            this.threadsCount = threadsCount;
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
     * @param j  the horizontal index of the pixel which was being cast
     * @param i  the vertical index of the pixel which was being cast
     * @return the color of the closest intersected geometry, if any
     */
    private Color castRay(int nX, int nY, int j, int i) {
        Ray ray = constructRay(nX, nY, j, i);
        return rayTracer.traceRay(ray);
    }


    /**
     * Casts multiple rays through a pixel with anti-aliasing and calculates the color at that pixel.
     *
     * @param nX the number of pixels along the x-axis
     * @param nY the number of pixels along the y-axis
     * @param j  the pixel's x-coordinate
     * @param i  the pixel's y-coordinate
     * @return the color at the pixel
     */
    private Color castRayBeam(int nX, int nY, int j, int i) {
        int superSamp = superSampling;
        List<Ray> ans = new ArrayList<>();

        // Image center
        Point p = p0.add(vTo.scale(distance));

        // Ratio (pixel width & height)
        double rY = height / nY;
        double rX = width / nX;

        // Pixel[i,j] center
        double yI = -(i - ((nY - 1) / 2)) * rY;
        double xJ = (j - ((nX - 1) / 2)) * rX;

        // Distance between the start of the ray in the pixel
        double dX = (double) rX / superSamp;
        double dY = (double) rY / superSamp;

        // The first point
        double firstX = xJ + ((int) (superSamp / 2)) * dX;
        double firstY = yI + ((int) (superSamp / 2)) * dY;
        Point pIJ = p;
        if (!isZero(firstX))
            pIJ = pIJ.add(vRight.scale(firstX));
        if (!isZero(firstY))
            pIJ = pIJ.add(vUp.scale(firstY));
        Point p1 = pIJ;

        // Generate the rays for the ray beam
        for (int c = 0; c < superSamp; c++) {
            for (int b = 0; b < superSamp; b++) {
                p1 = pIJ;
                if (!isZero(c)) {
                    p1 = p1.add(vRight.scale(dX * c));
                }
                if (!isZero(b)) {
                    p1 = p1.add(vUp.scale(dY * b));
                }

                ans.add(new Ray(p0, p1.subtract(p0)));
            }
        }

        // Calculate the average color from the rays in the beam
        double r = 0, g = 0, b = 0;
        for (Ray ray : ans) {
            Color rayColor = rayTracer.traceRay(ray);
            r += rayColor.getColor().getRed();
            g += rayColor.getColor().getGreen();
            b += rayColor.getColor().getBlue();
        }
        r = r / (ans.size());
        g = g / (ans.size());
        b = b / (ans.size());

        return new Color(r, g, b);
    }


    /**
     * Renders an image using the camera's settings.
     *
     * @return this camera object
     * @throws MissingResourceException if one of the camera's fields is missing
     */
    public Camera renderImage() {
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


        if (imageWriter == null)
            throw new MissingResourceException("missing the image writer of the camera", "ImageWriter", "imageWriter");
        if (rayTracer == null)
            throw new MissingResourceException("missing the ray tracer of the camera", "RayTracerBase", "rayTracer");


        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        pixelManager = new PixelManager(nY, nX, printInterval);

        int amountOfRays = superSampling == 0 ? 1 : superSampling;

        if (threadsCount == 0) {
            Color color;
            // Iterate over the width of the image (columns)
            for (int j = 0; j < nX; j++) {

                // Iterate over the height of the image (rows)
                for (int i = 0; i < nY; i++) {

                    renderPixel(j, i, amountOfRays);
                }
            }
        } else {
            var threads = new LinkedList<Thread>(); // list of threads
            while (threadsCount-- > 0) // add appropriate number of threads
                threads.add(new Thread(() -> { // add a thread with its code
                    PixelManager.Pixel pixel; // current pixel(row,col)

                    // allocate pixel(row,col) in loop until there are no more pixels
                    Color color;
                    while ((pixel = pixelManager.nextPixel()) != null) {

                        renderPixel(pixel.col(), pixel.row(), amountOfRays);
                    }
                }));

            // start all the threads
            for (var thread : threads)
                thread.start();
            // wait until all the threads have finished
            try {
                for (var thread : threads) thread.join();
            } catch (InterruptedException ignore) {
            }
        }
        return this;
    }

    /**
     * Renders a single pixel of the image.
     *
     * @param x            the x-coordinate of the pixel
     * @param y            the y-coordinate of the pixel
     * @param amountOfRays the number of rays to be cast through the pixel
     * @throws MissingResourceException if the imageWriter or viewPlane dimensions were not set
     */
    private void renderPixel(int x, int y, int amountOfRays) {
        Color color;
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        // without adaptive superSampling
        if (!adaptive) {

            // without antiAliasing
            if (superSampling == 0) {
                color = castRay(nX, nY, x, y);
            }
            // with antiAliasing
            else {
                color = castRayBeam(nX, nY, x, y);
            }
        }

        // with adaptive superSampling
        else {
            color = adaptiveSuperSampling(nX, nY, x, y, amountOfRays);
        }
        imageWriter.writePixel(x, y, color);
        pixelManager.pixelDone();
    }


    /**
     * Performs adaptive super-sampling by casting multiple rays through a pixel
     * with varying sub-pixel offsets and calculates the color at that pixel.
     *
     * @param nX        the number of pixels along the x-axis
     * @param nY        the number of pixels along the y-axis
     * @param j         the pixel's x-coordinate
     * @param i         the pixel's y-coordinate
     * @param numOfRays the number of rays to be cast through the pixel
     * @return the color at the pixel
     * @throws MissingResourceException if the imageWriter or viewPlane dimensions were not set
     */
    private Color adaptiveSuperSampling(int nX, int nY, int j, int i, int numOfRays) {

        Vector Vright = vRight;
        Vector Vup = vUp;
        Point cameraLocation = this.p0;
        int numOfRaysInRowCol = (int) Math.floor(Math.sqrt(numOfRays));

        // If only one ray is used, directly trace the ray through the pixel
        if (numOfRaysInRowCol == 1) {
            return castRay(nX, nY, j, i);
        }

        Point pIJ = getCenterOfPixel(nX, nY, j, i);

        // Calculate the ratios of pixel width and height
        double rY = alignZero(height / nY);
        double rX = alignZero(width / nX);

        double PRy = rY / numOfRaysInRowCol;
        double PRx = rX / numOfRaysInRowCol;

        // Perform recursive adaptive super sampling
        return rayTracer.adaptiveSuperSamplingRec(pIJ, rX, rY, PRx, PRy, cameraLocation, Vright, Vup, null);
    }


    /**
     * Calculates the center point of a pixel in the view plane.
     *
     * @param nX the number of pixels along the x-axis
     * @param nY the number of pixels along the y-axis
     * @param j  the pixel's x-coordinate
     * @param i  the pixel's y-coordinate
     * @return the center point of the pixel
     */
    private Point getCenterOfPixel(int nX, int nY, int j, int i) {

        // calculate the ratio of the pixel by the height and by the width of the view plane

        // the ratio Ry = h/Ny, the height of the pixel
        double rY = alignZero(height / nY);
        // the ratio Rx = w/Nx, the width of the pixel
        double rX = alignZero(width / nX);


        // Calculate the x-coordinate of the center point of the pixel
        double xJ = alignZero((j - ((nX - 1d) / 2d)) * rX);

        // Calculate the y-coordinate of the center point of the pixel
        double yI = alignZero(-(i - ((nY - 1d) / 2d)) * rY);

        Point pIJ = centerPoint;

        // Move the center point of the pixel horizontally
        if (!isZero(xJ)) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        // Move the center point of the pixel vertically
        if (!isZero(yI)) {
            pIJ = pIJ.add(vUp.scale(yI));
        }

        return pIJ;
    }

    /**
     * Prints a grid of a given interval and color on the image writer.
     *
     * @param interval the distance between the lines of the grid.
     * @param color    the color of the lines.
     * @throws MissingResourceException if the image writer of the camera is missing.
     */
    public Camera printGrid(int interval, Color color) {

        if (imageWriter == null)
            throw new MissingResourceException("missing the image writer of the camera", "ImageWriter", "imageWriter");

        // write grids rows
        for (int row = 0; row < imageWriter.getNy(); row += interval) {

            for (int col = 0; col < imageWriter.getNy(); col++) {
                imageWriter.writePixel(col, row, color);
            }
        }

        // write grids columns
        for (int col = 0; col < imageWriter.getNx(); col += interval) {

            for (int row = 0; row < imageWriter.getNy(); row++) {
                imageWriter.writePixel(col, row, color);
            }
        }
        return this;
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
