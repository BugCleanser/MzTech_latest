/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Damageable
 *  org.bukkit.entity.EnderDragon
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Wither
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SpawnEggMeta
 */
package mz.tech.item.sundry;

import mz.tech.EntityStack;
import mz.tech.NBT;
import mz.tech.category.SundryCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class EmptySpawnEgg
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new EmptySpawnEgg(), new ItemStackBuilder("totem", 0, "TOTEM_OF_UNDYING", 1).build(), 0, 0, 0, Material.EGG, 0, 0, 0, 0).reg("\u7a7a\u5237\u602a\u86cb");
    }

    public EmptySpawnEgg() {
        super(new ItemStackBuilder(Material.CLAY_BALL).setLocName("\u00a7e\u7a7a\u5237\u602a\u86cb").setLoreList("\u00a74\u53f3\u952e \u00a77\u6355\u6349\u751f\u7269").build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u7a7a\u5237\u602a\u86cb";
    }

    public static boolean isBoss(EntityType t) {
        return Wither.class.isAssignableFrom(t.getEntityClass()) || EnderDragon.class.isAssignableFrom(t.getEntityClass());
    }

    @Override
    public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        if (entity instanceof LivingEntity) {
            if (entity instanceof Player) {
                player.sendMessage("\u00a74\u4f60\u4e5f\u60f3\u5c1d\u5c1d\u88ab\u585e\u8fdb\u86cb\u91cc\u7684\u6ecb\u5473\u5417\uff1f");
                return false;
            }
            if (EmptySpawnEgg.isBoss(entity.getType())) {
                player.sendMessage("\u00a74\u8fd9\u2026\u2026\u592a\u5927\u4e00\u53ea\u4e86");
                return false;
            }
            EntityDamageByEntityEvent e = new EntityDamageByEntityEvent((Entity)player, entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 1.0);
            Bukkit.getPluginManager().callEvent((Event)e);
            if (!e.isCancelled()) {
                try {
                    ItemStackBuilder is = new ItemStackBuilder(Material.MONSTER_EGG);
                    ItemMeta im = is.getItemMeta();
                    ((SpawnEggMeta)im).setSpawnedType(entity.getType());
                    is.setItemMeta(im);
                    NBT nbt = new NBT(is);
                    NBT tag = nbt.getChild("tag");
                    NBT entityTag = EntityStack.removeUselessNBT(new NBT(entity));
                    entityTag.set("id", tag.getChild("EntityTag").getString("id"));
                    tag.set("EntityTag", entityTag);
                    nbt.set("tag", tag);
                    nbt.set(is);
                    if (EntityStack.hasCustomName((Damageable)((LivingEntity)entity))) {
                        is.setDisplayName(EntityStack.getEntityName((Damageable)entity));
                    }
                    new PlayerUtil(player).give(is.build());
                    this.setAmount(this.getAmount() - 1);
                }
                catch (Throwable e1) {
                    if (Material.getMaterial((String)(String.valueOf(entity.getType().toString().toUpperCase()) + "_SPAWN_EGG")) == null) {
                        return false;
                    }
                    ItemStackBuilder egg = new ItemStackBuilder(Material.getMaterial((String)(String.valueOf(entity.getType().toString().toUpperCase()) + "_SPAWN_EGG")));
                    NBT nbt = new NBT(egg);
                    NBT tag = nbt.hasKey("tag") ? nbt.getChild("tag") : new NBT();
                    tag.set("EntityTag", EntityStack.removeUselessNBT(new NBT(entity)));
                    nbt.set("tag", tag);
                    nbt.set(egg);
                    if (EntityStack.hasCustomName((Damageable)((LivingEntity)entity))) {
                        egg.setDisplayName(EntityStack.getEntityName((Damageable)entity));
                    }
                    new PlayerUtil(player).give(egg.build());
                    this.setAmount(this.getAmount() - 1);
                }
                entity.remove();
            }
            return false;
        }
        return false;
    }
}

