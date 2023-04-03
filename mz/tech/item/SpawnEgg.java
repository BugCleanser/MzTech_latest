/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.EntityType
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SpawnEggMeta
 */
package mz.tech.item;

import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class SpawnEgg
extends MzTechItem {
    public EntityType type;

    public SpawnEgg(ItemStack is) {
        super(is);
        if (is.getType().name().equals("MONSTER_EGG")) {
            SpawnEggMeta im = (SpawnEggMeta)is.getItemMeta();
            this.type = im.getSpawnedType();
        } else {
            this.type = Enum.valueOf(EntityType.class, is.getType().name().substring(0, is.getType().name().length() - "_SPAWN_EGG".length()));
        }
    }

    public SpawnEgg(String type) {
        super(SpawnEgg.newItemStack(type));
        this.type = Enum.valueOf(EntityType.class, type.toUpperCase());
    }

    public static ItemStack newItemStack(String type) {
        ItemStack is;
        try {
            is = new ItemStack(Material.MONSTER_EGG);
            SpawnEggMeta im = (SpawnEggMeta)is.getItemMeta();
            im.setSpawnedType(Enum.valueOf(EntityType.class, type.toUpperCase()));
            is.setItemMeta((ItemMeta)im);
        }
        catch (Throwable e) {
            is = new ItemStack(Enum.valueOf(Material.class, String.valueOf(type.toUpperCase()) + "_SPAWN_EGG"));
        }
        return is;
    }

    @Override
    public MzTechCategory getCategory() {
        return null;
    }

    @Override
    public String getTypeName() {
        return null;
    }
}

