package com.piotreee.game.server.listeners;

import com.piotreee.game.packets.InputPacket;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.networking.PacketListener;
import io.netty.channel.ChannelHandlerContext;

public class InputListener extends PacketListener<InputPacket> {
    private GameServer server;

    public InputListener(GameServer server) {
        super(InputPacket.class);
        this.server = server;
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
