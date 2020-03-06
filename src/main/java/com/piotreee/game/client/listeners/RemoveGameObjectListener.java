package com.piotreee.game.client.listeners;

import com.piotreee.game.packets.RemoveGameObjectPacket;
import com.piotreee.game.scenes.Game;
import com.piotreee.pixengine.networking.PacketListener;
import io.netty.channel.ChannelHandlerContext;

public class RemoveGameObjectListener extends PacketListener<RemoveGameObjectPacket> {
    private Game game;

    public RemoveGameObjectListener(Game game) {
        super(RemoveGameObjectPacket.class);
        this.game = game;
    }

    @Override
    public void on(ChannelHandlerContext ctx, RemoveGameObjectPacket packet) throws Exception {
        game.remove(packet.getGameObjectId());
    }
}
