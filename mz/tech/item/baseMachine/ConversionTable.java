/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.permission.Permission
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.configuration.MemorySection
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.FurnaceRecipe
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.ShapedRecipe
 *  org.bukkit.inventory.ShapelessRecipe
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package mz.tech.item.baseMachine;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mz.tech.MzTech;
import mz.tech.category.BaseMachineCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.sundry.CNMB;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ConversionTable
extends MzTechItem
implements Listener {
    static Map<Player, Inventory> playersOpenConversionTable = new LinkedHashMap<Player, Inventory>();
    static Map<Player, Integer> playerOpenConversionPage = new LinkedHashMap<Player, Integer>();
    public static Economy econ;
    public static Permission perms;
    public static Map<Player, List<ItemStack>> playersConversions;
    public static Map<ItemStack, Double> values;

    static {
        try {
            econ = (Economy)Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
        }
        catch (Throwable e) {
            Bukkit.getLogger().warning("\u65e0\u6cd5\u5f97\u5230\u7ecf\u6d4e\u7ba1\u7406\u5668\uff0c\u8bf7\u5b89\u88c5\u57fa\u4e8eVault\u7684\u7ecf\u6d4e\u7cfb\u7edf\u63d2\u4ef6");
        }
        perms = null;
        playersConversions = new HashMap<Player, List<ItemStack>>();
        values = new HashMap<ItemStack, Double>();
        new SmilingCraftingTableRecipe(new ConversionTable(), Material.OBSIDIAN, new CNMB(), 0, 1, new ItemStackBuilder("workbench", 0, "crafting_table", 1).build(), 1, 0, 1, 0).reg("\u8f6c\u5316\u684c");
        new BukkitRunnable(){

            public void run() {
                File dir2 = new File("plugins/MzTech/Conversions");
                if (!dir2.exists()) {
                    dir2.mkdirs();
                }
                ArrayList deleteList = new ArrayList();
                playersConversions.forEach((p, iss) -> {
                    File f = new File("plugins/MzTech/Conversions/" + p.getUniqueId().toString() + ".pc");
                    if (f.exists()) {
                        f.delete();
                    }
                    try {
                        f.createNewFile();
                        DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
                        dos.writeInt(iss.size());
                        iss.forEach(is -> {
                            try {
                                dos.writeInt(is.getType().ordinal());
                                dos.writeShort(is.getDurability());
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        dos.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!playersOpenConversionTable.containsKey(p)) {
                        deleteList.add(p);
                    }
                });
                deleteList.forEach(p -> playersConversions.remove(p));
            }
        }.runTaskTimer((Plugin)MzTech.instance, 1200L, 1200L);
        Bukkit.getPluginManager().registerEvents((Listener)new ConversionTable(), (Plugin)MzTech.instance);
    }

    public ConversionTable() {
        super(new ItemStackBuilder(Material.DAYLIGHT_DETECTOR).setLocName("\u00a71\u8f6c\u5316\u684c").setLoreList("\u00a77\u00a7m\u7cfb\u7edf\u5546\u5e97").build());
    }

    public static Double getItemValue(ItemStack is) {
        Double r = ConversionTable.getItemValue(is, new ArrayList<ItemStack>());
        return r;
    }

    public static boolean isPureItem(ItemStack is) {
        return is != null ? is.isSimilar(new ItemStack(is.getType(), 1, is.getDurability())) : false;
    }

    public static ItemStack getPureItem(ItemStack is) {
        if (is == null) {
            return null;
        }
        ItemStack pure = new ItemStack(is.getType(), 1, is.getDurability());
        if (pure.getDurability() == Short.MAX_VALUE) {
            pure.setDurability((short)0);
        }
        if (pure.getType().getMaxDurability() > 0) {
            pure.setDurability((short)0);
        }
        return pure;
    }

    public static Double getItemValue(ItemStack is, List<ItemStack> searchings) {
        if (searchings.contains(ConversionTable.getPureItem(is)) || !ConversionTable.isPureItem(is)) {
            return null;
        }
        ItemStack i = ConversionTable.getPureItem(is);
        searchings.add(i);
        if (i == null) {
            return null;
        }
        Double[] rd = new Double[1];
        if (values.containsKey(i)) {
            rd[0] = values.get(ConversionTable.getPureItem(i));
            if (rd[0] == null) {
                return null;
            }
            rd[0] = rd[0] * (double)is.getAmount();
        } else {
            ((MemorySection)MzTech.instance.getConfig().get("values")).getValues(true).forEach((s, v) -> {
                String[] ss = s.split(":");
                Material t = Material.getMaterial((String)ss[0].toUpperCase());
                if (t != null) {
                    ItemStack conS = new ItemStack(t);
                    if (ss.length > 1) {
                        conS.setDurability(Short.valueOf(ss[1]).shortValue());
                    }
                    if (v instanceof Double) {
                        if (conS.isSimilar(i) && (rd[0] == null || (Double)v * (double)is.getAmount() < rd[0])) {
                            doubleArray[0] = (Double)v * (double)is.getAmount();
                        }
                    } else if (v instanceof Integer && conS.isSimilar(i) && (rd[0] == null || (double)((Integer)v * is.getAmount()) < rd[0])) {
                        doubleArray[0] = (double)((Integer)v).intValue() * (double)is.getAmount();
                    }
                }
            });
            if (rd[0] == null) {
                Bukkit.getRecipesFor((ItemStack)i).forEach(r -> {
                    if (FurnaceRecipe.class.isAssignableFrom(r.getClass())) {
                        Double v = ConversionTable.getItemValue(ConversionTable.getPureItem(((FurnaceRecipe)r).getInput()), Lists.newArrayList((Iterable)searchings));
                        if (v != null && (rd[0] == null || v / (double)((FurnaceRecipe)r).getInput().getAmount() * (double)is.getAmount() + ConversionTable.getClinkeringValue() < rd[0])) {
                            doubleArray[0] = v / (double)((FurnaceRecipe)r).getInput().getAmount() * (double)is.getAmount() + ConversionTable.getClinkeringValue();
                        }
                    } else if (ShapedRecipe.class.isAssignableFrom(r.getClass())) {
                        Double[] v = new Double[]{0.0};
                        Lists.newArrayList((Object[])((ShapedRecipe)r).getShape()).forEach(a -> {
                            char[] cArray = a.toCharArray();
                            int n = cArray.length;
                            int n2 = 0;
                            while (n2 < n) {
                                Character a1 = Character.valueOf(cArray[n2]);
                                ItemStack tis = (ItemStack)((ShapedRecipe)r).getIngredientMap().get(a1);
                                if (tis != null) {
                                    Double va;
                                    if (tis.getDurability() == Short.MAX_VALUE) {
                                        tis.setDurability((short)0);
                                    }
                                    if ((va = ConversionTable.getItemValue(tis, Lists.newArrayList((Iterable)searchings))) != null) {
                                        if (v[0] != null) {
                                            doubleArray[0] = v[0] + va / (double)((ShapedRecipe)r).getResult().getAmount();
                                        }
                                    } else {
                                        doubleArray[0] = null;
                                    }
                                }
                                ++n2;
                            }
                        });
                        if (v[0] != null && v[0] != 0.0 && (rd[0] == null || v[0] * (double)is.getAmount() < rd[0])) {
                            doubleArray[0] = v[0] * (double)is.getAmount();
                        }
                    } else if (ShapelessRecipe.class.isAssignableFrom(r.getClass())) {
                        Double[] v = new Double[]{0.0};
                        ((ShapelessRecipe)r).getIngredientList().forEach(a -> {
                            if (a != null) {
                                Double va;
                                if (a.getDurability() == Short.MAX_VALUE) {
                                    a.setDurability((short)0);
                                }
                                if ((va = ConversionTable.getItemValue(a, Lists.newArrayList((Iterable)searchings))) != null) {
                                    if (v[0] != null) {
                                        doubleArray[0] = v[0] + va / (double)((ShapelessRecipe)r).getResult().getAmount();
                                    }
                                } else {
                                    doubleArray[0] = null;
                                }
                            }
                        });
                        if (v[0] != null && v[0] != 0.0 && (rd[0] == null || v[0] * (double)is.getAmount() < rd[0])) {
                            doubleArray[0] = v[0] * (double)is.getAmount();
                        }
                    }
                });
            }
        }
        if (rd[0] != null && is.getType().getMaxDurability() > 0) {
            rd[0] = rd[0] * (double)(is.getType().getMaxDurability() - is.getDurability());
            rd[0] = rd[0] / (double)is.getType().getMaxDurability();
        }
        if (rd[0] != null) {
            values.put(ConversionTable.getPureItem(i), rd[0] / (double)is.getAmount());
        }
        return rd[0];
    }

    @EventHandler
    void onInventoryClick(InventoryClickEvent event) {
        if (playersOpenConversionTable.containsKey(event.getWhoClicked())) {
            event.setCancelled(true);
            if (event.getClickedInventory() == event.getWhoClicked().getInventory()) {
                MzTechItem mzTechCopy;
                boolean onlySell = false;
                Double value = ConversionTable.getItemValue(event.getClickedInventory().getItem(event.getSlot()));
                if (value == null && (mzTechCopy = MzTechItem.asMzTechCopy(event.getClickedInventory().getItem(event.getSlot()))) instanceof Sellable) {
                    value = ((Sellable)((Object)mzTechCopy)).getPrice() * (double)mzTechCopy.getAmount();
                    onlySell = true;
                }
                if (value != null) {
                    ItemStack pureItem = ConversionTable.getPureItem(event.getClickedInventory().getItem(event.getSlot()));
                    if (playersConversions.get(event.getWhoClicked()).contains(pureItem)) {
                        try {
                            event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_HAT, 1.0f, 1.0f);
                        }
                        catch (Throwable e) {
                            event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Enum.valueOf(Sound.class, "BLOCK_NOTE_BLOCK_HAT"), 1.0f, 1.0f);
                        }
                        playersConversions.get(event.getWhoClicked()).remove(pureItem);
                    } else {
                        try {
                            event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BELL, 1.0f, 1.0f);
                        }
                        catch (Throwable e) {
                            event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Enum.valueOf(Sound.class, "BLOCK_NOTE_BLOCK_BELL"), 1.0f, 1.0f);
                        }
                    }
                    if (!onlySell) {
                        playersConversions.get(event.getWhoClicked()).add(pureItem);
                    }
                    econ.depositPlayer((OfflinePlayer)event.getWhoClicked(), value.doubleValue());
                    event.getCurrentItem().setAmount(0);
                    ConversionTable.setMoney((Player)event.getWhoClicked());
                }
            } else if (event.getClickedInventory() == event.getInventory()) {
                if (event.getSlot() < 45 && event.getInventory().getItem(event.getSlot()) != null && event.getInventory().getItem(event.getSlot()).getType() != Material.AIR) {
                    Double value = ConversionTable.getItemValue(ConversionTable.getPureItem(event.getInventory().getItem(event.getSlot()))) * (double)(event.isShiftClick() ? event.getInventory().getItem(event.getSlot()).getType().getMaxStackSize() : 1);
                    if (value != null) {
                        if (econ.getBalance((OfflinePlayer)event.getWhoClicked()) >= value) {
                            try {
                                event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BELL, 1.0f, 1.0f);
                            }
                            catch (Throwable e) {
                                event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Enum.valueOf(Sound.class, "BLOCK_NOTE_BLOCK_BELL"), 1.0f, 1.0f);
                            }
                            econ.withdrawPlayer((OfflinePlayer)event.getWhoClicked(), value.doubleValue());
                            ItemStack is = ConversionTable.getPureItem(event.getInventory().getItem(event.getSlot()));
                            is.setAmount(event.isShiftClick() ? event.getInventory().getItem(event.getSlot()).getType().getMaxStackSize() : 1);
                            new ItemStackBuilder(is).give((Player)event.getWhoClicked());
                            ConversionTable.setMoney((Player)event.getWhoClicked());
                        } else {
                            try {
                                event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1.0f, 1.0f);
                            }
                            catch (Throwable e) {
                                event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Enum.valueOf(Sound.class, "BLOCK_NOTE_BLOCK_XYLOPHONE"), 1.0f, 1.0f);
                            }
                            event.getWhoClicked().sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a74\u91d1\u5e01\u4e0d\u8db3");
                        }
                    }
                } else {
                    switch (event.getSlot()) {
                        case 47: {
                            if (playerOpenConversionPage.get(event.getWhoClicked()) <= 0) break;
                            event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
                            playerOpenConversionPage.put((Player)event.getWhoClicked(), playerOpenConversionPage.get(event.getWhoClicked()) - 1);
                            ConversionTable.setConversions((Player)event.getWhoClicked());
                            break;
                        }
                        case 51: {
                            if (playerOpenConversionPage.get(event.getWhoClicked()) * 45 + 45 >= playersConversions.get(event.getWhoClicked()).size()) break;
                            event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
                            playerOpenConversionPage.put((Player)event.getWhoClicked(), playerOpenConversionPage.get(event.getWhoClicked()) + 1);
                            ConversionTable.setConversions((Player)event.getWhoClicked());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    void onInventoryClose(InventoryCloseEvent event) {
        if (playersOpenConversionTable.containsKey(event.getPlayer())) {
            playersOpenConversionTable.remove(event.getPlayer());
            playerOpenConversionPage.remove(event.getPlayer());
            event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 1.0f);
        }
    }

    @Override
    public void onDisable() {
        playersOpenConversionTable.forEach((p, i) -> p.closeInventory());
        File dir2 = new File("plugins/MzTech/Conversions");
        if (!dir2.exists()) {
            dir2.mkdirs();
        }
        playersConversions.forEach((p, iss) -> {
            File f = new File("plugins/MzTech/Conversions/" + p.getUniqueId().toString() + ".pc");
            if (f.exists()) {
                f.delete();
            }
            try {
                f.createNewFile();
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
                dos.writeInt(iss.size());
                iss.forEach(is -> {
                    try {
                        dos.writeInt(is.getType().ordinal());
                        dos.writeShort(is.getDurability());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                dos.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void setConversions(Player player) {
        List<ItemStack> iss = playersConversions.get(player);
        if (iss != null && playerOpenConversionPage.containsKey(player)) {
            Integer page = playerOpenConversionPage.get(player);
            InventoryView inv = player.getOpenInventory();
            int i = 0;
            while (i < 45) {
                if (i + page * 45 < iss.size()) {
                    ItemStack is = new ItemStack(iss.get(iss.size() - 1 - i - page * 45));
                    ItemMeta im = is.getItemMeta();
                    Double value = ConversionTable.getItemValue(is);
                    if (value != null) {
                        im.setLore((List)Lists.newArrayList((Object[])new String[]{"\u00a7e\u4ef7\u503c\uff1a " + new BigDecimal(value).setScale(5, RoundingMode.HALF_UP).doubleValue()}));
                    }
                    is.setItemMeta(im);
                    inv.setItem(i, is);
                } else {
                    inv.setItem(i, new ItemStack(Material.AIR));
                }
                ++i;
            }
        }
    }

    public static void setMoney(Player player) {
        ItemStack money;
        try {
            money = new ItemStack(Material.DOUBLE_PLANT, 1, 0);
        }
        catch (Throwable e) {
            money = new ItemStack(Enum.valueOf(Material.class, "SUNFLOWER"));
        }
        ItemMeta im = money.getItemMeta();
        im.setDisplayName("\u00a7e\u901a\u7528\u8d27\u5e01\uff1a " + new BigDecimal(econ.getBalance((OfflinePlayer)player)).setScale(2, 1).doubleValue());
        im.setLore((List)Lists.newArrayList((Object[])new String[]{"\u00a77\u70b9\u51fb\u80cc\u5305\u7269\u54c1\u5b66\u4e60\u5e76\u8f6c\u5316"}));
        money.setItemMeta(im);
        player.getOpenInventory().setItem(49, money);
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        return this.onRightClickAir(player, hand);
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        ItemStack page;
        ItemStack glassPane;
        Inventory inv = Bukkit.createInventory(null, (int)54, (String)"\u00a71\u8f6c\u5316\u684c");
        try {
            glassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, 3);
        }
        catch (Throwable e) {
            glassPane = new ItemStack(Enum.valueOf(Material.class, "LIGHT_BLUE_STAINED_GLASS_PANE"));
        }
        int i = 0;
        while (i < 9) {
            inv.setItem(45 + i, glassPane);
            ++i;
        }
        try {
            page = new ItemStack(Material.STAINED_GLASS_PANE, 1, 5);
        }
        catch (Throwable e) {
            page = new ItemStack(Enum.valueOf(Material.class, "LIME_STAINED_GLASS_PANE"));
        }
        ItemMeta im = page.getItemMeta();
        im.setLocalizedName("\u00a7a\u4e0a\u4e00\u9875");
        page.setItemMeta(im);
        inv.setItem(47, page);
        im.setLocalizedName("\u00a7a\u4e0b\u4e00\u9875");
        page.setItemMeta(im);
        inv.setItem(51, page);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
        if (!playersConversions.containsKey(player)) {
            playersConversions.put(player, new ArrayList());
            File f = new File("plugins/MzTech/Conversions/" + player.getUniqueId().toString() + ".pc");
            if (f.exists()) {
                try {
                    DataInputStream dis = new DataInputStream(new FileInputStream(f));
                    int size = dis.readInt();
                    int i2 = 0;
                    while (i2 < size) {
                        playersConversions.get(player).add(new ItemStack(Material.values()[dis.readInt()], 1, dis.readShort()));
                        ++i2;
                    }
                    dis.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        player.openInventory(inv);
        playersOpenConversionTable.put(player, inv);
        playerOpenConversionPage.put(player, 0);
        ConversionTable.setConversions(player);
        ConversionTable.setMoney(player);
        return false;
    }

    @Override
    public MzTechCategory getCategory() {
        return BaseMachineCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u8f6c\u5316\u684c";
    }

    public static double getClinkeringValue() {
        Object obj = MzTech.instance.getConfig().get("clinkeringValue");
        if (obj instanceof Integer) {
            return ((Integer)obj).intValue();
        }
        if (obj instanceof Double) {
            return (Double)obj;
        }
        return 0.02;
    }

    public static interface Sellable {
        public double getPrice();
    }
}

