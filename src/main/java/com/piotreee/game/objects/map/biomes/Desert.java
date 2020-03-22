package com.piotreee.game.objects.map.biomes;

import com.piotreee.game.objects.tiles.Grass;
import com.piotreee.game.objects.tiles.Water;
import com.piotreee.pixengine.objects.tilemap.Biome;
import com.piotreee.pixengine.objects.tilemap.Tile;

public class Desert implements Biome {
    public final static Desert INSTANCE = new Desert();

    private Desert() {
    }

    @Override
    public Class<? extends Tile> getTile(double value) {
        if (value < 0.25 && value > -0.25) {
            return Water.class;
        } else {
            return Grass.class;
        }
    }
}
