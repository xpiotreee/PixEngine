package com.piotreee.game.objects.tiles;

import com.piotreee.game.packets.SetTilePacket;
import com.piotreee.pixengine.math.Collider;
import com.piotreee.pixengine.objects.tilemap.Tile;
import com.piotreee.pixengine.render.Visual;
import com.piotreee.pixengine.util.Resources;
import org.joml.Vector2i;

import java.util.Optional;

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
    public Optional<Collider> getCollider() {
        return Optional.empty();
    }

    @Override
    public float getDrag() {
        return 2;
    }

    @Override
    protected Visual getVisual() {
        return visual;
    }
}
