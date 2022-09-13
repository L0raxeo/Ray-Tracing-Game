package com.l0raxeo.rayCast.components;

import com.l0raxeo.rayCast.gameObjects.GameObject;
import com.l0raxeo.rayCast.physics.Collision;

import java.awt.*;

public abstract class Component
{

    // component class in general
    private static int ID_COUNTER = 0;
    // associated with individual components/objects
    private int uid = -1;

    public transient GameObject gameObject = null;

    public void start()
    {

    }

    public void update(double dt){}

    public void render(Graphics g) {}

    public void onCollision(Collision collision) {}

    public void generateId()
    {
        if (this.uid == -1)
            this.uid = ID_COUNTER++;
    }

    public void onDestroy()
    {

    }

}
