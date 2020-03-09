package com.piotreee.pixengine.gui;

import com.piotreee.pixengine.LwjglApplication;
import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.math.Collider;
import com.piotreee.pixengine.objects.Sheet;
import com.piotreee.pixengine.objects.Updatable;
import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.render.Texture;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class Button extends Panel implements Updatable {
    protected final static Vector4f COLOR_DEFAULT = new Vector4f(0, 0, 0, 0);
    protected final static Vector4f COLOR_IDLE = new Vector4f(0.5f, 0f, 0f, 0f);
    protected final static Vector4f COLOR_SELECTED = new Vector4f(0f, 0.5f, 0f, 0f);
    protected final static Vector4f COLOR_CLICKED = new Vector4f(0f, 0f, 0.5f, 0f);
    private final static int STATE_IDLE = 0;
    private final static int STATE_SELECTED = 1;
    private final static int STATE_CLICKED = 2;
    private Collider collider;
    private int selectedState;
    private ButtonCallback callback;

    public Button(Alignment alignment, float x, float y, float width, float height, ButtonCallback callback) {
        this(alignment, x, y, width, height, Resources.getTextureSheet("button"), callback);
    }

    public Button(Alignment alignment, float x, float y, float width, float height, Sheet<Texture> textureSheet, ButtonCallback callback) {
        this(alignment, new Vector2f(x, y), new Vector2f(width, height), textureSheet, callback);
    }

    public Button(Alignment alignment, Vector2f position, Vector2f size, ButtonCallback callback) {
        this(alignment, position, size, Resources.getTextureSheet("button"), callback);
    }

    public Button(Alignment alignment, Vector2f position, Vector2f size, Sheet<Texture> textureSheet, ButtonCallback callback) {
        super(alignment, position, size, textureSheet);
        this.collider = new Collider(position.x, position.y, size.x, size.y);
        this.selectedState = STATE_IDLE;
        this.callback = callback;
    }

    @Override
    public void update(float delta) {
        Input input = LwjglApplication.INSTANCE.getInput();
        if (collider.contains(input.getMousePos())) {
            selectedState = STATE_SELECTED;
            if (input.isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT) ||
                    input.isMouseButtonDown(GLFW_MOUSE_BUTTON_RIGHT) ||
                    input.isMouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
                selectedState = STATE_CLICKED;
            }

            if (input.isMouseButtonReleased(GLFW_MOUSE_BUTTON_LEFT)) {
                callback.pressed(GLFW_MOUSE_BUTTON_LEFT);
            } else if (input.isMouseButtonReleased(GLFW_MOUSE_BUTTON_RIGHT)) {
                callback.pressed(GLFW_MOUSE_BUTTON_RIGHT);
            } else if (input.isMouseButtonReleased(GLFW_MOUSE_BUTTON_MIDDLE)) {
                callback.pressed(GLFW_MOUSE_BUTTON_MIDDLE);
            }
        } else {
            selectedState = STATE_IDLE;
        }
    }

    @Override
    public void updateRenderPosition() {
        super.updateRenderPosition();
        collider.setPosition(renderPosition.x - collider.size.x / 2f, renderPosition.y - collider.size.y / 2f);
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        shader.bind();
        Vector4f color;
        switch (selectedState) {
            default:
                color = COLOR_DEFAULT;
                break;
            case STATE_IDLE:
                color = COLOR_IDLE;
                break;
            case STATE_SELECTED:
                color = COLOR_SELECTED;
                break;
            case STATE_CLICKED:
                color = COLOR_CLICKED;
                break;
        }

        shader.setUniform("color", color);
        super.render(shader, camera, view);
    }
}
