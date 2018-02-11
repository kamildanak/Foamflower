package com.kamildanak.minecraft.foamflower.gui.elements;

import com.kamildanak.minecraft.foamflower.gui.IGuiWrapper;

public class GuiCheckBox extends GuiElement {
    private GuiExButton button;
    private GuiLabel label;
    private boolean isChecked;

    public GuiCheckBox(IGuiWrapper guiWrapper, String caption, int color, boolean isChecked) {
        super(0, 0, 0, 0);
        this.isChecked = isChecked;
        this.gui = guiWrapper;
        this.button = new GuiExButton(0, 0, 15, 15, (isChecked) ? "x" : "") {
            public void onClick() {
                if (!(parent instanceof GuiCheckBox)) return;
                GuiCheckBox checkBox = (GuiCheckBox) parent;
                checkBox.isChecked = !checkBox.isChecked;
                super.setCaption((checkBox.isChecked) ? "x" : "");
            }
        };
        this.label = new GuiLabel(0, 0, caption, color);
        addChild(button);
        addChild(label);
    }

    public GuiCheckBox(IGuiWrapper guiCornerstoneCreate, String s, boolean b) {
        this(guiCornerstoneCreate, s, 0x404040, b);
    }

    public void setX(int x) {
        button.setX(x);
        label.setX(x + 20);
    }

    public void setY(int y) {
        button.setY(y);
        label.setY(y);
    }

    @Override
    public int getHeight() {
        if (hidden) return 0;
        if (gui.fontRenderer() != null) return Math.max(button.getHeight(), label.getHeight());
        return 0;
    }

    @Override
    public int getWidth() {
        if (hidden) return 0;
        if (gui.fontRenderer() != null) return button.getWidth() + 5 + label.getWidth();
        return 0;
    }
}
