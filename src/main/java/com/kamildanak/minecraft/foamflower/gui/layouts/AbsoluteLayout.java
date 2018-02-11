package com.kamildanak.minecraft.foamflower.gui.layouts;

import com.kamildanak.minecraft.foamflower.gui.IGuiWrapper;
import com.kamildanak.minecraft.foamflower.gui.elements.GuiElement;

public class AbsoluteLayout extends AbstractLayout {
    @Deprecated
    public AbsoluteLayout(int x, int y) {
        super(x, y, 0, 0);
    }

    public AbsoluteLayout(IGuiWrapper guiWrapper, int x, int y, GuiElement... elements) {
        super(guiWrapper, x, y, 0, 0, elements);
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }
}
