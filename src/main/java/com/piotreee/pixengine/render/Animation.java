package com.piotreee.pixengine.render;

public class Animation implements Visual {
    private Texture[] frames;
    private int texturePointer;

    private double elapsedTime;
    private double currentTime;
    private double lastTime;
    private double fps;

    public Animation(Texture[] frames, double fps) {
        this.texturePointer = 0;
        this.elapsedTime = 0;
        this.currentTime = 0;
        this.lastTime = (double) System.nanoTime() / 1000000000d;
        this.fps = 1.0 / fps;
        this.frames = frames;
    }

    @Override
    public void bind() {
        bind(0);
    }

    @Override
    public void bind(int sampler) {
        this.currentTime = (double) System.nanoTime() / 1000000000d;
        this.elapsedTime += currentTime - lastTime;

        if (elapsedTime >= fps) {
            elapsedTime = 0;
            texturePointer++;
        }

        if (texturePointer >= frames.length) texturePointer = 0;

        this.lastTime = currentTime;

        frames[texturePointer].bind(sampler);
    }

}
