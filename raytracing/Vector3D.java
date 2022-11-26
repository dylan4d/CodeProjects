/**
 * The type Vector 3D.
 */
// A simple vector class
class Vector3D {
    private float pointX;
    private float pointY;
    private float pointZ;

    /**
     * Instantiates a new Vector 3 d.
     */
// constructors
    public Vector3D( ) {
    }

    /**
     * Instantiates a new Vector 3 d.
     *
     * @param pointX the point x
     * @param pointY the point y
     * @param pointZ the point z
     */
    public Vector3D(float pointX, float pointY, float pointZ) {
        this.setPointX(pointX); this.setPointY(pointY); this.setPointZ(pointZ);
    }

    /**
     * Instantiates a new Vector 3 d.
     *
     * @param vec the vec
     */
    public Vector3D(Vector3D vec) {
        setPointX(vec.getPointX());
        setPointY(vec.getPointY());
        setPointZ(vec.getPointZ());
    }

    /**
     * Dot float.
     *
     * @param vecToDot the vec to dot
     * @return the float
     */
// methods
    public final float dot(Vector3D vecToDot) {
        return (getPointX() * vecToDot.getPointX() + getPointY() * vecToDot.getPointY() + getPointZ() * vecToDot.getPointZ());
    }

    /**
     * Dot float.
     *
     * @param xVec the x vec
     * @param yVec the y vec
     * @param zVec the z vec
     * @return the float
     */
    public final float dot(float xVec, float yVec, float zVec) {
        return (getPointX() *xVec + getPointY() *yVec + getPointZ() *zVec);
    }

    /**
     * Dot float.
     *
     * @param aVec the a vec
     * @param bVec the b vec
     * @return the float
     */
    public static float dot(Vector3D aVec, Vector3D bVec) {
        return (aVec.getPointX() * bVec.getPointX() + aVec.getPointY() * bVec.getPointY() + aVec.getPointZ() * bVec.getPointZ());
    }

    /**
     * Cross vector 3 d.
     *
     * @param bVec the b vec
     * @return the vector 3 d
     */
    public final Vector3D cross(Vector3D bVec) {
        return new Vector3D(getPointY() * bVec.getPointZ() - getPointZ() * bVec.getPointY(), getPointZ() * bVec.getPointX() - getPointX() * bVec.getPointZ(), getPointX() * bVec.getPointY() - getPointY() * bVec.getPointX());
    }

    /**
     * Cross vector 3 d.
     *
     * @param xCross the x cross
     * @param yCross the y cross
     * @param zCross the z cross
     * @return the vector 3 d
     */
    public final Vector3D cross(float xCross, float yCross, float zCross) {
        return new Vector3D(getPointY() *zCross - getPointZ() *yCross, getPointZ() *xCross - getPointX() *zCross, getPointX() *yCross - getPointY() *xCross);
    }

    /**
     * Cross vector 3 d.
     *
     * @param aVec the a vec
     * @param bVec the b vec
     * @return the vector 3 d
     */
    public static Vector3D cross(Vector3D aVec, Vector3D bVec) {
        return new Vector3D(aVec.getPointY() * bVec.getPointZ() - aVec.getPointZ() * bVec.getPointY(), aVec.getPointZ() * bVec.getPointX() - aVec.getPointX() * bVec.getPointZ(), aVec.getPointX() * bVec.getPointY() - aVec.getPointY() * bVec.getPointX());
    }

    /**
     * Length float.
     *
     * @return the float
     */
    public final float length( ) {
        return (float) Math.sqrt(getPointX() * getPointX() + getPointY() * getPointY() + getPointZ() * getPointZ());
    }

    /**
     * Length float.
     *
     * @param A the a
     * @return the float
     */
    public static float length(Vector3D A) {
        return (float) Math.sqrt(A.getPointX() * A.getPointX() + A.getPointY() * A.getPointY() + A.getPointZ() * A.getPointZ());
    }

    /**
     * Normalize.
     */
    public final void normalize( ) {
        float total = getPointX() * getPointX() + getPointY() * getPointY() + getPointZ() * getPointZ();
        if (total != 0 && total != 1) total = (float) (1 / Math.sqrt(total));
        setPointX(getPointX() * total);
        setPointY(getPointY() * total);
        setPointZ(getPointZ() * total);
    }

    /**
     * Normalize vector 3 d.
     *
     * @param A the a
     * @return the vector 3 d
     */
    public static Vector3D normalize(Vector3D A) {
        float total = A.getPointX() * A.getPointX() + A.getPointY() * A.getPointY() + A.getPointZ() * A.getPointZ();
        if (total != 0 && total != 1) total = (float)(1 / Math.sqrt(total));
        return new Vector3D(A.getPointX() *total, A.getPointY() *total, A.getPointZ() *total);
    }
    public String toString() {
        return new String("["+ getPointX() +", "+ getPointY() +", "+ getPointZ() +"]");
    }

    /**
     * Gets point x.
     *
     * @return the point x
     */
// getters and setters
    public float getPointX() {
        return pointX;
    }

    /**
     * Sets point x.
     *
     * @param pointX the point x
     */
    public void setPointX(float pointX) {
        this.pointX = pointX;
    }

    /**
     * Gets point y.
     *
     * @return the point y
     */
    public float getPointY() {
        return pointY;
    }

    /**
     * Sets point y.
     *
     * @param pointY the point y
     */
    public void setPointY(float pointY) {
        this.pointY = pointY;
    }

    /**
     * Gets point z.
     *
     * @return the point z
     */
    public float getPointZ() {
        return pointZ;
    }

    /**
     * Sets point z.
     *
     * @param pointZ the point z
     */
    public void setPointZ(float pointZ) {
        this.pointZ = pointZ;
    }
}


