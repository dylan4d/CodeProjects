import java.awt.*;
import java.util.List;

/**
 * The interface Renderable.
 * An object must implement a Renderable interface in order to
 An object must implement a Renderable interface in order to
 // be ray traced. Using this interface it is straight forward
 // to add new objects
 */
interface Renderable {
    /**
     * Intersect.
     *
     * @param r the r
     */
    void intersect(Ray r);

    /**
     * Shade color.
     *
     * @param r       the r
     * @param lights  the lights
     * @param objects the objects
     * @param bgnd    the bgnd
     * @return the color
     */
    Color Shade(Ray r, java.util.List<Object> lights, List<Object> objects, Color bgnd);

    String toString();
}
