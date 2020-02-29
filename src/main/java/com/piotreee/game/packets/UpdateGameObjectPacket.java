package com.piotreee.game.packets;

import com.piotreee.game.objects.server.ServerGameObject;
import com.piotreee.pixengine.networking.Packet;
import org.joml.Vector2f;

import java.nio.ByteBuffer;

public class UpdateGameObjectPacket extends Packet {
    protected int gameObjectId;
    protected float posX;
    protected float posY;
    protected float velX;
    protected float velY;
    protected float rotation;

    public UpdateGameObjectPacket(ServerGameObject gameObject) {
        this.id = 1;
        this.gameObjectId = gameObject.getId();
        Vector2f position = gameObject.getTransform().position;
        this.posX = position.x;
        this.posY = position.y;
        Vector2f velocity = gameObject.getVelocity();
        this.velX = velocity.x;
        this.velY = velocity.y;
        this.rotation = gameObject.getTransform().rotation;
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
        this.posX = buffer.getFloat();
        this.posY = buffer.getFloat();
        this.velX = buffer.getFloat();
        this.velY = buffer.getFloat();
        this.rotation = buffer.getFloat();
    }

    @Override
    public ByteBuffer writeData(ByteBuffer buffer) {
        super.writeData(buffer);
        buffer.putInt(gameObjectId);
        buffer.putFloat(posX);
        buffer.putFloat(posY);
        buffer.putFloat(velX);
        buffer.putFloat(velY);
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

    public float getPosX() {
        return posX;
    }

    public UpdateGameObjectPacket setPosX(float posX) {
        this.posX = posX;
        return this;
    }

    public float getPosY() {
        return posY;
    }

    public UpdateGameObjectPacket setPosY(float posY) {
        this.posY = posY;
        return this;
    }

    public float getVelX() {
        return velX;
    }

    public UpdateGameObjectPacket setVelX(float velX) {
        this.velX = velX;
        return this;
    }

    public float getVelY() {
        return velY;
    }

    public UpdateGameObjectPacket setVelY(float velY) {
        this.velY = velY;
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
                ", posX=" + posX +
                ", posY=" + posY +
                ", velX=" + velX +
                ", velY=" + velY +
                ", rotation=" + rotation +
                ", id=" + id +
                '}';
    }
}
