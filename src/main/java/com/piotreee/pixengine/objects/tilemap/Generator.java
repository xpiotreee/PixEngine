package com.piotreee.pixengine.objects.tilemap;

import org.joml.Vector2i;

public class Generator {
    private BiomeAtlas biomeAtlas;
    private OpenSimplexNoise terrainNoise;
    private OpenSimplexNoise biomeNoise;

    public Generator(BiomeAtlas biomeAtlas) {
        this(biomeAtlas, 0);
    }

    public Generator(BiomeAtlas biomeAtlas, long seed) {
        this.biomeAtlas = biomeAtlas;
        this.terrainNoise = new OpenSimplexNoise(seed);
        this.biomeNoise = new OpenSimplexNoise(seed + 1);
    }

    public Chunk genChunk(int x, int y) {
        return genChunk(new Vector2i(x, y));
    }

    public Chunk genChunk(Vector2i position) {
        Chunk chunk = new Chunk(position);
        Biome biome = biomeAtlas.getBiome(biomeNoise.eval(position.x / 1, position.y / 1));
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                try {
                    Vector2i tilePos = position.mul(8, new Vector2i()).add(x, y);
                    chunk.setTile(
                            biome.getTile(terrainNoise.eval(tilePos.x / 8.0, tilePos.y / 8.0))
                                    .getConstructor(Vector2i.class).newInstance(tilePos), x, y
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return chunk;
    }
}
