package primitives;


/**
 * Represents a material used for rendering.
 *
 * @author Efrat Roth and Hadassah Stulman
 */
public class Material {

    /**
     * The diffuse reflection coefficient.
     */
    public Double3 kD = Double3.ZERO;


    /**
     * The specular reflection coefficient.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * The shininess value.
     */
    public int nShininess = 0;


    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kD The diffuse reflection coefficient as a Double3.
     * @return The updated Material object.
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }


    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kD The diffuse reflection coefficient as a double.
     * @return The updated Material object.
     */
    public Material setKd(double kD) {
        return setKd(new Double3(kD));
    }


    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kS The specular reflection coefficient as a Double3.
     * @return The updated Material object.
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }


    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kS The specular reflection coefficient as a double.
     * @return The updated Material object.
     */

    public Material setKs(double kS) {
        return setKs(new Double3(kS));
    }


    /**
     * Sets the shininess value of the material.
     *
     * @param nShininess The shininess value as an integer.
     * @return The updated Material object.
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}