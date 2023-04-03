/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mz.tech.DataFile;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.ReflectionWrapper;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

public abstract class StackedBlock
extends MzTechMachine {
    List<FallingBlock> fakeBlocks;

    static {
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 100L, 100L, () -> MzTechMachine.forEach(StackedBlock.class, machine -> machine.fakeBlocks.forEach(fallingBlock -> {
            if (fallingBlock == null) {
                return;
            }
            Class<?> craftEntityClass = ReflectionWrapper.getCraftBukkitClass("entity.CraftEntity");
            Class<?> nmsEntityClass = ReflectionWrapper.getNMSClass("Entity");
            Object nmsEntity = ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(craftEntityClass, "getHandle", new Class[0]), fallingBlock, new Object[0]);
            try {
                Object nbt = ReflectionWrapper.invokeStaticMethod(ReflectionWrapper.getMethod(ReflectionWrapper.getNMSClass("CommandAbstract"), "a", nmsEntityClass), nmsEntity);
                int t = 1;
                ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethodParent(nbt.getClass(), "setInt", String.class, Integer.TYPE), nbt, "Time", t);
                ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(nmsEntityClass, "f", nbt.getClass()), nmsEntity, nbt);
            }
            catch (Throwable e) {
                Object com = ReflectionWrapper.newInstance(ReflectionWrapper.getConstructor(ReflectionWrapper.getNMSClass("CommandDataAccessorEntity"), nmsEntityClass), nmsEntity);
                Object nbt = ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(com.getClass(), "a", new Class[0]), com, new Object[0]);
                int t = 1;
                ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethodParent(nbt.getClass(), "setInt", String.class, Integer.TYPE), nbt, "Time", t);
                try {
                    ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(nmsEntityClass, "f", nbt.getClass()), nmsEntity, nbt);
                }
                catch (Throwable e2) {
                    ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(nmsEntityClass, "load", nbt.getClass()), nmsEntity, nbt);
                }
            }
        })));
    }

    @Override
    public MzTechMachine setBlock(Block block) {
        this.fakeBlocks = new ArrayList<FallingBlock>();
        return super.setBlock(block);
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public boolean onPlace(BlockPlaceEvent event) {
        MaterialData[] materialDataArray = this.getFakeBlocksType();
        int n = materialDataArray.length;
        int n2 = 0;
        while (n2 < n) {
            MaterialData blockData = materialDataArray[n2];
            this.addFakeBlock(blockData);
            ++n2;
        }
        return true;
    }

    public FallingBlock addFakeBlock(MaterialData blockData) {
        FallingBlock block = this.getBlock().getWorld().spawnFallingBlock(this.getBlock().getLocation().add(0.5, 0.0, 0.5), blockData);
        block.setGravity(false);
        block.setInvulnerable(true);
        block.setDropItem(false);
        this.fakeBlocks.add(block);
        return block;
    }

    @Override
    public boolean onMove(Location targetLocation) {
        TaskUtil.runTaskLater((Plugin)MzTech.instance, 3L, () -> this.fakeBlocks.forEach(fallingBlock -> fallingBlock.teleport(targetLocation.add(0.5, 0.0, 0.5))));
        return true;
    }

    @Override
    public NBT save(NBT nbt) {
        ArrayList ns = new ArrayList();
        this.fakeBlocks.forEach(f -> ns.add(new NBT(f.getUniqueId())));
        nbt.set("fakeBlocks", ns.toArray());
        return super.save(nbt);
    }

    @Override
    public MzTechMachine load(NBT nbt) {
        this.fakeBlocks = Lists.newArrayList(nbt.getUUIDList("fakeBlocks").stream().map(Bukkit::getEntity).iterator());
        return super.load(nbt);
    }

    @Override
    @Deprecated
    public void loadOld(DataInputStream dfi) throws IOException {
        int num = dfi.readInt();
        while (num > 0) {
            FallingBlock entity = (FallingBlock)Bukkit.getEntity((UUID)DataFile.loadUUID(dfi));
            if (entity != null && !entity.isDead()) {
                this.fakeBlocks.add(entity);
            }
            --num;
        }
    }

    public abstract MaterialData[] getFakeBlocksType();

    public List<FallingBlock> getFakeBlocks() {
        return this.fakeBlocks;
    }

    public void clear() {
        this.fakeBlocks.forEach(f -> f.remove());
        this.fakeBlocks = new ArrayList<FallingBlock>();
    }

    @Override
    public void remove() {
        this.fakeBlocks.forEach(fakeBlock -> {
            if (fakeBlock != null) {
                fakeBlock.remove();
            }
        });
        super.remove();
    }
}

