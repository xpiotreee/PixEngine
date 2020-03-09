package com.piotreee.pixengine.objects;

import com.piotreee.game.packets.SetTilePacket;
import com.piotreee.pixengine.math.Collider;
import com.piotreee.pixengine.render.*;
import org.joml.Matrix4f;
import org.joml.Vector2i;

import java.util.Optional;

public abstract class Tile implements Renderable {
    protected int type;
    protected Vector2i position;

    public Tile(SetTilePacket packet) {
        this(packet.getType(), new Vector2i(packet.getX(), packet.getY()));
        init();
    }

    public Tile(int type, Vector2i position) {
        this.type = type;
        this.position = position;
    }

    protected abstract Tile init();

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        Matrix4f projection = camera.getProjection();
        projection.mul(view);

        shader.setUniform("sampler", 0);
        shader.setUniform("view", view);
        shader.setUniform("projection", getProjection(projection));
        this.getVisual().bind();
        Model.RECTANGLE.render(shader, camera, view);
    }

    private Matrix4f getProjection(Matrix4f target) {
        target.translate(position.x, position.y, 0);
        return target;
    }

    protected abstract Visual getVisual();

    public abstract Optional<Collider> getCollider();

    public int getType() {
        return type;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public Vector2i getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return position.equals(tile.position);
    }
}
