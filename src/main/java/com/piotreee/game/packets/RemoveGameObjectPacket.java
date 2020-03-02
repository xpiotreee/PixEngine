package com.piotreee.game.packets;

import com.piotreee.pixengine.networking.Packet;

import java.nio.ByteBuffer;

public class RemoveGameObjectPacket extends Packet {
    private int gameObjectId;

    public RemoveGameObjectPacket(int gameObjectId) {
        this.id = 5;
        this.gameObjectId = gameObjectId;
    }

    public RemoveGameObjectPacket() {
        this.id = 5;
    }

    public RemoveGameObjectPacket(byte[] data) {
        super(data);
    }

    @Override
    public ByteBuffer writeData(ByteBuffer buffer) {
        super.writeData(buffer);
        buffer.putInt(gameObjectId);
        return buffer;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        this.gameObjectId = buffer.getInt();
    }

    public int getGameObjectId() {
        return gameObjectId;
    }

    public RemoveGameObjectPacket setGameObjectId(int gameObjectId) {
        this.gameObjectId = gameObjectId;
        return this;
    }
}
