package com.piotreee.pixengine.gui;

import com.piotreee.pixengine.objects.Transform;
import org.joml.Vector2f;

public abstract class GuiElement implements GuiRenderable {
    protected GuiAlignment guiAlignment;
    protected Alignment alignment;
    protected Transform transform;
    protected Vector2f renderPosition;

    public GuiElement(Alignment alignment, float x, float y, float width, float height) {
        this(alignment, new Vector2f(x, y), new Vector2f(width, height));
    }

    public GuiElement(Alignment alignment, Vector2f position, Vector2f size) {
        this.alignment = alignment;
        this.transform = new Transform(position, size);
        this.renderPosition = new Vector2f();
    }

    @Override
    public void updateRenderPosition() {
        guiAlignment.getAlignment(alignment).add(transform.position, renderPosition);
    }

    public GuiAlignment getGuiAlignment() {
        return guiAlignment;
    }

    public void setGuiAlignment(GuiAlignment guiAlignment) {
        this.guiAlignment = guiAlignment;
    }

    public Vector2f getRenderPosition() {
        return renderPosition;
    }

    public void setRenderPosition(Vector2f renderPosition) {
        this.renderPosition = renderPosition;
    }
}
