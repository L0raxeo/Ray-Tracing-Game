package com.l0raxeo.rayCast.components;

import com.l0raxeo.rayCast.prefabs.Prefabs;
import com.l0raxeo.rayCast.window.Window;
import org.joml.Vector2f;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.Random;

public class WorldLines extends Component
{

    public LinkedList<Line2D.Float> lines;

    public WorldLines()
    {
        lines = buildLines();
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.RED);

        for (Line2D.Float line : lines)
            g.drawLine((int) line.x1, (int) line.y1, (int) line.x2, (int) line.y2);
    }

    private LinkedList<Line2D.Float> buildLines()
    {
        LinkedList<Line2D.Float> lines = new LinkedList<>();

        Random r = new Random();

        int wMultiplier = r.nextInt(50) + 1;
        int hMultiplier = r.nextInt(50) + 1;
        int x = r.nextInt((Window.getWidth() - (16 * wMultiplier)) + 1 - 16) + 16;
        int y = r.nextInt((Window.getHeight() - (16 * hMultiplier)) + 1 - 16) + 16;

        lines.add(new Line2D.Float(x, y, x + (16 * wMultiplier), y));
        lines.add(new Line2D.Float(x + (16 * wMultiplier), y, x + (16 * wMultiplier), y + (16 * hMultiplier)));
        lines.add(new Line2D.Float(x + (16 * wMultiplier), y + (16 * hMultiplier), x, y + (16 * hMultiplier)));
        lines.add(new Line2D.Float(x, y + (16 * hMultiplier), x, y));

        Window.getScene().addGameObjectToScene(Prefabs.generate(
                "Wall",
                new Vector2f(x, Window.getHeight() - y),
                new Vector2f(16 * wMultiplier, 16 * hMultiplier),
                new Rigidbody(1),
                new BoxBounds(),
                new RectRenderer(Color.CYAN, true)
        ));

        return lines;
    }

}
