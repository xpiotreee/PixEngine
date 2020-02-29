package com.piotreee.game.packets;

import com.piotreee.pixengine.networking.Packet;

import java.nio.ByteBuffer;

public class SetPlayerPacket extends Packet {
    private int gameObjectId;

    public SetPlayerPacket(int gameObjectId) {
        this.id = 4;
        this.gameObjectId = gameObjectId;
    }

    public SetPlayerPacket() {
        this.id = 4;
    }

    public SetPlayerPacket(byte[] data) {
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

    public SetPlayerPacket setGameObjectId(int gameObjectId) {
        this.gameObjectId = gameObjectId;
        return this;
    }
}
