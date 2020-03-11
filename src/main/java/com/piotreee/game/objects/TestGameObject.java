package com.piotreee.game.objects;

import com.piotreee.game.packets.AddGameObjectPacket;
import com.piotreee.game.packets.UpdateGameObjectPacket;
import com.piotreee.pixengine.objects.GameObject;
import com.piotreee.pixengine.objects.Transform;

public class TestGameObject implements GameObject {
    protected Transform transform = new Transform();
    protected int id;
    protected int type;

    public TestGameObject(AddGameObjectPacket p) {
        createFromPacket(p);
    }

    public TestGameObject(int id, int type) {
        this.id = id;
        this.type = type;
    }

    protected void createFromPacket(AddGameObjectPacket p) {
        transform.position.set(p.getPosition());
        transform.scale.set(p.getScale());
        transform.setRotation(p.getRotation());
        this.id = p.getGameObjectId();
        this.getRigidbody().ifPresent(rb -> rb.setVelocity(p.getVelocity()));
    }

    public void createPacket(AddGameObjectPacket p) {

    }

    public void updateFromPacket(UpdateGameObjectPacket p) {
//        if (transform.position.distance(p.getPosition()) > 0.1f) {
            transform.position.set(p.getPosition());
//        }

        transform.setRotation(p.getRotation());
        this.getRigidbody().ifPresent(rb -> rb.setVelocity(p.getVelocity()));
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestGameObject that = (TestGameObject) o;
        return id == that.id;
    }
}
