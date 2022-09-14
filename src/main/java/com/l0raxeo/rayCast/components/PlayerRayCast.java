package com.l0raxeo.rayCast.components;

import com.l0raxeo.rayCast.gameObjects.GameObject;
import com.l0raxeo.rayCast.input.keyboard.KeyManager;
import com.l0raxeo.rayCast.input.mouse.MouseManager;
import com.l0raxeo.rayCast.window.Window;
import org.joml.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.LinkedList;

public class PlayerRayCast extends Component
{

    private LinkedList<Line2D.Float> lines;
    private Rigidbody rigidbody;

    private final int rcResolution = 150;
    private final int rcMaxDist = 400;
    private int rotation;

    @Override
    public void start()
    {
        this.lines = Window.getScene().getGameObject("WorldLines").getComponent(WorldLines.class).lines;
        this.rigidbody = gameObject.getComponent(Rigidbody.class);
    }

    @Override
    public void update(double dt)
    {
        getInput();

        int mouseX = MouseManager.getMouseX();
        int mouseY = MouseManager.getMouseY();

        rotation = (int) (((getRotation(getRayX(), getRayY(), mouseX, mouseY) / 360) * rcResolution) + (rcResolution * 0.93));
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.WHITE);

        for (Line2D.Float ray : calcRays(lines, getRayX(), getRayY(), rcResolution, rcMaxDist))
            g.drawLine((int) ray.x1, (int) ray.y1, (int) ray.x2, (int) ray.y2);
    }

    private void getInput()
    {
        if (KeyManager.isHeld('w'))
            rigidbody.moveY(-5);
        if (KeyManager.isHeld('a'))
            rigidbody.moveX(-5);
        if (KeyManager.isHeld('s'))
            rigidbody.moveY(5);
        if (KeyManager.isHeld('d'))
            rigidbody.moveX(5);

        if (KeyManager.onRelease('w'))
            rigidbody.moveY(0);
        if (KeyManager.onRelease('a'))
            rigidbody.moveX(0);
        if (KeyManager.onRelease('s'))
            rigidbody.moveY(0);
        if (KeyManager.onRelease('d'))
            rigidbody.moveX(0);

        if (KeyManager.isHeld(KeyEvent.VK_LEFT))
            rotation -= 75;
        if (KeyManager.isHeld(KeyEvent.VK_RIGHT))
            rotation += 75;
    }

    private double getRotation(float originX, float originY, float targetX, float targetY)
    {
        double angle = Math.atan2(targetY - originY, targetX - originX) * (180 / Math.PI);

        if (angle < 0)
            angle += 360;

        return angle;
    }

    public static float dist(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static float getRayCast(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y) {
        float s1_x, s1_y, s2_x, s2_y, divisor;
        s1_x = p1_x - p0_x;
        s1_y = p1_y - p0_y;
        s2_x = p3_x - p2_x;
        s2_y = p3_y - p2_y;
        divisor = -s2_x * s1_y + s1_x * s2_y;

        float s, t;
        s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / divisor;
        t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / divisor;

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
            // Collision detected
            float x = p0_x + (t * s1_x);
            float y = p0_y + (t * s1_y);

            GameObject goReceiver = Window.getScene().getGameObject(new Vector2f(p2_x, p2_y));
            if (goReceiver != null && goReceiver.hasComponent(RayCastReceiver.class))
                goReceiver.getComponent(RayCastReceiver.class).onReceive();

            return dist(p0_x, p0_y, x, y);
        }

        return -1; // No collision
    }

    private LinkedList<Line2D.Float> calcRays(LinkedList<Line2D.Float> lines, int x, int y, int resolution, int maxDist)
    {
        LinkedList<Line2D.Float> rays = new LinkedList<>();
        for (int i = 0; i < resolution; i++)
        {
            int lRotation = rotation;

            if (rotation < 0)
                rotation = rcResolution;

            if (lRotation > resolution)
                lRotation = lRotation - (resolution * (rotation / resolution));

            if (i > lRotation + resolution / 7 || i < lRotation && !(lRotation > resolution - resolution / 7 && i < lRotation + resolution / 7 - resolution))
                continue;

            double dir = (Math.PI * 2) * ((double) i / resolution);

            float minDist = maxDist;

            for (Line2D.Float line : lines)
            {
                float dist = getRayCast((float) x, (float) y, x + (float) (Math.cos(dir) * maxDist), y + (float) (Math.sin(dir) * maxDist), line.x1, line.y1, line.x2, line.y2);
                if (dist < minDist && dist > 0)
                {
                    minDist = dist;
                }
            }

            rays.add(new Line2D.Float(x, y, x + (float) Math.cos(dir) * minDist, y + (float) Math.sin(dir) * minDist));
        }

        return rays;
    }

    private int getRayX()
    {
        return (int) (gameObject.transform.getScreenPosition().x + (gameObject.transform.scale.x / 2));
    }

    private int getRayY()
    {
        return (int) (gameObject.transform.getScreenPosition().y + (gameObject.transform.scale.y / 2));
    }

}
