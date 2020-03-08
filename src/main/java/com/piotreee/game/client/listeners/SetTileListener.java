package com.piotreee.game.client.listeners;

import com.piotreee.game.Main;
import com.piotreee.game.packets.SetTilePacket;
import com.piotreee.game.scenes.Game;
import com.piotreee.pixengine.networking.PacketListener;
import io.netty.channel.ChannelHandlerContext;

public class SetTileListener extends PacketListener<SetTilePacket> {
    private Game game;

    public SetTileListener(Game game) {
        super(SetTilePacket.class);
        this.game = game;
    }

    @Override
    public void on(ChannelHandlerContext ctx, SetTilePacket packet) throws Exception {
        game.getTileMap().setTile(Main.tileRegistry.get(packet.getType()).getConstructor(SetTilePacket.class).newInstance(packet));
    }
}
