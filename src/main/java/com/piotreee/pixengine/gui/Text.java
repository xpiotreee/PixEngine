package com.piotreee.pixengine.gui;

import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.text.Font;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Text extends GuiElement {
    private String text;
    private Font font;

    public Text(String text, Alignment alignment, Vector2f position, Vector2f size) {
        this(text, Resources.getFont("font"), alignment, position, size);
    }

    public Text(String text, Alignment alignment, float x, float y, float width, float height) {
        this(text, Resources.getFont("font"), alignment, new Vector2f(x, y), new Vector2f(width, height));
    }

    public Text(String text, Font font, Alignment alignment, Vector2f position, Vector2f size) {
        super(alignment, position, size);
        this.text = text;
        this.font = font;
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        shader.bind();
        shader.setUniform("color", new Vector4f(0, 0, 0, 0));
        font.drawText(text, 0, 0, shader, camera, view.identity().translate(renderPosition.x, renderPosition.y, 0).scale(transform.scale.x, transform.scale.y, 1));
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
