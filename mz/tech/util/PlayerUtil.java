/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.RegisteredListener
 */
package mz.tech.util;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.ReflectionWrapper;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

public class PlayerUtil {
    public static Class<?> entityPlayerClass = ReflectionWrapper.getNMSClass("EntityPlayer");
    public static Class<?> worldServerClass = ReflectionWrapper.getNMSClass("WorldServer");
    public static Class<?> craftServerClass = ReflectionWrapper.getCraftBukkitClass("CraftServer");
    public static Method craftServerGetServer = ReflectionWrapper.getMethod(craftServerClass, "getServer", new Class[0]);
    public static Class<?> playerInteractManagerClass = ReflectionWrapper.getNMSClass("PlayerInteractManager");
    public static Constructor<?> entityPlayerConstructor = ReflectionWrapper.getConstructor(entityPlayerClass, ReflectionWrapper.getNMSClass("MinecraftServer"), worldServerClass, GameProfile.class, playerInteractManagerClass);
    public static Class<?> entityHumanClass = ReflectionWrapper.getNMSClass("EntityHuman");
    public static Class<Enum> enumItemSlotClass = ReflectionWrapper.getNMSClass("EnumItemSlot");
    public static Object enumItemSlot_MAINHAND = Enum.valueOf(enumItemSlotClass, "MAINHAND");
    public static Object enumItemSlot_OFFHAND = Enum.valueOf(enumItemSlotClass, "OFFHAND");
    public static Object enumItemSlot_FEET = Enum.valueOf(enumItemSlotClass, "FEET");
    public static Object enumItemSlot_LEGS = Enum.valueOf(enumItemSlotClass, "LEGS");
    public static Object enumItemSlot_CHEST = Enum.valueOf(enumItemSlotClass, "CHEST");
    public static Object enumItemSlot_HEAD = Enum.valueOf(enumItemSlotClass, "HEAD");
    public static Method entityHumanSetSlot = ReflectionWrapper.getMethod(entityHumanClass, "setSlot", enumItemSlotClass, ItemStackBuilder.itemStackClass);
    public Player player;

    public PlayerUtil(Player player) {
        this.player = player;
    }

    public boolean canOpen() {
        return this.player.getItemInHand() == null || this.player.getItemInHand().getType() == Material.AIR || !this.player.isSneaking();
    }

    public boolean isInWater() {
        switch (this.player.getLocation().add(0.0, this.player.getEyeHeight(), 0.0).getBlock().getType().toString()) {
            case "STATIONARY_WATER": 
            case "WATER": {
                return true;
            }
        }
        return false;
    }

    public void give(ItemStack is) {
        new ItemStackBuilder(is).give(this.player);
    }

