package com.kamildanak.minecraft.foamflower.gui.layouts;

import com.kamildanak.minecraft.foamflower.gui.IGuiWrapper;
import com.kamildanak.minecraft.foamflower.gui.elements.GuiElement;

public abstract class AbstractLayout extends GuiElement {
    @Deprecated
    public AbstractLayout(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public AbstractLayout(IGuiWrapper guiWrapper, int x, int y, int w, int h, GuiElement... elements) {
        super(x, y, w, h);
        this.gui = guiWrapper;
        for (GuiElement element : elements) {
            this.addChild(element);
        }
    }
}
