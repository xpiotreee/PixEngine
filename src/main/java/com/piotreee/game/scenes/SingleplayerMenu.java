package com.piotreee.game.scenes;

import com.piotreee.pixengine.LwjglApplication;
import com.piotreee.pixengine.gui.*;
import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.objects.GameScene;

public class SingleplayerMenu extends GameScene {
    private TextField username;

    public SingleplayerMenu(Window window) {
        super(window);
    }

    @Override
    public void load() {
        gui.add(username = new TextField(Alignment.CENTER, 64, 36, 450, 64));
        gui.add(new Text(TextAlignment.RIGHT, "Name: ", Alignment.CENTER, -140, 36));
        gui.add(new TextButton("Join", Alignment.CENTER, 0, -36, 128, 64, button -> {
            Game.username = username.getText();
            Game.host = "localhost";
            Game.port = 2137;
            Game.isSingleplayer = true;
            LwjglApplication.INSTANCE.loadScene(Game.class);
        }));
    }

    @Override
    public void unload() {

    }
}
