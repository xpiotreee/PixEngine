package com.piotreee.game.packets;

import com.piotreee.game.objects.TestGameObject;
import com.piotreee.pixengine.objects.Transform;
import org.joml.Vector2f;

import java.nio.ByteBuffer;

public class AddGameObjectPacket extends UpdateGameObjectPacket {
    private Vector2f scale;
    private int type;

    public AddGameObjectPacket(TestGameObject gameObject) {
        this(gameObject.getId(), gameObject.getTransform(), new Vector2f(), gameObject.getType());
        gameObject.getRigidbody().ifPresent(rb -> velocity.set(rb.getVelocity()));
        gameObject.createPacket((AddGameObjectPacket) this.writeData());
    }

    public AddGameObjectPacket(int id, Transform transform, Vector2f velocity, int type) {
        this(id, transform.position, velocity,
                transform.getRotation(), transform.scale, type);
    }

    public AddGameObjectPacket(int id, Vector2f position, Vector2f velocity, float rotation, Vector2f scale, int type) {
        this.id = 2;
        this.gameObjectId = id;
        this.position = position;
        this.velocity = velocity;
        this.rotation = rotation;
        this.scale = scale;
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
        writeVector2f(scale);
        buffer.putInt(type);
        return buffer;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        scale = readVector2f();
        this.type = buffer.getInt();
    }

    public Vector2f getScale() {
        return scale;
    }

    public AddGameObjectPacket setScale(Vector2f scale) {
        this.scale = scale;
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
                "scale=" + scale +
                ", gameObjectId=" + gameObjectId +
                ", position=" + position +
                ", velocity=" + velocity +
                ", rotation=" + rotation +
                ", id=" + id +
                ", type=" + type +
                '}';
    }
}
