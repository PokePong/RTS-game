package engine.scene;

import engine.util.Debug;

public class Scenegraph extends Node {

    private Camera camera;
    private Node root;

    public Scenegraph() {
        this.root = new Node();
    }

    public void init() {
        if (camera == null) {
            Debug.fatal("[Scenegraph] Camera is null!");
            throw new IllegalStateException();
        }
        camera.init();
        root.init();
    }

    @Override
    public void render() {
        root.render();
        camera.render();
    }

    public void update(double delta) {
        camera.update(delta);
        root.update(delta);
    }

    public void cleanUp() {
        camera.cleanUp();
        root.cleanUp();
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
