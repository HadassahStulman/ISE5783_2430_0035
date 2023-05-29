package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * A subclass of Light representing a point light source.
 * Implements the LightSource interface.
 * @author Efrat Roth and Hadassah Stulman
 */
public class PointLight extends Light implements LightSource {

    private Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * Constructs a new PointLight object with the specified intensity and position.
     *
     * @param intensity the intensity of the point light
     * @param position the position of the point light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }


    /**
     * Sets the constant attenuation factor of the point light.
     *
     * @param kC the constant attenuation factor
     * @return the current PointLight object
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }


    /**
     * Sets the linear attenuation factor of the point light.
     *
     * @param kL the linear attenuation factor
     * @return the current PointLight object
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }


    /**
     * Sets the quadratic attenuation factor of the point light.
     *
     * @param kQ the quadratic attenuation factor
     * @return the current PointLight object
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {

        // Calculate the distance between the specified point and the position of the point light
        double distance = p.distance(position);

        // Calculate the denominator for the intensity calculation using attenuation factors
        // The intensity decreases with distance based on the attenuation factors
        double denom = kC + kL * distance + kQ * distance * distance;

        // Reduce the intensity by dividing it by the denominator
        Color IL = getIntensity().reduce(denom);

        return IL;
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
}
