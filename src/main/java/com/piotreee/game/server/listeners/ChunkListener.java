package com.piotreee.game.server.listeners;

import com.piotreee.game.packets.ChunkPacket;
import com.piotreee.game.packets.SetTilePacket;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.networking.PacketListener;
import com.piotreee.pixengine.objects.tilemap.Chunk;
import com.piotreee.pixengine.objects.tilemap.Tile;
import io.netty.channel.ChannelHandlerContext;

import java.util.Optional;

public class ChunkListener extends PacketListener<ChunkPacket> {
    private GameServer server;

    public ChunkListener(GameServer server) {
        super(ChunkPacket.class);
        this.server = server;
    }

    @Override
    public void on(ChannelHandlerContext ctx, ChunkPacket packet) throws Exception {
        Optional<Chunk> optional = server.getTileMap().getChunk(packet.getPosition());
        Chunk chunk;
        if (optional.isEmpty()) {
            chunk = server.getTileMap().genChunk(packet.getPosition());
        } else {
            chunk = optional.get();
        }

        Tile[][] tiles = chunk.getTiles();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (tiles[x][y] != null) {
                    ctx.writeAndFlush(new SetTilePacket(tiles[x][y]).writeData().getData());
                }
            }
        }
    }
}
