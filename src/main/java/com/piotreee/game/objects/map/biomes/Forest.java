package com.piotreee.game.objects.map.biomes;

import com.piotreee.game.objects.tiles.Sand;
import com.piotreee.game.objects.tiles.Water;
import com.piotreee.pixengine.objects.tilemap.Biome;
import com.piotreee.pixengine.objects.tilemap.Tile;

public class Forest implements Biome {
    public final static Forest INSTANCE = new Forest();

    private Forest() {
    }

    @Override
    public Class<? extends Tile> getTile(double value) {
        if (value < 0.25 && value > -0.25) {
            return Water.class;
        } else {
            return Sand.class;
        }
    }
}
