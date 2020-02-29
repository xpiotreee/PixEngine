package com.piotreee.game.server;

import com.piotreee.game.objects.server.ServerGameObject;
import com.piotreee.game.packets.AddGameObjectPacket;
import com.piotreee.game.packets.UpdateGameObjectPacket;
import com.piotreee.pixengine.networking.PacketListener;
import com.piotreee.pixengine.networking.Server;
import com.piotreee.pixengine.objects.Transform;
import io.netty.channel.ChannelHandlerContext;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GameServer extends Server {
    private static int IDs = 0;
    private List<ServerGameObject> gameObjects = new ArrayList<>();
    private int gameObjectsSize = 0;

    private boolean running = true;

    public GameServer(int port) {
        super(port);
        gameObjects.add(new ServerGameObject(IDs++, new Transform(new Vector2f()), new Vector2f()));
        gameObjectsSize++;
        gameObjects.add(new ServerGameObject(IDs++, new Transform(new Vector2f(3, -1), new Vector2f(2, 1), 45), new Vector2f()));
        gameObjectsSize++;

        addListeners(new PacketListener<Object>(Object.class) {
            @Override
            public void active(ChannelHandlerContext ctx) {
                for (int i = 0; i < gameObjectsSize; i++) {
                    ctx.writeAndFlush(new AddGameObjectPacket(gameObjects.get(i)).writeData());
                }
            }
        });

    }

    public void run() {
        runnable = () -> {
            double ns = 1000000000.0 / 20.0;
            double delta = 0;

            long lastTime = System.nanoTime();

            while (running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;

                while (delta >= 1) {
                    update(delta);
                    delta--;
                }
            }
        };

        super.run();
    }

    private void update(double delta) {
        gameObjects.get(0).getTransform().rotation += 0.01f * delta;
        gameObjects.get(1).getTransform().rotation -= 0.01f * delta;

        for (int i = 0; i < gameObjectsSize; i++) {
            ServerGameObject gameObject = gameObjects.get(i);
            gameObject.update(delta, null);
            sendAll(new UpdateGameObjectPacket(gameObject));
        }
    }

    public void stop() {
        running = false;
        super.stop();
    }

}
