package instance;

import engine.core.Context;
import engine.math.vector.Vector3;
import engine.scene.Camera;
import engine.util.Color4;
import module.camera.component.Frustum;
import module.camera.component.Picking;
import module.camera.controller.OrbitCamera;
import module.camera.component.ScreenSelectionBox;


public class RTSgame extends Context {

    int numPeople = 50;

    @Override
    public void __init__() {
        Camera camera = new Camera(new Vector3(0f, 20f, 20f), 0, 0);

        OrbitCamera controller = new OrbitCamera(camera);
        camera.setController(controller);

        camera.addComponent("SelectionBox", new ScreenSelectionBox());
        camera.addComponent("Frustum", new Frustum());
        camera.addComponent("Picking", new Picking(camera));
        getScenegraph().setCamera(camera);

        getScenegraph().getRoot().addChild(new PeopleCluster(numPeople));
        getScenegraph().getRoot().addChild(new Rat(Color4.ORANGE));
        //getScenegraph().getRoot().addChild(new Terrain(200, Color4.GREEN.brighter()));
    }

    @Override
    public void __update__(double delta) {

    }
}