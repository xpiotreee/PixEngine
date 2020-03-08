package com.piotreee.pixengine.networking;

import org.joml.Vector2f;
import org.joml.Vector2i;

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

    protected void writeVector2f(ByteBuffer buffer, Vector2f vector2f) {
        buffer.putFloat(vector2f.x);
        buffer.putFloat(vector2f.y);
    }

    protected Vector2f readVector2f(ByteBuffer buffer) {
        return new Vector2f(buffer.getFloat(), buffer.getFloat());
    }

    protected void writeVector2i(ByteBuffer buffer, Vector2i vector2i) {
        buffer.putInt(vector2i.x);
        buffer.putInt(vector2i.y);
    }

    protected Vector2i readVector2i(ByteBuffer buffer) {
        return new Vector2i(buffer.getInt(), buffer.getInt());
    }

    public int getId() {
        return id;
    }
}
