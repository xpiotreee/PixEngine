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
    protected List<Updatable> updatables = new ArrayList<>();
    protected int updatablesSize = 0;
    protected List<Renderable> renderables = new ArrayList<>();
    protected int renderablesSize = 0;

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
            addUpdatable((Updatable) o);
        }

        if (o instanceof Renderable) {
            addRenderable((Renderable) o);
        }
    }

    public void remove(Object o) {
        if (o instanceof Updatable) {
            removeUpdatable((Updatable) o);
        }

        if (o instanceof Renderable) {
            removeRenderable((Renderable) o);
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

    private void addUpdatable(Updatable updatable) {
        updatables.add(updatable);
        updatablesSize++;
    }

    private void addRenderable(Renderable renderable) {
        renderables.add(renderable);
        renderablesSize++;
    }

    private void removeUpdatable(Updatable updatable) {
        updatables.remove(updatable);
        updatablesSize--;
    }

    private void removeRenderable(Renderable renderable) {
        renderables.remove(renderable);
        renderablesSize--;
    }
}
