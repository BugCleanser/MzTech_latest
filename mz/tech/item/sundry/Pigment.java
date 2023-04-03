/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.PotionMeta
 *  org.bukkit.potion.PotionData
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.potion.PotionType
 */
package mz.tech.item.sundry;

import mz.tech.category.SundryCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Pigment
extends MzTechItem {
    static {
        ItemStack waterBottle = new ItemStack(Material.POTION);
        PotionMeta im = (PotionMeta)waterBottle.getItemMeta();
        im.setBasePotionData(new PotionData(PotionType.THICK));
        waterBottle.setItemMeta((ItemMeta)im);
        new SmilingCraftingTableRecipe(new Pigment(), new Object[]{ItemStackBuilder.redDye, ItemStackBuilder.yellowDye, ItemStackBuilder.blueDye, null, waterBottle}).reg("\u989c\u6599");
    }

    public Pigment() {
        super(new ItemStack(Material.POTION));
        ItemMeta im = this.getItemMeta();
        im.setLocalizedName("\u00a7c\u989c\u6599");
        ((PotionMeta)im).setColor(Color.fromRGB((int)255, (int)0, (int)0));
        this.setItemMeta(im);
        this.setHideFlags(ItemFlag.HIDE_POTION_EFFECTS);
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u989c\u6599";
    }

    @Override
    public void onConsume(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 2), false);
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 2), false);
    }
}

