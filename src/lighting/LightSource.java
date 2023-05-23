package lighting;
import primitives.*;
/**
 * The interface representing a light source.
 * @author Efrat Roth and Hadassah Stulman
 */
public interface LightSource {

    /**
     * Returns the intensity of the light at the specified point.
     *
     * @param p the point at which to evaluate the light intensity
     * @return the intensity of the light at the specified point
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction vector from the specified point towards the light source.
     *
     * @param p the point from which to calculate the direction vector
     * @return the direction vector from the specified point towards the light source
     */
    public Vector getL(Point p);
}
