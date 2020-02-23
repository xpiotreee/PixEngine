package com.piotreee.pixengine.render;

import org.joml.Matrix4f;

public interface Renderable {
    void render(Shader shader, Camera camera, Matrix4f view);
}
