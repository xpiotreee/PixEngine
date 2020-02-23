package com.piotreee.game;

import com.piotreee.game.scenes.MainMenu;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.LwjglApplication;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            new GameServer(Integer.parseInt(args[0])).run();
        } else {
            LwjglApplication.INSTANCE.run(MainMenu.class);
        }
    }
}
