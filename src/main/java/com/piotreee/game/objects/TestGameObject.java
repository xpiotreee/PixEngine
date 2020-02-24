package com.piotreee.game.objects;

import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.objects.GameObject;
import com.piotreee.pixengine.objects.Transform;
import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Renderable;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.render.Sprite;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.io.Serializable;

public class TestGameObject implements GameObject, Renderable, Serializable {
    private transient static int IDs = 0;
    private int id;
    private Transform transform;
    private Vector2f velocity;
    private transient Sprite sprite;

    public TestGameObject(Transform transform, Vector2f velocity) {
        id = IDs++;
        this.transform = transform;
        this.velocity = velocity;
        this.sprite = new Sprite(transform, Resources.getTexture("test"));
    }
//    public TestGameObject() {
//        id = IDs++;
//        this.transform = new Transform();
//        this.velocity = new Vector2f(0, 0);
//        this.sprite = new Sprite(transform, Resources.getTexture("test"));
//    }

    @Override
    public void update(double delta, Input input) {
//        if (input.isKeyDown(GLFW_KEY_A)) {
//            velocity.set(-1, 0);
//        } else if (input.isKeyDown(GLFW_KEY_D)) {
//            velocity.set(0, 1);
//        }
//
//        if (input.isKeyDown(GLFW_KEY_W)) {
//            velocity.set(0, 1);
//        } else if (input.isKeyDown(GLFW_KEY_S)) {
//            velocity.set(0, -1);
//        }

        transform.position.add(velocity.mul((float) delta));
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        if (sprite == null) {
            sprite = new Sprite(transform, Resources.getTexture("test"));
        }

        sprite.render(shader, camera, view);
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestGameObject that = (TestGameObject) o;
        return id == that.id;
    }
}
