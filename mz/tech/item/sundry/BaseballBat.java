/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.sundry;

import mz.tech.category.MzTechCategory;
import mz.tech.category.SundryCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.Tool;
import mz.tech.item.sundry.Baseball;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class BaseballBat
extends Tool {
    static {
        RawItem log = new RawItem(new ItemStack[0]);
        try {
            log.add(new ItemStack(Material.LOG, 1, 0), null);
            log.add(new ItemStack(Material.LOG, 1, 1), null);
            log.add(new ItemStack(Material.LOG, 1, 2), null);
            log.add(new ItemStack(Material.LOG, 1, 3), null);
            log.add(new ItemStack(Material.LOG_2, 1, 0), null);
            log.add(new ItemStack(Material.LOG_2, 1, 1), null);
        }
        catch (Throwable e) {
            log.add(new ItemStack(Enum.valueOf(Material.class, "OAK_LOG")), null);
            log.add(new ItemStack(Enum.valueOf(Material.class, "SPRUCE_LOG")), null);
            log.add(new ItemStack(Enum.valueOf(Material.class, "BIRCH_LOG")), null);
            log.add(new ItemStack(Enum.valueOf(Material.class, "JUNGLE_LOG")), null);
            log.add(new ItemStack(Enum.valueOf(Material.class, "ACACIA_LOG")), null);
            log.add(new ItemStack(Enum.valueOf(Material.class, "DARK_OAK_LOG")), null);
            log.add(new ItemStack(Enum.valueOf(Material.class, "STRIPPED_OAK_LOG")), null);
            log.add(new ItemStack(Enum.valueOf(Material.class, "STRIPPED_SPRUCE_LOG")), null);
            log.add(new ItemStack(Enum.valueOf(Material.class, "STRIPPED_BIRCH_LOG")), null);
            log.add(new ItemStack(Enum.valueOf(Material.class, "STRIPPED_JUNGLE_LOG")), null);
            log.add(new ItemStack(Enum.valueOf(Material.class, "STRIPPED_ACACIA_LOG")), null);
            log.add(new ItemStack(Enum.valueOf(Material.class, "STRIPPED_DARK_OAK_LOG")), null);
        }
        new SmilingCraftingTableRecipe(new BaseballBat(), log, null, null, 0, null, null, Material.STICK).reg("\u68d2\u7403\u68d2");
    }

    public BaseballBat() {
        super(new ItemStackBuilder("wood_spade", 0, "wooden_shovel", 1).setDurability(0).setLocName("\u00a76\u68d2\u7403\u68d2").build());
    }

    @Override
    public String getTypeName() {
        return "\u68d2\u7403\u68d2";
    }

    @Override
    public MzTechCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public boolean onLeftClickAir(Player player, EquipmentSlot hand) {
        player.getWorld().getNearbyEntities(player.getEyeLocation(), 2.0, 2.0, 2.0).forEach(entity -> {
            if (entity instanceof Item && MzTechItem.asMzTechCopy(((Item)entity).getItemStack()) instanceof Baseball) {
                entity.setVelocity(entity.getVelocity().add(player.getEyeLocation().getDirection().multiply(2)));
                try {
                    player.getWorld().playSound(player.getEyeLocation(), Sound.BLOCK_WOOD_BREAK, 1.0f, 1.0f);
                }
                catch (Throwable e) {
                    player.getWorld().playSound(player.getEyeLocation(), Enum.valueOf(Sound.class, "OAK_WOOD"), 1.0f, 1.0f);
                }
                if (player != null && player.getGameMode() != GameMode.CREATIVE) {
                    this.damage();
                }
            }
        });
        try {
            player.getWorld().playSound(player.getEyeLocation(), Sound.ENTITY_IRONGOLEM_ATTACK, 1.0f, 1.0f);
        }
        catch (Throwable e) {
            player.getWorld().playSound(player.getEyeLocation(), Enum.valueOf(Sound.class, "ENTITY_IRON_GOLEM_ATTACK"), 1.0f, 1.0f);
        }
        return super.onLeftClickAir(player, hand);
    }

    @Override
    public boolean onLeftClickBlock(Player player, EquipmentSlot hand, Block block) {
        return this.onLeftClickAir(player, hand);
    }

    @Override
    public boolean onLeftClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        return this.onLeftClickAir(player, hand);
    }
}

