/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.potion.PotionType
 */
package mz.tech.item.food;

import mz.tech.category.FoodCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.sundry.MouseTail;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class MouseTailJuice
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new MouseTailJuice(), new Object[]{new MouseTail(), null, null, new ItemStackBuilder(Material.POTION).setPotionType(PotionType.THICK).build()}).reg("\u8017\u5b50\u5c3e\u6c41");
    }

    public MouseTailJuice() {
        super(new ItemStackBuilder(Material.POTION).setPotionType(PotionType.WATER).setHideFlags(ItemFlag.HIDE_POTION_EFFECTS).setPotionColor(Color.fromRGB((int)128, (int)42, (int)42)).setLocName("\u00a76\u8017\u5b50\u5c3e\u6c41").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u8017\u5b50\u5c3e\u6c41";
    }

    @Override
    public void onConsume(Player player) {
        player.chat("\u6211\u529d\u8fd9\u4f4d\u5e74\u8f7b\u4eba\u559d\u70b9\u8017\u5b50\u5c3e\u6c41\uff0c\u597d\u597d\u53cd\u601d");
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
        super.onConsume(player);
    }
}

