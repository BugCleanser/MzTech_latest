/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.SoundCategory
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Chest
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.inventory.DoubleChestInventory
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.material.MaterialData
 *  org.bukkit.material.PistonBaseMaterial
 *  org.bukkit.material.Stairs
 *  org.bukkit.material.Step
 */
package mz.tech.item.tool;

import java.lang.reflect.Method;
import mz.tech.ReflectionWrapper;
import mz.tech.category.ToolCategory;
import mz.tech.item.Tool;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.material.PistonBaseMaterial;
import org.bukkit.material.Stairs;
import org.bukkit.material.Step;

public class Wrench
extends Tool {
    static {
        new SmilingCraftingTableRecipe(new Wrench(), Material.IRON_INGOT, null, 0, null, 0, null, 0, null, 0).reg("\u6273\u624b");
    }

    public Wrench() {
        super(new ItemStack(Material.IRON_HOE));
        this.setName("\u00a76\u6273\u624b");
    }

    @Override
    public ToolCategory getCategory() {
        return ToolCategory.instance;
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        switch (block.getType()) {
            case PISTON_STICKY_BASE: 
            case PISTON_BASE: {
                if (!((PistonBaseMaterial)block.getState().getData()).isPowered()) break;
                return false;
            }
            case CHEST: 
            case TRAPPED_CHEST: {
                if (!(((Chest)block.getState()).getInventory() instanceof DoubleChestInventory)) break;
                return false;
            }
            case BED_BLOCK: 
            case PISTON_EXTENSION: 
            case PISTON_MOVING_PIECE: {
                return false;
            }
        }
        BlockPlaceEvent e = new BlockPlaceEvent(block, block.getState(), block, new ItemStack(Material.AIR), player, true);
        Bukkit.getPluginManager().callEvent((Event)e);
        if (!e.isCancelled()) {
            BlockState state = e.getBlock().getState();
            MaterialData data = state.getData();
            Method getFacing = null;
            Method setFacingDirection = null;
            if (data instanceof Step) {
                ((Step)data).setInverted(!((Step)data).isInverted());
                state.setData(data);
                state.update();
            } else if (data instanceof Stairs) {
                data.setData((byte)((data.getData() + 1) % 8));
                state.setData(data);
                state.update();
            } else {
                try {
                    getFacing = ReflectionWrapper.getMethodParent(data.getClass(), "getFacing", new Class[0]);
                    setFacingDirection = ReflectionWrapper.getMethodParent(data.getClass(), "setFacingDirection", BlockFace.class);
                }
                catch (Exception exception) {
                    return false;
                }
                int i = ((BlockFace)ReflectionWrapper.invokeMethod(getFacing, data, new Object[0])).ordinal() + 1;
                while (i < BlockFace.values().length) {
                    try {
                        setFacingDirection.invoke(data, BlockFace.values()[i]);
                        state.setData(data);
                        state.update();
                        state = e.getBlock().getState();
                        data = state.getData();
                        if (((BlockFace)getFacing.invoke(data, new Object[0])).ordinal() == i) {
                            break;
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    ++i;
                }
                if (i == BlockFace.values().length) {
                    i = 0;
                    while (i < BlockFace.values().length) {
                        try {
                            setFacingDirection.invoke(data, BlockFace.values()[i]);
                            state.setData(data);
                            state.update();
                            state = e.getBlock().getState();
                            data = state.getData();
                            if (((BlockFace)getFacing.invoke(data, new Object[0])).ordinal() == i) {
                                break;
                            }
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        ++i;
                    }
                }
            }
            if (player.getGameMode() != GameMode.CREATIVE) {
                this.damage();
            }
            try {
                block.getWorld().playSound(block.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 1.0f, 10.0f);
            }
            catch (Throwable e1) {
                block.getWorld().playSound(block.getLocation(), Enum.valueOf(Sound.class, "BLOCK_WOODEN_BUTTON_CLICK_ON"), SoundCategory.BLOCKS, 1.0f, 10.0f);
            }
        }
        return false;
    }

    @Override
    public String getTypeName() {
        return "\u6273\u624b";
    }
}

