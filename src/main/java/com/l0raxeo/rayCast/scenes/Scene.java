package com.l0raxeo.rayCast.scenes;

import com.l0raxeo.rayCast.components.Component;
import com.l0raxeo.rayCast.dataStructure.Transform;
import com.l0raxeo.rayCast.gameObjects.GameObject;
import com.l0raxeo.rayCast.window.Window;
import org.joml.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

public abstract class Scene
{

    protected java.util.List<GameObject> gameObjects = new ArrayList<>();
    protected Color backdrop = new Color(0, 0, 0, 0);

    private boolean isRunning = false;

    public Scene() {

    }

    public void init() {

    }

    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.start();
        }
    }

    public GameObject getGameObject(String name)
    {
        Optional<GameObject> result = this.gameObjects.stream()
                .filter(gameObject -> gameObject.getName().equals(name))
                .findFirst();
        return result.orElse(null);
    }

    /**
     *
     * @param position in screen coordinates
     * @return Game Object with coordinates
     */
    public GameObject getGameObject(Vector2f position)
    {
        for (GameObject go : getGameObjects())
        {
            Transform t = go.transform;
            float x = t.getScreenPosition().x;
            float y = t.getScreenPosition().y;
            float width = t.scale.x;
            float height = t.scale.y;

            if (position.x >= x && position.x <= x + width && position.y >= y && position.y <= y + height)
                return go;
        }

        return null;
    }

    public java.util.List<GameObject> getGameObjects()
    {
        return this.gameObjects;
    }

    public List<GameObject> getGameObjectsWithComponent(Class<? extends Component> componentClass)
    {
        List<GameObject> result = new ArrayList<>();

        for (GameObject go : getGameObjects())
            if (go.hasComponent(componentClass))
                result.add(go);

        return result;
    }

    public abstract void update(double dt);
    public abstract void render(Graphics g);

    public void gui(Graphics g)
    {

    }

    public void loadResources()
    {

    }

    protected void setBackdrop(Color color)
    {
        this.backdrop = color;
        setBackdrop();
    }

    private void setBackdrop()
    {
        Window.setBackdrop(this.backdrop);
    }

    public void onDestroy()
    {

    }

}