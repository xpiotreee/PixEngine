package com.piotreee.game.objects.tiles;

import com.piotreee.game.packets.SetTilePacket;
import com.piotreee.pixengine.math.Collider;
import com.piotreee.pixengine.objects.tilemap.Tile;
import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Shader;
import com.piotreee.pixengine.render.Visual;
import com.piotreee.pixengine.util.Resources;
import org.joml.Matrix4f;
import org.joml.Vector2i;

import java.util.Optional;

public class Grass extends Tile {
    private Visual visual;

    public Grass(SetTilePacket packet) {
        super(packet);
    }

    public Grass(Vector2i position) {
        super(0, position);
    }

    @Override
    protected Tile init() {
        this.visual = Resources.getTexture("grass");
        return this;
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        super.render(shader, camera, view);
    }

    @Override
    public Optional<Collider> getCollider() {
        return Optional.empty();
    }

    @Override
    public float getDrag() {
        return 3;
    }

    @Override
    protected Visual getVisual() {
        return visual;
    }
}
