package com.kamildanak.minecraft.foamflower.gui.elements;

import com.kamildanak.minecraft.foamflower.gui.input.InputKeyboardEvent;
import com.kamildanak.minecraft.foamflower.gui.input.InputMouseEvent;
import net.minecraft.client.gui.GuiTextField;

public class GuiEdit extends GuiElement {
    GuiTextField field;
    private String tempString = "";

    public GuiEdit(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void onAdded() {
        field = new GuiTextField(0, gui.fontRenderer(), getX(), getY(), getWidth(), getHeight());
        setText(tempString);
    }

    public String getText() {
        if (field == null) {
            return tempString;
        } else {
            return field.getText();
        }
    }

    public void setText(String text) {
        if (field == null) {
            tempString = text;
        } else {
            field.setText(text);
        }
    }

    @Override
    public void render() {
        field.drawTextBox();
    }

    @Override
    public void mouseDown(InputMouseEvent ev) {
        field.mouseClicked(ev.x, ev.y, ev.button);

        if (isMouseOver(ev)) {
            ev.handled = true;
        }
    }

    @Override
    public void keyPressed(InputKeyboardEvent ev) {
        if (!field.isFocused()) return;

        field.textboxKeyTyped(ev.character, ev.key);
        ev.handled = true;
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        field.x = x;
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        field.y = y;
    }

    @Override
    public void setHeight(int h) {
        super.setHeight(h);
        field.height = h;
    }

    @Override
    public void setWidth(int w) {
        super.setWidth(w);
        field.width = w;
    }
}
