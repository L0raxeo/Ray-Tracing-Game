package com.l0raxeo.rayCast.components;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;

public class WorldLines extends Component
{

    public LinkedList<Line2D.Float> lines;

    public WorldLines()
    {
        lines = buildLines();
        System.out.println(lines);
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

        lines.add(new Line2D.Float(100 + 50, 100 + 50, 100 + 100, 100 + 50));
        lines.add(new Line2D.Float(100 + 100, 100 + 50, 100 + 100, 100 + 100));
        lines.add(new Line2D.Float(100 + 100, 100 + 100, 100 + 50, 100 + 100));
        lines.add(new Line2D.Float(100 + 50, 100 + 100, 100 + 50, 100 + 50));

        lines.add(new Line2D.Float(300 + 50, 300 + 50, 300 + 100, 300 + 50));
        lines.add(new Line2D.Float(300 + 100, 300 + 50, 300 + 100, 300 + 100));
        lines.add(new Line2D.Float(300 + 100, 300 + 100, 300 + 50, 300 + 100));
        lines.add(new Line2D.Float(300 + 50, 300 + 100, 300 + 50, 300 + 50));

        return lines;
    }

}
