package com.piotreee.pixengine.io;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private long window;

    private int width, height;
    private boolean fullscreen;
    private boolean hasResized;

    private Input input;

    public Window() {
        setSize(640, 480);
        setFullscreen(false);
        hasResized = false;
    }

    public void createWindow(String title) {
        window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        if (!fullscreen) {
            GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vid.width() - width) / 2, (vid.height() - height) / 2);
        }

        glfwSetWindowSizeCallback(window, (argWindow, argWidth, argHeight) -> {
            width = argWidth;
            height = argHeight;
            hasResized = true;
        });

        glfwMakeContextCurrent(window);

        glfwSwapInterval(1);

        glfwShowWindow(window);

        input = new Input(window);
    }

    public void cleanUp() {
        glfwFreeCallbacks(window);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void swapBuffers() {
        glfwSwapBuffers(window);
    }

    public void setShouldClose(boolean shouldClose) {
        glfwSetWindowShouldClose(window, shouldClose);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void update() {
        hasResized = false;
        input.update();
        if (input.isKeyReleased(GLFW_KEY_ESCAPE)) {
            setShouldClose(true);
        }

        glfwPollEvents();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean hasResized() {
        return hasResized;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public long getWindowLong() {
        return window;
    }

    public Input getInput() {
        return input;
    }
}
