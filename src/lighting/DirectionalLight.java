package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * A subclass of Light representing a directional light source.
 * Implements the LightSource interface.
 * @author Efrat Roth and Hadassah Stulman
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**
     * Constructs a new DirectionalLight object with the specified intensity and direction.
     *
     * @param intensity the intensity of the directional light
     * @param direction the direction vector of the directional light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }


    /**
     * Returns the intensity of the directional light.
     * In the directional light the intensity isn't effected by direction or distance
     *
     * @param p the point at which to evaluate the light intensity (ignored)
     * @return the intensity of the directional light
     */
    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
       return direction.normalize();
    }
}

