/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityResurrectEvent
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.item.food;

import mz.tech.MzTech;
import mz.tech.category.FoodCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.Consumeable;
import mz.tech.item.MzTechItem;
import mz.tech.item.food.Flour;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class GingerbreadMan
extends MzTechItem
implements Consumeable {
    static {
        new SmilingCraftingTableRecipe(false, (ItemStack)new GingerbreadMan(), new Object[]{Material.SUGAR, Material.EGG, new Flour()}).reg("\u59dc\u997c\u4eba");
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler(priority=EventPriority.LOWEST)
            public void onEntityResurrect(EntityResurrectEvent event) {
                if (event.isCancelled()) {
                    return;
                }
                if (event.getEntity() instanceof LivingEntity) {
                    EntityEquipment equipment = event.getEntity().getEquipment();
                    MzTechItem mzTechCopy = MzTechItem.asMzTechCopy(equipment.getItemInMainHand());
                    if (mzTechCopy instanceof GingerbreadMan) {
                        event.setCancelled(true);
                    }
                    if (equipment.getItemInMainHand().getType() == new GingerbreadMan().getType()) {
                        return;
                    }
                    mzTechCopy = MzTechItem.asMzTechCopy(equipment.getItemInOffHand());
                    if (mzTechCopy instanceof GingerbreadMan) {
                        event.setCancelled(true);
                    }
                }
            }
        }, (Plugin)MzTech.instance);
    }

    public GingerbreadMan() {
        super(new ItemStackBuilder("TOTEM", 0, "totem_of_undying", 1).setLocName("\u00a76\u59dc\u997c\u4eba").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u59dc\u997c\u4eba";
    }

    @Override
    public int getFoodLevel() {
        return 10;
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

