package engine.scene.temp;

import engine.math.Transform;
import module.bounding.BoundingVolume;

public abstract class Spatial {

    public enum CullHint {
        Inherit,
        Dynamic,
        Always,
        Never
    }

    protected String name;
    protected BoundingVolume worldBound;
    protected CullHint cullHint;
    protected Transform localTransform;
    protected Transform worldTransform;

    protected Spatial(String name) {
        this.name = name;
        this.cullHint = CullHint.Inherit;
        this.localTransform = new Transform();
        this.worldTransform = new Transform();
    }


}
