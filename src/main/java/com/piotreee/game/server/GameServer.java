package com.piotreee.game.server;

import com.piotreee.game.objects.Papierz;
import com.piotreee.game.objects.Player;
import com.piotreee.game.objects.TestGameObject;
import com.piotreee.game.packets.RemoveGameObjectPacket;
import com.piotreee.game.packets.UpdateGameObjectPacket;
import com.piotreee.game.server.listeners.InputListener;
import com.piotreee.pixengine.networking.Server;
import com.piotreee.pixengine.objects.TileMap;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameServer extends Server {
    public static int IDs = 0;
    private TileMap tileMap = new TileMap(16, 16);
    private HashMap<Channel, Player> players = new HashMap<>();
    private List<TestGameObject> gameObjects = new ArrayList<>();
    private int gameObjectsSize = 0;

    private boolean running = true;

    public GameServer(int port) {
        super(port);
        addGameObject(new Papierz(IDs++, 0, 0, 3, 3, 0));
        addGameObject(new Papierz(IDs++, 3, -1, 2, 1, 45));
        addListeners(new InputListener(this));

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
        gameObjects.get(0).getTransform().rotate(0.01f * (float) delta);
        gameObjects.get(1).getTransform().rotate(-0.01f * (float) delta);

        for (int i = 0; i < gameObjectsSize; i++) {
            TestGameObject gameObject = gameObjects.get(i);
            gameObject.update(delta);
            sendAll(new UpdateGameObjectPacket(gameObject));
        }
    }

    public void removeObject(TestGameObject gameObject) {
        gameObjects.remove(gameObject);
        gameObjectsSize--;
        sendAll(new RemoveGameObjectPacket(gameObject.getId()));
    }

    public void addGameObject(TestGameObject gameObject) {
        gameObjects.add(gameObject);
        gameObjectsSize++;
    }

    public void stop() {
        running = false;
        super.stop();
    }

    public HashMap<Channel, Player> getPlayers() {
        return players;
    }

    public int getGameObjectsSize() {
        return gameObjectsSize;
    }

    public List<TestGameObject> getGameObjects() {
        return gameObjects;
    }

    public TileMap getTileMap() {
        return tileMap;
    }
}
