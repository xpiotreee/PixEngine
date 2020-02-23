package com.piotreee.pixengine.gui;

import com.piotreee.pixengine.objects.Sheet;
import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Model;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.render.Texture;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Panel extends GuiElement {
    protected int width;
    protected int height;
    protected float top;
    protected float bottom;
    protected float left;
    protected float right;
    private Sheet<Texture> textureSheet;

    public Panel(Alignment alignment, float x, float y, float width, float height, Sheet<Texture> textureSheet) {
        this(alignment, new Vector2f(x, y), new Vector2f(width, height), textureSheet);
    }

    public Panel(Alignment alignment, Vector2f position, Vector2f size, Sheet<Texture> textureSheet) {
        super(alignment, position, size);
        this.textureSheet = textureSheet;
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        Texture texture = textureSheet.getElement(1, 1);
        width = texture.getWidth();
        height = texture.getHeight();
        top = renderPosition.y + (transform.scale.y - height) / 2f;
        bottom = renderPosition.y - transform.scale.y / 2f + height / 2f;
        left = renderPosition.x - transform.scale.x / 2f + width / 2f;
        right = renderPosition.x + (transform.scale.x - width) / 2f;

        renderElement(renderPosition.x, renderPosition.y, transform.scale.x, transform.scale.y, camera, texture, shader, view); //MIDDLE

        renderElement(renderPosition.x, top, transform.scale.x, height, camera, textureSheet.getElement(1, 0), shader, view); //TOP SIDE
        renderElement(renderPosition.x, bottom, transform.scale.x, height, camera, textureSheet.getElement(1, 2), shader, view); //BOTTOM SIDE
        renderElement(left, renderPosition.y, width, transform.scale.y, camera, textureSheet.getElement(0, 1), shader, view); //LEFT SIDE
        renderElement(right, renderPosition.y, width, transform.scale.y, camera, textureSheet.getElement(2, 1), shader, view); //RIGHT SIDE

        renderElement(left, top, width, height, camera, textureSheet.getElement(0, 0), shader, view); //TOP LEFT CORNER
        renderElement(right, top, width, height, camera, textureSheet.getElement(2, 0), shader, view); //TOP RIGHT CORNER
        renderElement(left, bottom, width, height, camera, textureSheet.getElement(0, 2), shader, view); //BOTTOM LEFT CORNER
        renderElement(right, bottom, width, height, camera, textureSheet.getElement(2, 2), shader, view); //BOTTOM RIGHT CORNER
    }

    private void renderElement(float x, float y, float width, float height, Camera camera, Texture texture, Shader shader, Matrix4f view) {
        view.identity().translate(x, y, 0).scale(width, height, 1);
        shader.setUniform("projection", camera.getProjection().mul(view));
        texture.bind();
        Model.RECTANGLE.render(shader, camera, null);
    }
}
