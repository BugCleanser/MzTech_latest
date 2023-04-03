/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  com.comphenix.protocol.wrappers.EnumWrappers$ItemSlot
 *  com.comphenix.protocol.wrappers.EnumWrappers$NativeGameMode
 *  com.comphenix.protocol.wrappers.EnumWrappers$PlayerInfoAction
 *  com.comphenix.protocol.wrappers.PlayerInfoData
 *  com.comphenix.protocol.wrappers.WrappedDataWatcher
 *  com.comphenix.protocol.wrappers.WrappedGameProfile
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.server.v1_12_R1.DataWatcher$Item
 *  net.minecraft.server.v1_12_R1.DataWatcherObject
 *  net.minecraft.server.v1_12_R1.Entity
 *  net.minecraft.server.v1_12_R1.EntityCreeper
 *  net.minecraft.server.v1_12_R1.EntityHuman
 *  net.minecraft.server.v1_12_R1.EntityPlayer
 *  net.minecraft.server.v1_12_R1.EntityTypes
 *  net.minecraft.server.v1_16_R3.Block
 *  net.minecraft.server.v1_16_R3.Blocks
 *  net.minecraft.server.v1_16_R3.EntityTypes
 *  net.minecraft.server.v1_16_R3.EnumItemSlot
 *  net.minecraft.server.v1_16_R3.IBlockData
 *  net.minecraft.server.v1_16_R3.IRegistry
 *  net.minecraft.server.v1_16_R3.ItemStack
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Entity
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SkullMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package mz.tech;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.ReflectionWrapper;
import mz.tech.event.CreativeSetSlotEvent;
import mz.tech.event.ShowBlockEvent;
import mz.tech.event.ShowBlockNbtEvent;
import mz.tech.event.ShowItemEvent;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.NbtUtil;
import mz.tech.util.TaskUtil;
import net.minecraft.server.v1_12_R1.DataWatcher;
import net.minecraft.server.v1_12_R1.DataWatcherObject;
import net.minecraft.server.v1_12_R1.EntityCreeper;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class CuteCreeper
implements Listener {
    public static Field entityId = ReflectionWrapper.getField(net.minecraft.server.v1_12_R1.Entity.class, "id");
    public static Field gameProfileLegacy = ReflectionWrapper.getField(GameProfile.class, "legacy");
    public static UUID uid = UUID.fromString("02760a74-2d3d-48f4-9858-79d3b549ff9b");
    public static String textureValue = "ewogICJ0aW1lc3RhbXAiIDogMTYxNjg0OTgwMjA5NiwKICAicHJvZmlsZUlkIiA6ICIxZDUyMzNkMzg4NjI0YmFmYjAwZTMxNTBhN2FhM2E4OSIsCiAgInByb2ZpbGVOYW1lIiA6ICIwMDAwMDAwMDAwMDAwMDBKIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzkxODI0YmY1MDkyZjU2ZTA3OWQyMGFhOTRkNzE5YWFkMzI1YjQ3MjQ5N2QyMmIyZWJmMDhkMzYxZWI0MmZkZjUiCiAgICB9CiAgfQp9";
    public static org.bukkit.inventory.ItemStack head = ItemStackBuilder.newSkull("\u82e6\u529b\u6015\u5a18", uid, textureValue);
    public static org.bukkit.inventory.ItemStack rawHead = new ItemStackBuilder("skull_item", 4, "creeper_head", 1);
    public static NBT cnbt = new NBT(head).getChild("tag").getChild("SkullOwner").set("Id", uid.toString());
    public static NBT nbt = new NBT().set("SkullOwner", cnbt).set("Owner", cnbt);
    public static int rawSkullId;
    public static int rawSkullWallId;
    public static int skullId;
    public static int skullWallId;
    public static DataWatcherObject<Byte> skinData;
    public static int creeperId;
    public static int playerId;

    static {
        try {
            rawSkullId = Block.getCombinedId((IBlockData)Blocks.CREEPER_HEAD.getBlockData());
            rawSkullWallId = Block.getCombinedId((IBlockData)Blocks.CREEPER_WALL_HEAD.getBlockData());
            skullId = Block.getCombinedId((IBlockData)Blocks.PLAYER_HEAD.getBlockData());
            skullWallId = Block.getCombinedId((IBlockData)Blocks.PLAYER_WALL_HEAD.getBlockData());
        }
        catch (Throwable e) {
            skullWallId = -1;
            skullId = -1;
            rawSkullWallId = -1;
            rawSkullId = -1;
        }
        try {
            creeperId = EntityTypes.b.a(EntityCreeper.class);
            playerId = EntityTypes.b.a(EntityPlayer.class);
        }
        catch (Throwable e) {
            creeperId = IRegistry.ENTITY_TYPE.a((Object)net.minecraft.server.v1_16_R3.EntityTypes.CREEPER);
            playerId = IRegistry.ENTITY_TYPE.a((Object)net.minecraft.server.v1_16_R3.EntityTypes.PLAYER);
        }
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.ENTITY_METADATA}){

            public void onPacketSending(PacketEvent event) {
                if (event.isCancelled()) {
                    return;
                }
                try {
                    if (MzTech.instance.getConfig().getBoolean("cuteCreeper")) {
                        if (event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
                            Integer id = (Integer)event.getPacket().getIntegers().read(0);
                            block5: for (Entity e : event.getPlayer().getWorld().getEntities()) {
                                if (e.getEntityId() != id.intValue()) continue;
                                switch (e.getType()) {
                                    case CREEPER: {
                                        List l = (List)MzTech.castBypassCheck((List)event.getPacket().getSpecificModifier(List.class).read(0));
                                        if (l != null && l.size() > 0 && ((DataWatcher.Item)l.get(0)).a().a() == skinData.a()) break block5;
                                        event.setCancelled(true);
                                        break;
                                    }
                                }
                                break;
                            }
                            return;
                        }
                        if (creeperId == (Integer)event.getPacket().getIntegers().read(1)) {
                            event.setCancelled(true);
                            TaskUtil.runTask((Plugin)MzTech.instance, () -> {
                                block9: {
                                    try {
                                        if (!event.isCancelled() || !event.getPlayer().isOnline()) break block9;
                                        Entity creeper = Bukkit.getEntity((UUID)((UUID)event.getPacket().getUUIDs().read(0)));
                                        if (creeper == null || creeper.isDead()) {
                                            return;
                                        }
                                        try {
                                            BukkitTask[] t;
                                            PacketContainer p = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
                                            p.getPlayerInfoAction().write(0, (Object)EnumWrappers.PlayerInfoAction.ADD_PLAYER);
                                            GameProfile gp = ItemStackBuilder.newTexture("\u00a7\u65e0\u82e6\u529b\u6015", creeper.getUniqueId(), textureValue, "Arlmmmx7K48FT+Aa4ihIMVbuxp7TfAjCN0L+LZNUATXOeVVu3bA7r5gYwehtAscSCBs1RElSRzfAwIbywb24iu+M7+9ru1AWSm+W+r+523TgnjArDIMHd/Gpmkmv5gCL8f3ojgmlbrHaURSRWxMmC7pYHlTwaDxMe7ETToQAhPP1exi20goR+2Lu9XyA0UZLi+iWKVomBa4504XYHhCnj9Rym/H9Py87GjdpGSFdKi358sadmFPD+HDCMjX5xeYwRw93Akq8YJmzBEE/XbwuPV4PZncC76FtVqd1HWLhEvAStPy9sG7MOUafr+t0LoHBqjLd49o5IFPHT7fAg4bFxElZsRvjZU+B6BNIjvhgU9mmmc84ndm4NFwgzactgO8cvK/7RwOqLjys28DA5PpsnRJUfAhtCADzPint4A8gftjvJMoZgr9e2njfOijk5lbfCZ6y4RxRCzCiYq0Fcw3YTmrcy16ZZpp/dcvH8LHjf6a8yQBA+ermUiQYL4LC3toDHEEX5SinAWHFok3SKJmbt/cC2zF4vPgZNXrxyX4fsknAOL/7KGqhgqZhRNoaqxbVqw1U6vipDebea7l5JtJ7go1n15xDkM1XWxdiDe6j+hWbH/RYo9b3zQbO0yPBTvN9FCaIf86Uc9ZiFB42+UzqC4Oo1qqTgMh+Z67vrS0ykAE=");
                                            PlayerInfoData pid = new PlayerInfoData(WrappedGameProfile.fromHandle((Object)gp), 0, EnumWrappers.NativeGameMode.SURVIVAL, null);
                                            p.getPlayerInfoDataLists().write(0, (Object)Lists.newArrayList((Object[])new PlayerInfoData[]{pid}));
                                            ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), p);
                                            p = new PacketContainer(PacketType.Play.Server.NAMED_ENTITY_SPAWN);
                                            p.getIntegers().write(0, (Object)creeper.getEntityId());
                                            p.getUUIDs().write(0, (Object)creeper.getUniqueId());
                                            p.getDoubles().write(0, (Object)((Double)event.getPacket().getDoubles().read(0)));
                                            p.getDoubles().write(1, (Object)((Double)event.getPacket().getDoubles().read(1)));
                                            p.getDoubles().write(2, (Object)((Double)event.getPacket().getDoubles().read(2)));
                                            p.getBytes().write(0, (Object)((Byte)event.getPacket().getBytes().read(0)));
                                            p.getBytes().write(1, (Object)((Byte)event.getPacket().getBytes().read(1)));
                                            try {
                                                p.getDataWatcherModifier().write(0, (Object)new WrappedDataWatcher(creeper));
                                            }
                                            catch (Throwable throwable) {
                                                // empty catch block
                                            }
                                            ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), p);
                                            p = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
                                            p.getIntegers().write(0, (Object)creeper.getEntityId());
                                            try {
                                                p.getItemSlots().write(0, (Object)EnumWrappers.ItemSlot.MAINHAND);
                                                p.getItemModifier().write(0, (Object)new org.bukkit.inventory.ItemStack(Material.TNT));
                                            }
                                            catch (Throwable e) {
                                                p.getSpecificModifier(List.class).write(0, (Object)Lists.newArrayList((Object[])new Pair[]{new Pair((Object)EnumItemSlot.MAINHAND, (Object)((ItemStack)NbtUtil.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.TNT))))}));
                                            }
                                            ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), p);
                                            p = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
                                            p.getIntegers().write(0, (Object)creeper.getEntityId());
                                            p.getSpecificModifier(List.class).write(0, (Object)Lists.newArrayList((Object[])new DataWatcher.Item[]{new DataWatcher.Item(skinData, (Object)-1)}));
                                            ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), p);
                                            t = new BukkitTask[]{TaskUtil.runTaskLater((Plugin)MzTech.instance, 100L, () -> {
                                                TaskUtil.necessaryTasks.remove(t[0]);
                                                try {
                                                    PacketContainer p1 = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
                                                    p1.getPlayerInfoAction().write(0, (Object)EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
                                                    p1.getPlayerInfoDataLists().write(0, (Object)Lists.newArrayList((Object[])new PlayerInfoData[]{pid}));
                                                    ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), p1);
                                                }
                                                catch (Throwable e) {
                                                    e.printStackTrace();
                                                }
                                            })};
                                            TaskUtil.necessaryTasks.add(t[0]);
                                        }
                                        catch (Throwable e) {
                                            MzTech.throwException(e);
                                        }
                                    }
                                    catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CuteCreeper() {
        try {
            skinData = (DataWatcherObject)ReflectionWrapper.getStaticFieldValue(ReflectionWrapper.getField(EntityHuman.class, "br"));
        }
        catch (Throwable e) {
            skinData = (DataWatcherObject)ReflectionWrapper.getStaticFieldValue(ReflectionWrapper.getField(EntityHuman.class, "bi"));
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    void onShowItem(ShowItemEvent event) {
        if (event.itemStack.getType() == rawHead.getType() && event.itemStack.getDurability() == rawHead.getDurability()) {
            ItemMeta oim = event.itemStack.getItemMeta();
            int num = event.itemStack.getAmount();
            event.itemStack = new org.bukkit.inventory.ItemStack(head);
            event.itemStack.setAmount(num);
            ItemMeta im = event.itemStack.getItemMeta();
            if (oim.hasLore()) {
                im.setLore(oim.getLore());
            }
            if (oim.hasDisplayName()) {
                im.setDisplayName(oim.getDisplayName());
            }
            if (oim.hasLocalizedName()) {
                im.setLocalizedName(oim.getLocalizedName());
            }
            if (oim.hasEnchants()) {
                oim.getEnchants().forEach((e, l) -> im.addEnchant(e, l.intValue(), true));
            }
            event.itemStack.setItemMeta(im);
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    void onShowBlock(ShowBlockEvent event) {
        if (skullId > 0) {
            if (event.id >= rawSkullId && event.id < rawSkullId + 16) {
                event.id = skullId + event.id - rawSkullId;
                TaskUtil.runTask((Plugin)MzTech.instance, () -> {
                    NBT nbt = nbt.clone();
                    nbt.set("id", "minecraft:skull");
                    nbt.set("x", showBlockEvent.block.getX());
                    nbt.set("y", showBlockEvent.block.getY());
                    nbt.set("z", showBlockEvent.block.getZ());
                    new ShowBlockNbtEvent(showBlockEvent.player, showBlockEvent.block, 4, nbt).send();
                });
            } else if (event.id >= rawSkullWallId && event.id < rawSkullWallId + 4) {
                event.id = skullWallId + event.id - rawSkullWallId;
                TaskUtil.runTask((Plugin)MzTech.instance, () -> {
                    NBT nbt = nbt.clone();
                    nbt.set("id", "minecraft:skull");
                    nbt.set("x", showBlockEvent.block.getX());
                    nbt.set("y", showBlockEvent.block.getY());
                    nbt.set("z", showBlockEvent.block.getZ());
                    new ShowBlockNbtEvent(showBlockEvent.player, showBlockEvent.block, 4, nbt).send();
                });
            }
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    void onShowBlockNbt(ShowBlockNbtEvent event) {
        if (event.nbt.hasKey("id") && event.nbt.getString("id").equals("minecraft:skull")) {
            if (event.nbt.hasKey("SkullType")) {
                if (event.nbt.getByte("SkullType") == 4) {
                    event.nbt.set("SkullType", (byte)3);
                    event.nbt.set("Owner", cnbt);
                }
            } else if (!event.nbt.hasKey("SkullOwner")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    void onCreativeSetSlot(CreativeSetSlotEvent event) {
        if (event.is.getType() == head.getType() && event.is.getDurability() == head.getDurability() && ((SkullMeta)head.getItemMeta()).getOwner().equals(((SkullMeta)event.is.getItemMeta()).getOwner())) {
            ItemMeta oim = event.is.getItemMeta();
            int num = event.is.getAmount();
            event.is = new org.bukkit.inventory.ItemStack(rawHead);
            event.is.setAmount(num);
            ItemMeta im = event.is.getItemMeta();
            if (oim.hasLore()) {
                im.setLore(oim.getLore());
            }
            if (oim.hasDisplayName()) {
                im.setDisplayName(oim.getDisplayName());
            }
            if (oim.hasLocalizedName()) {
                im.setLocalizedName(oim.getLocalizedName());
            }
            if (oim.hasEnchants()) {
                oim.getEnchants().forEach((e, l) -> im.addEnchant(e, l.intValue(), true));
            }
            event.is.setItemMeta(im);
        }
    }
}

