/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.block.Block
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.event.MachineBreakEvent;
import mz.tech.item.MzTechItem;
import mz.tech.machine.MzTechMachine;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class PlacedItem
extends MzTechMachine {
    public String realType;

    public PlacedItem(Block block, String realType) {
        this.setBlock(block);
        this.realType = realType;
    }

    @Override
    public String getType() {
        return "\u653e\u7f6e\u7684\u7269\u54c1";
    }

    @Override
    public NBT save(NBT nbt) {
        nbt.set("type", this.realType);
        return super.save(nbt);
    }

    @Override
    public MzTechMachine load(NBT nbt) {
        this.realType = nbt.getString("type");
        return super.load(nbt);
    }

    @Override
    @Deprecated
    public void loadOld(DataInputStream dfi) throws IOException {
        byte[] bs = new byte[dfi.readInt()];
        dfi.read(bs);
        this.realType = new String(bs, MzTech.UTF8);
        super.loadOld(dfi);
    }

    @Override
    public List<ItemStack> getDropAccurate(MachineBreakEvent event) {
        return Lists.newArrayList((Object[])new ItemStack[]{MzTechItem.get(this.realType)});
    }
}

