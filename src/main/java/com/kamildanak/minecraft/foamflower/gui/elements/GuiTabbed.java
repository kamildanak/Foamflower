package com.kamildanak.minecraft.foamflower.gui.elements;

import com.kamildanak.minecraft.foamflower.gui.GuiScreenPlus;
import com.kamildanak.minecraft.foamflower.inventory.ContainerPickBlock;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiTabbed extends GuiScreenPlus {
    private final ArrayList<Tab> tabs;
    public GuiTab extraTopTab;
    protected int selectedTabIndex = 0;
    GuiEdit searchBox;
    ContainerPickBlock containterPickBlock;
    private int tabPage = 0;
    private int maxPages = 0;
    private ArrayList<GuiTab> guiTabs;
    private GuiTab extraBottomTab;

    public GuiTabbed(ContainerPickBlock containerPickBlock, int i, int i1, String s) {
        super(containerPickBlock, i, i1, s);
        guiTabs = new ArrayList<>();
        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 5; column++) {
                GuiTab guiTab = new GuiTab(this, column * 29, (row == 0) ? -28 : 136 - 4, row == 0, column);
                guiTabs.add(guiTab);
                this.addChild(guiTab);
            }
        }
        this.addChild(extraTopTab = new GuiTab(this, 6 * 29 - 7, -28, true, 6));
        this.addChild(extraBottomTab = new GuiTab(this, 6 * 29, 136 - 4, false, 6));
        this.tabs = getTabs();
        updateTabs();
    }

    protected void updateActivePotionEffects() {
        super.updateActivePotionEffects();
    }

    public abstract ArrayList<Tab> getTabs();

    public Tab getTab(int i) {
        if (i >= 0) return tabs.get(i);
        if (i == -1) return extraTopTab.getTab();
        if (i == -2) return extraBottomTab.getTab();
        return null;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.addChild(searchBox = new GuiEdit(82, 6, 89, this.fontRenderer.FONT_HEIGHT));
        searchBox.onAdded();
        int i = selectedTabIndex;
        selectedTabIndex = -1;
        this.setCurrentCreativeTab(getTab(i));
        int tabCount = tabs.size();
        if (tabCount > guiTabs.size()) {
            buttonList.add(new GuiButton(101, guiLeft, guiTop - 50, 20, 20, "<"));
            buttonList.add(new GuiButton(102, guiLeft + xSize - 20, guiTop - 50, 20, 20, ">"));
            maxPages = ((tabCount - 12) / 10) + 1;
        }
        buttonList.add(new GuiButton(100, guiLeft + 34, guiTop + 109, 70, 20, net.minecraft.client.resources.I18n.format("gui.foamflower.select").trim()));
    }


    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        Tab tab = getTab(selectedTabIndex);
        if (tab != null && tab.shouldDrawTitle()) {
            GlStateManager.disableBlend();
            this.fontRenderer.drawString(I18n.format(tab.getTranslatedTabLabel()), 8, 6, 4210752);
        }
        root.render();

        for (GuiTab tab2 : guiTabs) {
            if (!tab2.isMouseOver(mouseX - this.screenX, mouseY - 1 - this.screenY)) continue;
            if (tab2.getTab() == null) return;
            this.drawHoveringText(I18n.format(tab2.getTab().getTranslatedTabLabel()),
                    mouseX - this.screenX, mouseY - 1 - this.screenY);
            break;
        }
    }

    protected abstract void setCurrentCreativeTab(Tab tab);

    private void updateTabs() {
        int c = 0;
        for (GuiTab tab : guiTabs) {
            tab.setTab(null);
        }
        for (Tab tab : getTabsPage(tabs, tabPage)) {
            if (c >= guiTabs.size()) break;
            guiTabs.get(c).setTab(tab);
            c++;
        }
    }

    private List<Tab> getTabsPage(ArrayList<Tab> tabs, int page) {
        int start = page * guiTabs.size();
        int end = Math.min(tabs.size(), ((page + 1) * guiTabs.size()));
        return tabs.subList(start, end);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 101:
                tabPage = Math.max(tabPage - 1, 0);
                break;
            case 102:
                tabPage = Math.min(tabPage + 1, maxPages);
        }
    }

    public void setZLevel(float zLevel) {
        this.zLevel = zLevel;
        this.itemRender.zLevel = zLevel;
    }
}
