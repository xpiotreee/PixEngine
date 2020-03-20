package com.piotreee.game.objects.map.biomes;

import com.piotreee.game.objects.tiles.Dirt;
import com.piotreee.game.objects.tiles.Grass;
import com.piotreee.pixengine.objects.tilemap.Biome;
import com.piotreee.pixengine.objects.tilemap.Tile;

public class Desert implements Biome {
    public final static Desert INSTANCE = new Desert();

    private Desert() {
    }

    @Override
    public Class<? extends Tile> getTile(double value) {
        if (value < 0.1 && value > -0.1) {
            return Dirt.class;
        } else {
            return Grass.class;
        }
    }
}
