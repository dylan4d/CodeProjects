import java.io.IOException;


public class Main {
    /**
     *
     * @param args: arguments for method for class raytracer
     * @throws IOException: exception causes scene not to render/throws error
     */
    public static void main(String[] args) throws IOException {
        raytracer newRayTracer = new raytracer();
        newRayTracer.addElement();
        newRayTracer.createScene();
        Driver sceneToRender = newRayTracer.renderScene();
        newRayTracer.setSceneToRender(sceneToRender);
    }
}
