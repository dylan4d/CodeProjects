import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * The type Raytracer.
 * Raytracer is used to implement the API
 */
// raytracer class that builds scene, adds elements, and saves the img
public class raytracer implements API {
    /**
     *
     */

    private JFrame frame;
    private ImagePanel image;
    private Driver sceneToRender;

    /**
     *
     */
    public void createScene(){
        Dimension displaySize = new Dimension(600, 600); //moved to createFrame
        setSceneToRender(new Driver(600, 600,"resources/SceneToRender.txt"));

        setFrame(new JFrame("Ray Tracing Demonstration"));
        getFrame().setSize(600, 600);
        getFrame().setPreferredSize(displaySize);
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void addElement() {
        Scanner LightOrSphere = new Scanner(System.in);
        System.out.println("1: add ambient Light\n2: add directional Light\n3: add sphere\n4: skip");
        System.out.println("Enter 1,2, or 3");
        int choice = LightOrSphere.nextInt();

        if (choice == 1) {
            addAmbientLight();
        } else if (choice == 2) {
            addDirectionalLight();
        } else  if (choice == 3){
            addSphere();
        } else {
            System.out.println("You have chosen to skip");
        }

    }

    public void addDirectionalLight(){
        //light 0.6 0.6 0.6 directional -1 -1 -1
        System.out.println("You must add six decimal values");
        Path path_var = Path.of("resources/SceneToRender.txt");
        Scanner choice2Scanner = new Scanner(System.in);
        double[] light_values = new double[6];
        for (int i = 0; i < 6; i++) {
            light_values[i] = choice2Scanner.nextDouble();
        }
        try {
            Files.writeString(path_var, String.format("\nlight %f %f %f directional %f %f %f", light_values[0], light_values[1], light_values[2], light_values[3], light_values[4], light_values[5]), StandardOpenOption.APPEND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addAmbientLight(){
        //light 1.0 1.0 0.981 ambient
        System.out.println("You must add three decimal values");
        Path path_var = Path.of("resources/SceneToRender.txt");
        Scanner choice1Scanner = new Scanner(System.in);
        double[] light_values = new double[3];
        for (int i = 0; i < 3; i++) {
            light_values[i] = choice1Scanner.nextDouble();
        }
        try {
            Files.writeString(path_var, String.format("\nlight %f %f %f ambient", light_values[0], light_values[1], light_values[2]), StandardOpenOption.APPEND);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addSphere(){
        Path pathVar = Path.of("resources/SceneToRender.txt");
        System.out.println("You are now adding a sphere");
        //surface 0.8 0.9 0.5 0.6 0.9 0.3 10.0 0 0 1
        //sphere -1.1 1.05 -0.1 0.6
        System.out.println("You must add ten decimal values for surface");
        Scanner choice3surfaceScanner = new Scanner(System.in);
        double[] surfaceValues = new double[10];
        for (int i = 0; i < 10; i++) {
            surfaceValues[i] = choice3surfaceScanner.nextDouble();
        }
        System.out.println("you must add 4 decimal numbers for sphere");
        Scanner choice3sphereScanner = new Scanner(System.in);
        double[] sphereValues = new double[4];
        for (int i = 0; i < 4; i++){
            sphereValues[i] = choice3sphereScanner.nextDouble();
        }
        try {
            Files.writeString(pathVar, String.format("\nsurface %f %f %f %f %f %f %f %f %f %f", surfaceValues[0], surfaceValues[1], surfaceValues[2], surfaceValues[3], surfaceValues[4], surfaceValues[5], surfaceValues[6], surfaceValues[7], surfaceValues[8], surfaceValues[9]), StandardOpenOption.APPEND);
            Files.writeString(pathVar, String.format("\nsphere %f %f %f %f", sphereValues[0], sphereValues[1], sphereValues[2], sphereValues[3]), StandardOpenOption.APPEND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Driver renderScene() throws IOException {
        ImagePanel image = new ImagePanel("resources/Placeholder-01.png");
        getFrame().add(image);
        getFrame().pack();
        getFrame().setVisible(true);

        long time = System.currentTimeMillis();
        for (int j = 0; j < getSceneToRender().getHeight(); j += 1) {
            for (int i = 0; i < getSceneToRender().getWidth(); i += 1) {
                getSceneToRender().renderPixel(i, j);
            }
        }
        image.updateImage(getSceneToRender().getRenderedImage());

        time = System.currentTimeMillis() - time;
        System.err.println("Rendered in "+(time/60000)+" minutes : "+((time%60000)*0.001) + " seconds");

        return sceneToRender;
    }

    public void setSceneToRender(Driver sceneToRender) {
        this.sceneToRender = sceneToRender;

        BufferedImage buffered_image = sceneToRender.getCanvas();
        try {
            File outfile = new File("resources/saved.png");
            ImageIO.write(buffered_image, "png", outfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // getters and setters

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public ImagePanel getImage() {
        return image;
    }

    public void setImage(ImagePanel image) {
        this.image = image;
    }

    public Driver getSceneToRender() {
        return sceneToRender;
    }

}

