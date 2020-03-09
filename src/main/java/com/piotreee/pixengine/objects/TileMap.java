package com.piotreee.pixengine.objects;

import com.piotreee.game.objects.tiles.Grass;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class TileMap {
    private HashMap<Vector2i, Tile> tiles = new HashMap<>();

    public TileMap() {
    }

    public TileMap(int width, int height) {
        generate(0, 0, width, height);
    }

    public void generate(int x, int y, int width, int height) {
        int tx = width + x;
        int ty = height + y;
        for (int i = x; i < tx; i++) {
            for (int j = x; j < ty; j++) {
                Vector2i position = new Vector2i(i, j);
                tiles.put(position, new Grass(position));
            }
        }
    }

    public synchronized void setTile(Tile newTile) {
        Tile tile = tiles.get(newTile.getPosition());
        if (tile == null) {
            tiles.put(newTile.getPosition(), newTile);
        } else {
            tiles.replace(newTile.getPosition(), newTile);
        }
    }

    public Optional<Tile> getTile(int x, int y) {
        return getTile(new Vector2i(x, y));
    }

    public Optional<Tile> getTile(Vector2i position) {
        return Optional.ofNullable(tiles.get(position));
    }

    public Optional<Tile> getTile(Vector2f position) {
        return getTile(Math.round(position.x), Math.round(position.y));
    }

    public void removeTile(Tile tile) {
        removeTile(tile.getPosition());
    }

    public void removeTile(int x, int y) {
        removeTile(new Vector2i(x, y));
    }

    public void removeTile(Vector2i position) {
        tiles.remove(position);
    }

    public synchronized Tile[] getTiles() {
        return tiles.values().toArray(new Tile[]{});
    }

    public Optional<Tile>[] getTiles(int startX, int startY, int endX, int endY) {
        int width = endX - startX;
        int height = endY - startY;
        List<Optional<Tile>> tileList = new ArrayList<>(width * height);
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                tileList.add(getTile(x, y));
            }
        }

        return tileList.toArray(new Optional[]{});
    }

    public Optional<Tile>[] getTiles(Vector2i start, Vector2i end) {
        return getTiles(start.x, start.y, end.x, end.y);
    }

}
