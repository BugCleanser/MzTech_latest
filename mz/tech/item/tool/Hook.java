/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Enderman
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.FishHook
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.player.PlayerFishEvent
 *  org.bukkit.event.player.PlayerFishEvent$State
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package mz.tech.item.tool;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import mz.tech.MzTech;
import mz.tech.ReflectionWrapper;
import mz.tech.category.ToolCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class Hook
extends MzTechItem
implements Listener {
    static {
        new SmilingCraftingTableRecipe(new Hook(), Material.AIR, null, new ItemStackBuilder("lead", "leather", 0, 1).build(), null, 2, Material.IRON_INGOT, Material.STICK, 5, 5).reg("\u94a9\u5b50");
        Bukkit.getPluginManager().registerEvents((Listener)new Hook(), (Plugin)MzTech.instance);
    }

    public Hook() {
        super(new ItemStackBuilder(Material.FISHING_ROD).setDurability(0).setLocName("\u00a76\u94a9\u5b50").setLoreList("\u00a77\u4f60\u60f3\u5f53\u4e00\u4e2a\u6252\u624b\u5417\uff1f", "\u00a74\u53f3\u952e \u00a77\u5b9e\u4f53").build());
    }

    @Override
    public ToolCategory getCategory() {
        return ToolCategory.instance;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    static void onPlayerFish(PlayerFishEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY && Item.class.isAssignableFrom(event.getCaught().getClass()) && ((Item)event.getCaught()).getPickupDelay() == Short.MAX_VALUE) {
            event.setCancelled(true);
            Hook.getEventHook(event).remove();
            return;
        }
        ItemStack hook = event.getPlayer().getItemInHand();
        if (hook.getType() != Material.FISHING_ROD) {
            hook = event.getPlayer().getInventory().getItemInOffHand();
        }
        if (hook.getItemMeta().hasLocalizedName() && hook.getItemMeta().getLocalizedName().equals(MzTechItem.get("\u94a9\u5b50").getItemMeta().getLocalizedName())) {
            switch (event.getState()) {
                case FISHING: {
                    Hook.getEventHook(event).setVelocity(Hook.getEventHook(event).getVelocity().multiply(3).add(event.getPlayer().getVelocity()));
                    break;
                }
                case IN_GROUND: {
                    switch (Hook.getEventHook(event).getWorld().getBlockAt(Hook.getEventHook(event).getLocation()).getType()) {
                        case WATER: 
                        case STATIONARY_WATER: 
                        case LAVA: 
                        case STATIONARY_LAVA: {
                            return;
                        }
                    }
                    Location playerLoc = event.getPlayer().getLocation();
                    Location hookLoc = Hook.getEventHook(event).getLocation();
                    event.getPlayer().setVelocity(event.getPlayer().getVelocity().add(Hook.getEventHook(event).getVelocity().add(hookLoc.subtract(playerLoc).toVector().multiply(0.5))));
                    break;
                }
                case CAUGHT_ENTITY: {
                    EntityDamageByEntityEvent e = new EntityDamageByEntityEvent((Entity)event.getPlayer(), event.getCaught(), EntityDamageEvent.DamageCause.ENTITY_ATTACK, 0.0);
                    Bukkit.getPluginManager().callEvent((Event)e);
                    if (e.isCancelled()) break;
                    if (Item.class.isAssignableFrom(event.getCaught().getClass())) {
                        ((Item)event.getCaught()).setPickupDelay(-1);
                        event.getCaught().teleport((Entity)event.getPlayer());
                        break;
                    }
                    boolean[] hookItem = new boolean[1];
                    if (Enderman.class.isAssignableFrom(event.getCaught().getClass())) {
                        MaterialData md = ((Enderman)event.getCaught()).getCarriedMaterial();
                        if (md != null && md.getItemType() != Material.AIR) {
                            hookItem[0] = true;
                            event.getCaught().getWorld().dropItem(event.getCaught().getLocation(), md.toItemStack(1));
                            ((Enderman)event.getCaught()).setCarriedMaterial(new MaterialData(Material.AIR));
                        }
                    } else if (LivingEntity.class.isAssignableFrom(event.getCaught().getClass()) && !Player.class.isAssignableFrom(event.getCaught().getClass())) {
                        EntityEquipment ee = ((LivingEntity)event.getCaught()).getEquipment();
                        ArrayList items = Lists.newArrayList((Object[])ee.getArmorContents());
                        items.add(ee.getItemInMainHand());
                        items.add(ee.getItemInOffHand());
                        items.forEach(i -> {
                            if (i.getType() != Material.AIR) {
                                Item it = event.getCaught().getWorld().dropItemNaturally(event.getCaught().getLocation(), i);
                                it.setVelocity(new Vector(0, 0, 0));
                                it.teleport((Entity)event.getPlayer());
                                it.setPickupDelay(-1);
                                blArray[0] = true;
                            }
                        });
                        ee.clear();
                    }
                    if (hookItem[0]) break;
                    Location EntityLoc = event.getCaught().getLocation();
                    Location playerLoc2 = event.getPlayer().getLocation();
                    Entity ec = event.getCaught();
                    while (ec.getVehicle() != null) {
                        ec = ec.getVehicle();
                    }
                    ec.setVelocity(event.getCaught().getVelocity().add(event.getPlayer().getVelocity().add(playerLoc2.subtract(EntityLoc).toVector().multiply(0.1))));
                    break;
                }
            }
        }
    }

    @Override
    public String getTypeName() {
        return "\u94a9\u5b50";
    }

    public static FishHook getEventHook(PlayerFishEvent event) {
        try {
            return event.getHook();
        }
        catch (Throwable e) {
            return (FishHook)ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(PlayerFishEvent.class, "getHook", new Class[0]), event, new Object[0]);
        }
    }
}

