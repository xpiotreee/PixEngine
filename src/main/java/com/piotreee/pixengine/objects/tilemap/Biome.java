package com.piotreee.pixengine.objects.tilemap;

import com.piotreee.game.objects.tiles.Dirt;
import com.piotreee.game.objects.tiles.Grass;

public class Biome {
    public Class<? extends Tile> getTile(double value) {
        if (value < 0) {
            return Dirt.class;
        } else {
            return Grass.class;
        }
    }
}
