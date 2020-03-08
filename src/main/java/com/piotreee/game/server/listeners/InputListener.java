package com.piotreee.game.server.listeners;

import com.piotreee.game.objects.Player;
import com.piotreee.game.packets.*;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.networking.PacketListener;
import com.piotreee.pixengine.objects.Tile;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class InputListener extends PacketListener<InputPacket> {
    private GameServer server;

    public InputListener(GameServer server) {
        super(InputPacket.class);
        this.server = server;
    }

    @Override
    public void active(ChannelHandlerContext ctx) {
        Player player = new Player(GameServer.IDs++);
        Channel channel = ctx.channel();
        server.getPlayers().put(channel, player);
        for (int i = 0; i < server.getGameObjectsSize(); i++) {
            ctx.writeAndFlush(new AddGameObjectPacket(server.getGameObjects().get(i)).writeData());
        }

        server.addGameObject(player);
        server.sendAll(new AddGameObjectPacket(player));

        Tile[] tiles = server.getTileMap().getTiles();
        for (int i = 0; i < tiles.length; i++) {
            ctx.writeAndFlush(new SetTilePacket(tiles[i]).writeData());
        }

        ctx.writeAndFlush(new SetPlayerPacket(player.getId()).writeData());
        server.sendAll(new ConnectedCountPacket(server.getHandler().getChannelsSize()));
    }

    @Override
    public void inActive(ChannelHandlerContext ctx) {
        server.removeObject(server.getPlayers().get(ctx.channel()));
    }

    @Override
    public void on(ChannelHandlerContext ctx, InputPacket packet) {
        server.getPlayers().get(ctx.channel()).setInput(packet);
    }
}
