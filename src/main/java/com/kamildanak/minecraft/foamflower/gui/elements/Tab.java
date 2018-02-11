package com.kamildanak.minecraft.foamflower.gui.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Tab {
    private int i;
    private CreativeTabs tab;

    public Tab(int i, CreativeTabs creativeTabs) {
        this.i = i;
        this.tab = creativeTabs;
    }

    public String getTranslatedTabLabel() {
        return tab.getTranslatedTabLabel();
    }

    public int getTabIndex() {
        return i;
    }

    public ItemStack getIconItemStack() {
        return tab.getIconItemStack();
    }

    public CreativeTabs getCreativeTab() {
        return tab;
    }

    public boolean shouldDrawTitle() {
        return tab.drawInForegroundOfTab();
    }
}
