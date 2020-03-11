package com.piotreee.pixengine.networking;

import org.joml.Vector2f;
import org.joml.Vector2i;

import java.nio.ByteBuffer;

public class Packet {
    private ByteBuffer byteBuffer;
    protected int id = -1;

    public Packet(byte[] data) {
        readData(data);
    }

    public Packet() {
    }

    public Packet writeData() {
        writeData(byteBuffer = ByteBuffer.wrap(new byte[500]));
        return this;
    }

    public ByteBuffer writeData(ByteBuffer buffer) {
        buffer.putInt(id);
        return buffer;
    }

    public void readData(byte[] bytes) {
        readData(byteBuffer = ByteBuffer.wrap(bytes));
    }

    public void readData(ByteBuffer buffer) {
        this.id = buffer.getInt();
    }

    public void writeVector2f(Vector2f vector2f) {
        byteBuffer.putFloat(vector2f.x);
        byteBuffer.putFloat(vector2f.y);
    }

    public Vector2f readVector2f() {
        return new Vector2f(byteBuffer.getFloat(), byteBuffer.getFloat());
    }

    public void writeVector2i(Vector2i vector2i) {
        byteBuffer.putInt(vector2i.x);
        byteBuffer.putInt(vector2i.y);
    }

    public Vector2i readVector2i(ByteBuffer buffer) {
        return new Vector2i(buffer.getInt(), buffer.getInt());
    }

    public void writeString(String str) {
        char[] chars = str.toCharArray();
        byteBuffer.putInt(chars.length);
        for (int i = 0; i < chars.length; i++) {
            byteBuffer.putChar(chars[i]);
        }
    }

    public String readString() {
        int length = byteBuffer.getInt();
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = byteBuffer.getChar();
        }

        return String.valueOf(chars);
    }

    public int getId() {
        return id;
    }

    public byte[] getData() {
        if (byteBuffer == null) {
            writeData();
        }

        return byteBuffer.array();
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }
}
