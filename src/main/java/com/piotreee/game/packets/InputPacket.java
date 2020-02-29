package com.piotreee.game.packets;

import com.piotreee.pixengine.networking.Packet;

import java.nio.ByteBuffer;

public class InputPacket extends Packet {
    private byte moveHorizontally;
    private byte moveVertically;

    public InputPacket(byte moveHorizontally, byte moveVertically) {
        this.moveHorizontally = moveHorizontally;
        this.moveVertically = moveVertically;
        this.id = 3;
    }

    public InputPacket() {
        this.id = 3;
    }

    public InputPacket(byte[] data) {
        super(data);
    }

    @Override
    public ByteBuffer writeData(ByteBuffer buffer) {
        super.writeData(buffer);
        buffer.put(moveHorizontally);
        buffer.put(moveVertically);
        return buffer;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        this.moveHorizontally = buffer.get();
        this.moveVertically = buffer.get();
    }

    public byte getMoveHorizontally() {
        return moveHorizontally;
    }

    public InputPacket setMoveHorizontally(byte moveHorizontally) {
        this.moveHorizontally = moveHorizontally;
        return this;
    }

    public byte getMoveVertically() {
        return moveVertically;
    }

    public InputPacket setMoveVertically(byte moveVertically) {
        this.moveVertically = moveVertically;
        return this;
    }

    @Override
    public String toString() {
        return "InputPacket{" +
                "moveHorizontally=" + moveHorizontally +
                ", moveVertically=" + moveVertically +
                '}';
    }
}
