package com.piotreee.pixengine.objects.tilemap;

import com.piotreee.pixengine.objects.Registry;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

public class TileMap {
    public static Registry<Tile> tileRegistry = new Registry<>();
    private Generator generator;
    private HashMap<Vector2i, Chunk> chunks = new HashMap<>();

    public TileMap() {
    }

    public TileMap(Generator generator, int width, int height) {
        this.generator = generator;
        generate(-width, -height, width, height);
    }

    public void generate(int x, int y, int width, int height) {
        for (int i = x; i < width; i++) {
            for (int j = y; j < height; j++) {
                genChunk(i, j);
            }
        }
    }

    public Chunk genChunk(int x, int y) {
        return genChunk(new Vector2i(x, y));
    }

    public Chunk genChunk(Vector2i position) {
        Chunk chunk = generator.genChunk(position);
        chunks.put(position, chunk);
        return chunk;
    }

    public Optional<Chunk> getChunk(int x, int y) {
        return getChunk(new Vector2i(x, y));
    }

    public Optional<Chunk> getChunk(Vector2i position) {
        return Optional.ofNullable(chunks.get(position));
    }

    public Optional<Chunk> getChunk(Tile tile) {
        return getChunkByPos(tile.getPosition());
    }

    public Optional<Chunk> getChunkByPos(Vector2f position) {
        return getChunkByPos((int) Math.floor(position.x / 8f), (int) Math.floor(position.y / 8f));
    }

    public Optional<Chunk> getChunkByPos(Vector2i position) {
        return getChunkByPos((int) Math.floor(position.x / 8f), (int) Math.floor(position.y / 8f));
    }

    public Optional<Chunk> getChunkByPos(int x, int y) {
        return getChunk(x, y);
    }

    public synchronized void setTile(Tile newTile) {
        getChunk(newTile).ifPresent(chunk -> chunk.setTile(newTile));
    }

    public Optional<Tile> getTile(int x, int y) {
        return getTile(new Vector2i(x, y));
    }

    public Optional<Tile> getTile(Vector2i position) {
//        return Optional.ofNullable(tiles.get(position));
//        return getChunkByPos(position).getTile(position.x % 8, position.y % 8);

        AtomicReference<Optional<Tile>> tile = new AtomicReference<>(Optional.empty());
        getChunkByPos(position).ifPresent(chunk -> tile.set(Optional.ofNullable(chunk.getTile(calcTilePos(position.x), calcTilePos(position.y)))));
        return tile.get();
    }

    private int calcTilePos(int i) {
        i %= 8;
        if (i < 0) {
            i = 8 + i;
        }

        return i;
    }

    public Optional<Tile> getTile(Vector2f position) {
        return getTile(Math.round(position.x), Math.round(position.y));
    }

//    public synchronized Tile[] getTiles() {
//        return tiles.values().toArray(new Tile[]{});
//    }

    public Optional<Tile>[] getTiles(Vector2i start, Vector2i end) {
        return getTiles(start.x, start.y, end.x, end.y);
    }

    public Optional<Tile>[] getTiles(int startX, int startY, int endX, int endY) {
        return getElements(startX, startY, endX, endY, (list, position) -> list.add(getTile(position)));
    }

    public Optional<Chunk>[] getChunks(Vector2i start, Vector2i end) {
        return getChunks(start.x, start.y, end.x, end.y);
    }

    public Optional<Chunk>[] getChunks(int startX, int startY, int endX, int endY) {
        return getElements(startX, startY, endX, endY, (list, position) -> list.add(getChunk(position)));
    }

    public Chunk[] getChunks() {
        return chunks.values().toArray(new Chunk[]{});
    }

    private Optional[] getElements(int startX, int startY, int endX, int endY, BiConsumer<List<Optional>, Vector2i> addElement) {
        int width = endX - startX;
        int height = endY - startY;
        List<Optional> list = new ArrayList<>(width * height);
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                addElement.accept(list, new Vector2i(x, y));
            }
        }

        return list.toArray(new Optional[]{});
    }

    public void createChunk(Vector2i chunk) {
        chunks.put(chunk, new Chunk(chunk));
    }

    public void createChunk(int x, int y) {
        createChunk(new Vector2i(x, y));
    }
}
