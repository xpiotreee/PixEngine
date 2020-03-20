package com.piotreee.game;

import com.piotreee.game.objects.Papierz;
import com.piotreee.game.objects.Player;
import com.piotreee.game.objects.TestGameObject;
import com.piotreee.game.objects.tiles.Bricks;
import com.piotreee.game.objects.tiles.Dirt;
import com.piotreee.game.objects.tiles.Grass;
import com.piotreee.game.objects.tiles.Sand;
import com.piotreee.game.packets.*;
import com.piotreee.game.scenes.MainMenu;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.LwjglApplication;
import com.piotreee.pixengine.networking.Network;
import com.piotreee.pixengine.objects.Registry;
import com.piotreee.pixengine.objects.tilemap.TileMap;

public class Main {
    public static Registry<TestGameObject> objectRegistry = new Registry<>();

    public static void main(String[] args) {
        registerPackets();
        registerGameObjects();
        registerTiles();

        if (args.length > 0) {
            new GameServer(Integer.parseInt(args[0])).run();
        } else {
            LwjglApplication.INSTANCE.run(MainMenu.class);
        }
    }

    private static void registerPackets() {
        Network.packetRegistry.register(0, ConnectedCountPacket.class);
        Network.packetRegistry.register(1, UpdateGameObjectPacket.class);
        Network.packetRegistry.register(2, AddGameObjectPacket.class);
        Network.packetRegistry.register(3, InputPacket.class);
        Network.packetRegistry.register(4, SetPlayerPacket.class);
        Network.packetRegistry.register(5, RemoveGameObjectPacket.class);
        Network.packetRegistry.register(6, SetTilePacket.class);
        Network.packetRegistry.register(7, TileActionPacket.class);
        Network.packetRegistry.register(8, JoinPacket.class);
        Network.packetRegistry.register(9, ChunkPacket.class);
    }

    private static void registerGameObjects() {
        objectRegistry.register(0, TestGameObject.class);
        objectRegistry.register(1, Papierz.class);
        objectRegistry.register(2, Player.class);
    }

    private static void registerTiles() {
        TileMap.tileRegistry.register(0, Grass.class);
        TileMap.tileRegistry.register(1, Dirt.class);
        TileMap.tileRegistry.register(2, Bricks.class);
        TileMap.tileRegistry.register(3, Sand.class);
    }
}
