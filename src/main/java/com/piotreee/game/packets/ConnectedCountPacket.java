package com.piotreee.game.packets;

import com.piotreee.pixengine.networking.Packet;

import java.nio.ByteBuffer;

public class ConnectedCountPacket extends Packet {
    private int connectedCount;

    public ConnectedCountPacket(int connectedCount) {
        this.id = 0;
        this.connectedCount = connectedCount;
    }

    public ConnectedCountPacket(byte[] data) {
        super(data);
    }

    @Override
    public ByteBuffer writeData(ByteBuffer buffer) {
        super.writeData(buffer);
        buffer.putInt(connectedCount);
        return buffer;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        this.connectedCount = buffer.getInt();
    }

    public int getConnectedCount() {
        return connectedCount;
    }

    public void setConnectedCount(int connectedCount) {
        this.connectedCount = connectedCount;
    }
}
