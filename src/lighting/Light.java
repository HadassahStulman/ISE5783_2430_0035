package lighting;

import primitives.Color;

/**
 * The abstract base class representing a light source.
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public abstract class Light {

    private final Color intensity;

    /**
     * Constructs a new Light object with the specified intensity.
     *
     * @param intensity the intensity of the light source
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light source.
     *
     * @return the intensity of the light source
     */
    public Color getIntensity() {
        return intensity;
    }
}

