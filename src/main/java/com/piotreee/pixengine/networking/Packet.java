package com.piotreee.pixengine.networking;

import java.nio.ByteBuffer;

public class Packet {
    protected int id = -1;

    public Packet(byte[] data) {
        readData(data);
    }

    public Packet() {
    }

    public byte[] writeData() {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[500]);
        return writeData(buffer).array();
    }

    public ByteBuffer writeData(ByteBuffer buffer) {
        buffer.putInt(id);
        return buffer;
    }

    public void readData(byte[] bytes) {
        readData(ByteBuffer.wrap(bytes));
    }

    public void readData(ByteBuffer buffer) {
        this.id = buffer.getInt();
    }


    public int getId() {
        return id;
    }
}
