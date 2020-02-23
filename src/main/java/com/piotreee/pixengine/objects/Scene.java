package com.piotreee.pixengine.objects;

import com.piotreee.pixengine.io.Input;
import org.joml.Matrix4f;

public interface Scene {
    void update(double delta, Input input);

    void render(Matrix4f view);

    void load();

    void unload();
}
