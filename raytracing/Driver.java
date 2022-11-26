import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Driver.
 * Driver for image creation
 */
class Driver  {
    private final static int CHUNK_SIZE = 100;
    private List<Object> objectList;
    private List<Object> lightList;
    private Surface currentSurface;
	private BufferedImage canvas;

    private Vector3D eye;
	private Vector3D lookAt;
	private Vector3D upValue;
    private Vector3D Du;
	private Vector3D Dv;
	private Vector3D Vector_p;
    private float fov;

    private Color background;

    private int width;
	private int height;

	/**
	 * Instantiates a new Driver.
	 *
	 * @param width    the width
	 * @param height   the height
	 * @param dataFile the data file
	 */
	public Driver(int width, int height, String dataFile) {
        this.setWidth(width);
        this.setHeight(height);

		setCanvas(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));

        setFov(30);               // default horizontal field of view

        // Initialize various lists
        setObjectList(new ArrayList<>(getChunkSize()));
        setLightList(new ArrayList<>(getChunkSize()));
        setCurrentSurface(new Surface(0.8f, 0.2f, 0.9f, 0.2f, 0.4f, 0.4f, 10.0f, 0f, 0f, 1f));

        // Parse the scene file
        String filename = dataFile != null ? dataFile : "defaultScene.txt";

        InputStream is;
        try {
            is = new FileInputStream(filename);
            ReadInput(is);
            is.close();
        } catch (IOException e) {
			System.err.println("Error reading "+ new File(filename).getAbsolutePath());
			e.printStackTrace();
            System.exit(-1);
        }

        // Initialize more defaults if they weren't specified
        if (getEye() == null) setEye(new Vector3D(0, 0, 10));
        if (getLookAt() == null) setLookAt(new Vector3D(0, 0, 0));
        if (getUpValue() == null) setUpValue(new Vector3D(0, 1, 0));
        if (getBackground() == null) setBackground(new Color(0,0,0));

