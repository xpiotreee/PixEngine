package com.piotreee.game.client.listeners;

import com.piotreee.game.Main;
import com.piotreee.game.packets.AddTilePacket;
import com.piotreee.game.scenes.Game;
import com.piotreee.pixengine.networking.PacketListener;
import io.netty.channel.ChannelHandlerContext;

public class AddTileListener extends PacketListener<AddTilePacket> {
    private Game game;

    public AddTileListener(Game game) {
        super(AddTilePacket.class);
        this.game = game;
    }

    @Override
    public void on(ChannelHandlerContext ctx, AddTilePacket packet) throws Exception {
        game.addTile(Main.tileRegistry.get(packet.getType()).getConstructor(AddTilePacket.class).newInstance(packet));
    }
}
