package com.piotreee.pixengine.text;

import com.piotreee.pixengine.render.Model;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.render.Texture;
import org.joml.Matrix4f;

public class Letter {
    private Texture texture;

    public Letter(Texture texture) {
        this.texture = texture;
    }

    public void draw(Shader shader, Matrix4f view, Matrix4f projection) {
        shader.setUniform("projection", projection);
        texture.bind();
        Model.RECTANGLE.render(null, null, null);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
