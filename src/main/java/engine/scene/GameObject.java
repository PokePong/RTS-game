package engine.scene;

import engine.gl.VBO;
import engine.model.Mesh;
import engine.util.Constants;
import engine.util.Debug;
import module.Color4;

import java.util.HashMap;
import java.util.UUID;

public abstract class GameObject extends Node {

    private UUID uuid;
    private Mesh mesh;
    private VBO vbo;
    private HashMap<String, Component> components;
    private Color4 color;
    private boolean instanced;

    public GameObject() {
        this.components = new HashMap<String, Component>();
        this.uuid = UUID.randomUUID();
        this.instanced = false;
    }

    @Override
    public void init() {
        super.init();
        __init__();
        if (!instanced && !components.containsKey(Constants.RENDERER_COMPONENT)) {
            Debug.warn("Renderer component missing!");
        }
        for (String key : components.keySet()) {
            components.get(key).init();
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

    public void addComponent(String name, Component component) {
        if (components.containsKey(name)) {
            Debug.err("Component already exists! " + name);
            return;
        }
        component.setParent(this);
        components.put(name, component);
    }

    public void removeComponent(String name) {
        if (!components.containsKey(name)) {
            Debug.err("Component doesn't exist!");
            return;
        }
        components.remove(name);
    }

    public Component getComponent(String key) {
        if (!components.containsKey(key)) {
            Debug.err("Component doesn't exist!");
            return null;
        }
        return components.get(key);
    }

    public HashMap<String, Component> getComponents() {
        return components;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public VBO getVbo() {
        return vbo;
    }

    public void setVbo(VBO vbo) {
        this.vbo = vbo;
    }

    public Color4 getColor() {
        return color;
    }

    public void setColor(Color4 color) {
        this.color = color;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isInstanced() {
        return instanced;
    }

    public void setInstanced(boolean instanced) {
        this.instanced = instanced;
    }
}