        // Compute viewing matrix that maps a
        // screen coordinate to a ray direction
        Vector3D look = new Vector3D(getLookAt().getPointX() - getEye().getPointX(), getLookAt().getPointY() - getEye().getPointY(), getLookAt().getPointZ() - getEye().getPointZ());
        setDu(Vector3D.normalize(look.cross(getUpValue())));
        setDv(Vector3D.normalize(look.cross(getDu())));
        float fl = (float)(width / (2*Math.tan((0.5* getFov())*Math.PI/180)));
        setVp(Vector3D.normalize(look));
        getVp().setPointX(getVp().getPointX() *fl - 0.5f*(width* getDu().getPointX() + height* getDv().getPointX()));
        getVp().setPointY(getVp().getPointY() *fl - 0.5f*(width* getDu().getPointY() + height* getDv().getPointY()));
        getVp().setPointZ(getVp().getPointZ() *fl - 0.5f*(width* getDu().getPointZ() + height* getDv().getPointZ()));
    }

	/**
	 * Gets chunk size.
	 *
	 * @return the chunk size
	 */
	public static int getChunkSize() {
		return CHUNK_SIZE;
	}


	/**
	 * Gets number.
	 *
	 * @param st the st
	 * @return the number
	 * @throws IOException the io exception
	 */
	double getNumber(StreamTokenizer st) throws IOException {
        if (st.nextToken() != StreamTokenizer.TT_NUMBER) {
            System.err.println("ERROR: number expected in line "+st.lineno());
            throw new IOException(st.toString());
        }
        return st.nval;
    }

	/**
	 * Read input.
	 *
	 * @param input_stream the input_stream
	 * @throws IOException the io exception
	 */
	void ReadInput(InputStream input_stream) throws IOException {
		Reader streamReader = new BufferedReader(new InputStreamReader(input_stream));
		StreamTokenizer stream = new StreamTokenizer(streamReader);
    	stream.commentChar('#');
		while (true) {
			if (stream.nextToken() == StreamTokenizer.TT_WORD) {
				switch (stream.sval) {
					case "sphere" -> {
						Vector3D v = new Vector3D((float) getNumber(stream), (float) getNumber(stream), (float) getNumber(stream));
						float r = (float) getNumber(stream);
						getObjectList().add(new Sphere(getCurrentSurface(), v, r));
					}
					case "eye" ->
							setEye(new Vector3D((float) getNumber(stream), (float) getNumber(stream), (float) getNumber(stream)));
					case "lookat" ->
							setLookAt(new Vector3D((float) getNumber(stream), (float) getNumber(stream), (float) getNumber(stream)));
					case "up" ->
							setUpValue(new Vector3D((float) getNumber(stream), (float) getNumber(stream), (float) getNumber(stream)));
					case "fov" -> setFov((float) getNumber(stream));
					case "background" ->
							setBackground(new Color((int) getNumber(stream), (int) getNumber(stream), (int) getNumber(stream)));
					case "light" -> {
						float red = (float) getNumber(stream);
						float green = (float) getNumber(stream);
						float blue = (float) getNumber(stream);
						if (stream.nextToken() != StreamTokenizer.TT_WORD) {
							throw new IOException(stream.toString());
						}
						switch (stream.sval) {
							case "ambient" -> getLightList().add(new Light(Light.getAMBIENT(), null, red, green, blue));
							case "directional" -> {
								Vector3D v = new Vector3D((float) getNumber(stream), (float) getNumber(stream), (float) getNumber(stream));
								getLightList().add(new Light(Light.getDIRECTIONAL(), v, red, green, blue));
							}
							case "point" -> {
								Vector3D v = new Vector3D((float) getNumber(stream), (float) getNumber(stream), (float) getNumber(stream));
								getLightList().add(new Light(Light.getPOINT(), v, red, green, blue));
							}
							default -> {
								System.err.println("ERROR: in line " + stream.lineno() + " at " + stream.sval);
								throw new IOException(stream.toString());
							}
						}
					}
					case "surface" -> {
						float red = (float) getNumber(stream);
						float green = (float) getNumber(stream);
						float blue = (float) getNumber(stream);
						float surfaceValueAbmientReflectionCoeff = (float) getNumber(stream);
						float surfaceValueDiffuseReflectionCoeff = (float) getNumber(stream);
						float surfaceValueSpecularReflectionCoeff = (float) getNumber(stream);
						float surfaceValuePhongExponent = (float) getNumber(stream);
						float surfaceValueReflectanceCoeff = (float) getNumber(stream);
						float surfaceValueTransmissionCoeff = (float) getNumber(stream);
						float index = (float) getNumber(stream);
						setCurrentSurface(new Surface(red, green, blue, surfaceValueAbmientReflectionCoeff, surfaceValueDiffuseReflectionCoeff, surfaceValueSpecularReflectionCoeff, surfaceValuePhongExponent, surfaceValueReflectanceCoeff, surfaceValueTransmissionCoeff, index));
					}
				}
			} else {
				break;
			}
		}
        input_stream.close();
	    if (stream.ttype != StreamTokenizer.TT_EOF)
	        throw new IOException(stream.toString());
	}

	/**
	 * Gets rendered image.
	 *
	 * @return the rendered image
	 */
	Image getRenderedImage() {
		return getCanvas();
	}

	/**
	 * Render pixel.
	 *
	 * @param i the
	 * @param j the j
	 */
	public void renderPixel(int i, int j) {
		Vector3D dir = new Vector3D(
				i* getDu().getPointX() + j* getDv().getPointX() + getVp().getPointX(),
				i* getDu().getPointY() + j* getDv().getPointY() + getVp().getPointY(),
				i* getDu().getPointZ() + j* getDv().getPointZ() + getVp().getPointZ());
		Ray ray = new Ray(getEye(), dir);
		Color pixelColour;

		if (ray.trace(getObjectList())) {
			java.awt.Color bg = getBackground();
			pixelColour = ray.Shade(getLightList(), getObjectList(), bg);
		} else {
			pixelColour = getBackground();
		}
		getCanvas().setRGB(i, j, pixelColour.getRGB());
	}


	/**
	 * Gets object list.
	 *
	 * @return the object list
	 */
