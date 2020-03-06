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
        transform.position.set(p.getPosX(), p.getPosY());
        transform.scale.set(p.getScaleX(), p.getScaleY());
        transform.setRotation(p.getRotation());
        this.id = p.getGameObjectId();
        this.getRigidbody().ifPresent(rb -> rb.setVelocity(p.getVelX(), p.getVelY()));
    }

    public void updateFromPacket(UpdateGameObjectPacket p) {
        transform.position.set(p.getPosX(), p.getPosY());
        transform.setRotation(p.getRotation());
        this.getRigidbody().ifPresent(rb -> rb.setVelocity(p.getVelX(), p.getVelY()));
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
