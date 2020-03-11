package com.piotreee.pixengine.gui;

import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.text.Font;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Text extends GuiElement {
    private TextAlignment textAlignment;
    private String text;
    private float offset;
    private Font font;

    public Text(TextAlignment textAlignment, String text, Alignment alignment, Vector2f position) {
        this(textAlignment, text, Resources.getFont("font"), alignment, position);
    }

    public Text(TextAlignment textAlignment, String text, Alignment alignment, float x, float y) {
        this(textAlignment, text, Resources.getFont("font"), alignment, new Vector2f(x, y));
    }

    public Text(TextAlignment textAlignment, String text, Font font, Alignment alignment, Vector2f position) {
        super(alignment, position, new Vector2f(font.calcStringWidth(text), 1));
        this.textAlignment = textAlignment;
        this.text = text;
        switch (textAlignment) {
            case LEFT:
                this.offset = 0;
                break;
            case CENTER:
                this.offset = transform.scale.x * 0.75f;
                break;
            case RIGHT:
                this.offset = transform.scale.x * 1.75f;
                break;
        }
        this.font = font;
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        shader.bind();
        shader.setUniform("color", new Vector4f(0, 0, 0, 0));
        font.drawText(text, 0, 0, shader, camera, view.identity().translate(renderPosition.x - offset, renderPosition.y, 0).scale(transform.scale.x, transform.scale.y, 1));
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
