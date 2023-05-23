package primitives;

public class Material {

    public Double3 kD = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
     public int nShininess = 0;

    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    public Material setkD(double kD) {
      return setkD(new Double3(kD));
    }

    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    public Material setkS(double kS) {
        return setkS(new Double3(kS));
    }

    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

}
