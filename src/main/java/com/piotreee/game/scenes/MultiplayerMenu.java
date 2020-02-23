package com.piotreee.game.scenes;

import com.piotreee.pixengine.LwjglApplication;
import com.piotreee.pixengine.gui.Alignment;
import com.piotreee.pixengine.gui.Text;
import com.piotreee.pixengine.gui.TextButton;
import com.piotreee.pixengine.gui.TextField;
import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.objects.GameScene;

public class MultiplayerMenu extends GameScene {
    private TextField host;
    private TextField port;

    public MultiplayerMenu(Window window) {
        super(window);
    }

    @Override
    public void load() {
        gui.add(new Text("Host:", Alignment.CENTER, -256, 36, 256, 32));
        gui.add(host = new TextField(Alignment.CENTER, 64, 36, 450, 64));
        gui.add(new Text("Port:", Alignment.CENTER, -256, -36, 256, 32));
        gui.add(port = new TextField(Alignment.CENTER, 64, -36, 450, 64));
        gui.add(new TextButton("Join", Alignment.CENTER, 0, -108, 128, 64, button -> {
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
