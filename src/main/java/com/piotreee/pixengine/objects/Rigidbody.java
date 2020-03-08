package com.piotreee.pixengine.objects;

import org.joml.Vector2f;

public class Rigidbody {
    private Vector2f velocity = new Vector2f();
    private float minVel = 0.05f;
    private float maxVel = 3f;

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f vel) {
        velocity.set(vel);
    }

    public void setVelocity(float velX, float velY) {
        velocity.set(velX, velY);
    }

    public void addVelocity(Vector2f vel) {
        velocity.add(vel);
    }

    public void addVelocity(float velX, float velY) {
        velocity.add(velX, velY);
    }

    public void move(Vector2f position, float drag, float delta) {
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
            x -= drag * delta;
        } else if (x < 0) {
            x += drag * delta;
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
            y -= drag * delta;
        } else if (y < 0) {
            y += drag * delta;
        }

        velocity.set(x, y);
        position.add(velocity.x * delta, velocity.y * delta);
    }
}
