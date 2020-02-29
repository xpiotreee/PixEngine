package com.piotreee.pixengine.gui;

import com.piotreee.pixengine.LwjglApplication;
import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.math.Rectangle;
import com.piotreee.pixengine.objects.Sheet;
import com.piotreee.pixengine.objects.Updatable;
import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.render.Texture;
import com.piotreee.pixengine.text.Font;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class TextField extends Panel implements Updatable {
    private final static Vector4f COLOR_DEFAULT = new Vector4f(0, 0, 0, 0);
    private final static int STATE_IDLE = 0;
    private final static Vector4f COLOR_IDLE = new Vector4f(-0.1f, -0.1f, -0.1f, 0);
    private final static int STATE_SELECTED = 1;
    private final static Vector4f COLOR_SELECTED = new Vector4f(-0.3f, -0.3f, -0.3f, 0);
    private final static int STATE_CLICKED = 2;
    private final static Vector4f COLOR_CLICKED = new Vector4f(-0.5f, -0.5f, -0.5f, 0);

    private int currentState = STATE_IDLE;
    private Rectangle collider;
    private String text = "";
    private Font font;

    public TextField(Alignment alignment, float x, float y, float width, float height) {
        this(alignment, x, y, width, height, Resources.getTextureSheet("textField"));
    }

    public TextField(Alignment alignment, float x, float y, float width, float height, Sheet<Texture> textureSheet) {
        this(Resources.getFont("font"), alignment, x, y, width, height, textureSheet);
    }

    public TextField(Font font, Alignment alignment, float x, float y, float width, float height, Sheet<Texture> textureSheet) {
        this(font, alignment, new Vector2f(x, y), new Vector2f(width, height), textureSheet);
    }

    public TextField(Alignment alignment, Vector2f position, Vector2f size) {
        this(alignment, position, size, Resources.getTextureSheet("textField"));
    }

    public TextField(Alignment alignment, Vector2f position, Vector2f size, Sheet<Texture> textureSheet) {
        this(Resources.getFont("font"), alignment, position, size, textureSheet);
    }

    public TextField(Font font, Alignment alignment, Vector2f position, Vector2f size, Sheet<Texture> textureSheet) {
        super(alignment, position, size, textureSheet);
        this.font = font;
        this.collider = new Rectangle(position, size);
    }

    @Override
    public void update(double delta) {
        Input input = LwjglApplication.INSTANCE.getInput();
        if (collider.contains(input.getMousePos()) && currentState != STATE_CLICKED) {
            if (input.isMouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT)) {
                currentState = STATE_CLICKED;
                glfwSetKeyCallback(LwjglApplication.INSTANCE.getWindow().getWindowLong(), (window, key, scancode, action, mods) -> {
                    if (currentState == STATE_CLICKED && (action == GLFW_PRESS || action == GLFW_REPEAT)) {
                        if (key == GLFW_KEY_ESCAPE || key == GLFW_KEY_ENTER) {
                            currentState = STATE_IDLE;
                            return;
                        }

                        if (key == GLFW_KEY_BACKSPACE) {
                            if (!text.equals("")) {
                                text = text.substring(0, text.length() - 1);
                            }
                            return;
                        }

                        text += (char) key;
                        System.out.println(text);
                    }
                });
            } else {
                currentState = STATE_SELECTED;
            }
        } else if (!collider.contains(input.getMousePos()) && currentState != STATE_CLICKED || (input.isMouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT) ||
                input.isMouseButtonPressed(GLFW_MOUSE_BUTTON_RIGHT) ||
                input.isMouseButtonPressed(GLFW_MOUSE_BUTTON_MIDDLE))) {
            currentState = STATE_IDLE;
        }
    }

    @Override
    public void updateRenderPosition() {
        super.updateRenderPosition();
        collider.setPosition(renderPosition.x - collider.width / 2f, renderPosition.y - collider.height / 2f);
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        Vector4f color;
        switch (currentState) {
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
        shader.setUniform("color", COLOR_DEFAULT);
        font.drawText(text, 0, 0, shader, camera, view.identity().translate(left, renderPosition.y, 0).scale(width, height, 1));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
