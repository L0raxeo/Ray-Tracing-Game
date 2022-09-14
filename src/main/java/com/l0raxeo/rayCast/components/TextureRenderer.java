package com.l0raxeo.rayCast.components;

import com.l0raxeo.rayCast.dataStructure.Transform;

import java.awt.*;

public class TextureRenderer extends Component
{

    private RayCastReceiver rayCastReceiver;
    private Transform t;

    public TextureRenderer()
    {

    }

    @Override
    public void start()
    {
        rayCastReceiver = gameObject.getComponent(RayCastReceiver.class);
        t = gameObject.transform;
    }

    @Override
    public void render(Graphics g)
    {
        if (rayCastReceiver.isCastUpon())
        {
            g.setColor(Color.CYAN);
            g.fillRect((int) t.getScreenPosition().x, (int) t.getScreenPosition().y, (int) t.scale.x, (int) t.scale.y);
        }
    }

}
