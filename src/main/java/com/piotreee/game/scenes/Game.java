package com.piotreee.game.scenes;

import com.piotreee.game.client.GameClient;
import com.piotreee.game.objects.Player;
import com.piotreee.game.objects.TestGameObject;
import com.piotreee.game.objects.tiles.Bricks;
import com.piotreee.game.objects.tiles.Dirt;
import com.piotreee.game.objects.tiles.Grass;
import com.piotreee.game.packets.ChunkPacket;
import com.piotreee.game.packets.TileActionPacket;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.gui.Alignment;
import com.piotreee.pixengine.gui.Text;
import com.piotreee.pixengine.gui.TextAlignment;
import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.objects.GameScene;
import com.piotreee.pixengine.objects.Updatable;
import com.piotreee.pixengine.objects.tilemap.Chunk;
import com.piotreee.pixengine.objects.tilemap.TileMap;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.Optional;

import static org.lwjgl.glfw.GLFW.*;

public class Game extends GameScene {
    public static Game INSTANCE;
    public static boolean isSingleplayer;
    public static String host;
    public static int port;
    public static String username;

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
        if (client.getPlayer() == null) {
            return;
        }

        Vector2f position = client.getPlayer().getTransform().position;
        int x = Math.round(position.x / 8f);
        int y = Math.round(position.y / 8f);

        Optional[] chunks = tileMap.getChunks(x - 3, y - 3, x + 3, y + 3);

        for (int i = 0; i < chunks.length; i++) {
            ((Optional<Chunk>) chunks[i]).ifPresentOrElse(
                    chunk -> chunk.render(shader, camera, view),
                    () -> client.send(new ChunkPacket(new Vector2i()))
            );
        }

        super.render(view);
    }

    @Override
    public void load() {
        if (isSingleplayer) {
            new Thread(server = new GameServer(2137)).start();
        }

        new Thread(client = new GameClient(host, port, this)).start();

        gui.add(connectedCount = new Text(TextAlignment.LEFT, "Connected: ", Alignment.LEFT, 16, 0));
        gui.add(new Text(TextAlignment.LEFT, "Connected to: " + host + ":" + port, Alignment.BOTTOM_LEFT, 16, 16));
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
