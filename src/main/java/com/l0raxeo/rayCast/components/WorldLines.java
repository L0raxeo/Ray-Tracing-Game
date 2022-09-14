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

    private LinkedList<Line2D.Float> buildLines()
    {
        LinkedList<Line2D.Float> lines = new LinkedList<>();

        Random r = new Random();

        int x = 200;
        int y = 200;
        int width = 128, height = 16;

        lines.add(new Line2D.Float(x, y, x + width, y));
        lines.add(new Line2D.Float(x + width, y, x + width, x + height));
        lines.add(new Line2D.Float(x + width, x + height, x, y + height));
        lines.add(new Line2D.Float(x, y + height, x, y));

        Window.getScene().addGameObjectToScene(Prefabs.generate(
                "Wall",
                new Vector2f(x, Window.getHeight() - y),
                new Vector2f(width, height),
                new Rigidbody(1),
                new BoxBounds(),
                new RectRenderer(Color.WHITE, true)
        ));

        lines.add(new Line2D.Float(400, 400, 464, 400));
        lines.add(new Line2D.Float(464, 400, 464, 464));
        lines.add(new Line2D.Float(464, 464, 400, 464));
        lines.add(new Line2D.Float(400, 464, 400, 400));

        Window.getScene().addGameObjectToScene(Prefabs.generate(
                "Item",
                new Vector2f(400, Window.getHeight() - 400),
                new Vector2f(64, 64),
                new Rigidbody(1),
                new BoxBounds(),
                new TextureRenderer(),
                new RayCastReceiver()
        ));

        return lines;
    }

}