    public boolean hasAquaAffinity() {
        ItemStack[] itemStackArray = this.player.getInventory().getArmorContents();
        int n = itemStackArray.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack armor = itemStackArray[n2];
            if (armor != null && armor.getType() != Material.AIR && armor.hasItemMeta() && (short)armor.getItemMeta().getEnchantLevel(Enchantment.WATER_WORKER) > 0) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public ItemStack getItemInHand() {
        return this.player.getItemInHand();
    }

    public ItemStack getItemInMainHand() {
        return this.player.getInventory().getItemInMainHand();
    }

    public ItemStack getItemInOffHand() {
        return this.player.getInventory().getItemInOffHand();
    }

    public void setSlot(EquipmentSlot hand, ItemStack item) {
        switch (hand) {
            case HAND: {
                this.player.getInventory().setItemInMainHand(item);
                break;
            }
            case OFF_HAND: {
                this.player.getInventory().setItemInOffHand(item);
                break;
            }
            case CHEST: {
                this.player.getInventory().setChestplate(item);
                break;
            }
            case FEET: {
                this.player.getInventory().setBoots(item);
                break;
            }
            case HEAD: {
                this.player.getInventory().setHelmet(item);
                break;
            }
            case LEGS: {
                this.player.getInventory().setLeggings(item);
            }
        }
    }

    public EquipmentSlot getUsedHand() {
        if (ItemStackBuilder.isEmpty(this.getItemInMainHand())) {
            if (ItemStackBuilder.isEmpty(this.getItemInOffHand())) {
                return EquipmentSlot.HAND;
            }
            return EquipmentSlot.OFF_HAND;
        }
        return EquipmentSlot.HAND;
    }

    public ItemStack getSlot(EquipmentSlot hand) {
        switch (hand) {
            case HAND: {
                return this.player.getInventory().getItemInMainHand();
            }
            case OFF_HAND: {
                return this.player.getInventory().getItemInOffHand();
            }
            case CHEST: {
                return this.player.getInventory().getChestplate();
            }
            case FEET: {
                return this.player.getInventory().getBoots();
            }
            case HEAD: {
                return this.player.getInventory().getHelmet();
            }
            case LEGS: {
                return this.player.getInventory().getLeggings();
            }
        }
        return null;
    }

    public static void setNmsPlayerSlot(Object nmsPlayer, EquipmentSlot hand, ItemStackBuilder itemStack) {
        ReflectionWrapper.invokeMethod(entityHumanSetSlot, nmsPlayer, hand == EquipmentSlot.HAND ? enumItemSlot_MAINHAND : (hand == EquipmentSlot.OFF_HAND ? enumItemSlot_OFFHAND : (hand == EquipmentSlot.FEET ? enumItemSlot_FEET : (hand == EquipmentSlot.LEGS ? enumItemSlot_LEGS : (hand == EquipmentSlot.CHEST ? enumItemSlot_CHEST : (hand == EquipmentSlot.HEAD ? enumItemSlot_HEAD : null))))), itemStack.getNmsItemStack());
    }

    public static List<Plugin> getAntiCheatPlugins() {
        return Lists.newArrayList(MzTech.instance.getConfig().getStringList("antiCheatPlugins").stream().map(n -> Bukkit.getPluginManager().getPlugin(n)).filter(n -> n != null).iterator());
    }

    public static List<RegisteredListener> ignoreAntiCheat(HandlerList hl) {
        ArrayList<RegisteredListener> ts = new ArrayList<RegisteredListener>();
        List<Plugin> ps = PlayerUtil.getAntiCheatPlugins();
        RegisteredListener[] registeredListenerArray = hl.getRegisteredListeners();
        int n = registeredListenerArray.length;
        int n2 = 0;
        while (n2 < n) {
            RegisteredListener li = registeredListenerArray[n2];
            if (ps.contains(li.getPlugin())) {
                ts.add(li);
                hl.unregister(li);
            }
            ++n2;
        }
        return ts;
    }

    public static void restoreAntiCheat(HandlerList hl, List<RegisteredListener> ts) {
        ts.forEach(li -> hl.register(li));
    }

    public boolean canBuild(Block l) {
        if (this.player == null) {
            return false;
        }
        BlockPlaceEvent event = new BlockPlaceEvent(l, l.getState(), l, new ItemStack(Material.STONE), this.player, true, EquipmentSlot.HAND);
        Bukkit.getPluginManager().callEvent((Event)event);
        return !event.isCancelled();
    }

    public boolean canBuildIgnoreAntiCheat(Block b) {
        List<RegisteredListener> ts = PlayerUtil.ignoreAntiCheat(BlockPlaceEvent.getHandlerList());
        boolean rb = true;
        try {
            rb = this.canBuild(b);
        }
        finally {
            PlayerUtil.restoreAntiCheat(BlockPlaceEvent.getHandlerList(), ts);
        }
        return rb;
    }

    public boolean canBreak(Block b) {
        if (this.player == null) {
            return false;
        }
        BlockBreakEvent event = new BlockBreakEvent(b, this.player);
        Bukkit.getPluginManager().callEvent((Event)event);
        return !event.isCancelled();
    }

    public boolean canBreakIgnoreAntiCheat(Block b) {
        List<RegisteredListener> ts = PlayerUtil.ignoreAntiCheat(BlockBreakEvent.getHandlerList());
        boolean rb = true;
        try {
            rb = this.canBreak(b);
        }
        finally {
            PlayerUtil.restoreAntiCheat(BlockBreakEvent.getHandlerList(), ts);
        }
        return rb;
    }

    public void sendMessage(String message) {
        MzTech.sendMessage((CommandSender)this.player, message);
    }
}

