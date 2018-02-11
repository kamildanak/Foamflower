package com.kamildanak.minecraft.foamflower.gui.elements;

import com.kamildanak.minecraft.foamflower.Utils;
import com.kamildanak.minecraft.foamflower.gui.input.InputMouseEvent;
import net.minecraft.util.math.MathHelper;

public abstract class GuiScrollbar extends GuiElement {
    protected float step;
    public float offset;
    private boolean active;
    private boolean dragged;
    private int elementHeight = 15;

    public GuiScrollbar(int x, int y, int w, int h) {
        super(x, y, w, h);
        offset = 0;
        step = 0.025f;
        active = true;
        dragged = false;
    }


    @Override
    public void mouseDown(InputMouseEvent ev) {
        if (!isMouseOver(ev)) return;
        if (active) {
            dragged = true;
        }
    }

    @Override
    public void mouseUp(InputMouseEvent ev) {
        dragged = false;
    }

    @Override
    public void mouseMove(InputMouseEvent ev) {
        if (!active) {
            super.mouseMove(ev);
            return;
        }
        float initialOffset = offset;
        if (dragged) {
            offset += ((float) ev.dy) / (h - elementHeight);
        }
        offset = MathHelper.clamp(offset, 0, 1);
        if (initialOffset != offset) {
            onScrolled(offset);
        }
    }

    @Override
    public void mouseWheel(InputMouseEvent ev) {
        if (!active) {
            super.mouseWheel(ev);
            return;
        }
        float initialOffset = offset;
        offset += (ev.wheel > 0) ? -step : step;
        offset = MathHelper.clamp(offset, 0, 1);
        if (initialOffset != offset) {
            onScrolled(offset);
        }
    }

    @Override
    public void render() {
        if (hidden) return;
        Utils.bind("textures/gui/container/creative_inventory/tabs.png");
        this.gui.drawTexturedModalRect(this.x, this.y + (int) ((h - elementHeight) * offset),
                active ? 232 : 244, 0, 12, elementHeight);
    }

    public abstract void onScrolled(float off);

    public void setActive(boolean active) {
        this.active = active;
    }
}
