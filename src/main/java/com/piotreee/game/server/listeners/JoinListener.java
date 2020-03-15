package com.piotreee.game.server.listeners;

import com.piotreee.game.objects.Player;
import com.piotreee.game.packets.*;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.networking.PacketListener;
import com.piotreee.pixengine.objects.tilemap.Chunk;
import com.piotreee.pixengine.objects.tilemap.Tile;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.Optional;

public class JoinListener extends PacketListener<JoinPacket> {
    private GameServer server;

    public JoinListener(GameServer gameServer) {
        super(JoinPacket.class);
        this.server = gameServer;
    }

    @Override
    public void on(ChannelHandlerContext ctx, JoinPacket packet) throws Exception {
        Player player = new Player(GameServer.IDs++, packet.getUsername());
        Channel channel = ctx.channel();
        server.getPlayers().put(channel, player);
        for (int i = 0; i < server.getGameObjectsSize(); i++) {
            ctx.writeAndFlush(new AddGameObjectPacket(server.getGameObjects().get(i)).getData());
        }

        server.addGameObject(player);
        server.sendAll(new AddGameObjectPacket(player));

        Optional[] chunks = server.getTileMap().getChunks(-2, -2, 2, 2);
        for (int i = 0; i < chunks.length; i++) {
            ((Optional<Chunk>) chunks[i]).ifPresent(chunk -> {
                Tile[][] tiles = chunk.getTiles();
                for (int x = 0; x < tiles.length; x++) {
                    for (int y = 0; y < tiles[x].length; y++) {
                        ctx.writeAndFlush(new SetTilePacket(tiles[x][y]).writeData().getData());
                    }
                }
            });
        }

        ctx.writeAndFlush(new SetPlayerPacket(player.getId()).writeData().getData());
        server.sendAll(new ConnectedCountPacket(server.getHandler().getChannelsSize()));
    }
}
