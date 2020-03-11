package com.piotreee.game.packets;

import com.piotreee.pixengine.networking.Packet;
import org.joml.Vector2i;

import java.nio.ByteBuffer;

public class TileActionPacket extends Packet {
    private byte action;
    private Vector2i position;

    public TileActionPacket(byte[] data) {
        super(data);
    }

    public TileActionPacket() {
        this.id = 7;
    }

    public TileActionPacket(byte action, Vector2i position) {
        this.id = 7;
        this.action = action;
        this.position = position;
    }

    @Override
    public ByteBuffer writeData(ByteBuffer buffer) {
        super.writeData(buffer);
        buffer.put(action);
        writeVector2i(position);
        return buffer;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        this.action = buffer.get();
        this.position = readVector2i(buffer);
    }

    public byte getAction() {
        return action;
    }

    public TileActionPacket setAction(byte action) {
        this.action = action;
        return this;
    }

    public Vector2i getPosition() {
        return position;
    }

    public TileActionPacket setPosition(Vector2i position) {
        this.position = position;
        return this;
    }
}
