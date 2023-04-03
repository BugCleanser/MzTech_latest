/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.luckyEffect;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.category.DecorationCategory;
import mz.tech.item.LuckyBow;
import mz.tech.item.LuckySword;
import mz.tech.item.MzTechItem;
import mz.tech.item.SpawnEgg;
import mz.tech.item.sundry.EmptySpawnEgg;
import mz.tech.luckyEffect.LuckyEffect;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropItemsLuckyEffect
extends LuckyEffect {
    public List<ItemStack> itemStacks;

    public DropItemsLuckyEffect(List<ItemStack> iss) {
        this.itemStacks = iss;
    }

    public DropItemsLuckyEffect() {
        this.itemStacks = new ArrayList<ItemStack>();
    }

    public DropItemsLuckyEffect(ItemStack ... iss) {
        this(Lists.newArrayList((Object[])iss));
    }

    public DropItemsLuckyEffect(Material ... iss) {
        this(Lists.newArrayList(Lists.newArrayList((Object[])iss).stream().map(ItemStack::new).iterator()));
    }

    public List<ItemStack> getItemStacks() {
        return this.itemStacks;
    }

    @Override
    public void toggle(Player player, Location loc) {
        this.getItemStacks().forEach(is -> {
            Item item = MzTech.dropItemStack(loc, is);
        });
    }

    public static void regAll() {
        DropItemsLuckyEffect.reg(new DropItemsLuckyEffect(new ItemStack[0]){

            @Override
            public void toggle(Player player, Location loc) {
                try {
                    loc.getWorld().dropItemNaturally(loc, new ItemStack(MzTech.randValue(Material.values()), MzTech.rand.nextInt(9)));
                }
                catch (Throwable e) {
                    this.toggle(player, loc);
                }
            }
        });
        DropItemsLuckyEffect.reg(new DropItemsLuckyEffect(Material.IRON_AXE, Material.IRON_PICKAXE, Material.IRON_HOE, Material.IRON_SWORD));
        DropItemsLuckyEffect.reg(LuckyEffect.LuckyEffectType.GOOD, new DropItemsLuckyEffect(Material.DIAMOND_AXE, Material.DIAMOND_PICKAXE, Material.DIAMOND_HOE, Material.DIAMOND_SWORD));
        DropItemsLuckyEffect.reg(LuckyEffect.LuckyEffectType.GOOD, new DropItemsLuckyEffect(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS));
        DropItemsLuckyEffect.reg(new DropItemsLuckyEffect(ItemStackBuilder.saplings));
        DropItemsLuckyEffect.reg(LuckyEffect.LuckyEffectType.GOOD, new DropItemsLuckyEffect(new ItemStack(Material.DIAMOND, 6), new ItemStack(Material.EMERALD, 6), new ItemStack(Material.GOLD_INGOT, 6), new ItemStack(Material.IRON_INGOT, 6)));
        DropItemsLuckyEffect.reg(new DropItemsLuckyEffect(ItemStackBuilder.dyes));
        DropItemsLuckyEffect.reg(LuckyEffect.LuckyEffectType.GOOD, new DropItemsLuckyEffect(){

            @Override
            public List<ItemStack> getItemStacks() {
                return Lists.newArrayList((Object[])new ItemStack[]{(ItemStack)MzTech.randValue(Lists.newArrayList(MzTechItem.getTypes().stream().filter(item -> item.getCategory() == DecorationCategory.instance).iterator()))});
            }
        });
        DropItemsLuckyEffect.reg(LuckyEffect.LuckyEffectType.GOOD, new DropItemsLuckyEffect(){

            @Override
            public List<ItemStack> getItemStacks() {
                ArrayList<ItemStack> rl = new ArrayList<ItemStack>();
                int i = 0;
                while (i < 5) {
                    try {
                        EntityType t = MzTech.randValue(EntityType.values());
                        if (t.isSpawnable() && t.isAlive() && !EmptySpawnEgg.isBoss(t)) {
                            rl.add(new SpawnEgg(t.toString()));
                        }
                    }
                    catch (Throwable e) {
                        --i;
                    }
                    ++i;
                }
                return rl;
            }
        });
        new DropItemsLuckyEffect(new LuckyBow()).reg(LuckyEffect.LuckyEffectType.GOOD);
        new DropItemsLuckyEffect(new LuckySword()).reg(LuckyEffect.LuckyEffectType.GOOD);
    }
}

