package com.piotreee.pixengine.gui;

import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.objects.Updatable;
import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Shader;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class Gui implements Updatable {
    protected static Matrix4f transformMatrix = new Matrix4f();

    private GuiAlignment guiAlignment;
    private Window window;
    private Camera camera;
    private List<GuiRenderable> renderables = new ArrayList<>();
    private int renderablesSize = 0;
    private List<Updatable> updatables = new ArrayList<>();
    private int updatablesSize = 0;

    public Gui(Window window) {
        this.window = window;
        this.guiAlignment = new GuiAlignment(window);
        this.camera = new Camera(window);
    }

    public void render(Shader shader) {
        for (int i = 0; i < renderablesSize; i++) {
            renderables.get(i).render(shader, this.camera, transformMatrix);
        }
    }

    public void update(double delta, Input input) {
        if (window.hasResized()) {
            camera.setProjection(window.getWidth(), window.getHeight());
            guiAlignment.fixAlignments();
            for (int i = 0; i < renderablesSize; i++) {
                renderables.get(i).updateRenderPosition();
            }
        }

        for (int i = 0; i < updatablesSize; i++) {
            updatables.get(i).update(delta, input);
        }
    }

    public GuiAlignment getGuiAlignment() {
        return guiAlignment;
    }

    public Window getWindow() {
        return window;
    }

    public void add(Object o) {
        if (o instanceof Updatable) {
            addUpdatable((Updatable) o);
        }

        if (o instanceof GuiRenderable) {
            addRenderable((GuiRenderable) o);
        }
    }

    private void addUpdatable(Updatable updatable) {
        updatables.add(updatable);
        updatablesSize++;
    }

    private void addRenderable(GuiRenderable renderable) {
        renderable.setGuiAlignment(guiAlignment);
        renderable.updateRenderPosition();
        renderables.add(renderable);
        renderablesSize++;
    }
}
