package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * A class representing a scene that contains geometries and lighting properties.
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class Scene {

    /**
     * The name of the scene.
     */
    public String name;


    /**
     * The background color of the scene.
     */
    public Color background = Color.BLACK;


    /**
     * The ambient light of the scene.
     */
    public AmbientLight ambientLight = AmbientLight.NONE;


    /**
     * The geometries in the scene.
     */
    public Geometries geometries = new Geometries();


    /**
     * Constructs a new Scene object with the given name.
     *
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }


    /**
     * Sets the background color of the scene.
     *
     * @param background The background color to set.
     * @return This Scene object with the updated background color.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight The ambient light to set.
     * @return This Scene object with the updated ambient light.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries in the scene.
     *
     * @param geometries The geometries to set.
     * @return This Scene object with the updated geometries.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
