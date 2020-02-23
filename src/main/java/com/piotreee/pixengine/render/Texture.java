package com.piotreee.pixengine.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Texture implements Visual {
    private int textureObject;
    private int width;
    private int height;

    public Texture(int textureObject, int width, int height) {
        this.textureObject = textureObject;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void finalize() throws Throwable {
        glDeleteTextures(textureObject);
        super.finalize();
    }

    @Override
    public void bind() {
        bind(0);
    }

    @Override
    public void bind(int sampler) {
        if (sampler >= 0 && sampler <= 31) {
            glActiveTexture(GL_TEXTURE0 + sampler);
            glBindTexture(GL_TEXTURE_2D, textureObject);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
