package com.piotreee.pixengine.math;

import org.joml.Vector2f;

public class Collider {
    public Vector2f position;
    public Vector2f size;

    public Collider(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
    }

    public Collider(float x, float y, float width, float height) {
        this(new Vector2f(x, y), new Vector2f(width, height));
    }

    public boolean contains(float x, float y) {
        return position.x <= x && position.x + size.x >= x && position.y <= y && position.y + size.y >= y;
    }

    public boolean contains(Vector2f point) {
        return contains(point.x, point.y);
    }

    public boolean overlaps(Collider r) {
        return position.x < r.position.x + r.size.x &&
                position.x + size.x > r.position.x &&
                position.y < r.position.y + r.size.y &&
                position.y + size.y > r.position.y;
    }

    public Vector2f getCenter() {
        return new Vector2f(position.x + size.x / 2, position.y + size.y / 2);
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public void setPosition(Vector2f position) {
        this.position.set(position);
    }
}
