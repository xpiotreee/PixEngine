package com.piotreee.game.packets;

import com.piotreee.pixengine.networking.Packet;
import com.piotreee.pixengine.objects.Tile;

import java.nio.ByteBuffer;

public class AddTilePacket extends Packet {
    private int type;
    private int x;
    private int y;

    public AddTilePacket(byte[] data) {
        super(data);
    }

    public AddTilePacket(Tile tile) {
        this(tile.getType(), tile.getX(), tile.getY());
    }

    public AddTilePacket(int type, int x, int y) {
        this.id = 6;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    @Override
    public ByteBuffer writeData(ByteBuffer buffer) {
        super.writeData(buffer);
        buffer.putInt(type);
        buffer.putInt(x);
        buffer.putInt(y);
        return buffer;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        this.type = buffer.getInt();
        this.x = buffer.getInt();
        this.y = buffer.getInt();
    }

    public int getType() {
        return type;
    }

    public AddTilePacket setType(int type) {
        this.type = type;
        return this;
    }

    public int getX() {
        return x;
    }

    public AddTilePacket setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public AddTilePacket setY(int y) {
        this.y = y;
        return this;
    }
}
