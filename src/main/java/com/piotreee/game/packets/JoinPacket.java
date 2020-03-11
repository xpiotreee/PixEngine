package com.piotreee.game.packets;

import com.piotreee.pixengine.networking.Packet;

import java.nio.ByteBuffer;

public class JoinPacket extends Packet {
    private String username;

    public JoinPacket(byte[] data) {
        super(data);
    }

    public JoinPacket() {
        this.id = 8;
    }

    public JoinPacket(String username) {
        this.id = 8;
        this.username = username;
    }

    @Override
    public ByteBuffer writeData(ByteBuffer buffer) {
        super.writeData(buffer);
        writeString(username);
        return buffer;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        this.username = readString();
    }

    public String getUsername() {
        return username;
    }

    public JoinPacket setUsername(String username) {
        this.username = username;
        return this;
    }
}
