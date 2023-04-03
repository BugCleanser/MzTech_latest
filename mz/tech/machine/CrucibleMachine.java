/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import java.io.DataInputStream;
import java.io.IOException;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.item.baseMachine.Crucible;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.StackedBlock;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.PlayerUtil;
import mz.tech.util.TaskUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

public class CrucibleMachine
extends StackedBlock {
    public double lava = 0.0;

    @Override
    public String getType() {
        return "\u5769\u57da";
    }

    @Override
    public boolean onRightClick(PlayerInteractEvent event) {
        if (new PlayerUtil(event.getPlayer()).canOpen()) {
            if (event.getItem() != null && event.getItem().getType() != Material.AIR) {
                switch (event.getItem().getType()) {
                    case BUCKET: {
                        if (!this.hasBucket()) break;
                        this.getBlock().getWorld().playSound(this.getBlock().getLocation(), Sound.ITEM_BOTTLE_FILL, 1.0f, 1.0f);
                        this.removeBucket();
                        new PlayerUtil(event.getPlayer()).setSlot(event.getHand(), new ItemStackBuilder(event.getItem()).setCount(event.getItem().getAmount() - 1));
                        new PlayerUtil(event.getPlayer()).give(new ItemStack(Material.LAVA_BUCKET));
                        break;
                    }
                    case WATER_BUCKET: {
                        if (!this.hasBucket()) break;
                        this.getBlock().getWorld().playSound(this.getBlock().getLocation(), Sound.ITEM_BOTTLE_EMPTY, 1.0f, 1.0f);
                        this.removeBucket();
                        if (event.getPlayer() == null || event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                            new PlayerUtil(event.getPlayer()).setSlot(event.getHand(), new ItemStackBuilder(event.getItem()).setCount(event.getItem().getAmount() - 1));
                            new PlayerUtil(event.getPlayer()).give(new ItemStack(Material.BUCKET));
                        }
                        this.getBlock().getWorld().dropItemNaturally(this.getBlock().getLocation(), new ItemStack(Material.OBSIDIAN));
                        break;
                    }
                    default: {
                        if (!this.ifFull()) {
                            Crucible.getRaws().forEach(raw -> {
                                if (event.getItem().isSimilar(raw)) {
                                    this.getBlock().getWorld().playSound(this.getBlock().getLocation(), Sound.BLOCK_STONE_PLACE, 1.0f, 1.0f);
                                    if (event.getPlayer() == null || event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                                        new PlayerUtil(event.getPlayer()).setSlot(event.getHand(), new ItemStackBuilder(event.getItem()).setCount(event.getItem().getAmount() - 1));
                                    }
                                    this.lava += 100.0 / (double)raw.getAmount();
                                    if (this.lava > 400.0) {
                                        this.lava = 400.0;
                                    }
                                    this.clear();
                                    if (this.lava < 100.0) {
                                        FallingBlock fb = this.addFakeBlock(ItemStackBuilder.redCarpet.getBlockData());
                                        fb.teleport(fb.getLocation().add(0.0, 0.2, 0.0));
                                    } else {
                                        this.addFakeBlock(ItemStackBuilder.redWool.getBlockData());
                                    }
                                }
                            });
                            break;
                        }
                        MzTech.sendMessage((CommandSender)event.getPlayer(), "\u00a7e\u6ee1\u4e86\uff0c\u4eb2");
                    }
                }
            }
            return false;
        }
        return super.onRightClick(event);
    }

    public boolean ifFull() {
        return this.lava > 399.0;
    }

    public boolean hasBucket() {
        return this.lava > 99.0;
    }

    public void removeBucket() {
        this.lava -= 100.0;
        if (this.lava < 0.0) {
            this.lava = 0.0;
        }
        this.clear();
        if (this.lava > 0.0 && this.lava < 100.0) {
            FallingBlock fb = this.addFakeBlock(ItemStackBuilder.redCarpet.getBlockData());
            fb.teleport(fb.getLocation().add(0.0, 0.2, 0.0));
        }
    }

    @Override
    public NBT save(NBT nbt) {
        nbt.set("lava", this.lava);
        return super.save(nbt);
    }

    @Override
    public MzTechMachine load(NBT nbt) {
        this.lava = nbt.getDouble("lava");
        return super.load(nbt);
    }

    @Override
    @Deprecated
    public void loadOld(DataInputStream dfi) throws IOException {
        this.lava = dfi.readDouble();
    }

    @Override
    public void onBreak(Player player, boolean silkTouch, boolean drop) {
        if (this.hasBucket()) {
            TaskUtil.runTask((Plugin)MzTech.instance, () -> this.getBlock().setType(Material.LAVA));
        }
    }

    @Override
    public MaterialData[] getFakeBlocksType() {
        return new MaterialData[0];
    }
}

