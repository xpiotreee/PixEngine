package com.piotreee.game.packets;

import com.piotreee.game.objects.server.ServerGameObject;
import com.piotreee.pixengine.objects.Transform;

import java.nio.ByteBuffer;

public class AddGameObjectPacket extends UpdateGameObjectPacket {
    private float scaleX;
    private float scaleY;
    private String spriteName;

    public AddGameObjectPacket(ServerGameObject gameObject) {
        this(gameObject.getId(), gameObject.getTransform(), gameObject.getVelocity().x, gameObject.getVelocity().y, "test");
    }

    public AddGameObjectPacket(int id, Transform transform, float velX, float velY, String spriteName) {
        this(id, transform.position.x, transform.position.y, velX, velY,
                transform.rotation, transform.scale.x, transform.scale.y, spriteName);
    }

    public AddGameObjectPacket(int id, float posX, float posY, float velX, float velY, float rotation, float scaleX, float scaleY, String spriteName) {
        this.id = 2;
        this.gameObjectId = id;
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.rotation = rotation;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.spriteName = spriteName;
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
        char[] chars = spriteName.toCharArray();
        buffer.putInt(chars.length);
        for (int i = 0; i < chars.length; i++) {
            buffer.putChar(chars[i]);
        }

        return buffer;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        this.scaleX = buffer.getFloat();
        this.scaleY = buffer.getFloat();

        int size = buffer.getInt();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(buffer.getChar());
        }

        this.spriteName = builder.toString();
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

    public String getSpriteName() {
        return spriteName;
    }

    public AddGameObjectPacket setSpriteName(String spriteName) {
        this.spriteName = spriteName;
        return this;
    }

    @Override
    public String toString() {
        return "AddGameObjectPacket{" +
                "scaleX=" + scaleX +
                ", scaleY=" + scaleY +
                ", spriteName='" + spriteName + '\'' +
                ", gameObjectId=" + gameObjectId +
                ", posX=" + posX +
                ", posY=" + posY +
                ", velX=" + velX +
                ", velY=" + velY +
                ", rotation=" + rotation +
                ", id=" + id +
                '}';
    }
}
