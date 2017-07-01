package com.kamildanak.minecraft.foamflower.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class InventoryStatic extends ItemStackHandler implements IInventory {
    public InventoryStatic(int size) {
        super(size);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        setSize(nbt.hasKey("Size", Constants.NBT.TAG_INT) ? nbt.getInteger("Size") : stacks.size());
        NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
            int slot = itemTags.getByte("slot") & 0xff;

            if (slot >= 0 && slot < stacks.size()) {
                stacks.set(slot, new ItemStack(itemTags));
            }
        }
        onLoad();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("slot", (byte) i);
                stacks.get(i).writeToNBT(itemTag);
                nbtTagList.appendTag(itemTag);
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("Items", nbtTagList);
        nbt.setInteger("Size", stacks.size());
        return nbt;
    }

    @Override
    public void setInventorySlotContents(int i, @Nonnull ItemStack itemstack) {
        super.setStackInSlot(i, itemstack);
    }

    public boolean isEmpty() {
        for (ItemStack item : stacks) {
            if (!item.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ItemStack insertItem(ItemStack paid, int start, int end, boolean simulate) {
        for (int i = start; i <= end; i++) {
            paid = this.insertItem(i, paid, simulate);
        }
        return paid;
    }

    public ItemStack extractItem(ItemStack sold, int count, int start, int end, boolean simulate) {
        int expected = count;
        for (int i = start; i <= end; i++) {
            if (sold.isItemEqual(this.extractItem(i, count, true))) {
                count -= this.extractItem(i, count, simulate).getCount();
            }
        }
        return ItemHandlerHelper.copyStackWithSize(sold, expected - count);
    }

    @Override
    @Nonnull
    public String getName() {
        return "";
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("");
    }


    @Override
    public boolean hasCustomName() {
        return true;
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer entityplayer) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return stacks.size();
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int index, int amount) {
        return ItemStackHelper.getAndSplit(this.stacks, index, amount);
    }

    @Override
    @Nonnull
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.stacks, index);
    }


    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public void openInventory(@Nonnull EntityPlayer player) {
    }

    @Override
    public void closeInventory(@Nonnull EntityPlayer player) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public boolean isItemValidForSlot(int i, @Nonnull ItemStack itemstack) {
        return true;
    }

    public void clear() {
        for (int i = 0; i < stacks.size(); i++) {
            stacks.set(i, ItemStack.EMPTY);
        }
    }
}
