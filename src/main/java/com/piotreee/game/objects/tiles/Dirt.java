package com.piotreee.game.objects.tiles;

import com.piotreee.game.packets.SetTilePacket;
import com.piotreee.pixengine.objects.Tile;
import com.piotreee.pixengine.render.Visual;
import com.piotreee.pixengine.util.Resources;
import org.joml.Vector2i;

public class Dirt extends Tile {
    private Visual visual;

    public Dirt(SetTilePacket packet) {
        super(packet);
    }

    public Dirt(Vector2i position) {
        super(1, position);
    }

    @Override
    public Tile init() {
        this.visual = Resources.getTexture("dirt");
        return this;
    }

    @Override
    protected Visual getVisual() {
        return visual;
    }
}
