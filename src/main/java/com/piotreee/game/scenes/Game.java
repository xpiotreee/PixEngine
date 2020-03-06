package com.piotreee.game.scenes;

import com.piotreee.game.client.GameClient;
import com.piotreee.game.objects.Player;
import com.piotreee.game.objects.TestGameObject;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.gui.Alignment;
import com.piotreee.pixengine.gui.Text;
import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.objects.GameScene;
import com.piotreee.pixengine.objects.Tile;
import com.piotreee.pixengine.objects.Updatable;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class Game extends GameScene {
    public static boolean isSingleplayer = true;
    public static String host;
    public static int port;

    private GameServer server;
    private GameClient client;

    private Text connectedCount;

    private List<Tile> tiles = new ArrayList<>();
    private int tilesSize = 0;

    public Game(Window window) {
        super(window);
    }

    @Override
    public void update(double delta, Input input) {
        super.update(delta, input);
        client.update(delta, input);
    }

    @Override
    public void render(Matrix4f view) {
        shader.bind();
        for (int i = 0; i < tilesSize; i++) {
            tiles.get(i).render(shader, camera, view);
        }

        super.render(view);
    }

    @Override
    public void load() {
        if (isSingleplayer) {
            new Thread(server = new GameServer(2137)).start();
            host = "localhost";
            port = 2137;
        }

        new Thread(client = new GameClient(host, port, this)).start();

        gui.add(connectedCount = new Text("Connected: ", Alignment.LEFT, 16, 0, 128, 32));
        gui.add(new Text("Connected to: " + host + ":" + port, Alignment.BOTTOM_LEFT, 16, 16, 128, 32));
    }

    @Override
    public void unload() {
        client.stop();
        if (isSingleplayer) {
            server.stop();
        }
    }

    public void remove(int id) {
        super.remove(getGameObject(id));
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
        tilesSize++;
    }

    public void removeTile(Tile tile) {
        tiles.remove(tile);
        tilesSize--;
    }

    public Text getConnectedCount() {
        return connectedCount;
    }

    private TestGameObject getGameObject(int id) {
        for (int i = 0; i < updatablesSize; i++) {
            Updatable updatable = updatables.get(i);
            if (updatable instanceof TestGameObject) {
                TestGameObject gameObject = (TestGameObject) updatable;
                if (gameObject.getId() == id) {
                    return gameObject;
                }
            }
        }

        return null;
    }

    public void setPlayer(int id) {
        client.setPlayer((Player) getGameObject(id));
    }
}
