/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.wrappers.BlockPosition
 *  com.google.common.collect.Maps
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.Particle
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.ItemSpawnEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.RegisteredListener
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package mz.tech.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mz.tech.MzTech;
import mz.tech.ReflectionWrapper;
import mz.tech.event.BlockDamageStopEvent;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.Breaking;
import mz.tech.util.PlayerUtil;
import mz.tech.util.TaskUtil;
import mz.tech.util.ToolTexture;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public final class ToolUtil {
    private static Map<Player, Map<Integer, Breaking>> playersBreakings = new HashMap<Player, Map<Integer, Breaking>>();
    private static List<Player> locks = new ArrayList<Player>();
    private static List<ItemStack> autoBreak = null;

    static {
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler(priority=EventPriority.LOWEST)
            void onBlockDamageStop(BlockDamageStopEvent event) {
                if (locks.contains(event.player)) {
                    locks.remove(event.player);
                } else if (playersBreakings.containsKey(event.player)) {
                    ((Map)playersBreakings.get(event.player)).forEach((id, breaking) -> ToolUtil.setBlockDamage(breaking.block, id, 0));
                    playersBreakings.remove(event.player);
                }
            }

            @EventHandler
            void onItemSpawn(ItemSpawnEvent event) {
                if (autoBreak != null) {
                    autoBreak.add(event.getEntity().getItemStack());
                    event.setCancelled(true);
                }
            }
        }, (Plugin)MzTech.instance);
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 1L, 1L, () -> Maps.newHashMap(playersBreakings).forEach((p, b) -> {
            short speedLevel;
            double speed = 1.0;
            if (p.getInventory().getItemInMainHand() != null) {
                switch (ToolTexture.getToolTexture(p.getInventory().getItemInMainHand())) {
                    case WOODEN: {
                        speed *= 2.0;
                        break;
                    }
                    case STONE: {
                        speed *= 4.0;
                        break;
                    }
                    case IRON: {
                        speed *= 6.0;
                        break;
                    }
                    case DIAMOND: {
                        speed *= 8.0;
                        break;
                    }
                    case NETHERITE: {
                        speed *= 9.0;
                        break;
                    }
                    case OBSIDIAN: {
                        speed *= 10.0;
                        break;
                    }
                    case BEDROCK: {
                        speed *= 11.0;
                        break;
                    }
                    case GOLDEN: {
                        speed *= 12.0;
                        break;
                    }
                }
            }
            if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().hasItemMeta() && (speedLevel = (short)p.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(Enchantment.DIG_SPEED)) > 0) {
                speed *= (double)(speedLevel * speedLevel + 1);
            }
            if (p.getPotionEffect(PotionEffectType.FAST_DIGGING) != null) {
                speed *= 1.0 + 0.2 * (double)p.getPotionEffect(PotionEffectType.FAST_DIGGING).getAmplifier();
            }
            if (p.getPotionEffect(PotionEffectType.SLOW_DIGGING) != null) {
                speed *= Math.pow(0.3, p.getPotionEffect(PotionEffectType.SLOW_DIGGING).getAmplifier());
            }
            if (!p.isOnGround()) {
                speed /= 5.0;
            } else if (new PlayerUtil((Player)p).isInWater() && !new PlayerUtil((Player)p).hasAquaAffinity()) {
                speed /= 5.0;
            }
            double finalSpeed = speed;
            Maps.newHashMap((Map)b).forEach((id, breaking) -> {
                breaking.when += finalSpeed;
                if (breaking.when >= breaking.total) {
                    b.remove(id);
                    if (b.isEmpty()) {
                        playersBreakings.remove(p);
                    }
                    ToolUtil.setBlockDamage(breaking.block, id, 0);
                    if (new PlayerUtil((Player)p).canBreakIgnoreAntiCheat(breaking.block)) {
                        if (playersBreakings.containsKey(p)) {
                            playersBreakings.get(p).forEach((id1, breaking1) -> ToolUtil.setBlockDamage(breaking1.block, id1, 0));
                            playersBreakings.remove(p);
                        }
                        locks.add((Player)p);
                        ToolUtil.showBlockBreak(breaking.block);
                        ToolUtil.breakBlock(breaking.block, p.getInventory().getItemInMainHand(), breaking.drops);
                    }
                } else {
                    ToolUtil.setBlockDamage(breaking.block, id, (int)(breaking.when * 10.0 / breaking.total));
                }
            });
        }));
    }

    public static void breakBlock(Block block, ItemStack tool, List<ItemStack> drops) {
        MzTechMachine m = MzTechMachine.asMzTechCopy(block);
        if (drops == null) {
            if (m == null) {
                block.breakNaturally();
            } else {
                m.getDropNaturally(null).forEach(is -> {
                    Item item = MzTech.dropItemStack(block, is);
                });
            }
        } else {
            block.setType(Material.AIR);
            drops.forEach(drop -> MzTech.dropItemStack(block, drop));
        }
        if (m != null) {
            m.remove();
            m.onBreak(null, false, true);
        }
    }

    public static void startBreakBlock(Player player, Block block, int time, List<ItemStack> drops) {
        if (!playersBreakings.containsKey(player)) {
            playersBreakings.put(player, new HashMap());
        }
        playersBreakings.get(player).put(MzTech.rand.nextInt(), new Breaking(block, time, drops));
    }

    public static List<ItemStack> fell(Block block) {
        return ToolUtil.fell(block, 8);
    }

    public static List<ItemStack> fell(Block block, int num) {
        ArrayList<ItemStack> r = new ArrayList<ItemStack>();
        switch (block.getType().name()) {
            case "LEAVES": 
            case "LEAVES_2": 
            case "ACACIA_LOG": 
            case "JUNGLE_LOG": 
            case "OAK_LOG": 
            case "SPRUCE_LOG": 
            case "DARK_OAK_LOG": 
            case "LOG": 
            case "LOG_2": 
            case "BIRCH_LOG": {
                if (num <= 0) break;
                --num;
                autoBreak = r;
                BlockBreakEvent e = new BlockBreakEvent(block, null);
                List<RegisteredListener> ts = PlayerUtil.ignoreAntiCheat(BlockPlaceEvent.getHandlerList());
                try {
                    Bukkit.getPluginManager().callEvent((Event)e);
                }
                finally {
                    PlayerUtil.restoreAntiCheat(BlockPlaceEvent.getHandlerList(), ts);
                }
                if (!e.isCancelled() && e.isDropItems()) {
                    r.addAll(block.getDrops());
                }
                autoBreak = null;
                if (block.getType() != Material.AIR) {
                    ToolUtil.showBlockBreak(block);
                }
                block.setType(Material.AIR);
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(0, 0, 1)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(0, 0, -1)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(1, 0, 1)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(1, 0, 0)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(1, 0, -1)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(-1, 0, 1)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(-1, 0, 0)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(-1, 0, -1)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(0, 1, 1)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(0, 1, 0)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(0, 1, -1)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(1, 1, 1)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(1, 1, 0)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(1, 1, -1)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(-1, 1, 1)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(-1, 1, 0)).getBlock(), num));
                r.addAll(ToolUtil.fell(block.getLocation().add(new Vector(-1, 1, -1)).getBlock(), num));
                break;
            }
        }
        return r;
    }

    public static void setBlockDamage(Block loc, int entity, int degree) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
        packet.getIntegers().write(0, (Object)entity);
        packet.getBlockPositionModifier().write(0, (Object)new BlockPosition(loc.getX(), loc.getY(), loc.getZ()));
        packet.getIntegers().write(1, (Object)degree);
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(packet, loc.getLocation(), 32);
    }

    public static boolean damageTool(ItemStack tool) {
        if (tool.getType().getMaxDurability() > 0) {
            if (!tool.getItemMeta().isUnbreakable() && MzTech.rand.nextDouble() <= 1.0 / (double)(tool.getItemMeta().getEnchantLevel(Enchantment.DURABILITY) + 1)) {
                tool.setDurability((short)(tool.getDurability() + 1));
            }
            if (tool.getDurability() > tool.getType().getMaxDurability()) {
                tool.setAmount(tool.getAmount() - 1);
                return true;
            }
            return false;
        }
        return false;
    }

    public static void showBlockBreak(Block block) {
        Object data;
        try {
            data = ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethodParent(block.getClass(), "getBlockData", new Class[0]), block, new Object[0]);
        }
        catch (Throwable e) {
            data = block.getState().getData();
        }
        block.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0.5), 20, 0.5, 0.5, 0.5, data);
        block.getWorld().playSound(block.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_STONE_BREAK, 1.0f, 1.0f);
    }

    private ToolUtil() {
    }
}

