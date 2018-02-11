package com.kamildanak.minecraft.foamflower.gui.layouts;

import com.kamildanak.minecraft.foamflower.gui.IGuiWrapper;
import com.kamildanak.minecraft.foamflower.gui.elements.GuiElement;
import org.lwjgl.opengl.GL11;

public class CenteredLayout extends AbstractLayout {
    private boolean horizontal;
    private boolean vertical;
    private int cX, cY;

    public CenteredLayout(int x, int y, int w, int h, boolean horizontal, boolean vertical) {
        super(x, y, w, h);
        this.horizontal = horizontal;
        this.vertical = vertical;
        cX = (x + w) / 2;
        cY = (y + h) / 2;
    }

    public CenteredLayout(IGuiWrapper iGuiWrapper, int x, int y, int w, int h,
                          boolean horizontal, boolean vertical, GuiElement... elements) {
        super(iGuiWrapper, x, y, w, h, elements);
        this.horizontal = horizontal;
        this.vertical = vertical;
        cX = (x + w) / 2;
        cY = (y + h) / 2;
    }

    public void render() {
        cX = getWidth() / 2;
        cY = getHeight() / 2;
        if (getChildren() == null || hidden) {
            return;
        }
        for (GuiElement e : getChildren()) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (horizontal)
                e.setX(cX - e.getWidth() / 2);
            else
                e.setX(getX());
            if (vertical)
                e.setY(cY - e.getHeight() / 2);
            else
                e.setY(getY());
            e.render();
        }
    }
}
