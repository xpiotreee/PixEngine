package com.piotreee.game.objects.tiles;

import com.piotreee.game.packets.SetTilePacket;
import com.piotreee.pixengine.math.Collider;
import com.piotreee.pixengine.objects.tilemap.Tile;
import com.piotreee.pixengine.render.Visual;
import com.piotreee.pixengine.util.Resources;
import org.joml.Vector2i;

import java.util.Optional;

public class Water extends Tile {
    private Visual visual;

    public Water(SetTilePacket packet) {
        super(packet);
    }

    public Water(Vector2i position) {
        super(4, position);
    }

    @Override
    protected Tile init() {
        this.visual = Resources.getAnimation("water");
        return this;
    }

    @Override
    protected Visual getVisual() {
        return visual;
    }

    @Override
    public float getDrag() {
        return 4f;
    }

    @Override
    public Optional<Collider> getCollider() {
        return Optional.empty();
    }
}
