package com.piotreee.game.objects.map;

import com.piotreee.game.objects.map.biomes.Desert;
import com.piotreee.game.objects.map.biomes.Forest;
import com.piotreee.pixengine.objects.tilemap.Biome;
import com.piotreee.pixengine.objects.tilemap.BiomeAtlas;

public class Biomes implements BiomeAtlas {
    @Override
    public Biome getBiome(double value) {
        if (value < 0.25 && value > -0.25) {
            return Desert.INSTANCE;
        } else {
            return Forest.INSTANCE;
        }
    }
}
