package com.kamildanak.minecraft.foamflower.gui.layouts;

import com.kamildanak.minecraft.foamflower.gui.IGuiWrapper;
import com.kamildanak.minecraft.foamflower.gui.elements.GuiElement;
import org.lwjgl.opengl.GL11;

public class LinearLayout extends AbstractLayout {
    private int height, width;
    private boolean horizontal;

    @Deprecated
    public LinearLayout(int x, int y, boolean horizontal) {
        super(x, y, 0, 0);
        this.horizontal = horizontal;
    }

    public LinearLayout(IGuiWrapper guiWrapper, int x, int y, boolean horizontal, GuiElement... elements) {
        super(guiWrapper, x, y, 0, 0, elements);
        this.horizontal = horizontal;
    }

    public void render() {
        //((HUD)gui).drawRect(x, y + 1, x + 1, y+getHeight(), 0xFFFFFF);
        //((HUD)gui).drawRect(x+getWidth(), y + 1, x + getWidth() + 1, y+getHeight(), 0xFFFFFF);
        if (getChildren() == null || hidden) {
            return;
        }

        int offset = 0;
        if (horizontal) {
            for (GuiElement e : getChildren()) {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                e.setX(getX() + offset);
                e.setY(getY());
                if (e.center) e.setY(getY() + getHeight() / 2 - e.getHeight() / 2);
                e.render();
                int w = e.getWidth();
                offset += w + ((w == 0) ? 0 : 6);
            }
        } else {
            for (GuiElement e : getChildren()) {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                e.setX(getX());
                if (e.center) e.setX(getX() + getWidth() / 2 - e.getWidth() / 2);
                e.setY(getY() + offset);
                e.render();
                offset += e.getHeight();
            }
        }

    }

    @Override
    public int getHeight() {
        if (hidden) return 0;
        int s = 0;
        if (horizontal) {
            for (GuiElement guiElement : this.getChildren()) {
                if (s < guiElement.getHeight()) s = guiElement.getHeight();
            }
        } else {
            for (GuiElement guiElement : this.getChildren()) {
                s += guiElement.getHeight();
            }
        }
        return s;
    }

    @Override
    public int getWidth() {
        if (hidden) return 0;
        int s = 0;
        if (!horizontal) {
            for (GuiElement guiElement : this.getChildren()) {
                if (s < guiElement.getWidth()) s = guiElement.getWidth();
            }
        } else {
            for (GuiElement guiElement : this.getChildren()) {
                int w = guiElement.getWidth();
                s += w + ((w == 0) ? 0 : 6);
            }
            s -= 6;
        }
        return s;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }
}
