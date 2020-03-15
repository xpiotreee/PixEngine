package com.piotreee.game.packets;

import com.piotreee.pixengine.networking.Packet;
import com.piotreee.pixengine.objects.tilemap.Tile;
import org.joml.Vector2i;

import java.nio.ByteBuffer;

public class SetTilePacket extends Packet {
    private int type;
    private Vector2i position;

    public SetTilePacket(byte[] data) {
        super(data);
    }

    public SetTilePacket(int type, Vector2i position) {
        this.id = 6;
        this.type = type;
        this.position = position;
    }

    public SetTilePacket(Tile tile) {
        this(tile.getType(), tile.getPosition());
    }

    @Override
    public ByteBuffer writeData(ByteBuffer buffer) {
        super.writeData(buffer);
        buffer.putInt(type);
        writeVector2i(position);
        return buffer;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        super.readData(buffer);
        this.type = buffer.getInt();
        this.position = readVector2i(buffer);
    }

    public int getType() {
        return type;
    }

    public SetTilePacket setType(int type) {
        this.type = type;
        return this;
    }

    public Vector2i getPosition() {
        return position;
    }

    public SetTilePacket setPosition(Vector2i position) {
        this.position = position;
        return this;
    }
}
