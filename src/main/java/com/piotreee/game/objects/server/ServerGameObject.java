package com.piotreee.game.objects.server;

import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.objects.GameObject;
import com.piotreee.pixengine.objects.Transform;
import org.joml.Vector2f;

public class ServerGameObject implements GameObject {
    protected int id;
    protected Transform transform;
    protected Vector2f velocity;

    public ServerGameObject(int id, Transform transform, Vector2f velocity) {
        this.id = id;
        this.transform = transform;
        this.velocity = velocity;
    }

    @Override
    public void update(double delta, Input input) {
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
}
