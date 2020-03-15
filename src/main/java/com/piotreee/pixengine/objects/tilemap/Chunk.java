package com.piotreee.pixengine.objects.tilemap;

import com.piotreee.pixengine.render.Camera;
import com.piotreee.pixengine.render.Renderable;
import com.piotreee.pixengine.render.Shader;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public class Chunk implements Renderable {
    private Vector2i position;
    private Tile[][] tiles = new Tile[8][8];

    public Chunk(Vector2i position) {
        this.position = position;
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f view) {
        for (int tileX = 0; tileX < 8; tileX++) {
            for (int tileY = 0; tileY < 8; tileY++) {
                if (tiles[tileX][tileY] != null) {
                    tiles[tileX][tileY].render(shader, camera, view);
                }
            }
        }
    }

    public Vector2i getPosition() {
        return position;
    }

    public void setPosition(Vector2i position) {
        this.position = position;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public void setTile(Tile tile, Vector2i position) {
        setTile(tile, position.x, position.y);
    }

    public void setTile(Tile tile, int x, int y) {
        tiles[x][y] = tile;
    }

    public void setTile(Tile tile) {
        tiles[calcPos(tile.position.x)][calcPos(tile.position.y)] = tile;
    }

    public Tile getTile(Vector2i position) {
        return getTile(position.x, position.y);
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    private int calcPos(int i) {
        i %= 8;
        if (i < 0) {
            i = 8 + i;
        }

        return i;
    }
}
