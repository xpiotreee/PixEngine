package com.piotreee.game.objects.tiles;

import com.piotreee.game.packets.SetTilePacket;
import com.piotreee.pixengine.math.Collider;
import com.piotreee.pixengine.objects.Tile;
import com.piotreee.pixengine.render.Visual;
import org.joml.Vector2i;

import java.util.Optional;

public class CollidableTile extends Tile {
    private Collider collider;

    public CollidableTile(SetTilePacket packet) {
        super(packet);
        this.collider = new Collider(position.x + 0.1f, position.y + 0.1f, 0.8f, 0.8f);
    }

    public CollidableTile(int type, Vector2i position) {
        super(type, position);
        this.collider = new Collider(position.x + 0.1f, position.y + 0.1f, 0.8f, 0.8f);
    }

    @Override
    protected Tile init() {
        return null;
    }

    @Override
    protected Visual getVisual() {
        return null;
    }

    @Override
    public Optional<Collider> getCollider() {
        return Optional.ofNullable(collider);
    }
}
