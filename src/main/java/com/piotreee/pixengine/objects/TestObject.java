package com.piotreee.pixengine.objects;

import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Renderable;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.render.Sprite;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;

public class TestObject implements GameObject, Renderable {
    protected Transform transform;
    protected Renderable renderable;

    public TestObject(Transform transform, Renderable renderable) {
        this.transform = transform;
        this.renderable = renderable;
    }

    public TestObject(Transform transform) {
        this(transform, new Sprite(transform, Resources.getTexture("test")));
    }

    public TestObject(Renderable renderable) {
        this(new Transform(), renderable);
    }

    public TestObject() {
        this.transform = new Transform();
        this.renderable = new Sprite(transform, Resources.getTexture("test"));
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f world) {
        renderable.render(shader, camera, world);
    }
}
