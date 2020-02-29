package com.piotreee.game.packets;

import com.piotreee.pixengine.networking.Packet;

import java.nio.ByteBuffer;

public class TestPacket extends Packet {
    private int test;

    public TestPacket() {
        this.id = 0;
    }

    public TestPacket(byte[] data) {
        super(data);
    }

    @Override
    public ByteBuffer writeData(ByteBuffer buffer) {
        super.writeData(buffer);
        buffer.putInt(test);
        return buffer;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        this.test = buffer.getInt();
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }
}
