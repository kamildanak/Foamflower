package com.kamildanak.minecraft.foamflower;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Utils {
    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    private static HashMap<String, ResourceLocation> resources = new HashMap<>();


    public static void bind(String textureName) {
        ResourceLocation res = resources.get(textureName);

        if (res == null) {
            res = new ResourceLocation(textureName);
            resources.put(textureName, res);
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(res);
    }
}
