package com.piotreee.game.server;

import com.piotreee.game.objects.TestGameObject;
import com.piotreee.game.packets.UpdatePacket;
import com.piotreee.pixengine.networking.Server;
import com.piotreee.pixengine.objects.GameObject;
import com.piotreee.pixengine.objects.Transform;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GameServer extends Server {
    private List<TestGameObject> gameObjects = new ArrayList<>();
    private int gameObjectsSize = 0;

    private boolean running = true;

    public GameServer(int port) {
        super(port);
        gameObjects.add(new TestGameObject(new Transform(), new Vector2f()));
        gameObjectsSize++;
        gameObjects.add(new TestGameObject(new Transform(new Vector2f(2, 0)), new Vector2f()));
        gameObjectsSize++;
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
        gameObjects.get(0).getTransform().rotation += 0.01f;
        gameObjects.get(1).getTransform().rotation -= 0.01f;

        sendAll(new UpdatePacket(new Date().toString(), String.valueOf(handler.getChannelsSize()), gameObjects));
    }

    public void stop() {
        running = false;
        super.stop();
    }
}
