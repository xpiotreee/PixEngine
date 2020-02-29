package com.piotreee.pixengine.objects;

import com.piotreee.pixengine.gui.Gui;
import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Renderable;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public abstract class GameScene implements Scene {
    protected Camera camera;
    protected Gui gui;
    protected Shader shader;
    protected Shader guiShader;
    private List<Updatable> updatables = new ArrayList<>();
    private int updatablesSize = 0;
    private List<Renderable> renderables = new ArrayList<>();
    private int renderablesSize = 0;

    public GameScene(Window window) {
        this(window, Resources.getShader("shader"), Resources.getShader("gui"));
    }

    public GameScene(Window window, Shader shader, Shader guiShader) {
        this.camera = new Camera(window);
        this.gui = new Gui(window);
        this.shader = shader;
        this.guiShader = guiShader;
    }

    @Override
    public void update(double delta, Input input) {
        camera.update();
        gui.update(delta);
        for (int i = 0; i < updatablesSize; i++) {
            updatables.get(i).update(delta);
        }
    }

    @Override
    public void render(Matrix4f view) {
        shader.bind();
        for (int i = 0; i < renderablesSize; i++) {
            renderables.get(i).render(shader, camera, view);
        }

        guiShader.bind();
        gui.render(guiShader);
    }

    public void add(Object o) {
        if (o instanceof Updatable) {
            addGameObject((Updatable) o);
        }

        if (o instanceof Renderable) {
            addRenderable((Renderable) o);
        }
    }

    public List<Updatable> getUpdatables() {
        return updatables;
    }

    public int getUpdatablesSize() {
        return updatablesSize;
    }

    public List<Renderable> getRenderables() {
        return renderables;
    }

    public int getRenderablesSize() {
        return renderablesSize;
    }

    private void addGameObject(Updatable updatable) {
        updatables.add(updatable);
        updatablesSize++;
    }

    public void addRenderable(Renderable renderable) {
        renderables.add(renderable);
        renderablesSize++;
    }
}
