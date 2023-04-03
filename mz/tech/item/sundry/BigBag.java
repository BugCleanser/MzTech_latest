/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.sundry;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import mz.tech.MzTech;
import mz.tech.category.SundryCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.Storeable;
import mz.tech.item.sundry.Bag;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class BigBag
extends Storeable {
    static {
        SmilingCraftingTableRecipe recipe = new SmilingCraftingTableRecipe((raws, output) -> {
            if (raws.size() == 9) {
                MzTechItem bag = MzTechItem.asMzTechCopy((ItemStack)raws.get(4));
                if (!(bag instanceof Bag)) {
                    return false;
                }
                int i = 0;
                while (i < 4) {
                    if (!new ItemStack(Material.SHULKER_SHELL).isSimilar((ItemStack)raws.get(i))) {
                        return false;
                    }
                    ++i;
                }
                i = 5;
                while (i < 9) {
                    if (!new ItemStack(Material.SHULKER_SHELL).isSimilar((ItemStack)raws.get(i))) {
                        return false;
                    }
                    ++i;
                }
                BigBag bigBag = new BigBag();
                bigBag.setId(((Bag)bag).getId());
                ArrayList contents = Lists.newArrayList((Object[])bigBag.getInventory().getContents());
                int i2 = contents.size();
                while (i2 < bigBag.getSize()) {
                    contents.add(null);
                    ++i2;
                }
                bigBag.setInventory(Bukkit.createInventory(null, (int)bigBag.getSize(), (String)bigBag.getTitle()));
                bigBag.getInventory().setContents(contents.toArray(new ItemStack[0]));
                bigBag.setInventory(bigBag.getInventory());
                output.add(bigBag);
                raws.forEach(is -> is.setAmount(is.getAmount() - 1));
                return true;
            }
            return false;
        }, (ItemStack)new BigBag(), new Object[]{Material.SHULKER_SHELL, 0, 0, 0, new Bag(), 0, 0, 0, 0});
        recipe.reg("\u5927\u80cc\u5305");
    }

    public BigBag() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(876788888431877L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmY2OGQ1MDliNWQxNjY5Yjk3MWRkMWQ0ZGYyZTQ3ZTE5YmNiMWIzM2JmMWE3ZmYxZGRhMjliZmM2ZjllYmYifX19").setLocName("\u00a76\u5927\u80cc\u5305").setLoreList("\u00a74\u53f3\u952e \u00a77\u6253\u5f00", "\u00a77\u00a7m\u5141\u8bb8\u5957\u5a03").build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public String getTypeName() {
        return "\u5927\u80cc\u5305";
    }

    @Override
    public String getTitle() {
        return "\u00a76\u5927\u80cc\u5305";
    }

    @Override
    public Sound getOpenSound() {
        return Sound.BLOCK_CHEST_OPEN;
    }

    @Override
    public Sound getCloseSound() {
        return Sound.BLOCK_CHEST_CLOSE;
    }
}

