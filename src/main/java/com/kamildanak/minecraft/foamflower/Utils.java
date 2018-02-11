package com.kamildanak.minecraft.foamflower;

import com.kamildanak.minecraft.foamflower.gui.GuiScreenPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    private static HashMap<String, ResourceLocation> resources = new HashMap<>();


    public static void bind(String textureName) {
        ResourceLocation res = resources.get(textureName);

        if (res == null) {
            res = new ResourceLocation(textureName);
            resources.put(textureName, res);
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(res);
    }

    public static void renderToolTipDetailed(GuiScreenPlus gui, ItemStack stack, int x, int y) {
        List<String> list = stack.getTooltip(gui.mc.player, () -> gui.mc.gameSettings.advancedItemTooltips);
        CreativeTabs creativetabs = stack.getItem().getCreativeTab();

        if (creativetabs == null && stack.getItem() == Items.ENCHANTED_BOOK) {
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);

            if (map.size() == 1) {
                Enchantment enchantment = map.keySet().iterator().next();

                for (CreativeTabs creativetabs1 : CreativeTabs.CREATIVE_TAB_ARRAY) {
                    if (creativetabs1.hasRelevantEnchantmentType(enchantment.type)) {
                        creativetabs = creativetabs1;
                        break;
                    }
                }
            }
        }

        if (creativetabs != null) {
            list.add(1, "" + TextFormatting.BOLD + TextFormatting.BLUE + I18n.format(creativetabs.getTranslatedTabLabel()));
        }

        for (int i = 0; i < list.size(); ++i) {
            if (i == 0) {
                list.set(i, stack.getRarity().rarityColor + list.get(i));
            } else {
                list.set(i, TextFormatting.GRAY + list.get(i));
            }
        }

        net.minecraftforge.fml.client.config.GuiUtils.preItemToolTip(stack);
        gui.drawHoveringText(list, x, y);
        net.minecraftforge.fml.client.config.GuiUtils.postItemToolTip();
    }
}
