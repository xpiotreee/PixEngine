package com.piotreee.game.packets;

import com.piotreee.pixengine.networking.Packet;
import org.joml.Vector2i;

import java.nio.ByteBuffer;

public class ChunkPacket extends Packet {
    private Vector2i position;

    public ChunkPacket(byte[] data) {
        super(data);
    }

    public ChunkPacket() {
        this.id = 9;
    }

    public ChunkPacket(Vector2i position) {
        this.id = 9;
        this.position = position;
    }

    @Override
    public ByteBuffer writeData(ByteBuffer buffer) {
        super.writeData(buffer);
        writeVector2i(position);
        return buffer;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        this.position = readVector2i(buffer);
    }

    public Vector2i getPosition() {
        return position;
    }

    public ChunkPacket setPosition(Vector2i position) {
        this.position = position;
        return this;
    }
}
