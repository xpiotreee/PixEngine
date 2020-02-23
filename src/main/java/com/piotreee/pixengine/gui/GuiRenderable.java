package com.piotreee.pixengine.gui;

import com.piotreee.pixengine.render.Renderable;

public interface GuiRenderable extends Renderable {
    void updateRenderPosition();

    void setGuiAlignment(GuiAlignment guiAlignment);
}
