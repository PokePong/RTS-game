package module.camera.component;

import engine.core.Input;
import engine.math.Vector2;
import engine.scene.Component;
import engine.util.Debug;
import module.Color4;

public class ScreenSelectionBox extends Component {

    private final float MIN_MAGNITUDE = 50;

    private Vector2 p1;
    private Vector2 p2;

    private boolean dragSelect;

    public ScreenSelectionBox() {
        this.p1 = new Vector2();
        this.p2 = new Vector2();
        this.dragSelect = false;
    }

    public void update(double delta) {
        if (Input.isButtonDown(0)) {
            p1 = Input.getCursorPosition();
        }

        if (Input.isButtonHold(0)) {
            Vector2 diff = p1.sub(Input.getCursorPosition());
            if (diff.lengthSquared() > MIN_MAGNITUDE) {
                dragSelect = true;
                p2 = Input.getCursorPosition();
            }
        }

        if (Input.isButtonUp(0)) {
            if (dragSelect) {
                dragSelect = false;
            }
        }
    }

    public void render() {
        if (dragSelect) {
            Debug.drawRectScreen(p1, p2, new Color4(93, 109, 126, 100));
        }
    }

}
