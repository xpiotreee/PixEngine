package com.piotreee.game.server.listeners;

import com.piotreee.game.objects.tiles.Bricks;
import com.piotreee.game.objects.tiles.Dirt;
import com.piotreee.game.objects.tiles.Grass;
import com.piotreee.game.packets.SetTilePacket;
import com.piotreee.game.packets.TileActionPacket;
import com.piotreee.game.server.GameServer;
import com.piotreee.pixengine.networking.PacketListener;
import com.piotreee.pixengine.objects.tilemap.Tile;
import io.netty.channel.ChannelHandlerContext;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;

public class TileActionListener extends PacketListener<TileActionPacket> {
    private GameServer server;

    public TileActionListener(GameServer server) {
        super(TileActionPacket.class);
        this.server = server;
    }

    @Override
    public void on(ChannelHandlerContext ctx, TileActionPacket packet) throws Exception {
        Tile tile;
        if (packet.getAction() == GLFW_MOUSE_BUTTON_LEFT) {
            server.getTileMap().setTile(tile = new Dirt(packet.getPosition()));
        } else if (packet.getAction() == GLFW_MOUSE_BUTTON_RIGHT) {
            server.getTileMap().setTile(tile = new Bricks(packet.getPosition()));
        } else {
            server.getTileMap().setTile(tile = new Grass(packet.getPosition()));
        }

        server.sendAll(new SetTilePacket(tile));
    }
}