package com.piotreee.game.scenes;

import com.piotreee.game.client.GameClient;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.LwjglApplication;
import com.piotreee.pixengine.gui.Alignment;
import com.piotreee.pixengine.gui.Text;
import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.objects.GameScene;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;

public class Game extends GameScene {
    public static boolean isSingleplayer = true;
    public static String host;
    public static int port;

    private GameServer server;
    private GameClient client;

    private Text date;
    private Text connectedCount;

    public Game(Window window) {
        super(window);
    }

    @Override
    public void update(double delta, Input input) {
        super.update(delta, input);
        if (input.isKeyPressed(GLFW_KEY_TAB)) {
            LwjglApplication.INSTANCE.loadScene(MainMenu.class);
        }

        if (input.isKeyPressed(GLFW_KEY_SPACE)) {
            client.send("papierz 2137");
        }
    }

    @Override
    public void load() {
        if (isSingleplayer) {
            new Thread(server = new GameServer(2137)).start();
            host = "localhost";
            port = 2137;
        }

        new Thread(client = new GameClient(host, port, this)).start();

        gui.add(date = new Text("", Alignment.TOP_LEFT, 16, -16, 128, 32));
        gui.add(connectedCount = new Text("", Alignment.LEFT, 16, 0, 128, 32));
        gui.add(new Text(host + ":" + port, Alignment.BOTTOM_LEFT, 16, 16, 128, 32));
    }

    @Override
    public void unload() {
        client.stop();
        if (isSingleplayer) {
            server.stop();
        }
    }

    public Text getDate() {
        return date;
    }

    public Text getConnectedCount() {
        return connectedCount;
    }

}
