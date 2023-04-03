/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.util.Vector
 */
package mz.tech.item.sundry;

import mz.tech.category.MzTechCategory;
import mz.tech.category.SundryCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.Tool;
import mz.tech.item.sundry.Baseball;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

public class BaseballGlove
extends Tool {
    static {
        new SmilingCraftingTableRecipe(new BaseballGlove(), null, Material.RABBIT_HIDE, 1, 1, 1, 1, 1, 1, 1).reg("\u68d2\u7403\u624b\u5957");
    }

    public BaseballGlove() {
        super(new ItemStackBuilder(Material.LEATHER).setLocName("\u00a76\u68d2\u7403\u624b\u5957").build());
    }

    @Override
    public String getTypeName() {
        return "\u68d2\u7403\u624b\u5957";
    }

    @Override
    public MzTechCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        player.getWorld().getNearbyEntities(player.getEyeLocation(), 2.0, 2.0, 2.0).forEach(entity -> {
            if (entity instanceof Item && MzTechItem.asMzTechCopy(((Item)entity).getItemStack()) instanceof Baseball) {
                entity.setVelocity(new Vector());
                ((Item)entity).setPickupDelay(0);
            }
        });
        try {
            player.getWorld().playSound(player.getEyeLocation(), Sound.ENTITY_IRONGOLEM_ATTACK, 1.0f, 1.0f);
        }
        catch (Throwable e) {
            player.getWorld().playSound(player.getEyeLocation(), Enum.valueOf(Sound.class, "ENTITY_IRON_GOLEM_ATTACK"), 1.0f, 1.0f);
        }
        return super.onRightClickAir(player, hand);
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        return this.onRightClickAir(player, hand);
    }

    @Override
    public boolean onLeftClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        return this.onRightClickAir(player, hand);
    }
}

