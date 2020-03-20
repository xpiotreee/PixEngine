package com.piotreee.pixengine.objects.tilemap;

public interface Biome {
    Class<? extends Tile> getTile(double value);
}
