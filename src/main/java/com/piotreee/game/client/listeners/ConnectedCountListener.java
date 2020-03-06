package com.piotreee.game.client.listeners;

import com.piotreee.game.packets.ConnectedCountPacket;
import com.piotreee.game.scenes.Game;
import com.piotreee.pixengine.networking.PacketListener;
import io.netty.channel.ChannelHandlerContext;

public class ConnectedCountListener extends PacketListener<ConnectedCountPacket> {
    private Game game;

    public ConnectedCountListener(Game game) {
        super(ConnectedCountPacket.class);
        this.game = game;
    }

    @Override
    public void on(ChannelHandlerContext ctx, ConnectedCountPacket packet) throws Exception {
        game.getConnectedCount().setText("Connected: " + packet.getConnectedCount());
    }
}
