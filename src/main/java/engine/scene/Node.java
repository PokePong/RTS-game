package engine.scene;

import engine.math.Transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

    private Node parent;
    private List<Node> children;
    private Transform localTransform;
    private Transform worldTransform;

    public Node() {
        this.children = new ArrayList<Node>();
        this.localTransform = new Transform();
        this.worldTransform = new Transform();
    }

    public void addChild(Node child) {
        child.setParent(this);
        children.add(child);
    }

    public void addChildren(Node... children) {
        this.children.addAll(Arrays.asList(children));
        Arrays.stream(children).forEach(e -> {
            e.setParent(this);
        });
    }

    public void init() {
        for (Node child : children) {
            child.init();
        }
    }

    public void update(double delta) {
        for (Node child : children) {
            child.update(delta);
        }
    }

    public void render() {
        for (Node child : children) {
            child.render();
        }
    }

    public void cleanUp() {
        for (Node child : children) {
            child.cleanUp();
        }
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Transform getWorldTransform() {
        return worldTransform;
    }

    public void setWorldTransform(Transform worldTransform) {
        this.worldTransform = worldTransform;
    }

    public Transform getLocalTransform() {
        return localTransform;
    }

    public void setLocalTransform(Transform localTransform) {
        this.localTransform = localTransform;
    }

}
