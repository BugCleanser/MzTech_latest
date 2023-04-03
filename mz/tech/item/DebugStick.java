/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Damageable
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 */
package mz.tech.item;

import mz.tech.EntityStack;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public class DebugStick
extends MzTechItem {
    public DebugStick() {
        super(new ItemStackBuilder(Material.STICK).setLocName("\u00a71\u8c03\u8bd5\u68d2").build());
    }

    @Override
    public String getTypeName() {
        return "\u8c03\u8bd5\u68d2";
    }

    @Override
    public MzTechCategory getCategory() {
        return null;
    }

    @Override
    public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        if (!player.isSneaking()) {
            entity.addPassenger((Entity)player);
        } else {
            MzTech.sendMessage((CommandSender)player, "\u00a7a\u5b9e\u4f53\u7c7b\u578bMID\uff1a " + entity.getType().name());
            MzTech.sendMessage((CommandSender)player, "\u00a7a\u5b9e\u4f53id\uff1a " + entity.getEntityId());
            MzTech.sendMessage((CommandSender)player, "\u00a7a\u5b9e\u4f53uuid\uff1a " + entity.getUniqueId().toString());
            MzTech.sendMessage((CommandSender)player, "\u00a7a\u5b9e\u4f53\u7ffb\u8bd1\u524d\u540d\u79f0\uff1a " + EntityStack.getEntityUnlocalizedName(entity).replace("\u00a7", "&"));
            if (entity instanceof Damageable) {
                if (((Damageable)entity).getCustomName() != null) {
                    MzTech.sendMessage((CommandSender)player, "\u00a7a\u5b9e\u4f53\u540d\uff1a " + ((Damageable)entity).getCustomName().replace("\u00a7", "&"));
                }
                MzTech.sendMessage((CommandSender)player, "\u00a7a\u5b9e\u4f53\u663e\u793a\u540d\uff1a " + EntityStack.getEntityName((Damageable)entity).replace("\u00a7", "&"));
            }
            MzTech.sendMessage((CommandSender)player, "\u00a7a\u5b9e\u4f53\u53ef\u7528\u4e8e\u533a\u5206\u7684nbt\uff1a " + EntityStack.removeUselessNBT(new NBT(entity)).toString().replace("\u00a7", "&"));
        }
        return false;
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        MzTech.sendMessage((CommandSender)player, "\u00a7a\u65b9\u5757\u7c7b\u578bMID\uff1a " + block.getType().name());
        MzTech.sendMessage((CommandSender)player, "\u00a7a\u65b9\u5757\u6570\u636e\uff1a " + block.getData());
        MzTech.sendMessage((CommandSender)player, "\u00a7a\u65b9\u5757\u5750\u6807\uff1a \uff08" + block.getWorld().getName() + "\uff0c" + block.getX() + "\uff0c" + block.getY() + "\uff0c" + block.getZ() + "\uff09");
        MzTech.sendMessage((CommandSender)player, "\u00a7a\u65b9\u5757NBT\uff1a " + new NBT(block));
        MzTechMachine mzt = MzTechMachine.asMzTechCopy(block);
        if (mzt != null) {
            MzTech.sendMessage((CommandSender)player, "\u00a7a\u673a\u5668\u7c7b\u578b\uff1a " + mzt.getType());
        }
        return false;
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        MzTech.sendMessage((CommandSender)player, "\u00a7a\u5750\u6807\uff1a " + player.getLocation().getX() + " \uff0c " + player.getLocation().getY() + " \uff0c " + player.getLocation().getZ());
        return true;
    }
}

