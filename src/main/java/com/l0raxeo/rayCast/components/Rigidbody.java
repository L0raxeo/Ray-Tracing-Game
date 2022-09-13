package com.l0raxeo.rayCast.components;

import com.l0raxeo.rayCast.physics.Bounds;
import com.l0raxeo.rayCast.physics.Collision;
import com.l0raxeo.rayCast.physics.CollisionType;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Rigidbody extends Component
{

    public Vector2f velocity;
    private final float friction;

    public Rigidbody(float friction)
    {
        this.velocity = new Vector2f();
        this.friction = friction;
    }

    @Override
    public void update(double dt)
    {
        boolean teleportY = false;
        boolean teleportX = false;

        if (gameObject.hasComponent(Bounds.class))
        {
            for (Collision collider : gameObject.getComponent(Bounds.class).findGameObjectsInPath(velocity))
            {
                if (collider.collider.equals(this.gameObject))
                    continue;

                if (collider.type == CollisionType.TOP && velocity.y < 0) {
                    velocity.y = 0;
                    gameObject.transform.getScreenPosition().y = collider.collider.transform.getScreenPosition().y + collider.collider.transform.scale.y;
                    teleportY = true;

                    //friction
                    if (velocity.x < 0)
                        velocity.x += friction / 10;
                    else if (velocity.x > 0)
                        velocity.x -= friction / 10;
                } else if (collider.type == CollisionType.BOTTOM && velocity.y > 0) {
                    velocity.y = 0;
                    gameObject.transform.getScreenPosition().y = collider.collider.transform.getScreenPosition().y - gameObject.transform.scale.y;
                    teleportY = true;

                    //friction
                    if (velocity.x < 0)
                        velocity.x += friction / 10;
                    else if (velocity.x > 0)
                        velocity.x -= friction / 10;
                }

                if (collider.type == CollisionType.RIGHT && velocity.x > 0) {
                    velocity.x = 0;
                    gameObject.transform.getScreenPosition().x = collider.collider.transform.getScreenPosition().x - gameObject.transform.scale.x;
                    teleportX = true;

                    //friction
                    if (velocity.y < 0)
                        velocity.y += friction / 10;
                    else if (velocity.y > 0)
                        velocity.y -= friction / 10;
                } else if (collider.type == CollisionType.LEFT && velocity.x < 0) {
                    velocity.x = 0;
                    gameObject.transform.getScreenPosition().x = collider.collider.transform.getScreenPosition().x + collider.collider.transform.scale.x;
                    teleportX = true;

                    //friction
                    if (velocity.y < 0)
                        velocity.y += friction / 10;
                    else if (velocity.y > 0)
                        velocity.y -= friction / 10;
                }

                for (Component c : gameObject.getAllComponents())
                {
                    c.onCollision(collider);
                }
            }
        }

        if (Math.abs(velocity.x) < 0.1)
            velocity.x = 0;
        if (Math.abs(velocity.y) < 0.1)
            velocity.y = 0;

        if (!teleportX)
            gameObject.transform.getScreenPosition().x += velocity.x * dt;

        if (!teleportY)
            gameObject.transform.getScreenPosition().y += velocity.y * dt;

        if(!moveForces.isEmpty())
        {
            int velX = 0;
            int velY = 0;

            for (Vector2f v : moveForces)
            {
                velX += v.x;
                velY += v.y;
            }

            velocity = new Vector2f(velX, velY);
        }

        moveForces.clear();
    }

    public void addForce(Vector2f force)
    {
        velocity.add(force);
    }

    private final List<Vector2f> moveForces = new ArrayList<>();

    public void move(Vector2f velocity)
    {
        this.velocity = velocity;
    }

    public void moveX(float x)
    {
        this.velocity.x = x;
    }

    public void moveY(float y)
    {
        this.velocity.y = y;
    }

    public Vector2f getVelocity()
    {
        return velocity;
    }

    public float getFriction()
    {
        return friction;
    }

}
