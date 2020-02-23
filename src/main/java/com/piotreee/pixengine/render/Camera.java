package com.piotreee.pixengine.render;

import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.objects.Transform;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class Camera {
    private Window window;
    private Transform transform;
    private Matrix4f projection;

    public Camera(Window window) {
        this.window = window;
        this.transform = new Transform();
        setProjection(window.getWidth(), window.getHeight());
    }

    public void update() {
        if (window.hasResized()) {
            setProjection(window.getWidth(), window.getHeight());
            glViewport(0, 0, window.getWidth(), window.getHeight());
        }

        if (window.getInput().isKeyDown(GLFW_KEY_LEFT)) {
            addPosition(10f, 0);
        } else if (window.getInput().isKeyDown(GLFW_KEY_RIGHT)) {
            addPosition(-10f, 0);
        }

        if (window.getInput().isKeyDown(GLFW_KEY_UP)) {
            addPosition(0, -10f);
        } else if (window.getInput().isKeyDown(GLFW_KEY_DOWN)) {
            addPosition(0, 10f);
        }

        if (window.getInput().isKeyDown(GLFW_KEY_A)) {
            rotate(-0.01f);
        } else if (window.getInput().isKeyDown(GLFW_KEY_D)) {
            rotate(0.01f);
        }
    }

    public void setProjection(int width, int height) {
        width /= 2f;
        height /= 2f;
        projection = new Matrix4f().setOrtho2D(-width, width, -height, height);
    }

    public void rotate(float amount) {
        transform.rotate(amount);
    }

    public void addPosition(float x, float y) {
        this.transform.position.add(x, y);
    }

    public void addPosition(Vector2f position) {
        this.transform.position.add(position);
    }

    public Vector2f getPosition() {
        return transform.position;
    }

    public void setPosition(Vector2f position) {
        transform.position = position;
    }

    public Matrix4f getUntransformedProjection() {
        return projection;
    }

    public Matrix4f getProjection() {
        return projection
                .translate(transform.position.x, transform.position.y, 0, new Matrix4f())
                .rotateZ(transform.rotation)
                .scale(transform.scale.x, transform.scale.y, 1);
    }

    public float getRotation() {
        return transform.rotation;
    }

    public void setRotation(float rotation) {
        transform.setRotation(rotation);
    }

    public Vector2f getScale() {
        return transform.scale;
    }

    public Transform getTransform() {
        return transform;
    }
}
