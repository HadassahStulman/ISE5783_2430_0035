package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents the ambient light in a scene,
 * which is the constant light that is present regardless of the position or orientation of objects in the scene.
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class AmbientLight extends Light {


    /**
     * Represents the absence of ambient light (i.e., an ambient light with zero intensity).
     */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);


    /**
     * Constructs a new AmbientLight object with the given color and attenuation factor.
     * the constructor sets the intensity to be IA x kA
     *
     * @param IA the color of the ambient light
     * @param kA the attenuation factor for the light as a Double3 object
     */
    public AmbientLight(Color IA, Double3 kA) {
        super(IA.scale(kA));
    }


    /**
     * Constructs a new AmbientLight object with the given color and attenuation factor.
     * the constructor sets the intensity to be IA x kA
     *
     * @param IA the color of the ambient light
     * @param kA the attenuation factor for the light as a double value
     */
    public AmbientLight(Color IA, Double kA) {
        super(IA.scale(kA));
    }


}

