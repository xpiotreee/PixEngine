package com.piotreee.pixengine.render;

import com.piotreee.pixengine.objects.Transform;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;

public class Sprite implements Renderable {
    private Transform transform;
    private Visual visual;
    private Model model;

    public Sprite(Transform transform, Texture texture, Model model) {
        this.transform = transform;
        this.visual = texture;
        this.model = model;
    }

    public Sprite(Transform transform, Texture texture) {
        this(transform, texture, Model.RECTANGLE);
    }

    public Sprite(Texture texture) {
        this(new Transform(), texture, Model.RECTANGLE);
    }

    public Sprite() {
        this(new Transform(), Resources.getTexture("test"), Model.RECTANGLE);
    }

    public Sprite(Transform transform, Animation animation, Model model) {
        this.transform = transform;
        this.visual = animation;
        this.model = model;
    }

    public Sprite(Transform transform, Animation animation) {
        this(transform, animation, Model.RECTANGLE);
    }

    public Sprite(Animation animation) {
        this(new Transform(), animation, Model.RECTANGLE);
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        Matrix4f projection = camera.getProjection();
        projection.mul(view);

        shader.setUniform("sampler", 0);
        shader.setUniform("view", view);
        shader.setUniform("projection", transform.getProjection(projection));
        visual.bind();
        model.render(shader, camera, view);
    }

    public Visual getVisual() {
        return visual;
    }

    public void setVisual(Visual visual) {
        this.visual = visual;
    }
}
