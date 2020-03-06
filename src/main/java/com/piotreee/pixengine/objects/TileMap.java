package com.piotreee.pixengine.objects;

import com.piotreee.game.objects.tiles.Grass;
import org.joml.Vector2i;

import java.util.HashMap;

public class TileMap {
    private HashMap<Vector2i, Tile> tiles = new HashMap<>();

    public TileMap(int width, int height) {
        generate(0, 0, width, height);
    }

    public void generate(int x, int y, int width, int height) {
        int tx = width + x;
        int ty = height + y;
        for (int i = x; i < tx; i++) {
            for (int j = x; j < ty; j++) {
                Vector2i position = new Vector2i(i, j);
                tiles.put(position, new Grass(0, position));
            }
        }
    }

    public Tile getTile(Vector2i position) {
        return tiles.get(position);
    }

    public Tile getTile(int x, int y) {
        return getTile(new Vector2i(x, y));
    }

    public Tile[] getTiles() {
        return tiles.values().toArray(new Tile[]{});
    }
}
