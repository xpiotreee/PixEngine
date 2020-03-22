package com.piotreee.game.server.listeners;

import com.piotreee.game.objects.Player;
import com.piotreee.game.packets.AddGameObjectPacket;
import com.piotreee.game.packets.ConnectedCountPacket;
import com.piotreee.game.packets.JoinPacket;
import com.piotreee.game.packets.SetPlayerPacket;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.networking.PacketListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

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

//        Chunk[] chunks = server.getTileMap().getChunks();
//        for (int i = 0; i < chunks.length; i++) {
//            Tile[][] tiles = chunks[i].getTiles();
//            for (int x = 0; x < tiles.length; x++) {
//                for (int y = 0; y < tiles[x].length; y++) {
//                    ctx.writeAndFlush(new SetTilePacket(tiles[x][y]).writeData().getData());
//                }
//            }
//        }

        ctx.writeAndFlush(new SetPlayerPacket(player.getId()).writeData().getData());
        server.sendAll(new ConnectedCountPacket(server.getHandler().getChannelsSize()));
    }
}
