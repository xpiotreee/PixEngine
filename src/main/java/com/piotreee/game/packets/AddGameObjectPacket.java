package com.piotreee.game.packets;

import com.piotreee.game.objects.TestGameObject;
import com.piotreee.pixengine.objects.Transform;
import org.joml.Vector2f;

import java.nio.ByteBuffer;

public class AddGameObjectPacket extends UpdateGameObjectPacket {
    private float scaleX;
    private float scaleY;
    private int type;

    public AddGameObjectPacket(TestGameObject gameObject) {
        this(gameObject.getId(), gameObject.getTransform(), 0, 0, gameObject.getType());
//        if (gameObject instanceof IRigidbody) {
//            Vector2f vel = ((IRigidbody) gameObject).getRigidbody().getVelocity();
//            this.velX = vel.x;
//            this.velY = vel.y;
//        }
        Vector2f vel = new Vector2f();
        gameObject.getRigidbody().ifPresent(rb -> vel.set(rb.getVelocity()));
        this.velX = vel.x;
        this.velY = vel.y;
    }

    public AddGameObjectPacket(int id, Transform transform, float velX, float velY, int type) {
        this(id, transform.position.x, transform.position.y, velX, velY,
                transform.getRotation(), transform.scale.x, transform.scale.y, type);
    }

    public AddGameObjectPacket(int id, float posX, float posY, float velX, float velY, float rotation, float scaleX, float scaleY, int type) {
        this.id = 2;
        this.gameObjectId = id;
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.rotation = rotation;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.type = type;
    }

    public AddGameObjectPacket() {
        this.id = 2;
    }

    public AddGameObjectPacket(byte[] data) {
        super(data);
    }

    @Override
    public ByteBuffer writeData(ByteBuffer buffer) {
        super.writeData(buffer);
        buffer.putFloat(scaleX);
        buffer.putFloat(scaleY);
        buffer.putInt(type);
        return buffer;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        this.scaleX = buffer.getFloat();
        this.scaleY = buffer.getFloat();
        this.type = buffer.getInt();
    }

    public float getScaleX() {
        return scaleX;
    }

    public AddGameObjectPacket setScaleX(float scaleX) {
        this.scaleX = scaleX;
        return this;
    }

    public float getScaleY() {
        return scaleY;
    }

    public AddGameObjectPacket setScaleY(float scaleY) {
        this.scaleY = scaleY;
        return this;
    }

    public int getType() {
        return type;
    }

    public AddGameObjectPacket setType(int type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "AddGameObjectPacket{" +
                "scaleX=" + scaleX +
                ", scaleY=" + scaleY +
                ", gameObjectId=" + gameObjectId +
                ", posX=" + posX +
                ", posY=" + posY +
                ", velX=" + velX +
                ", velY=" + velY +
                ", rotation=" + rotation +
                ", id=" + id +
                ", type=" + type +
                '}';
    }
}
