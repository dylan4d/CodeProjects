import java.awt.*;
import java.util.List;

/**
 * The type Sphere.
 */
// Render Sphere class
class Sphere implements Renderable {
    private Surface surface;
    private Vector3D center;
    private float radius;
    private float radSqr;

    /**
     * Instantiates a new Sphere.
     *
     * @param surface_cal the surface cal
     * @param c_vector    the c vector
     * @param r_value     the r value
     */
    public Sphere(Surface surface_cal, Vector3D c_vector, float r_value) {
        setSurface(surface_cal);
        setCenter(c_vector);
        setRadius(r_value);
        setRadSqr(r_value * r_value);
    }

    public void intersect(Ray ray) {
        float dx = getCenter().getPointX() - ray.getOrigin().getPointX();
        float dy = getCenter().getPointY() - ray.getOrigin().getPointY();
        float dz = getCenter().getPointZ() - ray.getOrigin().getPointZ();
        float ray_val = ray.getDirection().dot(dx, dy, dz);

        // Do the following quick check to see if there is even a chance
        // that an intersection here might be closer than a previous one
        if (ray_val - getRadius() > ray.getTValue())
            return;

        // Test if the ray actually intersects the sphere
        float intersection = getRadSqr() + ray_val * ray_val - dx * dx - dy * dy - dz * dz;
        if (intersection < 0)
            return;

        // Test if the intersection is in the positive
        // ray direction, and it is the closest so far
        intersection = ray_val - ((float) Math.sqrt(intersection));
        if ((intersection > ray.getTValue()) || (intersection < 0))
            return;

        ray.setTValue(intersection);
        ray.setObject(this);
    }

    /**
     *
     * @param ray       the r
     * @param lights  the lights
     * @param objects the objects
     * @param bgnd    the bgnd
     * @return
     */
    public Color Shade(Ray ray, java.util.List<Object> lights, List<Object> objects, Color bgnd) {
        /**
         *  // An object shader doesn't really do too much other than
         *         // supply a few critical bits of geometric information
         *         // for a surface shader. It must must compute:
         *         //
         *         //   1. the point of intersection (point_of_intersection)
         *         //   2. a unit-length surface normal (normal)
         *         //   3. a unit-length vector towards the ray's origin (unit_vec)
         *         //
         */
        float x_point = ray.getOrigin().getPointX() + ray.getTValue() * ray.getDirection().getPointX();
        float y_point = ray.getOrigin().getPointY() + ray.getTValue() * ray.getDirection().getPointY();
        float z_point = ray.getOrigin().getPointZ() + ray.getTValue() * ray.getDirection().getPointZ();

        Vector3D point_of_intersection = new Vector3D(x_point, y_point, z_point);
        Vector3D unit_vec = new Vector3D(-ray.getDirection().getPointX(), -ray.getDirection().getPointY(), -ray.getDirection().getPointZ());
        Vector3D normal = new Vector3D(x_point - getCenter().getPointX(), y_point - getCenter().getPointY(), z_point - getCenter().getPointZ());
        normal.normalize();

        // The illumination model is applied
        // by the surface's Shade() method
        return getSurface().Shade(point_of_intersection, normal, unit_vec, lights, objects, bgnd);
    }

    public String toString() {
        return ("sphere " + getCenter() + " " + getRadius());
    }


    /**
     * Gets surface.
     *
     * @return the surface
     */
// getters and setters
    public Surface getSurface() {
        return surface;
    }

    /**
     * Sets surface.
     *
     * @param surface the surface
     */
    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    /**
     * Gets center.
     *
     * @return the center
     */
    public Vector3D getCenter() {
        return center;
    }

    /**
     * Sets center.
     *
     * @param center the center
     */
    public void setCenter(Vector3D center) {
        this.center = center;
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Sets radius.
     *
     * @param radius the radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * Gets rad sqr.
     *
     * @return the rad sqr
     */
    public float getRadSqr() {
        return radSqr;
    }

    /**
     * Sets rad sqr.
     *
     * @param radSqr the rad sqr
     */
    public void setRadSqr(float radSqr) {
        this.radSqr = radSqr;
    }
}
