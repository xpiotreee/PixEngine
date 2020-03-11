package com.piotreee.game.packets;

import com.piotreee.game.objects.TestGameObject;
import com.piotreee.pixengine.networking.Packet;
import org.joml.Vector2f;

import java.nio.ByteBuffer;

public class UpdateGameObjectPacket extends Packet {
    protected int gameObjectId;
    protected Vector2f position;
    protected Vector2f velocity;
    protected float rotation;

    public UpdateGameObjectPacket(TestGameObject gameObject) {
        this.id = 1;
        this.gameObjectId = gameObject.getId();
        position = gameObject.getTransform().position;
        Vector2f velocity = new Vector2f();
        gameObject.getRigidbody().ifPresent(rb -> velocity.set(rb.getVelocity()));
        this.velocity = velocity;
        this.rotation = gameObject.getTransform().getRotation();
    }

    public UpdateGameObjectPacket() {
        this.id = 1;
    }

    public UpdateGameObjectPacket(byte[] data) {
        super(data);
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        this.gameObjectId = buffer.getInt();
        this.position = readVector2f();
        this.velocity = readVector2f();
        this.rotation = buffer.getFloat();
    }

    @Override
    public ByteBuffer writeData(ByteBuffer buffer) {
        super.writeData(buffer);
        buffer.putInt(gameObjectId);
        writeVector2f(position);
        writeVector2f(velocity);
        buffer.putFloat(rotation);
        return buffer;
    }

    public int getGameObjectId() {
        return gameObjectId;
    }

    public UpdateGameObjectPacket setGameObjectId(int gameObjectId) {
        this.gameObjectId = gameObjectId;
        return this;
    }

    public Vector2f getPosition() {
        return position;
    }

    public UpdateGameObjectPacket setPosition(Vector2f position) {
        this.position = position;
        return this;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public UpdateGameObjectPacket setVelocity(Vector2f velocity) {
        this.velocity = velocity;
        return this;
    }

    public float getRotation() {
        return rotation;
    }

    public UpdateGameObjectPacket setRotation(float rotation) {
        this.rotation = rotation;
        return this;
    }

    @Override
    public String toString() {
        return "UpdateGameObjectPacket{" +
                "gameObjectId=" + gameObjectId +
                ", position=" + position +
                ", velocity=" + velocity +
                ", rotation=" + rotation +
                ", id=" + id +
                '}';
    }
}
