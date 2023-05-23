package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;
import primitives.Double3;

import java.util.MissingResourceException;

/**
 * A class representing a scene that contains geometries and lighting properties.
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class Scene {

    private String name;
    private Color background = Color.BLACK;
    private AmbientLight ambientLight = AmbientLight.NONE;
    private Geometries geometries = new Geometries();

    public String getName() {
        return name;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public Geometries getGeometries() {
        return geometries;
    }


    /**
     * Constructs a new Scene object with the given name.
     *
     * @param name The name of the scene.
     */
    private Scene(String name) {
        this.name = name;
    }


    /**
     * SceneBuilder class is responsible for constructing scenes in a graphical application using the Builder pattern.
     * It allows users to create scenes step by step, providing flexibility and maintainability.
     *
     * @author Efrat Roth and Hadassah Stulman
     */
    public static class sceneBuilder {
        private Scene scene = null;

        /**
         * private constructor that creates scene
         *
         * @param name
         */
        public sceneBuilder(String name) {
            scene = new Scene(name);
        }

        /**
         * Sets the background color of the sceneBuilder.
         *
         * @param background The background color to set.
         * @return This sceneBuilder object with the updated background color.
         */
        public sceneBuilder setBackground(Color background) {
            scene.background = background;
            return this;
        }

        /**
         * Sets the ambient light of the sceneBuilder.
         *
         * @param ambient The ambient light to set.
         * @return This sceneBuilder object with the updated ambient light.
         */
        public sceneBuilder setAmbientLight(AmbientLight ambient) {
            scene.ambientLight = ambient;
            return this;

        }

        /**
         * Sets the geometries in the sceneBuilder.
         *
         * @param geometries The geometries to set.
         * @return This sceneBuilder object with the updated geometries.
         */
        public sceneBuilder setGeometries(Geometries geometries) {
            scene.geometries = geometries;
            return this;
        }


        /**
         * the function Build returns the final scene that was built
         *
         * @return The scene
         */
        public Scene build() {
            if (scene.name == null)
                throw new MissingResourceException("missing scene name", "Scene", "name");
            if (scene.geometries == null)
                throw new MissingResourceException("scene has no Geometries", "Scene", "geometries");
            return scene;
        }
    }


}
