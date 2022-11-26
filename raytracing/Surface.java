import java.awt.*;
import java.util.List;

/**
 * The type Surface.
 */
/*
ambientReflectionCoeff =  ambient reflection coefficient
aFloat = diffuse reflection coefficient
specularReflectionCoeff = specular reflection coefficient
transmissionCoeff = transmission coefficient
reflectanceCoeff = reflectance coefficient
phongCoeff= phong exponent
 */
class Surface {
    private float intenseRed;
    private float intenseGreen;
    private float intenseBlue;    // surface's intrinsic color
    private float ambientReflectionCoeff;
    private float aFloat;
    private float specularReflectionCoeff;
    private float phongCoeff;    // constants for phong model
    private float transmissionCoeff;
    private float reflectanceCoeff;
    private float nt;
    private static final float TINY = 0.001f;
    private static final float I255 = 0.00392156f;  // 1/255

    /**
     *
     * @param redVal
     * @param greenVal
     * @param blueVal
     * @param ambientReflectionCoeff
     * @param diffuseReflectionCoeff
     * @param specularReflectionCoeff
     * @param phongCoef
     * @param reflectanceCoeff
     * @param transmissionCoeff
     * @param index
     */
    public Surface(float redVal, float greenVal, float blueVal, float ambientReflectionCoeff, float diffuseReflectionCoeff, float specularReflectionCoeff, float phongCoef, float reflectanceCoeff, float transmissionCoeff, float index) {
        setIntenseRed(redVal);
        setIntenseGreen(greenVal);
        setIntenseBlue(blueVal);
        setAmbientReflectionCoeff(ambientReflectionCoeff);
        setDiffuseReflectionCoeff(diffuseReflectionCoeff);
        setSpecularReflectionCoeff(specularReflectionCoeff);
        setPhongCoeff(phongCoef);
        setReflectanceCoeff(reflectanceCoeff * getI255());
        setTransmissionCoeff(transmissionCoeff);
        setNt(index);
    }

    /**
     * Gets tiny.
     *
     * @return the tiny
     */
    public static float getTINY() {
        return TINY;
    }

    /**
     * Gets i 255.
     *
     * @return the i 255
     */
    public static float getI255() {
        return I255;
    }

    /**
     * shade color
     * @param p
     * @param nValue
     * @param v
     * @param lights
     * @param objects
     * @param bgnd
     * @return
     */
    public Color Shade(Vector3D p, Vector3D nValue, Vector3D v, List<Object> lights, List<Object> objects, Color bgnd) {
        float red = 0;
        float green = 0;
        float blue = 0;
        for (Object lightSources : lights) {
            Light light = (Light) lightSources;
            if (light.getLightType() == Light.getAMBIENT()) {
                red += getAmbientReflectionCoeff() * getIntenseRed() * light.getIntenseRed();
                green += getAmbientReflectionCoeff() * getIntenseGreen() * light.getIntenseGreen();
                blue += getAmbientReflectionCoeff() * getIntenseBlue() * light.getIntenseBlue();
            } else {
                Vector3D lVector;
                if (light.getLightType() == Light.getPOINT()) {
                    lVector = new Vector3D(light.getLightVector().getPointX() - p.getPointX(), light.getLightVector().getPointY() - p.getPointY(), light.getLightVector().getPointZ() - p.getPointZ());
                    lVector.normalize();
                } else {
                    lVector = new Vector3D(-light.getLightVector().getPointX(), -light.getLightVector().getPointY(), -light.getLightVector().getPointZ());
                }

                // Check if the surface point is in shadow
                Vector3D poffset = new Vector3D(p.getPointX() + getTINY() * lVector.getPointX(), p.getPointY() + getTINY() * lVector.getPointY(), p.getPointZ() + getTINY() * lVector.getPointZ());
                Ray shadowRay = new Ray(poffset, lVector);
                if (shadowRay.trace(objects))
                    break;

                float lambert = Vector3D.dot(nValue, lVector);
                if (lambert > 0) {
                    if (getDiffuseReflectionCoeff() > 0) {
                        float diffuse = getDiffuseReflectionCoeff() * lambert;
                        red += diffuse * getIntenseRed() * light.getIntenseRed();
                        green += diffuse * getIntenseGreen() * light.getIntenseGreen();
                        blue += diffuse * getIntenseBlue() * light.getIntenseBlue();
                    }
                    if (getSpecularReflectionCoeff() > 0) {
                        lambert *= 2;
                        float spec = v.dot(lambert * nValue.getPointX() - lVector.getPointX(), lambert * nValue.getPointY() - lVector.getPointY(), lambert * nValue.getPointZ() - lVector.getPointZ());
                        if (spec > 0) {
                            spec = getSpecularReflectionCoeff() * ((float) Math.pow(spec, getPhongCoeff()));
                            red += spec * light.getIntenseRed();
                            green += spec * light.getIntenseGreen();
                            blue += spec * light.getIntenseBlue();
                        }
                    }
                }
            }
        }

        // Compute illumination due to reflection
        if (getReflectanceCoeff() > 0) {
            float t = v.dot(nValue);
            if (t > 0) {
                t *= 2;
                Vector3D reflect = new Vector3D(t * nValue.getPointX() - v.getPointX(), t * nValue.getPointY() - v.getPointY(), t * nValue.getPointZ() - v.getPointZ());
                Vector3D poffset = new Vector3D(p.getPointX() + getTINY() * reflect.getPointX(), p.getPointY() + getTINY() * reflect.getPointY(), p.getPointZ() + getTINY() * reflect.getPointZ());
                Ray reflectedRay = new Ray(poffset, reflect);
                if (reflectedRay.trace(objects)) {
                    Color rcolor = reflectedRay.Shade(lights, objects, bgnd);
                    red += getReflectanceCoeff() * rcolor.getRed();
                    green += getReflectanceCoeff() * rcolor.getGreen();
                    blue += getReflectanceCoeff() * rcolor.getBlue();
                } else {
                    red += getReflectanceCoeff() * bgnd.getRed();
                    green += getReflectanceCoeff() * bgnd.getGreen();
                    blue += getReflectanceCoeff() * bgnd.getBlue();
                }
            }
        }

        //refraction code

        red = Math.min(red, 1f);
        green = Math.min(green, 1f);
        blue = Math.min(blue, 1f);

        red = (red < 0) ? 0 : red;
        green = (green < 0) ? 0 : green;
        blue = (blue < 0) ? 0 : blue;

        return new Color(red, green, blue);
    }

