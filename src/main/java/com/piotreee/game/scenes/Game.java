package com.piotreee.game.scenes;

import com.piotreee.game.client.GameClient;
import com.piotreee.game.objects.Player;
import com.piotreee.game.objects.TestGameObject;
import com.piotreee.game.objects.tiles.Bricks;
import com.piotreee.game.objects.tiles.Dirt;
import com.piotreee.game.objects.tiles.Grass;
import com.piotreee.game.packets.TileActionPacket;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.gui.Alignment;
import com.piotreee.pixengine.gui.Text;
import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.objects.GameScene;
import com.piotreee.pixengine.objects.Tile;
import com.piotreee.pixengine.objects.TileMap;
import com.piotreee.pixengine.objects.Updatable;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.util.Optional;

import static org.lwjgl.glfw.GLFW.*;

public class Game extends GameScene {
    public static Game INSTANCE;
    public static boolean isSingleplayer = true;
    public static String host;
    public static int port;

    private GameServer server;
    private GameClient client;

    private Text connectedCount;

    private TileMap tileMap = new TileMap();

    public Game(Window window) {
        super(window);
        INSTANCE = this;
    }

    @Override
    public void update(float delta, Input input) {
        super.update(delta, input);
        if (input.isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            tileMap.getTile(input.getMouseWorldPos()).ifPresent(tile -> {
                if (!(tile instanceof Dirt)) {
                    client.send(new TileActionPacket((byte) GLFW_MOUSE_BUTTON_LEFT, tile.getPosition()));
                }
            });
        }

        if (input.isMouseButtonDown(GLFW_MOUSE_BUTTON_RIGHT)) {
            tileMap.getTile(input.getMouseWorldPos()).ifPresent(tile -> {
                if (!(tile instanceof Bricks)) {
                    client.send(new TileActionPacket((byte) GLFW_MOUSE_BUTTON_RIGHT, tile.getPosition()));
                }
            });
        }

        if (input.isMouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            tileMap.getTile(input.getMouseWorldPos()).ifPresent(tile -> {
                if (!(tile instanceof Grass)) {
                    client.send(new TileActionPacket((byte) GLFW_MOUSE_BUTTON_MIDDLE, tile.getPosition()));
                }
            });
        }

        client.update(delta, input);
    }

    @Override
    public void render(Matrix4f view) {
        shader.bind();
//        Tile[] tiles = tileMap.getTiles();
//        for (int i = 0; i < tiles.length; i++) {
//            tiles[i].render(shader, camera, view);
//        }
        if (client.getPlayer() == null) {
            return;
        }

        Vector2f position = client.getPlayer().getTransform().position;
        int x = Math.round(position.x);
        int y = Math.round(position.y);
        Optional[] tiles = tileMap.getTiles(x - 7, y - 5, x + 8, y + 5);
        for (int i = 0; i < tiles.length; i++) {
            ((Optional<Tile>) tiles[i]).ifPresent(tile -> tile.render(shader, camera, view));
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

    public TileMap getTileMap() {
        return tileMap;
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
