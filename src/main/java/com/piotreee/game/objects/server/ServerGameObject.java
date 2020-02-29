package com.piotreee.game.objects.server;

import com.piotreee.pixengine.objects.GameObject;
import com.piotreee.pixengine.objects.Transform;
import org.joml.Vector2f;

public class ServerGameObject implements GameObject {
    private static final float drag = 0.01f;
    private static final float minVel = 0.011f;
    private static final float maxVel = 0.3f;

    protected int id;
    protected Transform transform;
    protected Vector2f velocity;
    protected String spriteName;

    public ServerGameObject(int id, Transform transform, Vector2f velocity, String spriteName) {
        this.id = id;
        this.transform = transform;
        this.velocity = velocity;
        this.spriteName = spriteName;
    }

    @Override
    public void update(double delta) {
        float x = velocity.x;
        float y = velocity.y;

        if (x > maxVel) {
            x = maxVel;
        } else if (x < -maxVel) {
            x = -maxVel;
        }

        if (x < minVel && x > -minVel) {
            x = 0;
        }

        if (x > 0) {
            x -= drag;
        } else if (x < 0) {
            x += drag;
        }

        if (y > maxVel) {
            y = maxVel;
        } else if (y < -maxVel) {
            y = -maxVel;
        }

        if (y < minVel && y > -minVel) {
            y = 0;
        }

        if (y > 0) {
            y -= drag;
        } else if (y < 0) {
            y += drag;
        }

        velocity.set(x, y);
        transform.position.add(velocity.mul((float) delta));
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerGameObject that = (ServerGameObject) o;
        return id == that.id;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    public String getSpriteName() {
        return spriteName;
    }

    public void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }
}
