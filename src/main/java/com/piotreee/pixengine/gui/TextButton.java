package com.piotreee.pixengine.gui;

import com.piotreee.pixengine.objects.Sheet;
import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.render.Texture;
import com.piotreee.pixengine.text.Font;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class TextButton extends Button {
    private Font font;
    private String text;

    public TextButton(String text, Alignment alignment, float x, float y, float width, float height, ButtonCallback callback) {
        this(text, alignment, x, y, width, height, Resources.getTextureSheet("button"), callback);
    }

    public TextButton(String text, Alignment alignment, float x, float y, float width, float height, Sheet<Texture> textureSheet, ButtonCallback callback) {
        this(text, alignment, new Vector2f(x, y), new Vector2f(width, height), textureSheet, callback);
    }

    public TextButton(Font font, String text, Alignment alignment, float x, float y, float width, float height, ButtonCallback callback) {
        this(font, text, alignment, new Vector2f(x, y), new Vector2f(width, height), Resources.getTextureSheet("button"), callback);
    }

    public TextButton(Font font, String text, Alignment alignment, float x, float y, float width, float height, Sheet<Texture> textureSheet, ButtonCallback callback) {
        this(font, text, alignment, new Vector2f(x, y), new Vector2f(width, height), textureSheet, callback);
    }

    public TextButton(String text, Alignment alignment, Vector2f position, Vector2f size, ButtonCallback callback) {
        this(Resources.getFont("font"), text, alignment, position, size, Resources.getTextureSheet("button"), callback);
    }

    public TextButton(String text, Alignment alignment, Vector2f position, Vector2f size, Sheet<Texture> textureSheet, ButtonCallback callback) {
        this(Resources.getFont("font"), text, alignment, position, size, textureSheet, callback);
    }

    public TextButton(Font font, String text, Alignment alignment, Vector2f position, Vector2f size, ButtonCallback callback) {
        this(font, text, alignment, position, size, Resources.getTextureSheet("button"), callback);
    }

    public TextButton(Font font, String text, Alignment alignment, Vector2f position, Vector2f size, Sheet<Texture> textureSheet, ButtonCallback callback) {
        super(alignment, position, size, textureSheet, callback);
        this.font = font;
        this.text = text;
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
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

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
