/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item;

import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.machine.BigLuckyBlock;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class BigLuckyBlockItem
extends MzTechItem {
    Location centre;

    public BigLuckyBlockItem() {
        this(1, null);
    }

    public BigLuckyBlockItem(int i, Location centre) {
        super(new ItemStackBuilder(BigLuckyBlockItem.getBlockType(i)).setLocName("\u00a7e\u5927\u5e78\u8fd0\u65b9\u5757").setLoreList("\u00a77\u5de8\u578b\u5e78\u8fd0\u65b9\u5757\u7684\u4e00\u90e8\u5206").build());
        this.centre = centre;
    }

    public static ItemStack getBlockType(int i) {
        switch (i) {
            case 1: {
                return ItemStackBuilder.yellowConcrete;
            }
            case 2: {
                return ItemStackBuilder.yellowWool;
            }
            case 3: {
                return ItemStackBuilder.whiteConcrete;
            }
            case 4: {
                return ItemStackBuilder.yellowTerracotta;
            }
        }
        throw new RuntimeException(String.valueOf(i) + "\u4e0d\u662f\u4e00\u4e2a\u6709\u6548\u7684\u65b9\u5757\u5e8f\u53f7");
    }

    @Override
    public String getTypeName() {
        return "\u5927\u5e78\u8fd0\u65b9\u5757";
    }

    @Override
    public MzTechCategory getCategory() {
        return null;
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        if (this.centre == null) {
            return null;
        }
        return new BigLuckyBlock(this.centre).setBlock(block);
    }
}

