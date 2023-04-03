/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.sundry;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import mz.tech.MzTech;
import mz.tech.category.SundryCategory;
import mz.tech.item.Consumeable;
import mz.tech.item.MzTechItem;
import mz.tech.item.SpawnEgg;
import mz.tech.item.baseMachine.ConversionTable;
import mz.tech.recipe.KillEntityRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CNMB
extends MzTechItem
implements ConversionTable.Sellable,
Consumeable {
    static {
        ArrayList li = Lists.newArrayList((Object[])new ItemStack[]{new ItemStackBuilder(new SpawnEgg("llama")).setLoreList("\u00a77\u51e0\u7387\uff1a 50%").build()});
        try {
            li.add(new ItemStackBuilder(new SpawnEgg("trader_llama")).setLoreList("\u00a77\u51e0\u7387\uff1a 80%").build());
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        new KillEntityRecipe((ItemStack)new CNMB(), li.toArray(new ItemStack[0]), new int[]{50, 80}).reg("\u8349\u6ce5\u9a6c\u5e01");
    }

    public CNMB() {
        super(new ItemStackBuilder(Material.EMERALD).setLocName("\u00a7d\u8349\u6ce5\u9a6c\u5e01").setLoreList("\u00a77\u8349\u6ce5\u9a6c\u7684\u5e01", "\u00a77\u7f8a\u9a7c\u5e01", "\u00a74\u53f3\u952e \u00a77\u5151\u6362\u4e3a\u00a7e\uffe5" + MzTech.instance.getConfig().get("cnmb")).build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u8349\u6ce5\u9a6c\u5e01";
    }

    @Override
    public boolean canConsume(Player player) {
        return true;
    }

    @Override
    public int getFoodLevel() {
        return 0;
    }

    @Override
    public void onConsume(Player player) {
        ConversionTable.econ.depositPlayer((OfflinePlayer)player, this.getPrice());
        MzTech.sendMessage((CommandSender)player, "\u00a7a\uffe5" + this.getPrice() + "\u5df2\u6dfb\u52a0\u81f3\u60a8\u7684\u8d26\u6237");
    }

    @Override
    public Sound getSound() {
        try {
            return Sound.BLOCK_NOTE_BELL;
        }
        catch (Throwable e) {
            return Enum.valueOf(Sound.class, "BLOCK_NOTE_BLOCK_BELL");
        }
    }

    @Override
    public double getPrice() {
        Object cnmb = MzTech.instance.getConfig().get("cnmb");
        double value = 1.0;
        if (cnmb instanceof Integer) {
            value = ((Integer)cnmb).intValue();
        } else if (cnmb instanceof Double) {
            value = (Double)cnmb;
        }
        return value;
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

