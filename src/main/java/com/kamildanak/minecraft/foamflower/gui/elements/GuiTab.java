package com.kamildanak.minecraft.foamflower.gui.elements;

import com.kamildanak.minecraft.foamflower.gui.input.InputMouseEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.util.ResourceLocation;

public class GuiTab extends GuiElement {
    private static final ResourceLocation CREATIVE_INVENTORY_TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
    private final GuiTabbed guiTabbed;
    private Tab tab;
    private GuiRenderItem guiRenderItem;
    private boolean isTabInFirstRow;
    private int column;

    public GuiTab(GuiTabbed guiTabbed, int x, int y, int w, int h, boolean isTabInFirstRow, int column) {
        super(x, y, w, h);
        this.guiTabbed = guiTabbed;
        Minecraft mc = Minecraft.getMinecraft();
        ItemModelMesher itemModelMesher = mc.getRenderItem().getItemModelMesher();
        guiRenderItem = new GuiRenderItem(mc.getTextureManager(), itemModelMesher, new ItemColors());
        this.isTabInFirstRow = isTabInFirstRow;
        this.column = column;
    }

    public GuiTab(GuiTabbed guiTabbed, int x, int y, boolean isTabInFirstRow, int position) {
        this(guiTabbed, x, y, 28, 32, isTabInFirstRow, position);
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    @Override
    public void render() {
        if (hidden) return;
        if (tab == null) return;
        GlStateManager.color(0, 0, 0, 0);
        guiTabbed.mc.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);

        GlStateManager.disableLighting();
        GlStateManager.color(1F, 1F, 1F); //Forge: Reset color in case Items change it.
        GlStateManager.enableBlend(); //Forge: Make sure blend is enabled else tabs show a white border.

        int textureY = 0;
        if (guiTabbed.selectedTabIndex == this.tab.getTabIndex()) {
            textureY += 32;
        } else {
            guiTabbed.setZLevel(-100.0F);
        }
        if (!isTabInFirstRow) textureY += 64;
        int textureX = 0;
        if (column > 0) textureX += 28;
        if (column >= 5) textureX += 4 * 28;
        guiTabbed.drawTexturedModalRect(x, y, textureX, textureY, 28, 32);

        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        if (tab != null) {
            guiRenderItem.renderItemAndEffectIntoGUI(tab.getIconItemStack(), x + 6,
                    y + (isTabInFirstRow ? 10 : 4));
        }
        GlStateManager.disableLighting();
        guiTabbed.setZLevel(0.0F);
    }

    @Override
    public int getHeight() {
        if (hidden) return 0;
        if (gui.fontRenderer() != null) return gui.fontRenderer().FONT_HEIGHT + 2;
        return 0;
    }

    @Override
    public int getWidth() {
        if (hidden) return 0;
        return 0;
    }

    public void mouseUp(InputMouseEvent ev) {
        if (isMouseOver(ev)) {
            guiTabbed.setCurrentCreativeTab(tab);
        }
        super.mouseUp(ev);
    }
}
