package instance;

import engine.core.Input;
import engine.math.Transform;
import engine.scene.GameObject;
import engine.util.Debug;
import engine.core.Context;
import module.Color4f;
import engine.scene.Camera;
import module.camera.FPSCamera;
import module.camera.Frustum;
import module.camera.OrbitCamera;
import module.camera.ScreenSelectionBox;
import module.infiniteGrid.InfiniteGrid;
import module.instanced.InstancedCluster;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class RTSgame extends Context {

    int numPeople = 100;

    @Override
    public void __init__() {
        Debug.log("Init");

        Camera camera = new Camera(new Vector3f(0f, 20f, 20f), 0, 0);
        //camera.setController(new OrbitCamera(camera, new Transform()));
        camera.setController(new FPSCamera(camera));
        camera.addComponent("SelectionBox", new ScreenSelectionBox());
        camera.addComponent("Frustum", new Frustum());

        getScenegraph().setCamera(camera);

        InstancedCluster cluster = new PeopleCluster(numPeople);
        getScenegraph().getRoot().addChild(cluster);

        getScenegraph().addChild(new InfiniteGrid());
    }

    @Override
    public void __update__(double delta) {

    }
}