    /**
     * Gets intense red.
     *
     * @return the intense red
     */
// getters and setters
    public float getIntenseRed() {
        return intenseRed;
    }

    /**
     * Sets intense red.
     *
     * @param intenseRed the intense red
     */
    public void setIntenseRed(float intenseRed) {
        this.intenseRed = intenseRed;
    }

    /**
     * Gets intense green.
     *
     * @return the intense green
     */
    public float getIntenseGreen() {
        return intenseGreen;
    }

    /**
     * Sets intense green.
     *
     * @param intenseGreen the intense green
     */
    public void setIntenseGreen(float intenseGreen) {
        this.intenseGreen = intenseGreen;
    }

    /**
     * Gets intense blue.
     *
     * @return the intense blue
     */
    public float getIntenseBlue() {
        return intenseBlue;
    }

    /**
     * Sets intense blue.
     *
     * @param intenseBlue the intense blue
     */
    public void setIntenseBlue(float intenseBlue) {
        this.intenseBlue = intenseBlue;
    }

    /**
     * Gets ambient reflection coeff.
     *
     * @return the ambient reflection coeff
     */
    public float getAmbientReflectionCoeff() {
        return ambientReflectionCoeff;
    }

    /**
     * Sets ambient reflection coeff.
     *
     * @param ambientReflectionCoeff the ambient reflection coeff
     */
    public void setAmbientReflectionCoeff(float ambientReflectionCoeff) {
        this.ambientReflectionCoeff = ambientReflectionCoeff;
    }

    /**
     * Gets diffuse reflection coeff.
     *
     * @return the diffuse reflection coeff
     */
    public float getDiffuseReflectionCoeff() {
        return aFloat;
    }

    /**
     * Sets diffuse reflection coeff.
     *
     * @param diffuseReflectionCoeff the diffuse reflection coeff
     */
    public void setDiffuseReflectionCoeff(float diffuseReflectionCoeff) {
        this.aFloat = diffuseReflectionCoeff;
    }

    /**
     * Gets specular reflection coeff.
     *
     * @return the specular reflection coeff
     */
    public float getSpecularReflectionCoeff() {
        return specularReflectionCoeff;
    }

    /**
     * Sets specular reflection coeff.
     *
     * @param specularReflectionCoeff the specular reflection coeff
     */
    public void setSpecularReflectionCoeff(float specularReflectionCoeff) {
        this.specularReflectionCoeff = specularReflectionCoeff;
    }

    /**
     * Gets phong coeff.
     *
     * @return the phong coeff
     */
    public float getPhongCoeff() {
        return phongCoeff;
    }

    /**
     * Sets phong coeff.
     *
     * @param phongCoeff the phong coeff
     */
    public void setPhongCoeff(float phongCoeff) {
        this.phongCoeff =  phongCoeff;
    }

    /**
     * Gets transmission coeff.
     *
     * @return the transmission coeff
     */
    public float getTransmissionCoeff() {
        return transmissionCoeff;
    }

    /**
     * Sets transmission coeff.
     *
     * @param transmissionCoeff the transmission coeff
     */
    public void setTransmissionCoeff(float transmissionCoeff) {
        this.transmissionCoeff = transmissionCoeff;
    }

    /**
     * Gets reflectance coeff.
     *
     * @return the reflectance coeff
     */
    public float getReflectanceCoeff() {
        return reflectanceCoeff;
    }

    /**
     * Sets reflectance coeff.
     *
     * @param reflectanceCoeff the reflectance coeff
     */
    public void setReflectanceCoeff(float reflectanceCoeff) {
        this.reflectanceCoeff = reflectanceCoeff;
    }

    /**
     * Gets nt.
     *
     * @return the nt
     */
    public float getNt() {
        return nt;
    }

    /**
     * Sets nt.
     *
     * @param nt the nt
     */
    public void setNt(float nt) {
        this.nt = nt;
    }
}
