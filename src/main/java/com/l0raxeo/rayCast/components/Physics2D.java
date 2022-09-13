package com.l0raxeo.rayCast.components;

import org.joml.Vector2f;

public class Physics2D extends Component
{

    private Vector2f velocity;

    @Override
    public void start()
    {
        if (gameObject.hasComponent(Rigidbody.class))
            velocity = gameObject.getComponent(Rigidbody.class).velocity;
        else
            System.out.println("<WARNING> (In GameObject: " + gameObject.getName() + "| From Class: " + this.getClass() + ") doesn't have a Rigidbody. Physics 2D will not work without one.");
    }

    @Override
    public void update(double dt)
    {
        if (velocity == null)
            return;

        fall();
    }

    private void fall()
    {
        velocity.y += 0.5;

        if (velocity.y > 15)
            velocity.y = 15;
    }

}
