package com.piotreee.game.objects.tiles;

import com.piotreee.game.packets.SetTilePacket;
import com.piotreee.pixengine.objects.Tile;
import com.piotreee.pixengine.render.Visual;
import com.piotreee.pixengine.util.Resources;
import org.joml.Vector2i;

public class Bricks extends CollidableTile {
    private Visual visual;

    public Bricks(SetTilePacket packet) {
        super(packet);
    }

    public Bricks(Vector2i position) {
        super(2, position);
    }

    @Override
    protected Tile init() {
        this.visual = Resources.getTexture("bricks");
        return this;
    }

    @Override
    protected Visual getVisual() {
        return visual;
    }
}
