package com.piotreee.game.client.listeners;

import com.piotreee.game.objects.TestGameObject;
import com.piotreee.game.packets.UpdateGameObjectPacket;
import com.piotreee.game.scenes.Game;
import com.piotreee.pixengine.networking.PacketListener;
import com.piotreee.pixengine.objects.Updatable;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class UpdateGameObjectListener extends PacketListener<UpdateGameObjectPacket> {
    private Game game;

    public UpdateGameObjectListener(Game game) {
        super(UpdateGameObjectPacket.class);
        this.game = game;
    }

    @Override
    public void on(ChannelHandlerContext ctx, UpdateGameObjectPacket packet) throws Exception {
        List<Updatable> updatables = game.getUpdatables();
        int size = game.getUpdatablesSize();
        for (int i = 0; i < size; i++) {
            Updatable updatable = updatables.get(i);
            if (updatable instanceof TestGameObject) {
                TestGameObject gameObject = (TestGameObject) updatable;
                if (gameObject.getId() == packet.getGameObjectId()) {
                    gameObject.updateFromPacket(packet);
                }
            }
        }
    }
}
