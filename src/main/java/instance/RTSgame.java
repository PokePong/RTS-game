package instance;

import engine.core.Context;
import engine.math.vector.Vector3;
import engine.scene.Camera;
import module.camera.component.Frustum;
import module.camera.component.Picking;
import module.camera.controller.OrbitCamera;
import module.camera.component.ScreenSelectionBox;
import module.infiniteGrid.InfiniteGrid;


public class RTSgame extends Context {

    int numPeople = 1000;

    @Override
    public void __init__() {
        Camera camera = new Camera(new Vector3(0f, 20f, 20f), 0, 0);
        camera.setController(new OrbitCamera(camera, 30, -45));
        //camera.setController(new FPSCamera(camera));
        camera.addComponent("SelectionBox", new ScreenSelectionBox());
        camera.addComponent("Frustum", new Frustum());
        camera.addComponent("Picking", new Picking(camera));
        getScenegraph().setCamera(camera);

        getScenegraph().getRoot().addChild(new PeopleCluster(numPeople));
        getScenegraph().getRoot().addChild(new InfiniteGrid());
    }

    @Override
    public void __update__(double delta) {

    }
}
