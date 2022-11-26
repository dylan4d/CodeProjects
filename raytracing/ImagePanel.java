import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The type Image panel.
 */
public class ImagePanel extends JPanel {
    private BufferedImage image;

    /**
     * Instantiates a new Image panel.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public ImagePanel(String filename) throws IOException {
        setImage(ImageIO.read(new File(filename)));
    }

    /**
     * Update image.
     *
     * @param img the img
     */
    public void updateImage(Image img) {
        setImage((BufferedImage) img);
    }

    /**
     *
     * @param graphic the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        graphic.drawImage(getImage(), 0, 0, this);
        repaint();
    }

    /**
     * Gets image.
     *
     * @return the image
     */
// getters and setters
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
