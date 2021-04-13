package module.camera;

import engine.core.Input;
import engine.scene.Component;
import engine.util.Debug;
import engine.util.Utils;
import module.Color4f;
import org.joml.Vector2f;

public class ScreenSelectionBox extends Component {

    private final float MIN_MAGNITUDE = 50;

    private Vector2f p1;
    private Vector2f p2;

    private boolean dragSelect;

    public ScreenSelectionBox() {
        this.p1 = new Vector2f();
        this.p2 = new Vector2f();
        this.dragSelect = false;
    }

    public void update(double delta) {
        if(Input.isButtonDown(0)) {
            p1 = Input.getCursorPosition();
        }

        if(Input.isButtonHold(0)) {
            Vector2f diff = new Vector2f();
            p1.sub(Input.getCursorPosition(), diff);
            if(diff.lengthSquared() > MIN_MAGNITUDE) {
                dragSelect = true;
                p2 = Input.getCursorPosition();
            }
        }

        if(Input.isButtonUp(0)) {
            if(dragSelect) {
                dragSelect = false;

            }
        }
    }

    public void render() {
        if(dragSelect) {
            Debug.drawRectScreen(p1, p2, new Color4f(93, 109, 126, 100));
        }
    }

}
