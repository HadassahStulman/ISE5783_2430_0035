package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * A subclass of PointLight representing a spotLight source.
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class SpotLight extends PointLight {

    /**
     * The direction vector of the spotLight.
     */
    private final Vector direction;

    /**
     * The concentration factor that determines the narrowness of the light beam emitted by the spotlight.
     * Higher values create a narrower and more focused beam, while lower values result in a wider beam.
     */
    private double beamConcentration = 1;


    /**
     * Constructs a new SpotLight object with the specified intensity, position, and direction.
     *
     * @param intensity the intensity of the spotlight
     * @param position  the position of the spotlight
     * @param direction the direction vector of the spotlight
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }


    /**
     * Sets the narrow beam concentration for the spotlight.
     * Higher concentration values create a narrower and more focused beam.
     * Lower concentration values result in a wider beam.
     *
     * @param concentration the narrow beam concentration value to set
     * @return the modified SpotLight object with the updated narrow beam concentration
     */
    public SpotLight setNarrowBeam(double concentration) {
        this.beamConcentration = concentration;
        return this;
    }


    @Override
    public Color getIntensity(Point p) {

        // Get the intensity of the point light at the specified point
        Color pointLightColor = super.getIntensity(p);

        // Get the direction vector from the specified point towards the point light
        Vector l = super.getL(p);

        // dot product between the direction vector of the spotlight and the direction vector from the point
        // towards the light. This determines the angle between the two vectors.
        double dirL = direction.dotProduct(l);

        // Calculate the beam factor, which represents the contribution of the light within the beam angle
        double beamFactor = Math.pow(Math.max(0, dirL), beamConcentration);

        return pointLightColor.scale(beamFactor);
    }
}

