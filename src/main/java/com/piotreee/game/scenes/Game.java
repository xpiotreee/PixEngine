package com.piotreee.game.scenes;

import com.piotreee.game.client.GameClient;
import com.piotreee.game.objects.client.ClientGameObject;
import com.piotreee.game.objects.server.ServerGameObject;
import com.piotreee.game.packets.InputPacket;
import com.piotreee.game.packets.RemoveGameObjectPacket;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.LwjglApplication;
import com.piotreee.pixengine.gui.Alignment;
import com.piotreee.pixengine.gui.Text;
import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.objects.GameScene;
import com.piotreee.pixengine.objects.Updatable;

import static org.lwjgl.glfw.GLFW.*;

public class Game extends GameScene {
    public static boolean isSingleplayer = true;
    public static String host;
    public static int port;

    private GameServer server;
    private GameClient client;

    private Text date;
    private Text connectedCount;

    private ClientGameObject player;
    private float speed = 0.015f;

    public Game(Window window) {
        super(window);
    }

    @Override
    public void update(double delta, Input input) {
        super.update(delta, input);
        if (input.isKeyPressed(GLFW_KEY_TAB)) {
            LwjglApplication.INSTANCE.loadScene(MainMenu.class);
        }

        if (player == null) {
            return;
        }

        float fixedSpeed = speed * (float) delta;
        byte moveHorizontally = 0;
        byte moveVertically = 0;

        if (input.isKeyDown(GLFW_KEY_A) && !input.isKeyDown(GLFW_KEY_D)) {
            moveHorizontally = -1;
            player.getVelocity().add(-fixedSpeed, 0);
        } else if (input.isKeyDown(GLFW_KEY_D) && !input.isKeyDown(GLFW_KEY_A)) {
            moveHorizontally = 1;
            player.getVelocity().add(fixedSpeed, 0);
        }

        if (input.isKeyDown(GLFW_KEY_S) && !input.isKeyDown(GLFW_KEY_W)) {
            moveVertically = -1;
            player.getVelocity().add(0, -fixedSpeed);
        } else if (input.isKeyDown(GLFW_KEY_W) && !input.isKeyDown(GLFW_KEY_S)) {
            moveVertically = 1;
            player.getVelocity().add(0, fixedSpeed);
        }


        client.send(new InputPacket(moveHorizontally, moveVertically));
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

    public void setPlayer(ClientGameObject player) {
        this.player = player;
    }
}
