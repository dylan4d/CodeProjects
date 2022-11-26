import javax.swing.*;
import java.io.IOException;

/**
 * creates API
 */
public interface API {
    void createScene();
    void addElement();
    void addAmbientLight();
    void addDirectionalLight();
    void addSphere();
    Driver renderScene() throws IOException;

    // getters and setters
    JFrame getFrame();
    void setFrame(JFrame frame);
    ImagePanel getImage();
    void setImage(ImagePanel image);
    Driver getSceneToRender();
    void setSceneToRender(Driver sceneToRender);
}
