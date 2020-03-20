package com.piotreee.game.objects.map.biomes;

import com.piotreee.game.objects.tiles.Dirt;
import com.piotreee.game.objects.tiles.Sand;
import com.piotreee.pixengine.objects.tilemap.Biome;
import com.piotreee.pixengine.objects.tilemap.Tile;

public class Forest implements Biome {
    public final static Forest INSTANCE = new Forest();

    private Forest() {
    }

    @Override
    public Class<? extends Tile> getTile(double value) {
        if (value < 0.1 && value > -0.1) {
            return Dirt.class;
        } else {
            return Sand.class;
        }
    }
}
