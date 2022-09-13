package com.l0raxeo.rayCast.gameObjects;

import com.l0raxeo.rayCast.components.Component;
import com.l0raxeo.rayCast.dataStructure.Transform;

import java.util.ArrayList;
import java.util.List;

public class GameObject
{

    private final String name;
    private final List<Component> components;
    public Transform transform;
    private boolean isDead = false;

    public GameObject(String name, Transform transform) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return null;
    }

    public <T extends Component> boolean hasComponent(Class<T> componentClass)
    {
        for (Component c : components)
            if (componentClass.isAssignableFrom(c.getClass()))
                return true;

        return false;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass)
    {
        for (int i=0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c)
    {
        c.generateId();
        this.components.add(c);
        c.gameObject = this;
    }

    public void update(double dt) {
        for (Component component : components)
        {
            component.update(dt);
        }
    }

    public void start() {
        for (Component component : components) {
            component.start();
        }
    }

    public String getName()
    {
        return this.name;
    }

    public List<Component> getAllComponents()
    {
        return this.components;
    }

    public boolean isDead()
    {
        return isDead;
    }

    public void die()
    {
        this.isDead = true;

        for (Component c : getAllComponents())
            c.onDestroy();
    }

}
