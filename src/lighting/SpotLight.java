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
    private Vector direction;

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

    @Override
    public Color getIntensity(Point p) {

        // Get the intensity of the point light at the specified point
        Color pointLightColor = super.getIntensity(p);

        // Get the direction vector from the specified point towards the point light
        Vector l = super.getL(p);

        // Calculate the dot product between the direction vector of the spotlight and the direction vector from the point
        // towards the light. This determines the angle between the two vectors.
        double dirL = direction.dotProduct(l);

        return pointLightColor.scale(Math.max(0,dirL));
    }
}

