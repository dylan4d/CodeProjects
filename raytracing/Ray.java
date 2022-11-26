import java.awt.*;
import java.util.List;

/**
 * The type Ray.
 * creates 3D image
 */
class Ray {
    private static final float MAX_T = Float.MAX_VALUE;
    private Vector3D origin;
    private Vector3D direction;
    private float tValue;
    private Renderable object;

    /**
     * Instantiates a new Ray.
     *
     * @param eye the eye
     * @param dir the dir
     */
    public Ray(Vector3D eye, Vector3D dir) {
        setOrigin(new Vector3D(eye));
        setDirection(Vector3D.normalize(dir));
    }

    /**
     * Gets max t.
     *
     * @return the max t
     */
    public static float getMaxT() {
        return MAX_T;
    }

    /**
     * Trace boolean.
     *
     * @param objects the objects
     * @return the boolean
     */
    public boolean trace(List<Object> objects) {
        setTValue(getMaxT());
        setObject(null);
        for (Object objList : objects) {
            Renderable object = (Renderable) objList;
            object.intersect(this);
        }
        return (getObject() != null);
    }

    /**
     * Shade color.
     *
     * @param lights  the lights
     * @param objects the objects
     * @param background    the background
     * @return the color
     */
    public final Color Shade(List<Object> lights, List<Object> objects, Color background) {
        return getObject().Shade(this, lights, objects, background);
    }

    public String toString() {
        return ("ray origin = " + getOrigin() + "  direction = " + getDirection() + "  t = " + getTValue());
    }

    // getters and setters

    /**
     * Gets origin.
     *
     * @return the origin
     */
    public Vector3D getOrigin() {
        return origin;
    }

    /**
     * Sets origin.
     *
     * @param origin the origin
     */
    public void setOrigin(Vector3D origin) {
        this.origin = origin;
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public Vector3D getDirection() {
        return direction;
    }

    /**
     * Sets direction.
     *
     * @param direction the direction
     */
    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

    /**
     * Gets t value.
     *
     * @return the t value
     */
    public float getTValue() {
        return tValue;
    }

    /**
     * Sets t value.
     *
     * @param tValue the t value
     */
    public void setTValue(float tValue) {
        this.tValue = tValue;
    }

    /**
     * Gets object.
     *
     * @return the object
     */
    public Renderable getObject() {
        return object;
    }

    /**
     * Sets object.
     *
     * @param object the object
     */
    public void setObject(Renderable object) {
        this.object = object;
    }
}
