/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package mz.tech.item.sundry;

import com.google.common.collect.Lists;
import java.util.List;
import mz.tech.category.SundryCategory;
import mz.tech.item.Consumeable;
import mz.tech.item.MzTechItem;
import mz.tech.item.baseMachine.MedicineBox;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Medicine
extends MzTechItem
implements Consumeable {
    static {
        new MzTechRecipe(){

            @Override
            public List<ItemStack> getResults() {
                return Lists.newArrayList((Object[])new ItemStack[]{new Medicine()});
            }

            @Override
            public List<RawItem> getRaws() {
                return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new MedicineBox())});
            }

            @Override
            public List<ItemStack> getMachines() {
                return Lists.newArrayList((Object[])new ItemStack[]{new MedicineBox().setLoreList("\u00a77\u5728\u836f\u54c1\u7bb1\u4e2d\u83b7\u5f97").build()});
            }
        }.reg("\u836f\u7247");
    }

    public Medicine() {
        super(new ItemStackBuilder("NETHER_STALK", 0, "nether_wart", 1).setLocName("\u00a7c\u836f\u7247").build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u836f\u7247";
    }

    @Override
    public boolean canConsume(Player player) {
        return true;
    }

    @Override
    public void onConsume(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 2), false);
        player.setFireTicks(-1);
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

