package com.piotreee.game.scenes;

import com.piotreee.pixengine.LwjglApplication;
import com.piotreee.pixengine.gui.*;
import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.objects.GameScene;

public class MultiplayerMenu extends GameScene {
    private TextField host;
    private TextField port;
    private TextField username;

    public MultiplayerMenu(Window window) {
        super(window);
    }

    @Override
    public void load() {
        gui.add(username = new TextField(Alignment.CENTER, 64, 108, 450, 64));
        gui.add(new Text(TextAlignment.RIGHT, "Name:", Alignment.CENTER, -180, 108));
        gui.add(host = new TextField(Alignment.CENTER, 64, 36, 450, 64));
        gui.add(new Text(TextAlignment.RIGHT, "Host:", Alignment.CENTER, -180, 36));
        gui.add(port = new TextField(Alignment.CENTER, 64, -36, 450, 64));
        gui.add(new Text(TextAlignment.RIGHT, "Port:", Alignment.CENTER, -180, -36));
        gui.add(new TextButton("Join", Alignment.CENTER, 0, -108, 128, 64, button -> {
            Game.username = username.getText();
            Game.isSingleplayer = false;
            Game.host = host.getText();
            Game.port = Integer.parseInt(port.getText());
            LwjglApplication.INSTANCE.loadScene(Game.class);
        }));
    }

    @Override
    public void unload() {

    }
}
