package engine.scene;

import engine.gl.VBO;
import engine.model.Mesh;
import engine.renderer.Renderer;
import engine.util.Constants;
import engine.util.Debug;
import module.Color4f;
import module.shader.GenericShader;
import engine.renderer.Default;

import java.util.HashMap;

public abstract class GameObject extends Node {

    private VBO vbo;
    private HashMap<String, Component> components;
    private Color4f color;

    public GameObject() {
        this.components = new HashMap<String, Component>();
    }

    public void addComponent(String name, Component component) {
        if (components.containsKey(name))
            throw new IllegalStateException("[Component] Already exists! " + name);
        component.setParent(this);
        components.put(name, component);
    }

    @Override
    public void init() {
        super.init();
        __init__();
        for (String key : components.keySet()) {
            components.get(key).init();
        }
        if (!components.containsKey(Constants.RENDERER_COMPONENT)) {
            throw new IllegalStateException("[Component] Renderer Component missing!");
        }
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        __update__(delta);
        for (String key : components.keySet()) {
            components.get(key).update(delta);
        }
    }

    @Override
    public void render() {
        super.render();
        for (String key : components.keySet()) {
            components.get(key).render();
        }
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        for (String key : components.keySet()) {
            components.get(key).cleanUp();
        }
    }

    public abstract void __init__();

    public abstract void __update__(double delta);

    public Component getComponent(String key) {
        if (components.containsKey(key))
            return components.get(key);
        return null;
    }

    public HashMap<String, Component> getComponents() {
        return components;
    }

    public VBO getVbo() {
        return vbo;
    }

    public void setVbo(VBO vbo) {
        this.vbo = vbo;
    }

    public Color4f getColor() {
        return color;
    }

    public void setColor(Color4f color) {
        this.color = color;
    }
}
