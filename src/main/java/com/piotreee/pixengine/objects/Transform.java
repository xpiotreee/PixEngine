package com.piotreee.pixengine.objects;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.io.Serializable;

public class Transform implements Serializable {
    public Vector2f position;
    public Vector2f scale;
    public float rotation;

    public Transform(Vector2f position, Vector2f scale, float rotation) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    public Transform(Vector2f position, Vector2f scale) {
        this(position, scale, 0);
    }

    public Transform(Vector2f position) {
        this(position, new Vector2f(1, 1), 0);
    }

    public Transform() {
        this(new Vector2f(), new Vector2f(1, 1), 0);
    }

    public void rotate(float amount) {
        rotation += amount;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Matrix4f getProjection(Matrix4f target) {
        target.translate(position.x, position.y, 0);
        target.rotateZ(rotation);
        target.scale(scale.x, scale.y, 0);
        return target;
    }
}
