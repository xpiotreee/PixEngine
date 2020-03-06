package com.piotreee.game.client.listeners;

import com.piotreee.game.Main;
import com.piotreee.game.packets.AddGameObjectPacket;
import com.piotreee.game.scenes.Game;
import com.piotreee.pixengine.networking.PacketListener;
import io.netty.channel.ChannelHandlerContext;

public class AddGameObjectListener extends PacketListener<AddGameObjectPacket> {
    private Game game;

    public AddGameObjectListener(Game game) {
        super(AddGameObjectPacket.class);
        this.game = game;
    }

    @Override
    public void on(ChannelHandlerContext ctx, AddGameObjectPacket packet) throws Exception {
        System.out.println(packet.toString());
        game.add(Main.objectRegistry.get(packet.getType()).getConstructor(AddGameObjectPacket.class).newInstance(packet));
    }
}
