package com.kamildanak.minecraft.foamflower.gui.elements;

import com.kamildanak.minecraft.foamflower.Utils;
import com.kamildanak.minecraft.foamflower.gui.input.IPickBlockHandler;
import com.kamildanak.minecraft.foamflower.inventory.ContainerPickBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class GuiPickBlock extends GuiTabbed {
    private GuiScrollbar scrollbar;
    private GuiScreen parent;

    public GuiPickBlock(EntityPlayer player, ItemStack stack, GuiScreen parent) {
        super(new ContainerPickBlock(), 195, 136, "foamflower:textures/gui/pickblock/tab_items.png");
        this.containterPickBlock = (ContainerPickBlock) this.inventorySlots;
        this.allowUserInput = true;
        this.containterPickBlock.resultSlot.putStack(stack);
        this.parent = parent;
    }

    @Override
    public ArrayList<Tab> getTabs() {
        ArrayList<Tab> tabs = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < CreativeTabs.CREATIVE_TAB_ARRAY.length; i++) {
            if (CreativeTabs.CREATIVE_TAB_ARRAY[i] == CreativeTabs.HOTBAR ||
                    CreativeTabs.CREATIVE_TAB_ARRAY[i] == CreativeTabs.INVENTORY) continue;
            if (CreativeTabs.CREATIVE_TAB_ARRAY[i] == CreativeTabs.SEARCH) {
                this.extraTopTab.setTab(new Tab(-1, CreativeTabs.SEARCH));
                continue;
            }
            tabs.add(new Tab(counter, CreativeTabs.CREATIVE_TAB_ARRAY[i]));
            counter++;
        }

        return tabs;
    }

    public void initGui() {
        super.initGui();

        this.addChild(scrollbar = new GuiScrollbar(175, 18, 20, 110) {
            @Override
            public void onScrolled(float off) {
                ((ContainerPickBlock) inventorySlots).scrollTo(off);
            }
        });
        this.searchBox.addChangeListener(s -> updateCreativeSearch());
    }

    /**
     * Called when the mouse is clicked over a slot or outside the gui.
     */
    protected void handleMouseClick(@Nullable Slot slotIn, int slotId, int mouseButton, @Nonnull ClickType type) {
        if (this.inventorySlots != null) {
            ItemStack itemStack3 = slotIn == null ? ItemStack.EMPTY : this.inventorySlots.getSlot(slotIn.slotNumber).getStack();
            ItemStack itemStack4 = containterPickBlock.resultSlot.getStack().copy();
            if (itemStack3.isItemEqual(itemStack4) && itemStack4.getCount() < itemStack4.getMaxStackSize())
                itemStack4.setCount(itemStack4.getCount() + 1);
            else
                itemStack4 = itemStack3;
            containterPickBlock.resultSlot.putStack(itemStack4);
        }
    }


    public void setCurrentCreativeTab(Tab tab) {
        if (tab == null) return;
        int i = selectedTabIndex;
        selectedTabIndex = tab.getTabIndex();
        ContainerPickBlock guicontainercreative$containercreative = (ContainerPickBlock) this.inventorySlots;
        this.dragSplittingSlots.clear();
        guicontainercreative$containercreative.itemList.clear();
        tab.getCreativeTab().displayAllRelevantItems(guicontainercreative$containercreative.itemList);

        if (this.searchBox != null) {
            if (tab.getCreativeTab().hasSearchBar()) {
                this.searchBox.setHidden(false);
                this.updateCreativeSearch();
            } else {
                this.searchBox.setHidden(true);
            }
        }
        updateScrollbar();
        guicontainercreative$containercreative.scrollTo(0.0F);
    }

    private boolean needsScrollBars() {
        return getTab(selectedTabIndex) != null && ((ContainerPickBlock) this.inventorySlots).canScroll();
    }

    private void updateCreativeSearch() {
        ContainerPickBlock guicontainercreative$containercreative = (ContainerPickBlock) this.inventorySlots;
        guicontainercreative$containercreative.itemList.clear();

        CreativeTabs tab = getTab(selectedTabIndex).getCreativeTab();
        if (tab.hasSearchBar() && tab != CreativeTabs.SEARCH) {
            tab.displayAllRelevantItems(guicontainercreative$containercreative.itemList);
            updateFilteredItems(guicontainercreative$containercreative);
            return;
        }

        for (Item item : Item.REGISTRY) {
            if (item != null && item.getCreativeTab() != null) {
                item.getSubItems(item.getCreativeTab(), guicontainercreative$containercreative.itemList);
            }
        }
        updateFilteredItems(guicontainercreative$containercreative);
    }

    //split from above for custom search tabs
    private void updateFilteredItems(ContainerPickBlock guicontainercreative$containercreative) {
        Iterator<ItemStack> iterator = guicontainercreative$containercreative.itemList.iterator();
        String s1 = this.searchBox.field.getText().toLowerCase(Locale.ROOT);

        while (iterator.hasNext()) {
            ItemStack itemstack = iterator.next();
            boolean flag = false;

            for (String s : itemstack.getTooltip(this.mc.player, () -> this.mc.gameSettings.advancedItemTooltips)) {
                if (TextFormatting.getTextWithoutFormattingCodes(s).toLowerCase(Locale.ROOT).contains(s1)) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                iterator.remove();
            }
        }

        updateScrollbar();
        guicontainercreative$containercreative.scrollTo(0.0F);
    }

    private void updateScrollbar() {
        if (scrollbar != null) {
            scrollbar.setActive(needsScrollBars());
            int rows = (((ContainerPickBlock) this.inventorySlots).itemList.size() + 9 - 1) / 9 - 5;
            scrollbar.step = 1.0F / rows;
            scrollbar.offset = 0.0F;
        }
    }

    protected void renderToolTip(ItemStack stack, int x, int y) {
        if (getTab(selectedTabIndex).getCreativeTab() == CreativeTabs.SEARCH) {
            Utils.renderToolTipDetailed(this, stack, x - screenX, y - 1 - screenY);
        } else {
            super.renderToolTip(stack, x - screenX, y - 1 - screenY);
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == 100) {
            ItemStack stack = containterPickBlock.resultSlot.getStack();

            if (parent instanceof IPickBlockHandler) {
                ((IPickBlockHandler) parent).blockPicked(stack);
            }

            Minecraft.getMinecraft().displayGuiScreen(parent);
            return;
        }
        super.actionPerformed(button);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
