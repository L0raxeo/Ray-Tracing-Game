package com.l0raxeo.rayTracingGame.scenes;

import com.l0raxeo.rayTracingGame.window.Window;

import java.awt.*;

public abstract class Scene
{
    protected Color backdrop = new Color(0, 0, 0, 0);

    private boolean isRunning = false;

    public Scene() {

    }

    public void init() {

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
