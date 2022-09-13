package com.l0raxeo.rayCast.dataStructure;

import com.l0raxeo.rayCast.window.Window;
import org.joml.Vector2f;

public class Transform
{

    private Vector2f position;
    public Vector2f scale;

    public Transform(Vector2f position, Vector2f scale)
    {
        init(position, scale);
    }

    /**
     * Screen to world coordinates gets translated here
     */
    public void init(Vector2f position, Vector2f scale)
    {
        // world to screen coordinates
        this.position = new Vector2f(position.x, Window.getHeight() - position.y);
        this.scale = scale;
    }

    // returns world coordinates
    public Vector2f position()
    {
        return new Vector2f(position.x, Window.getHeight() - position.y);
    }

    public Vector2f getScreenPosition()
    {
        return this.position;
    }

    /**
     * @param position in world coords
     */
    public void setPosition(Vector2f position)
    {
        this.position = new Vector2f(position.x, Window.getHeight() - position.y);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Transform t)) return false;

        return t.position.equals(this.position) && t.scale.equals(this.scale);
    }

}
