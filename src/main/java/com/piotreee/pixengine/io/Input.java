package com.piotreee.pixengine.io;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private long window;

    private boolean[] keys;
    private boolean[] mouseButtons;

    private Vector2f mousePos = new Vector2f();
    private double[] x = new double[1];
    private double[] y = new double[1];
    private int[] winWidth = new int[1];
    private int[] winHeight = new int[1];

    public Input(long window) {
        this.window = window;
        this.keys = new boolean[GLFW_KEY_LAST];
        this.mouseButtons = new boolean[GLFW_MOUSE_BUTTON_LAST];
        for (int i = 0; i < GLFW_KEY_LAST; i++) {
            keys[i] = false;
        }

        for (int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++) {
            mouseButtons[i] = false;
        }
    }

    public boolean isKeyDown(int key) {
        return glfwGetKey(window, key) == 1;
    }

    public boolean isKeyPressed(int key) {
        return isKeyDown(key) && !keys[key];
    }

    public boolean isKeyReleased(int key) {
        return !isKeyDown(key) && keys[key];
    }

    public boolean isMouseButtonDown(int button) {
        return glfwGetMouseButton(window, button) == 1;
    }

    public boolean isMouseButtonPressed(int button) {
        return isMouseButtonDown(button) && !mouseButtons[button];
    }

    public boolean isMouseButtonReleased(int button) {
        return !isMouseButtonDown(button) && mouseButtons[button];
    }

    public Vector2f getMousePos() {
        glfwGetCursorPos(window, x, y);
        glfwGetWindowSize(window, winWidth, winHeight);
        return mousePos.set((float) x[0] - (winWidth[0] / 2.0f), -((float) y[0] - (winHeight[0] / 2.0f)));
    }

    public void update() {
        for (int i = 32; i < GLFW_KEY_LAST; i++) {
            keys[i] = isKeyDown(i);
        }

        for (int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++) {
            mouseButtons[i] = isMouseButtonDown(i);
        }
    }
}
