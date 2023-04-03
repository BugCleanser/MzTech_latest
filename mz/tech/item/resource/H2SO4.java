/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.potion.PotionType
 */
package mz.tech.item.resource;

import mz.tech.category.MzTechCategory;
import mz.tech.category.ResourceCategory;
import mz.tech.item.MzTechItem;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class H2SO4
extends MzTechItem {
    public H2SO4() {
        super(new ItemStackBuilder(Material.POTION).setPotionType(PotionType.WATER).setHideFlags(ItemFlag.HIDE_POTION_EFFECTS).setLocName("\u00a7f\u786b\u9178").setLoreList("\u00a77H\u2082SO\u2084").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u786b\u9178";
    }

    @Override
    public void onConsume(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 600, 2));
    }
}

