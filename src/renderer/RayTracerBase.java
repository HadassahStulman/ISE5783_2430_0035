package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;


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
}
