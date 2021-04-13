package engine.scene;

public class Scenegraph extends Node {

    private Camera camera;
    private Node root;

    public Scenegraph() {
        this.root = new Node();
    }

    public void init() {
        if (camera == null)
            throw new IllegalStateException("[Scenegraph] Camera is null!");
        root.init();
        super.init();
        camera.init();
    }

    public void render() {
        root.render();
        super.render();
        camera.render();
    }

    public void update(double delta) {
        root.update(delta);
        super.update(delta);
        camera.update(delta);
    }

    public void cleanUp() {
        root.cleanUp();
        super.cleanUp();
        camera.cleanUp();
    }

    public Node getRoot() {
        return root;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
