package com.piotreee.game.scenes;

import com.piotreee.pixengine.LwjglApplication;
import com.piotreee.pixengine.gui.Alignment;
import com.piotreee.pixengine.gui.Text;
import com.piotreee.pixengine.gui.TextAlignment;
import com.piotreee.pixengine.gui.TextButton;
import com.piotreee.pixengine.io.Input;
import com.piotreee.pixengine.io.Window;
import com.piotreee.pixengine.objects.GameScene;

public class MainMenu extends GameScene {
    private Text fps;

    public MainMenu(Window window) {
        super(window);
    }

    @Override
    public void update(float delta, Input input) {
        super.update(delta, input);
        fps.setText("FPS: " + LwjglApplication.INSTANCE.getFPS());
    }

    @Override
    public void load() {
        gui.add(new Text(TextAlignment.CENTER, "Test pro game", Alignment.CENTER, 0, 128));
        gui.add(new TextButton("Singleplayer", Alignment.CENTER, 0, 64, 256, 64, button -> LwjglApplication.INSTANCE.loadScene(SingleplayerMenu.class)));
        gui.add(new TextButton("Multiplayer", Alignment.CENTER, 0, -8, 256, 64, button -> LwjglApplication.INSTANCE.loadScene(MultiplayerMenu.class)));
        gui.add(fps = new Text(TextAlignment.LEFT, "FPS: ", Alignment.TOP_LEFT, 16, -16));
        gui.add(new Text(TextAlignment.LEFT, "0.2137v", Alignment.BOTTOM_LEFT, 16, 16));
        gui.add(new Text(TextAlignment.RIGHT, "Author: piotreee", Alignment.BOTTOM_RIGHT, 0, 16));
    }

    @Override
    public void unload() {

    }
}
