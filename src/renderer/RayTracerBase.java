package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;


/**
 * An abstract class representing a basic Ray Tracer.
 * Contains the scene being traced and an abstract method for tracing a ray.
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public abstract class RayTracerBase {

    /**
     * The scene being traced.
     */
    protected Scene scene;


    /**
     * Constructs a RayTracerBase object with the specified scene.
     *
     * @param scene the scene being traced
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }


    /**
     * Traces the specified ray and returns the color of the closest intersection.
     *
     * @param ray the ray being traced
     * @return the color of the closest intersection
     */
    public abstract Color traceRay(Ray ray);

    public abstract Color adaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints);
}
