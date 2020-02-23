package com.piotreee.pixengine.configs;

import java.io.Serializable;

public class AnimationConfig implements Serializable {
    private int amount;
    private double fps;
    private int width;
    private int height;

    public AnimationConfig(int amount, double fps, int width, int height) {
        this.amount = amount;
        this.fps = fps;
        this.width = width;
        this.height = height;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
