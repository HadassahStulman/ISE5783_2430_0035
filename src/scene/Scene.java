package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * A class representing a scene that contains geometries and lighting properties.
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class Scene {

    private String name;
    public final Color background;
    public final AmbientLight ambientLight;
    public  final Geometries geometries;
    public final List<LightSource> lights ;



    /**
     * Constructs a new Scene object with the given name.
     *
     * @param builder The name of the scene.
     */
    private Scene(SceneBuilder builder ) {
        this.name = builder.name;
        this.background = builder.background;
        this.ambientLight = builder.ambientLight;
        this.geometries = builder.geometries;
        this.lights = builder.lights;
    }


    /**
     * SceneBuilder class is responsible for constructing scenes in a graphical application using the Builder pattern.
     * It allows users to create scenes step by step, providing flexibility and maintainability.
     *
     * @author Efrat Roth and Hadassah Stulman
     */
    public static class SceneBuilder {
        private final String name;
        private List<LightSource> lights = new LinkedList<>();
        private Geometries geometries = new Geometries();
        private AmbientLight ambientLight = AmbientLight.NONE;
        private Color background = Color.BLACK;

        /**
         * private constructor that creates scene
         *
         * @param name
         */
        public SceneBuilder(String name) {
            this.name=name;
        }

        /**
         * Sets the background color of the sceneBuilder.
         *
         * @param background The background color to set.
         * @return This sceneBuilder object with the updated background color.
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /**
         * Sets the ambient light of the sceneBuilder.
         *
         * @param ambient The ambient light to set.
         * @return This sceneBuilder object with the updated ambient light.
         */
        public SceneBuilder setAmbientLight(AmbientLight ambient) {
            this.ambientLight = ambient;
            return this;

        }

        /**
         * Sets the geometries in the sceneBuilder.
         *
         * @param geometries The geometries to set.
         * @return This sceneBuilder object with the updated geometries.
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }


        /**
         * Sets the lights in the sceneBuilder.
         *
         * @param lights The lights to set.
         * @return This sceneBuilder object with the updated lights.
         */
        public SceneBuilder setLights(LinkedList<LightSource> lights) {
            this.lights = lights;
            return this;
        }


        /**
         * the function Build returns the final scene that was built
         *
         * @return The scene
         */
        public Scene build() {
//            if (name == null)
//                throw new MissingResourceException("missing scene name", "Scene", "name");
//            if (geometries == null)
//                throw new MissingResourceException("scene has no Geometries", "Scene", "geometries");
            return new Scene(this);
        }
    }

}
