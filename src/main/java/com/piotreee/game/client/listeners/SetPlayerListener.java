package com.piotreee.game.client.listeners;

import com.piotreee.game.packets.JoinPacket;
import com.piotreee.game.packets.SetPlayerPacket;
import com.piotreee.game.scenes.Game;
import com.piotreee.pixengine.networking.PacketListener;
import io.netty.channel.ChannelHandlerContext;

public class SetPlayerListener extends PacketListener<SetPlayerPacket> {
    private Game game;

    public SetPlayerListener(Game game) {
        super(SetPlayerPacket.class);
        this.game = game;
    }

    @Override
    public void active(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new JoinPacket(Game.username).writeData().getData());
    }

    @Override
    public void on(ChannelHandlerContext ctx, SetPlayerPacket packet) throws Exception {
        game.setPlayer(packet.getGameObjectId());
    }
}
