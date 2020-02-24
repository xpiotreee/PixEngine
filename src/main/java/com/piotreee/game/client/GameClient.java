package com.piotreee.game.client;

import com.piotreee.game.objects.TestGameObject;
import com.piotreee.game.packets.UpdatePacket;
import com.piotreee.game.scenes.Game;
import com.piotreee.pixengine.networking.Client;
import com.piotreee.pixengine.networking.PacketListener;
import com.piotreee.pixengine.objects.GameObject;
import com.piotreee.pixengine.objects.Updatable;
import com.piotreee.pixengine.render.Renderable;
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
                List<Updatable> updatables = game.getUpdatables();
                List<Renderable> renderables = game.getRenderables();
                int size = game.getUpdatablesSize();
                List<TestGameObject> gameObjects = updatePacket.getGameObjects();
                int gameObjectsSize = gameObjects.size();
                for (int i = 0; i < gameObjectsSize; i++) {
                    boolean exists = false;
                    for (int j = 0; j < size; j++) {
                        Updatable updatable = updatables.get(j);
                        TestGameObject gameObject = gameObjects.get(i);
                        if (updatable.equals(gameObject)) {
                            exists = true;
                            updatables.set(j, gameObject);
                            int index = renderables.indexOf(gameObject);
                            if (index != -1) {
                                renderables.set(index, gameObject);
                            }
                        }
                    }

                    if (!exists) {
                        game.add(gameObjects.get(i));
                    }
                }
            }
        });
    }
}
