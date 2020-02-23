package com.piotreee.game.client;

import com.piotreee.game.packets.UpdatePacket;
import com.piotreee.game.scenes.Game;
import com.piotreee.pixengine.networking.Client;
import com.piotreee.pixengine.networking.PacketListener;
import com.piotreee.pixengine.objects.GameObject;
import com.piotreee.pixengine.objects.Updatable;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class GameClient extends Client {

    public GameClient(String host, int port, Game game) {
        super(host, port);
        addListeners(new PacketListener<UpdatePacket>(UpdatePacket.class) {
            @Override
            public void on(ChannelHandlerContext ctx, UpdatePacket updatePacket) {
                game.getDate().setText(updatePacket.getDate());
                game.getConnectedCount().setText("Connected: " + updatePacket.getConnected());
//                List<Updatable> updatables = game.getUpdatables();
//                int size = game.getUpdatablesSize();
//                GameObject[] gameObjects = updatePacket.getGameObjects();
//                for (int i = 0; i < gameObjects.length; i++) {
//                    boolean exists = false;
//                    for (int j = 0; j < size; j++) {
//                        Updatable updatable = updatables.get(j);
//                        if (updatable.equals(gameObjects[i])) {
//                            exists = true;
//                            updatables.set(j, gameObjects[i]);
//                        }
//                    }
//
//                    if (!exists) {
//                        game.add(gameObjects[i]);
//                    }
//                }
            }
        });
    }
}
