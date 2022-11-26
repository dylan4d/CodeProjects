// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

/**
 * The type Light.
 * light to change color
 */
class Light {
    private static final int AMBIENT = 0;
    private static final int DIRECTIONAL = 1;
    private static final int POINT = 2;

    private int lightType;
    private Vector3D lightVector;           // the position of a point light or
    // the direction to a directional light
    private float intenseRed;
    private float intenseGreen;
    private float intenseBlue;        // intensity of the light source

    /**
     * Instantiates a new Light.
     *
     * @param type   the type
     * @param vector the vector
     * @param red    the red
     * @param green  the green
     * @param blue   the blue
     */
    public Light(int type, Vector3D vector, float red, float green, float blue) {
        setLightType(type);
        setIntenseRed(red);
        setIntenseGreen(green);
        setIntenseBlue(blue);
        if (type != getAMBIENT()) {
            setLightVector(vector);
            if (type == getDIRECTIONAL()) {
                getLightVector().normalize();
            }
        }
    }


    /**
     * Gets ambient.
     *
     * @return the ambient
     */
// getters and setters
    public static int getAMBIENT() {
        return AMBIENT;
    }

    /**
     * Gets directional.
     *
     * @return the directional
     */
    public static int getDIRECTIONAL() {
        return DIRECTIONAL;
    }

    /**
     * Gets point.
     *
     * @return the point
     */
    public static int getPOINT() {
        return POINT;
    }

    /**
     * Gets light type.
     *
     * @return the light type
     */
    public int getLightType() {
        return lightType;
    }

    /**
     * Sets light type.
     *
     * @param lightType the light type
     */
    public void setLightType(int lightType) {
        this.lightType = lightType;
    }

    /**
     * Gets light vector.
     *
     * @return the light vector
     */
    public Vector3D getLightVector() {
        return lightVector;
    }

    /**
     * Sets light vector.
     *
     * @param lightVector the light vector
     */
    public void setLightVector(Vector3D lightVector) {
        this.lightVector = lightVector;
    }

    /**
     * Gets intense red.
     *
     * @return the intense red
     */
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
}
