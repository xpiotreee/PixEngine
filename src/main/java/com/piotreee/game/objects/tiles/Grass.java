package com.piotreee.game.objects.tiles;

import com.piotreee.game.packets.AddTilePacket;
import com.piotreee.pixengine.objects.Tile;
import com.piotreee.pixengine.render.Visual;
import com.piotreee.pixengine.util.Resources;
import org.joml.Vector2i;

public class Grass extends Tile {
    private Visual visual;

    public Grass(AddTilePacket packet) {
        super(packet.getType(), new Vector2i(packet.getX(), packet.getY()));
        this.visual = Resources.getTexture("grass");
    }

    public Grass(int type, Vector2i position) {
        super(type, position);
    }

    @Override
    protected Visual getVisual() {
        return visual;
    }
}
