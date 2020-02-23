package com.piotreee.pixengine.math;

import org.joml.Vector2f;

public class Rectangle {
    public float x;
    public float y;
    public float width;
    public float height;

    public Rectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Vector2f position, Vector2f size) {
        this(position.x, position.y, size.x, size.y);
    }

    public boolean contains(float x, float y) {
        return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y;
    }

    public boolean contains(Vector2f point) {
        return contains(point.x, point.y);
    }

    public boolean overlaps(Rectangle r) {
        return x < r.x + r.width && x + width > r.x && y < r.y + r.height && y + height > r.y;
    }

    public Vector2f getCenter() {
        return new Vector2f(x + width / 2, y + height / 2);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(Vector2f position) {
        setPosition(position.x, position.y);
    }
}