// getters and setters
	public List<Object> getObjectList() {
		return objectList;
	}

	/**
	 * Sets object list.
	 *
	 * @param objectList the object list
	 */
	public void setObjectList(List<Object> objectList) {
		this.objectList = objectList;
	}

	/**
	 * Gets light list.
	 *
	 * @return the light list
	 */
	public List<Object> getLightList() {
		return lightList;
	}

	/**
	 * Sets light list.
	 *
	 * @param lightList the light list
	 */
	public void setLightList(List<Object> lightList) {
		this.lightList = lightList;
	}

	/**
	 * Gets current surface.
	 *
	 * @return the current surface
	 */
	public Surface getCurrentSurface() {
		return currentSurface;
	}

	/**
	 * Sets current surface.
	 *
	 * @param currentSurface the current surface
	 */
	public void setCurrentSurface(Surface currentSurface) {
		this.currentSurface = currentSurface;
	}

	/**
	 * Gets canvas.
	 *
	 * @return the canvas
	 */
	public BufferedImage getCanvas() {
		return canvas;
	}

	/**
	 * Sets canvas.
	 *
	 * @param canvas the canvas
	 */
	public void setCanvas(BufferedImage canvas) {
		this.canvas = canvas;
	}

	/**
	 * Gets eye.
	 *
	 * @return the eye
	 */
	public Vector3D getEye() {
		return eye;
	}

	/**
	 * Sets eye.
	 *
	 * @param eye the eye
	 */
	public void setEye(Vector3D eye) {
		this.eye = eye;
	}

	/**
	 * Gets look at.
	 *
	 * @return the look at
	 */
	public Vector3D getLookAt() {
		return lookAt;
	}

	/**
	 * Sets look at.
	 *
	 * @param lookAt the look at
	 */
	public void setLookAt(Vector3D lookAt) {
		this.lookAt = lookAt;
	}

	/**
	 * Gets up value.
	 *
	 * @return the up value
	 */
	public Vector3D getUpValue() {
		return upValue;
	}

	/**
	 * Sets up value.
	 *
	 * @param upValue the up value
	 */
	public void setUpValue(Vector3D upValue) {
		this.upValue = upValue;
	}

	/**
	 * Gets du.
	 *
	 * @return the du
	 */
	public Vector3D getDu() {
		return Du;
	}

	/**
	 * Sets du.
	 *
	 * @param du the du
	 */
	public void setDu(Vector3D du) {
		Du = du;
	}

	/**
	 * Gets dv.
	 *
	 * @return the dv
	 */
	public Vector3D getDv() {
		return Dv;
	}

	/**
	 * Sets dv.
	 *
	 * @param dv the dv
	 */
	public void setDv(Vector3D dv) {
		Dv = dv;
	}

	/**
	 * Gets vp.
	 *
	 * @return the vp
	 */
	public Vector3D getVp() {
		return Vector_p;
	}

	/**
	 * Sets vp.
	 *
	 * @param vp the vp
	 */
	public void setVp(Vector3D vp) {
		Vector_p = vp;
	}

	/**
	 * Gets fov.
	 *
	 * @return the fov
	 */
	public float getFov() {
		return fov;
	}

	/**
	 * Sets fov.
	 *
	 * @param fov the fov
	 */
	public void setFov(float fov) {
		this.fov = fov;
	}

	/**
	 * Gets background.
	 *
	 * @return the background
	 */
	public Color getBackground() {
		return background;
	}

	/**
	 * Sets background.
	 *
	 * @param background the background
	 */
	public void setBackground(Color background) {
		this.background = background;
	}

	/**
	 * Gets width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets width.
	 *
	 * @param width the width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets height.
	 *
	 * @param height the height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}