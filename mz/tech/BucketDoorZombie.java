/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Zombie
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntitySpawnEvent
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech;

import mz.tech.MzTech;
import mz.tech.item.sundry.ReverseBucket;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class BucketDoorZombie
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE && MzTech.rand.nextInt(100) < 5) {
            event.getEntity().setCustomName("\u00a7e\u94bb\u77f3\u5251\u6876\u94a2\u95e8\u50f5\u5c38");
            ((Zombie)event.getEntity()).getEquipment().setHelmet((ItemStack)new ReverseBucket());
            ((Zombie)event.getEntity()).getEquipment().setItemInOffHand(new ItemStack(Material.IRON_DOOR));
            ((Zombie)event.getEntity()).getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
            ((Zombie)event.getEntity()).getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            ((Zombie)event.getEntity()).getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            ((Zombie)event.getEntity()).getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            ((Zombie)event.getEntity()).setMaxHealth(100.0);
            ((Zombie)event.getEntity()).setHealth(100.0);
        }
    }
}

