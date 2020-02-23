package com.piotreee.pixengine.gui;

import com.piotreee.pixengine.io.Window;
import org.joml.Vector2f;

import java.util.HashMap;

public class GuiAlignment {
    private Window window;
    private HashMap<Alignment, Vector2f> alignments = new HashMap<>(Alignment.values().length);

    public GuiAlignment(Window window) {
        this.window = window;
        Alignment[] values = Alignment.values();
        for (int i = 0; i < values.length; i++) {
            alignments.put(values[i], new Vector2f());
        }

        alignments.get(Alignment.CENTER).set(0, 0);
        fixAlignments();
    }

    public void fixAlignments() {
        float width = window.getWidth() / 2f;
        float height = window.getHeight() / 2f;

        alignments.get(Alignment.TOP).set(0, height);
        alignments.get(Alignment.BOTTOM).set(0, -height);
        alignments.get(Alignment.LEFT).set(-width, 0);
        alignments.get(Alignment.RIGHT).set(width, 0);
        alignments.get(Alignment.TOP_LEFT).set(-width, height);
        alignments.get(Alignment.TOP_RIGHT).set(width, height);
        alignments.get(Alignment.BOTTOM_LEFT).set(-width, -height);
        alignments.get(Alignment.BOTTOM_RIGHT).set(width, -height);
    }

    public Vector2f getAlignment(Alignment alignment) {
        return alignments.get(alignment);
    }
}
