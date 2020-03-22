package com.piotreee.game.server;

import com.piotreee.game.objects.Papierz;
import com.piotreee.game.objects.Player;
import com.piotreee.game.objects.TestGameObject;
import com.piotreee.game.objects.map.Biomes;
import com.piotreee.game.packets.RemoveGameObjectPacket;
import com.piotreee.game.packets.UpdateGameObjectPacket;
import com.piotreee.game.server.listeners.ChunkListener;
import com.piotreee.game.server.listeners.InputListener;
import com.piotreee.game.server.listeners.JoinListener;
import com.piotreee.game.server.listeners.TileActionListener;
import com.piotreee.pixengine.networking.Server;
import com.piotreee.pixengine.objects.tilemap.Generator;
import com.piotreee.pixengine.objects.tilemap.TileMap;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameServer extends Server {
    public static GameServer INSTANCE;
    public static int IDs = 0;
    private TileMap tileMap = new TileMap(new Generator(new Biomes()), 6, 6);
    private HashMap<Channel, Player> players = new HashMap<>();
    private List<TestGameObject> gameObjects = new ArrayList<>();
    private int gameObjectsSize = 0;

    private boolean running = true;

    public GameServer(int port) {
        super(port);
        INSTANCE = this;
        addGameObject(new Papierz(IDs++, -5, 0, 3, 3, 0));
        addGameObject(new Papierz(IDs++, 3, -1, 2, 1, 45));
        addListeners(
                new InputListener(this),
                new TileActionListener(this),
                new JoinListener(this),
                new ChunkListener(this)
        );

    }

    public void run() {
        runnable = () -> {
            double frame_cap = 1.0 / 60.0;

            double frame_time = 0;

            double time = System.nanoTime() / (double) 1000000000L;
            double unprocessed = 0;

            while (running) {
                double time_2 = System.nanoTime() / (double) 1000000000L;
                double passed = time_2 - time;
                unprocessed += passed;
                frame_time += passed;

                time = time_2;

                while (unprocessed >= frame_cap) {
                    unprocessed -= frame_cap;
                    update((float) frame_cap);
                    if (frame_time >= 1.0) {
                        frame_time = 0;
                    }
                }
            }
        };

        super.run();
    }

    private void update(float delta) {
        gameObjects.get(0).getTransform().rotate(20f * delta);
        gameObjects.get(1).getTransform().rotate(-10f * delta);

        for (int i = 0; i < gameObjectsSize; i++) {
            TestGameObject gameObject = gameObjects.get(i);
            gameObject.update(delta, true);
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
