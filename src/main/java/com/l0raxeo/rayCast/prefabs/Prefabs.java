package com.l0raxeo.rayCast.prefabs;

import com.l0raxeo.rayCast.components.Component;
import com.l0raxeo.rayCast.dataStructure.Transform;
import com.l0raxeo.rayCast.gameObjects.GameObject;
import org.joml.Vector2f;

public class Prefabs
{

    public static GameObject generate(String name, Vector2f pos, Vector2f size, Component... comps)
    {
        GameObject go = new GameObject(name, new Transform(pos, size));

        for (Component c : comps)
            go.addComponent(c);

        return go;
    }

}
