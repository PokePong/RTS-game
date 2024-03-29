package module.camera.component;

import engine.core.kernel.Input;
import engine.math.geometry.Plane;
import engine.math.geometry.Ray;
import engine.math.vector.Vector3;
import engine.scene.Camera;
import engine.scene.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Picking extends Component {

    private static final Logger logger = LogManager.getLogger(Picking.class);

    private Camera camera;
    private Vector3 pointOnPlane;

    public Picking(Camera camera) {
        this.camera = camera;
        this.pointOnPlane = new Vector3(0);
    }

    @Override
    public void update(double delta) {
        if (Input.isButtonDown(0)) {
            Ray ray = camera.getRay(Input.getCursorPosition());
            Plane plane = new Plane(new Vector3(0, 1, 0), 0);
            float dist = plane.rayCast(ray);
            pointOnPlane = ray.getPoint(dist);
            logger.debug("Picking:" + pointOnPlane);
        }
    }

    public Vector3 getPointOnPlane() {
        return pointOnPlane;
    }

}
