package com.piotreee.game;

import com.piotreee.game.packets.AddGameObjectPacket;
import com.piotreee.game.packets.TestPacket;
import com.piotreee.game.packets.UpdateGameObjectPacket;
import com.piotreee.game.scenes.MainMenu;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.LwjglApplication;
import com.piotreee.pixengine.networking.PacketRegistry;

public class Main {

    public static void main(String[] args) {
        registerPackets();
        if (args.length > 0) {
            new GameServer(Integer.parseInt(args[0])).run();
        } else {
            LwjglApplication.INSTANCE.run(MainMenu.class);
        }
    }

    private static void registerPackets() {
        PacketRegistry.registerPacket(0, TestPacket.class);
        PacketRegistry.registerPacket(1, UpdateGameObjectPacket.class);
        PacketRegistry.registerPacket(2, AddGameObjectPacket.class);
    }
}
