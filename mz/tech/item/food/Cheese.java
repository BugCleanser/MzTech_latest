/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.Furnace
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.FurnaceSmeltEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.FurnaceRecipe
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.item.food;

import mz.tech.MzTech;
import mz.tech.category.FoodCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.Consumeable;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.MzTechFurnaceRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Cheese
extends MzTechItem
implements Consumeable {
    static {
        new MzTechFurnaceRecipe(new FurnaceRecipe((ItemStack)new Cheese(), Material.MILK_BUCKET)).reg("\u5976\u916a");
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler
            public void onFurnaceSmelt(FurnaceSmeltEvent event) {
                if (event.isCancelled()) {
                    return;
                }
                if (event.getSource().equals((Object)new ItemStack(Material.MILK_BUCKET))) {
                    Furnace state = (Furnace)event.getBlock().getState();
                    state.getInventory().setSmelting(new ItemStack(Material.BUCKET));
                }
            }
        }, (Plugin)MzTech.instance);
    }

    public Cheese() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(18626842681446L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZjMDFiZmZlY2ZkYWI2ZDNjMGYxYTdjNmRmNmFhMTkzNmYyYWE3YTUxYjU0YTRkMzIzZTFjYWNiYzUzOSJ9fX0=").setLocName("\u00a7e\u5976\u916a").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u5976\u916a";
    }

    @Override
    public int getFoodLevel() {
        return 6;
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        return Consumeable.super.onRightClickAir(player, hand);
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        return Consumeable.super.onRightClickBlock(player, hand, block);
    }

    @Override
    public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        return Consumeable.super.onRightClickEntity(player, hand, entity);
    }
}

