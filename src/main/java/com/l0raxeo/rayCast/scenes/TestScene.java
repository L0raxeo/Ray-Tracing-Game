package com.l0raxeo.rayCast.scenes;

import com.l0raxeo.rayCast.components.PlayerRayCast;
import com.l0raxeo.rayCast.components.WorldLines;
import com.l0raxeo.rayCast.gameObjects.GameObject;
import com.l0raxeo.rayCast.components.Component;
import com.l0raxeo.rayCast.prefabs.Prefabs;
import org.joml.Vector2f;

import java.awt.*;
import java.util.ConcurrentModificationException;

public class TestScene extends Scene
{

    @Override
    public void init()
    {
        setBackdrop(Color.BLACK);

        addGameObjectToScene(Prefabs.generate(
                "WorldLines",
                new Vector2f(),
                new Vector2f(),
                new WorldLines()
        ));

        addGameObjectToScene(Prefabs.generate(
                "Player",
                new Vector2f(400, 300),
                new Vector2f(32, 32),
                new PlayerRayCast()
        ));
    }

    @Override
    public void update(double dt)
    {
        try {
            for (GameObject go : getGameObjects())
            {
                go.update(dt);
            }
        }
        catch(ConcurrentModificationException e) {
            try
            {
                for (GameObject go : getGameObjects())
                {
                    go.update(dt);
                }
            }
            catch (ConcurrentModificationException ignore) {}
        }

        getGameObjects().removeIf(GameObject::isDead);
    }

    @Override
    public void render(Graphics g)
    {
        gui(g);

        try
        {
            for (GameObject go : getGameObjects())
            {
                for (Component c : go.getAllComponents())
                    c.render(g);
            }
        }
        catch (ConcurrentModificationException e) {
            try
            {
                for (GameObject go : getGameObjects())
                {
                    for (com.l0raxeo.rayCast.components.Component c : go.getAllComponents())
                        c.render(g);
                }
            }
            catch (ConcurrentModificationException ignore) {}
        }
    }

}
