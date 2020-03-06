package com.piotreee.game.client;

import com.piotreee.game.client.listeners.*;
import com.piotreee.game.objects.Player;
import com.piotreee.game.packets.InputPacket;
import com.piotreee.game.scenes.Game;
import com.piotreee.game.scenes.MainMenu;
import com.piotreee.pixengine.LwjglApplication;
import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.networking.Client;

import static org.lwjgl.glfw.GLFW.*;

public class GameClient extends Client {
    private Player player;
    private Game game;

    public GameClient(String host, int port, Game game) {
        super(host, port);
        this.game = game;
        addListeners(
                new ConnectedCountListener(game),
                new UpdateGameObjectListener(game),
                new AddGameObjectListener(game),
                new SetPlayerListener(game),
                new RemoveGameObjectListener(game),
                new AddTileListener(game)
        );
    }

    public void update(double delta, Input input) {
        if (input.isKeyPressed(GLFW_KEY_TAB)) {
            LwjglApplication.INSTANCE.loadScene(MainMenu.class);
        }

        if (player == null) {
            return;
        }

        player.getTransform().position.mul(-LwjglApplication.INSTANCE.getScale(), game.getCamera().getPosition());

        byte moveHorizontally = 0;
        byte moveVertically = 0;

        if (input.isKeyDown(GLFW_KEY_A) && !input.isKeyDown(GLFW_KEY_D)) {
            moveHorizontally = -1;
        } else if (input.isKeyDown(GLFW_KEY_D) && !input.isKeyDown(GLFW_KEY_A)) {
            moveHorizontally = 1;
        }

        if (input.isKeyDown(GLFW_KEY_S) && !input.isKeyDown(GLFW_KEY_W)) {
            moveVertically = -1;
        } else if (input.isKeyDown(GLFW_KEY_W) && !input.isKeyDown(GLFW_KEY_S)) {
            moveVertically = 1;
        }

        InputPacket inputPacket = new InputPacket(moveHorizontally, moveVertically);
        player.setInput(inputPacket);
        send(inputPacket);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
