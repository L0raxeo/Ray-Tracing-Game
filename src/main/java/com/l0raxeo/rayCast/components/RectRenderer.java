package com.l0raxeo.rayCast.components;

import com.l0raxeo.rayCast.dataStructure.Transform;
import org.joml.Vector2f;

import java.awt.*;

public class RectRenderer extends Component
{

    private Color color;
    private final boolean isSolid;

    public RectRenderer(Color color, boolean isSolid)
    {
        this.color = color;
        this.isSolid = isSolid;
    }

    @Override
    public void render(Graphics g)
    {
        Transform t = gameObject.transform;
        g.setColor(this.color);
        if (isSolid)
            g.fillRect((int) (t.getScreenPosition().x), (int) (t.getScreenPosition().y), (int) t.scale.x, (int) t.scale.y);
        else
            g.drawRect((int) (t.getScreenPosition().x), (int) (t.getScreenPosition().y), (int) t.scale.x, (int) t.scale.y);
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

}
