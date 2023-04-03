/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.Property
 *  com.mojang.authlib.properties.PropertyMap
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.FireworkMeta
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.LeatherArmorMeta
 *  org.bukkit.inventory.meta.PotionMeta
 *  org.bukkit.inventory.meta.SkullMeta
 *  org.bukkit.material.MaterialData
 *  org.bukkit.potion.PotionData
 *  org.bukkit.potion.PotionType
 */
package mz.tech.util;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mz.tech.NBT;
import mz.tech.ReflectionWrapper;
import mz.tech.util.PlayerUtil;
import mz.tech.util.TaskUtil;
import mz.tech.util.ToolUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class ItemStackBuilder
extends ItemStack {
    public static ItemStackBuilder air = new ItemStackBuilder(Material.AIR);
    public static ItemStackBuilder lightBlueGlassPane = new ItemStackBuilder("STAINED_GLASS_PANE", 3, "light_blue_STAINED_GLASS_PANE", 1);
    public static ItemStackBuilder yellowGlassPane = new ItemStackBuilder("STAINED_GLASS_PANE", 4, "yellow_STAINED_GLASS_PANE", 1);
    public static ItemStackBuilder limeGlassPane = new ItemStackBuilder("STAINED_GLASS_PANE", 5, "lime_STAINED_GLASS_PANE", 1);
    public static ItemStackBuilder grayGlassPane = new ItemStackBuilder("STAINED_GLASS_PANE", 7, "GRAY_STAINED_GLASS_PANE", 1);
    public static ItemStackBuilder lightGrayGlassPane = new ItemStackBuilder("STAINED_GLASS_PANE", 8, "light_GRAY_STAINED_GLASS_PANE", 1);
    public static ItemStackBuilder redGlassPane = new ItemStackBuilder("STAINED_GLASS_PANE", 14, "red_STAINED_GLASS_PANE", 1);
    public static ItemStackBuilder blackGlassPane = new ItemStackBuilder("STAINED_GLASS_PANE", 15, "black_STAINED_GLASS_PANE", 1);
    public static ItemStackBuilder lightGrayGlass = new ItemStackBuilder("STAINED_GLASS", 8, "light_GRAY_STAINED_GLASS", 1);
    public static ItemStackBuilder whiteConcrete = new ItemStackBuilder("concrete", 0, "white_concrete", 1);
    public static ItemStackBuilder magentaConcrete = new ItemStackBuilder("concrete", 2, "magenta_concrete", 1);
    public static ItemStackBuilder yellowConcrete = new ItemStackBuilder("concrete", 4, "yellow_concrete", 1);
    public static ItemStackBuilder blackConcrete = new ItemStackBuilder("concrete", 15, "black_concrete", 1);
    public static ItemStackBuilder record_cat = new ItemStackBuilder("GREEN_RECORD", 0, "MUSIC_DISC_CAT", 1);
    public static ItemStackBuilder record_blocks = new ItemStackBuilder("RECORD_3", 0, "MUSIC_DISC_BLOCKS", 1);
    public static ItemStackBuilder record_chirp = new ItemStackBuilder("RECORD_4", 0, "MUSIC_DISC_CHIRP", 1);
    public static ItemStackBuilder record_far = new ItemStackBuilder("RECORD_5", 0, "MUSIC_DISC_FAR", 1);
    public static ItemStackBuilder record_mall = new ItemStackBuilder("RECORD_6", 0, "MUSIC_DISC_MALL", 1);
    public static ItemStackBuilder record_mellohi = new ItemStackBuilder("RECORD_7", 0, "MUSIC_DISC_MELLOHI", 1);
    public static ItemStackBuilder record_stal = new ItemStackBuilder("RECORD_8", 0, "MUSIC_DISC_STAL", 1);
    public static ItemStackBuilder record_strad = new ItemStackBuilder("RECORD_9", 0, "MUSIC_DISC_STRAD", 1);
    public static ItemStackBuilder record_ward = new ItemStackBuilder("RECORD_10", 0, "MUSIC_DISC_WARD", 1);
    public static ItemStackBuilder record_11 = new ItemStackBuilder("RECORD_11", 0, "MUSIC_DISC_WAIT", 1);
    public static ItemStackBuilder record_wait = new ItemStackBuilder("RECORD_12", 0, "MUSIC_DISC_WAIT", 1);
    public static ItemStackBuilder record_13 = new ItemStackBuilder("GOLD_RECORD", 0, "MUSIC_DISC_13", 1);
    public static ItemStackBuilder glassPane = new ItemStackBuilder("thin_glass", 0, "glass_pane", 1);
    public static ItemStackBuilder redCarpet = new ItemStackBuilder("carpet", 14, "red_carpet", 1);
    public static ItemStackBuilder whiteWool = new ItemStackBuilder("wool", 0, "white_wool", 1);
    public static ItemStackBuilder redWool = new ItemStackBuilder("wool", 14, "red_wool", 1);
    public static ItemStackBuilder blackWool = new ItemStackBuilder("wool", 15, "black_wool", 1);
    public static ItemStackBuilder redDye = new ItemStackBuilder("INK_SACK", 1, "RED_DYE", 1);
    public static ItemStackBuilder greenDye = new ItemStackBuilder("INK_SACK", 2, "green_DYE", 1);
    public static ItemStackBuilder brownDye = new ItemStackBuilder("INK_SACK", 3, "brown_DYE", 1);
    public static ItemStackBuilder blueDye = new ItemStackBuilder("INK_SACK", 4, "BLUE_DYE", 1);
    public static ItemStackBuilder purpleDye = new ItemStackBuilder("INK_SACK", 5, "purple_DYE", 1);
    public static ItemStackBuilder cyanDye = new ItemStackBuilder("INK_SACK", 6, "cyan_DYE", 1);
    public static ItemStackBuilder lightGrayDye = new ItemStackBuilder("INK_SACK", 7, "light_gray_DYE", 1);
    public static ItemStackBuilder pinkDye = new ItemStackBuilder("INK_SACK", 9, "pink_DYE", 1);
    public static ItemStackBuilder limeDye = new ItemStackBuilder("INK_SACK", 10, "LIME_DYE", 1);
    public static ItemStackBuilder yellowDye = new ItemStackBuilder("INK_SACK", 11, "YELLOW_DYE", 1);
    public static ItemStackBuilder lightBlueDye = new ItemStackBuilder("INK_SACK", 12, "light_blue_DYE", 1);
    public static ItemStackBuilder magentaDye = new ItemStackBuilder("INK_SACK", 13, "magenta_DYE", 1);
    public static ItemStackBuilder orangeDye = new ItemStackBuilder("INK_SACK", 14, "orange_DYE", 1);
    public static ItemStackBuilder[] dyes = new ItemStackBuilder[]{redDye, greenDye, brownDye, blueDye, purpleDye, cyanDye, lightGrayDye, pinkDye, limeDye, yellowDye, lightBlueDye, magentaDye, orangeDye};
    public static ItemStackBuilder oakLeaves = new ItemStackBuilder("leaves", 0, "oak_leaves", 1);
    public static ItemStackBuilder spruceLeaves = new ItemStackBuilder("leaves", 1, "spruce_leaves", 1);
    public static ItemStackBuilder birchLeaves = new ItemStackBuilder("leaves", 2, "birch_leaves", 1);
    public static ItemStackBuilder jungleLeaves = new ItemStackBuilder("leaves", 3, "jungle_leaves", 1);
    public static ItemStackBuilder acaciaLeaves = new ItemStackBuilder("leaves_2", 0, "acacia_leaves", 1);
    public static ItemStackBuilder darkOakLeaves = new ItemStackBuilder("leaves_2", 1, "dark_oak_leaves", 1);
    public static ItemStackBuilder[] leaves = new ItemStackBuilder[]{oakLeaves, spruceLeaves, birchLeaves, jungleLeaves, acaciaLeaves, darkOakLeaves};
    public static ItemStackBuilder oakSapling = new ItemStackBuilder("SAPLING", 0, "oak_sapling", 1);
    public static ItemStackBuilder spruceSapling = new ItemStackBuilder("SAPLING", 1, "spruce_sapling", 1);
    public static ItemStackBuilder birchSapling = new ItemStackBuilder("SAPLING", 2, "birch_sapling", 1);
    public static ItemStackBuilder jungleSapling = new ItemStackBuilder("SAPLING", 3, "jungle_sapling", 1);
    public static ItemStackBuilder acaciaSapling = new ItemStackBuilder("SAPLING", 4, "acacia_sapling", 1);
    public static ItemStackBuilder darkOakSapling = new ItemStackBuilder("SAPLING", 5, "dark_oak_sapling", 1);
    public static ItemStackBuilder[] saplings = new ItemStackBuilder[]{oakSapling, spruceSapling, birchSapling, jungleSapling, acaciaSapling, darkOakSapling};
    public static ItemStackBuilder oakFence = new ItemStackBuilder("fence", 0, "oak_fence", 1);
    public static ItemStackBuilder ironBars = new ItemStackBuilder("IRON_FENCE", 0, "iron_bars", 1);
    public static ItemStackBuilder yellowWool = new ItemStackBuilder("wool", 4, "yellow_wool", 1);
    public static ItemStackBuilder yellowTerracotta = new ItemStackBuilder("STAINED_CLAY", 4, "yellow_terracotta", 1);
    public static ItemStackBuilder fireCharge = new ItemStackBuilder("FIREWORK_CHARGE", 0, "fire_charge", 1);
    public static ItemStackBuilder stone = new ItemStackBuilder(Material.STONE);
    public static ItemStackBuilder granite = new ItemStackBuilder("granite", "stone", 1, 1);
    public static ItemStackBuilder diorite = new ItemStackBuilder("diorite", "stone", 3, 1);
    public static ItemStackBuilder andesite = new ItemStackBuilder("andesite", "stone", 5, 1);
    public static ItemStackBuilder gunpowder = new ItemStackBuilder("SULPHUR", 0, "gunpowder", 1);
    public static Class<?> craftItemStackClass = ReflectionWrapper.getCraftBukkitClass("inventory.CraftItemStack");
    public static Method craftItemStackAsNMSCopy = ReflectionWrapper.getMethod(craftItemStackClass, "asNMSCopy", ItemStack.class);
    public static Field handleField = ReflectionWrapper.getField(craftItemStackClass, "handle");
    public static Class<?> itemBlockClass = ReflectionWrapper.getNMSClass("ItemBlock");
    public static Method itemBlockGetBlock = ReflectionWrapper.getMethod(itemBlockClass, "getBlock", new Class[0]);
    public static Class<?> blockClass = ReflectionWrapper.getNMSClass("Block");
    public static Method BlockGetBlockData = ReflectionWrapper.getMethod(blockClass, "getBlockData", new Class[0]);
    public static Class<?> iBlockDataClass = ReflectionWrapper.getNMSClass("IBlockData");
    public static Class<?> blockPositionClass = ReflectionWrapper.getNMSClass("BlockPosition");
    public static Constructor<?> blockPositionConstructor = ReflectionWrapper.getConstructor(blockPositionClass, Integer.TYPE, Integer.TYPE, Integer.TYPE);
    public static Class<?> worldClass = ReflectionWrapper.getNMSClass("World");
    public static Method WorldSetTypeAndData = ReflectionWrapper.getMethod(worldClass, "setTypeAndData", blockPositionClass, iBlockDataClass, Integer.TYPE);
    public static Method worldGetTileEntity = ReflectionWrapper.getMethod(worldClass, "getTileEntity", blockPositionClass);
    public static Method worldGetType = ReflectionWrapper.getMethod(worldClass, "getType", blockPositionClass);
    public static Class<?> craftWorldClass = ReflectionWrapper.getCraftBukkitClass("CraftWorld");
    public static Method craftWorldGetHandle = ReflectionWrapper.getMethod(craftWorldClass, "getHandle", new Class[0]);
    public static Class<Enum> enumHandClass = ReflectionWrapper.getNMSClass("EnumHand");
    public static Class<Enum> enumDirectionClass = ReflectionWrapper.getNMSClass("EnumDirection");
    public static Class<?> entityHumanClass = ReflectionWrapper.getNMSClass("EntityHuman");
    public static Class<?> itemStackClass = ReflectionWrapper.getNMSClass("ItemStack");
    public static Method itemStackGetItem = ReflectionWrapper.getMethod(itemStackClass, "getItem", new Class[0]);
    private ItemMeta meta = null;

    public static Object getNmsBlockTypeIfLoaded(Block block) {
        return ReflectionWrapper.invokeMethod(worldGetType, ItemStackBuilder.getNmsWorld(block.getWorld()), ItemStackBuilder.newBlockPosition(block));
    }

    public ItemStackBuilder(ItemStack is) {
        super(is);
    }

    public ItemStackBuilder(Material mid) {
        super(mid);
    }

    public ItemStackBuilder setHideFlags(ItemFlag ... flags) {
        this.getItemMeta().addItemFlags(flags);
        return this;
    }

    public ItemStackBuilder removeHideFlags(ItemFlag ... flags) {
        this.getItemMeta().removeItemFlags(flags);
        return this;
    }

    public ItemStackBuilder(String oldMaterial, int oldData, String newMaterial, int num) {
        super(ItemStackBuilder.newGenericItemStack(oldMaterial, oldData, newMaterial, num));
    }

    public ItemStackBuilder(String newMaterial, String oldMaterial, int oldData, int num) {
        super(ItemStackBuilder.newGenericItemStack(newMaterial, oldMaterial, oldData, num));
    }

    public static ItemStack newGenericItemStack(String oldMaterial, int oldData, String newMaterial, int num) {
        try {
            return new ItemStack(Enum.valueOf(Material.class, oldMaterial.toUpperCase()), num, (short)oldData);
        }
        catch (Throwable e) {
            try {
                return new ItemStack(Enum.valueOf(Material.class, newMaterial.toUpperCase()), num);
            }
            catch (Throwable e2) {
                System.out.println("\u6784\u5efa\u7269\u54c1\u5931\u8d25\uff08\u9884\u8ba1MID\uff1a " + oldMaterial + " | " + newMaterial + "\uff09");
                return null;
            }
        }
    }

    public static ItemStack newGenericItemStack(String newMaterial, String oldMaterial, int oldData, int num) {
        try {
            return new ItemStack(Enum.valueOf(Material.class, newMaterial.toUpperCase()), num);
        }
        catch (Throwable e) {
            try {
                return new ItemStack(Enum.valueOf(Material.class, oldMaterial.toUpperCase()), num, (short)oldData);
            }
            catch (Throwable e2) {
                System.out.println("\u6784\u5efa\u7269\u54c1\u5931\u8d25\uff08\u9884\u8ba1MID\uff1a " + oldMaterial + " | " + newMaterial + "\uff09");
                return null;
            }
        }
    }

    public ItemMeta getItemMeta() {
        if (this.meta == null) {
            this.meta = super.getItemMeta();
        }
        return this.meta;
    }

    public ItemStackBuilder setLocName(String name) {
        this.getItemMeta().setLocalizedName(name);
        return this;
    }

    public ItemStackBuilder setLoreList(List<String> lore) {
        this.getItemMeta().setLore(lore);
        return this;
    }

    public ItemStackBuilder setLore(int line, String lore) {
        List<String> l = this.getLore();
        l.set(line, lore);
        this.setLoreList(l);
        return this;
    }

    public ItemStackBuilder setUnbreakable(boolean unbreakable) {
        this.getItemMeta().setUnbreakable(unbreakable);
        return this;
    }

    public ItemStackBuilder setLoreList(String ... lore) {
        return this.setLoreList(Lists.newArrayList((Object[])lore));
    }

    public boolean setItemMeta(ItemMeta meta) {
        this.meta = meta;
        return super.setItemMeta(meta);
    }

    public ItemStack build() {
        this.setItemMeta(this.getItemMeta());
        return this;
    }

    public boolean give(Player player) {
        this.build();
        player.updateInventory();
        if (ItemStackBuilder.isEmpty(player.getItemInHand())) {
            player.getInventory().setItemInMainHand((ItemStack)this);
            return true;
        }
        player.getInventory().forEach(i -> {
            if (!ItemStackBuilder.isEmpty(this) && this.equals(i)) {
                if (i.getAmount() + this.getAmount() > this.getMaxStackSize()) {
                    this.setAmount(this.getAmount() - (this.getMaxStackSize() - i.getAmount()));
                    i.setAmount(i.getMaxStackSize());
                } else {
                    i.setAmount(i.getAmount() + this.getAmount());
                    this.setAmount(0);
                }
            }
        });
        if (this.getAmount() > 0) {
            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItemNaturally(player.getLocation(), (ItemStack)this);
            } else {
                player.getInventory().setItem(player.getInventory().firstEmpty(), (ItemStack)this);
            }
        }
        return false;
    }

    public boolean contains(ItemStack raw) {
        return raw != null && this.isSimilar(raw) && this.getAmount() >= raw.getAmount();
    }

    public ItemStackBuilder setCount(int i) {
        this.setAmount(i);
        return this;
    }

    public ItemStackBuilder setPotionColor(Color color) {
        ((PotionMeta)this.getItemMeta()).setColor(color);
        return this;
    }

    public ItemStackBuilder setLeatherArmorColor(Color color) {
        ((LeatherArmorMeta)this.getItemMeta()).setColor(color);
        return this;
    }

    public ItemStackBuilder addLore(String ... lore) {
        List l;
        if (!this.getItemMeta().hasLore()) {
            this.meta.setLore(new ArrayList());
        }
        if ((l = this.meta.getLore()) == null) {
            l = Lists.newArrayList((Object[])lore);
        } else {
            l.addAll(Lists.newArrayList((Object[])lore));
        }
        this.meta.setLore(l);
        return this;
    }

    public String infoFromLore(String prefix) {
        return this.infoFromLore(prefix, null);
    }

    public String infoFromLore(String prefix, String def) {
        if (!this.getItemMeta().hasLore()) {
            this.meta.setLore(new ArrayList());
        }
        String[] rs = new String[1];
        this.meta.getLore().forEach(lore -> {
            if (lore.startsWith(prefix)) {
                stringArray[0] = lore.substring(prefix.length());
            }
        });
        if (rs[0] == null && def != null) {
            this.addLore(String.valueOf(prefix) + def);
            rs[0] = def;
        }
        return rs[0];
    }

    public ItemStackBuilder setInfoToLore(String prefix, String info) {
        if (!this.getItemMeta().hasLore()) {
            this.meta.setLore(new ArrayList());
        }
        List lore = this.meta.getLore();
        int i = 0;
        while (i < lore.size()) {
            if (((String)lore.get(i)).startsWith(prefix)) {
                lore.set(i, String.valueOf(prefix) + info);
                return this;
            }
            ++i;
        }
        this.addLore(String.valueOf(prefix) + info);
        return this;
    }

    public List<String> getLore() {
        return this.getItemMeta().getLore();
    }

    public String getLore(int line) {
        return (String)this.getItemMeta().getLore().get(line);
    }

    public ItemStackBuilder setOwner(String owner) {
        ItemMeta im = this.getItemMeta();
        if (im instanceof SkullMeta) {
            ((SkullMeta)im).setOwner(owner);
        }
        return this;
    }

    public static ItemStackBuilder newSkull(String name, UUID uuid, String value) {
        ItemStackBuilder is = new ItemStackBuilder("skull_item", 3, "player_head", 1);
        SkullMeta im = (SkullMeta)is.getItemMeta();
        Class<?> craftMetaSkull = ReflectionWrapper.getCraftBukkitClass("inventory.CraftMetaSkull");
        GameProfile gameProfile = ItemStackBuilder.newTexture(name, uuid, value, null);
        try {
            ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(craftMetaSkull, "setProfile", GameProfile.class), im, gameProfile);
        }
        catch (Throwable e) {
            ReflectionWrapper.setFieldValue(ReflectionWrapper.getField(craftMetaSkull, "profile"), im, gameProfile);
        }
        is.setItemMeta((ItemMeta)im);
        return is;
    }

    public static GameProfile newTexture(String name, UUID uuid, String value, String signature) {
        GameProfile gameProfile = new GameProfile(uuid, name);
        TaskUtil.throwRuntime(() -> ((PropertyMap)ReflectionWrapper.getFieldValue(GameProfile.class.getDeclaredField("properties"), gameProfile)).put((Object)"textures", (Object)new Property("textures", value, signature)));
        return gameProfile;
    }

    public ItemStackBuilder setDurability(int i) {
        this.setDurability((short)i);
        return this;
    }

    public ItemStackBuilder setPower(int i) {
        ((FireworkMeta)this.getItemMeta()).setPower(i);
        return this;
    }

    public static boolean isEmpty(ItemStack is) {
        return is == null || is.getType() == Material.AIR || is.getAmount() == 0;
    }

    public ItemStackBuilder setPotionType(PotionType potionType) {
        ((PotionMeta)this.getItemMeta()).setBasePotionData(new PotionData(potionType));
        return this;
    }

    public static boolean equals(ItemStack a, ItemStack b) {
        if (ItemStackBuilder.isEmpty(a) && ItemStackBuilder.isEmpty(b)) {
            return true;
        }
        if (ItemStackBuilder.isEmpty(a) || ItemStackBuilder.isEmpty(b)) {
            return false;
        }
        return new NBT(new ItemStackBuilder(a).setCount(1).build()).equals(new NBT(new ItemStackBuilder(b).setCount(1).build()));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ItemStack)) {
            return false;
        }
        return ItemStackBuilder.equals(this, (ItemStack)obj);
    }

    public Object getNmsItemStack() {
        return ReflectionWrapper.invokeStaticMethod(craftItemStackAsNMSCopy, new Object[]{this});
    }

    public static Object getNmsWorld(World world) {
        return ReflectionWrapper.invokeMethod(craftWorldGetHandle, world, new Object[0]);
    }

    public static Object getNmsIBlockData(Object nmsBlock) {
        return ReflectionWrapper.invokeMethod(BlockGetBlockData, nmsBlock, new Object[0]);
    }

    public static Object getNmsItem(Object nmsItemStack) {
        return ReflectionWrapper.invokeMethod(itemStackGetItem, nmsItemStack, new Object[0]);
    }

    public static Object getNmsItemBlock(Object nmsItemBlock) {
        return ReflectionWrapper.invokeMethod(itemBlockGetBlock, nmsItemBlock, new Object[0]);
    }

    public static Object getNmsBlockData(Object nmsBlock) {
        return ReflectionWrapper.invokeMethod(BlockGetBlockData, nmsBlock, new Object[0]);
    }

    public static Object newBlockPosition(int x, int y, int z) {
        return ReflectionWrapper.newInstance(blockPositionConstructor, x, y, z);
    }

    public static Object newBlockPosition(Block block) {
        return ReflectionWrapper.newInstance(blockPositionConstructor, block.getX(), block.getY(), block.getZ());
    }

    public boolean isBlock() {
        return itemBlockClass.isAssignableFrom(ItemStackBuilder.getNmsItem(this.getNmsItemStack()).getClass());
    }

    public static Object getTileEntity(Block block) {
        return ReflectionWrapper.invokeMethod(worldGetTileEntity, ItemStackBuilder.getNmsWorld(block.getWorld()), ItemStackBuilder.newBlockPosition(block));
    }

    public MaterialData getBlockData() {
        MaterialData data = new MaterialData(this.getType(), (byte)this.getDurability());
        switch (data.getItemType().name()) {
            case "SEEDS": {
                data = new MaterialData(Material.CROPS);
                break;
            }
            case "WHEAT_SEEDS": {
                data = new MaterialData(Material.WHEAT);
                break;
            }
            case "MELON_SEEDS": {
                data = new MaterialData(Material.MELON_STEM);
                break;
            }
            case "PUMPKIN_SEEDS": {
                data = new MaterialData(Material.PUMPKIN_STEM);
                break;
            }
            case "SUGAR_CANE": {
                try {
                    data = new MaterialData(Material.SUGAR_CANE_BLOCK);
                }
                catch (Throwable throwable) {}
                break;
            }
            case "SKULL_ITEM": {
                data = new MaterialData(Material.SKULL);
            }
        }
        return data;
    }

    public void toBlock(Block block) {
        block17: {
            NBT itemNbt;
            if (block.getType() != Material.AIR) {
                BlockBreakEvent event = new BlockBreakEvent(block, null);
                Lists.newArrayList((Object[])BlockBreakEvent.getHandlerList().getRegisteredListeners()).forEach(listener -> {
                    try {
                        event.setCancelled(false);
                        listener.callEvent((Event)event);
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                });
                ToolUtil.showBlockBreak(block);
                ToolUtil.breakBlock(block, null, event.isDropItems() ? null : new ArrayList());
            }
            MaterialData data = this.getBlockData();
            block.setType(data.getItemType());
            try {
                block.setData(data.getData());
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            Object tileEntity = ItemStackBuilder.getTileEntity(block);
            if (tileEntity == null || !(itemNbt = new NBT(this)).hasKey("tag")) break block17;
            itemNbt = itemNbt.getChild("tag");
            NBT blockNbt = new NBT();
            switch (block.getType().name()) {
                case "SKULL": {
                    block.setData((byte)1);
                }
                case "PLAYER_HEAD": {
                    if (block.getType().name().equals("SKULL")) {
                        blockNbt.set("SkullType", this.getDurability());
                    }
                    if (!itemNbt.hasKey("SkullOwner")) break;
                    blockNbt.set("Owner", itemNbt.getChild("SkullOwner"));
                    blockNbt.set("SkullOwner", itemNbt.getChild("SkullOwner"));
                    break;
                }
                default: {
                    if (!itemNbt.hasKey("BlockEntityTag")) break;
                    blockNbt = itemNbt.getChild("BlockEntityTag");
                }
            }
            if (this.getItemMeta().hasDisplayName()) {
                blockNbt.set("CustomName", this.getItemMeta().getDisplayName());
            } else if (this.getItemMeta().hasLocalizedName()) {
                blockNbt.set("CustomName", this.getItemMeta().getLocalizedName());
            }
            blockNbt.set("x", block.getX()).set("y", block.getY()).set("z", block.getZ()).set(block);
        }
    }

    public void toBlock(Player player, Block block) {
        if (new PlayerUtil(player).canBuildIgnoreAntiCheat(block)) {
            this.toBlock(block);
        }
    }

    public ItemStackBuilder setDisplayName(String customName) {
        this.getItemMeta().setDisplayName(customName);
        return this;
    }

    public static void toBlock(ItemStack is, Block block) {
        if (!(is instanceof ItemStackBuilder)) {
            is = new ItemStackBuilder(is);
        }
        ((ItemStackBuilder)is).toBlock(block);
    }

    public static void toBlock(ItemStack is, Player player, Block block) {
        if (!(is instanceof ItemStackBuilder)) {
            is = new ItemStackBuilder(is);
        }
        ((ItemStackBuilder)is).toBlock(player, block);
    }

    public ItemStackBuilder clone() {
        return (ItemStackBuilder)super.clone();
    }

    public static ItemStackBuilder[] clone(ItemStackBuilder ... iss) {
        if (iss == null) {
            return null;
        }
        ItemStackBuilder[] n = new ItemStackBuilder[iss.length];
        int i = 0;
        while (i < iss.length) {
            n[i] = new ItemStackBuilder(iss[i]);
            ++i;
        }
        return n;
    }

    public ItemStackBuilder setMaterial(Material m) {
        this.setType(m);
        return this;
    }

    public MaterialData getData() {
        return new MaterialData(this.getType(), (byte)this.getDurability());
    }

    public static boolean canHold(ItemStack[] slots, ItemStack[] newItems) {
        ItemStack[] newSlots = new ItemStack[slots.length];
        int i = 0;
        while (i < slots.length) {
            newSlots[i] = slots[i] == null ? null : slots[i].clone();
            ++i;
        }
        slots = newSlots;
        ItemStack[] newNewItems = new ItemStack[newItems.length];
        int i2 = 0;
        while (i2 < newItems.length) {
            newNewItems[i2] = newItems[i2] == null ? null : newItems[i2].clone();
            ++i2;
        }
        ItemStack[] itemStackArray = newItems = newNewItems;
        int n = newItems.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack i3 = itemStackArray[n2];
            if (!ItemStackBuilder.isEmpty(i3)) {
                ItemStack[] itemStackArray2 = slots;
                int n3 = slots.length;
                int n4 = 0;
                while (n4 < n3) {
                    ItemStack j = itemStackArray2[n4];
                    if (ItemStackBuilder.equals(i3, j)) {
                        if (i3.getAmount() > i3.getType().getMaxStackSize() - j.getAmount()) {
                            i3.setAmount(i3.getAmount() - (i3.getType().getMaxStackSize() - j.getAmount()));
                            j.setAmount(i3.getType().getMaxStackSize());
                        } else {
                            j.setAmount(j.getAmount() + i3.getAmount());
                            i3 = null;
                            break;
                        }
                    }
                    ++n4;
                }
                if (!ItemStackBuilder.isEmpty(i3)) {
                    int j = 0;
                    while (j < slots.length) {
                        if (ItemStackBuilder.isEmpty(slots[j])) {
                            slots[j] = i3;
                            i3 = null;
                            break;
                        }
                        ++j;
                    }
                    if (!ItemStackBuilder.isEmpty(i3)) {
                        return false;
                    }
                }
            }
            ++n2;
        }
        return true;
    }

    public static ItemStack[] addItems(ItemStack[] slots, ItemStack[] newItems) {
        ItemStack[] newSlots = new ItemStack[slots.length];
        int i = 0;
        while (i < slots.length) {
            newSlots[i] = slots[i] == null ? null : slots[i].clone();
            ++i;
        }
        slots = newSlots;
        ItemStack[] newNewItems = new ItemStack[newItems.length];
        int i2 = 0;
        while (i2 < newItems.length) {
            newNewItems[i2] = newItems[i2] == null ? null : newItems[i2].clone();
            ++i2;
        }
        ItemStack[] itemStackArray = newItems = newNewItems;
        int n = newItems.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack i3 = itemStackArray[n2];
            if (!ItemStackBuilder.isEmpty(i3)) {
                ItemStack[] itemStackArray2 = slots;
                int n3 = slots.length;
                int n4 = 0;
                while (n4 < n3) {
                    ItemStack j = itemStackArray2[n4];
                    if (ItemStackBuilder.equals(i3, j)) {
                        if (i3.getAmount() > i3.getType().getMaxStackSize() - j.getAmount()) {
                            i3.setAmount(i3.getAmount() - (i3.getType().getMaxStackSize() - j.getAmount()));
                            j.setAmount(i3.getType().getMaxStackSize());
                        } else {
                            j.setAmount(j.getAmount() + i3.getAmount());
                            i3 = null;
                            break;
                        }
                    }
                    ++n4;
                }
                if (!ItemStackBuilder.isEmpty(i3)) {
                    int j = 0;
                    while (j < slots.length) {
                        if (ItemStackBuilder.isEmpty(slots[j])) {
                            slots[j] = i3;
                            i3 = null;
                            break;
                        }
                        ++j;
                    }
                    if (!ItemStackBuilder.isEmpty(i3)) {
                        return null;
                    }
                }
            }
            ++n2;
        }
        return newSlots;
    }
}

