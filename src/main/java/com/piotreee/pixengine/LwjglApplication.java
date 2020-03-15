package com.piotreee.pixengine;

import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.objects.GameScene;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.lang.reflect.InvocationTargetException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


public class LwjglApplication {
    public final static LwjglApplication INSTANCE = new LwjglApplication();

    private Window window;
    private GameScene currentScene;
    private int FPS;
    private float scale = 48;
    private Matrix4f view;

    private LwjglApplication() {
    }

    public void run(Class<? extends GameScene> sceneClass) {
        System.out.println("Running LWJGL " + Version.getVersion() + "!");
        init();
        try {
            currentScene = sceneClass.getConstructor(Window.class).newInstance(window);
            currentScene.load();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }

        loop();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwInitHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = new Window();
        window.createWindow("PixEngine");

        GL.createCapabilities();
        Resources.init();
    }

    private void loop() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);
//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glClearColor(0.25f, 0.25f, 0.25f, 0.0f);
//        glClearColor(0, 0, 0, 0);
        view = new Matrix4f().setTranslation(new Vector3f(0));
        view.scale(scale);

        double frame_cap = 1.0 / 60.0;

        double frame_time = 0;
        int frames = 0;

        double time = System.nanoTime() / (double) 1000000000L;
        double unprocessed = 0;

        while (!window.shouldClose()) {
            boolean can_render = false;
            double time_2 = System.nanoTime() / (double) 1000000000L;
            double passed = time_2 - time;
            unprocessed += passed;
            frame_time += passed;

            time = time_2;

            while (unprocessed >= frame_cap) {
                unprocessed -= frame_cap;
                can_render = true;
                if (window.getInput().isKeyReleased(GLFW_KEY_ESCAPE)) {
                    window.setShouldClose(true);
                }

                currentScene.update((float) frame_cap, window.getInput());
                window.update();

                if (frame_time >= 1.0) {
                    frame_time = 0;
                    System.out.println("FPS: " + frames);
                    FPS = frames;
                    frames = 0;
                }
            }

            if (can_render) {
                glClear(GL_COLOR_BUFFER_BIT);
                currentScene.render(view);
                window.swapBuffers();
                frames++;
            }
        }

        currentScene.unload();
        glfwTerminate();
    }

    public void loadScene(Class<? extends GameScene> sceneClass) {
        currentScene.unload();
        try {
            currentScene = sceneClass.getConstructor(Window.class).newInstance(window);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        currentScene.load();
    }

    public Window getWindow() {
        return window;
    }

    public Input getInput() {
        return window.getInput();
    }

    public int getFPS() {
        return FPS;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        view = new Matrix4f().setTranslation(new Vector3f(0));
        view.scale(scale);
    }

    public GameScene getCurrentScene() {
        return currentScene;
    }
}
