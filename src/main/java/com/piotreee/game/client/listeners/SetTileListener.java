package com.piotreee.game.client.listeners;

import com.piotreee.game.packets.SetTilePacket;
import com.piotreee.game.scenes.Game;
import com.piotreee.pixengine.networking.PacketListener;
import com.piotreee.pixengine.objects.tilemap.Tile;
import com.piotreee.pixengine.objects.tilemap.TileMap;
import io.netty.channel.ChannelHandlerContext;

public class SetTileListener extends PacketListener<SetTilePacket> {
    private Game game;

    public SetTileListener(Game game) {
        super(SetTilePacket.class);
        this.game = game;
    }

    @Override
    public void on(ChannelHandlerContext ctx, SetTilePacket packet) throws Exception {
        TileMap tileMap = game.getTileMap();
        Tile tile = TileMap.tileRegistry.get(packet.getType()).getConstructor(SetTilePacket.class).newInstance(packet);
        tileMap.getChunk(tile).ifPresentOrElse(
                chunk -> tileMap.setTile(tile),
                () -> {
                    tileMap.createChunk(tile.getPosition().x / 8, tile.getPosition().y / 8);
                    tileMap.setTile(tile);
                }
        );
    }
}
