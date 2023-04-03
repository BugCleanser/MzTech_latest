/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.NamespacedKey
 *  org.bukkit.enchantments.EnchantmentTarget
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.PrepareAnvilEvent
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.EnchantmentStorageMeta
 *  org.bukkit.inventory.meta.ItemMeta
 */
package mz.tech.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mz.tech.MzTech;
import mz.tech.event.CreativeSetSlotEvent;
import mz.tech.event.ShowItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantUtil
implements Listener {
    static Map<Object, Enchantment> byKey = null;
    static Map<Integer, Enchantment> byId = null;
    static Map<String, Enchantment> byName = null;

    static {
        try {
            Field acceptingNew = Class.forName("org.bukkit.enchantments.Enchantment", true, Bukkit.getPluginManager().getPlugin("ProtocolLib").getClass().getClassLoader()).getDeclaredField("acceptingNew");
            acceptingNew.setAccessible(true);
            acceptingNew.setBoolean(null, true);
            Field field = Class.forName("org.bukkit.enchantments.Enchantment", true, Bukkit.getPluginManager().getPlugin("ProtocolLib").getClass().getClassLoader()).getDeclaredField("byName");
            field.setAccessible(true);
            byName = (Map)field.get(null);
            try {
                field = Class.forName("org.bukkit.enchantments.Enchantment", true, Bukkit.getPluginManager().getPlugin("ProtocolLib").getClass().getClassLoader()).getDeclaredField("byId");
                field.setAccessible(true);
                byId = (Map)field.get(null);
            }
            catch (Exception e) {
                field = Class.forName("org.bukkit.enchantments.Enchantment", true, Bukkit.getPluginManager().getPlugin("ProtocolLib").getClass().getClassLoader()).getDeclaredField("byKey");
                field.setAccessible(true);
                byKey = (Map)field.get(null);
            }
        }
        catch (Exception e) {
            MzTech.throwException(e);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onPrepareAnvil(PrepareAnvilEvent event) {
        if (event.getResult() != null && event.getResult().getType() != Material.AIR) {
            EnchantUtil.setEnchants(event.getResult(), EnchantUtil.enchantsForItem(EnchantUtil.mergeEnchants(EnchantUtil.getItemEnchants(event.getInventory().getItem(0)), EnchantUtil.getItemEnchants(event.getInventory().getItem(1))), event.getResult()));
        }
    }

    @EventHandler
    void onShowItem(ShowItemEvent event) {
        event.itemStack = EnchantUtil.enchantToLore(event.itemStack);
    }

    @EventHandler
    void onCreativeSetSlot(CreativeSetSlotEvent event) {
        List lores;
        ItemMeta im;
        if (event.is != null && event.is.getType() != Material.AIR && (im = event.is.getItemMeta()).hasLore() && (lores = im.getLore()).size() > 0 && ((String)lores.get(0)).endsWith("\u00a70\u00a7m\ufe28")) {
            ArrayList copyLores = Lists.newArrayList((Object[])lores.toArray());
            lores.forEach(l -> {
                if (l.endsWith("\u00a70\u00a7m\ufe28")) {
                    copyLores.remove(l);
                }
            });
            im.setLore((List)copyLores);
            if (event.is.getType() == Material.ENCHANTED_BOOK) {
                im.removeItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
            } else {
                im.removeItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            }
            event.is.setItemMeta(im);
        }
    }

    public static Map<Enchantment, Integer> enchantsForItem(Map<Enchantment, Integer> enchants, ItemStack item) {
        HashMap finalEnchants = Maps.newHashMap(enchants);
        enchants.forEach((e, l) -> {
            if (!e.canEnchantItem(item)) {
                finalEnchants.remove(e);
            }
        });
        return finalEnchants;
    }

    public static Map<Enchantment, Integer> mergeEnchants(Map<Enchantment, Integer> enchants1, Map<Enchantment, Integer> enchants2) {
        HashMap rm = Maps.newHashMap(enchants1);
        enchants2.forEach((e, l) -> {
            int befor = rm.getOrDefault(e, 0);
            befor = befor == l ? l + 1 : (befor > l ? befor : l);
            rm.put(e, befor > e.getMaxLevel() ? e.getMaxLevel() : befor);
        });
        return rm;
    }

    public static void setEnchants(ItemStack is, Map<Enchantment, Integer> enchants) {
        ItemMeta im = is.getItemMeta();
        if (is.getType() == Material.ENCHANTED_BOOK) {
            enchants.forEach((e, l) -> ((EnchantmentStorageMeta)im).addStoredEnchant(e, l.intValue(), true));
        } else {
            enchants.forEach((e, l) -> im.addEnchant(e, l.intValue(), true));
        }
        is.setItemMeta(im);
    }

    public static void setEnchant(ItemStack is, Enchantment enchant, int level) {
        if (is == null | is.getType() == Material.AIR) {
            return;
        }
        ItemMeta im = is.getItemMeta();
        if (is.getType() == Material.ENCHANTED_BOOK) {
            if (level == 0) {
                ((EnchantmentStorageMeta)im).removeStoredEnchant(enchant);
            } else {
                ((EnchantmentStorageMeta)im).addStoredEnchant(enchant, level, true);
            }
        } else if (level == 0) {
            im.removeEnchant(enchant);
        } else {
            im.addEnchant(enchant, level, true);
        }
        is.setItemMeta(im);
    }

    @Deprecated
    public static void unregEnchant(int id) {
        Enchantment enchant = byId.get(id);
        if (enchant != null) {
            byId.remove(id);
            Maps.newHashMap(byName).forEach((n, e) -> {
                if (e == enchant) {
                    byName.remove(n);
                }
            });
        }
    }

    public static void unregEnchant(String name) {
        Enchantment enchant = byName.get(name);
        if (enchant != null) {
            byName.remove(name);
            if (byId != null) {
                Maps.newHashMap(byId).forEach((i, e) -> {
                    if (e == enchant) {
                        byId.remove(i);
                    }
                });
            }
            if (byKey != null) {
                Maps.newHashMap(byKey).forEach((k, e) -> {
                    if (e == enchant) {
                        byKey.remove(k);
                    }
                });
            }
        }
    }

    @Deprecated
    public static void unregEnchant(NamespacedKey key) {
        Enchantment enchant = byKey.get(key);
        if (enchant != null) {
            byKey.remove(key);
            if (byId != null) {
                Maps.newHashMap(byId).forEach((i, e) -> {
                    if (e == enchant) {
                        byId.remove(i);
                    }
                });
            }
            Maps.newHashMap(byName).forEach((n, e) -> {
                if (e == enchant) {
                    byName.remove(n);
                }
            });
        }
    }

    public static Enchantment regEnchant(int id, NamespacedKey namespacedKey, final String name, final boolean treasure, final boolean cursed, final int maxLevel, final EnchantmentTarget target) {
        EnchantUtil.unregEnchant(name);
        if (Enchantment.getByName(name) == null) {
            Enchantment enchant = byId == null ? new Enchantment(namespacedKey){

                @Override
                public boolean isTreasure() {
                    return treasure;
                }

                @Override
                public boolean isCursed() {
                    return cursed;
                }

                @Override
                public int getStartLevel() {
                    return 0;
                }

                @Override
                public String getName() {
                    return name;
                }

                @Override
                public int getMaxLevel() {
                    return maxLevel;
                }

                @Override
                public EnchantmentTarget getItemTarget() {
                    return target;
                }

                @Override
                public boolean conflictsWith(Enchantment arg0) {
                    return false;
                }

                @Override
                public boolean canEnchantItem(ItemStack arg0) {
                    return true;
                }
            } : new Enchantment(id){

                @Override
                public boolean isTreasure() {
                    return treasure;
                }

                @Override
                public boolean isCursed() {
                    return cursed;
                }

                @Override
                public int getStartLevel() {
                    return 0;
                }

                @Override
                public String getName() {
                    return name;
                }

                @Override
                public int getMaxLevel() {
                    return maxLevel;
                }

                @Override
                public EnchantmentTarget getItemTarget() {
                    return target;
                }

                @Override
                public boolean conflictsWith(Enchantment arg0) {
                    return false;
                }

                @Override
                public boolean canEnchantItem(ItemStack arg0) {
                    return true;
                }
            };
            byName.put(name, enchant);
            if (byKey != null) {
                byKey.put(namespacedKey, enchant);
            }
            if (byId != null) {
                byId.put(id, enchant);
            }
            return enchant;
        }
        return null;
    }

    @Deprecated
    public static int romanToInt(String s) {
        HashMap<Character, Integer> lookup = new HashMap<Character, Integer>();
        lookup.put(Character.valueOf('M'), 1000);
        lookup.put(Character.valueOf('D'), 500);
        lookup.put(Character.valueOf('C'), 100);
        lookup.put(Character.valueOf('L'), 50);
        lookup.put(Character.valueOf('X'), 10);
        lookup.put(Character.valueOf('V'), 5);
        lookup.put(Character.valueOf('I'), 1);
        int result = 0;
        int current = 0;
        int next = 0;
        int i = 0;
        while (i < s.length() - 1) {
            current = (Integer)lookup.get(Character.valueOf(s.charAt(i)));
            if (s.charAt(i + 1) == 'm') {
                current *= 1000;
                ++i;
            }
            next = (Integer)lookup.get(Character.valueOf(s.charAt(i + 1)));
            if (s.charAt(i + 2) == 'm') {
                next *= 1000;
                ++i;
            }
            result = next > current ? (result -= current) : (result += current);
            ++i;
        }
        return result += ((Integer)lookup.get(Character.valueOf(s.charAt(s.length() - 1)))).intValue();
    }

    /*
     * Unable to fully structure code
     */
    public static String intToRoman(int number) {
        block4: {
            block3: {
                if (number <= 0 || number > 6000000) {
                    return Integer.valueOf(number).toString();
                }
                rNumber = "";
                aArray = new int[]{1000000, 900000, 500000, 400000, 100000, 90000, 50000, 40000, 10000, 9000, 5000, 4000, 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
                rArray = new String[]{"Mm", "CmMm", "Dm", "\u2188Dm", "\u2188", "\u2182\u2188", "\u2187", "\u2182\u2187", "\u2182", "M\u2182", "\u2181", "M\u2181", "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
                if (number >= 1) break block3;
                rNumber = "ERROR";
                break block4;
            }
            i = 0;
            ** GOTO lbl17
            {
                rNumber = String.valueOf(rNumber) + rArray[i];
                number -= aArray[i];
                do {
                    if (number >= aArray[i]) continue block0;
                    ++i;
lbl17:
                    // 2 sources

                } while (i < aArray.length);
            }
        }
        return rNumber;
    }

    public static boolean hasEnchants(ItemStack is) {
        if (!is.hasItemMeta()) {
            return false;
        }
        return is.getType() == Material.ENCHANTED_BOOK ? ((EnchantmentStorageMeta)is.getItemMeta()).hasStoredEnchants() : is.getItemMeta().hasEnchants();
    }

    public static Map<Enchantment, Integer> getItemEnchants(ItemStack is) {
        if (is == null || is.getType() == Material.AIR) {
            return new HashMap<Enchantment, Integer>();
        }
        return is.getType() == Material.ENCHANTED_BOOK ? ((EnchantmentStorageMeta)is.getItemMeta()).getStoredEnchants() : is.getEnchantments();
    }

    public static void removeEnchants(ItemStack is) {
        if (is.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta im = (EnchantmentStorageMeta)is.getItemMeta();
            Maps.newHashMap((Map)im.getStoredEnchants()).forEach((e, l) -> im.removeStoredEnchant(e));
            is.setItemMeta((ItemMeta)im);
        } else {
            ItemMeta im = is.getItemMeta();
            Maps.newHashMap((Map)im.getEnchants()).forEach((e, l) -> im.removeEnchant(e));
            is.setItemMeta(im);
        }
    }

    public static boolean isEnchantsHiden(ItemStack is) {
        if (!is.hasItemMeta()) {
            return false;
        }
        return is.getType() == Material.ENCHANTED_BOOK ? is.getItemMeta().hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS) : is.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS);
    }

    public static void hideEnchants(ItemStack is) {
        ItemMeta im = is.getItemMeta();
        if (is.getType() == Material.ENCHANTED_BOOK) {
            im.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        } else {
            im.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        }
        is.setItemMeta(im);
    }

    public static ItemStack enchantToLore(ItemStack is) {
        if (EnchantUtil.isEnchantsHiden(is) || !EnchantUtil.hasEnchants(is)) {
            return is;
        }
        ItemMeta im = is.getItemMeta();
        ArrayList lore = new ArrayList();
        EnchantUtil.getItemEnchants(is).forEach((enchant, level) -> {
            level = (short)level.intValue();
            String thisLore = enchant.isCursed() ? "\u00a7c" : "\u00a77";
            switch (enchant.getName()) {
                case "PROTECTION_ENVIRONMENTAL": {
                    thisLore = String.valueOf(thisLore) + "\u4fdd\u62a4";
                    break;
                }
                case "PROTECTION_FIRE": {
                    thisLore = String.valueOf(thisLore) + "\u706b\u7130\u4fdd\u62a4";
                    break;
                }
                case "PROTECTION_FALL": {
                    thisLore = String.valueOf(thisLore) + "\u6454\u843d\u4fdd\u62a4";
                    break;
                }
                case "PROTECTION_EXPLOSIONS": {
                    thisLore = String.valueOf(thisLore) + "\u7206\u70b8\u4fdd\u62a4";
                    break;
                }
                case "PROTECTION_PROJECTILE": {
                    thisLore = String.valueOf(thisLore) + "\u6295\u63b7\u7269\u4fdd\u62a4";
                    break;
                }
                case "OXYGEN": {
                    thisLore = String.valueOf(thisLore) + "\u6c34\u4e0b\u547c\u5438";
                    break;
                }
                case "WATER_WORKER": {
                    thisLore = String.valueOf(thisLore) + "\u6c34\u4e0b\u901f\u6398";
                    break;
                }
                case "THORNS": {
                    thisLore = String.valueOf(thisLore) + "\u8346\u68d8";
                    break;
                }
                case "DEPTH_STRIDER": {
                    thisLore = String.valueOf(thisLore) + "\u6df1\u6d77\u63a2\u7d22\u8005";
                    break;
                }
                case "FROST_WALKER": {
                    thisLore = String.valueOf(thisLore) + "\u51b0\u971c\u884c\u8005";
                    break;
                }
                case "BINDING_CURSE": {
                    thisLore = String.valueOf(thisLore) + "\u7ed1\u5b9a\u8bc5\u5492";
                    break;
                }
                case "DAMAGE_ALL": {
                    thisLore = String.valueOf(thisLore) + "\u950b\u5229";
                    break;
                }
                case "DAMAGE_UNDEAD": {
                    thisLore = String.valueOf(thisLore) + "\u4ea1\u7075\u6740\u624b";
                    break;
                }
                case "DAMAGE_ARTHROPODS": {
                    thisLore = String.valueOf(thisLore) + "\u8282\u80a2\u6740\u624b";
                    break;
                }
                case "KNOCKBACK": {
                    thisLore = String.valueOf(thisLore) + "\u51fb\u9000";
                    break;
                }
                case "FIRE_ASPECT": {
                    thisLore = String.valueOf(thisLore) + "\u706b\u7130\u9644\u52a0";
                    break;
                }
                case "LOOT_BONUS_MOBS": {
                    thisLore = String.valueOf(thisLore) + "\u62a2\u593a";
                    break;
                }
                case "SWEEPING_EDGE": {
                    thisLore = String.valueOf(thisLore) + "\u6a2a\u626b\u4e4b\u5203";
                    break;
                }
                case "DIG_SPEED": {
                    thisLore = String.valueOf(thisLore) + "\u6548\u7387";
                    break;
                }
                case "SILK_TOUCH": {
                    thisLore = String.valueOf(thisLore) + "\u7cbe\u51c6\u91c7\u96c6";
                    break;
                }
                case "DURABILITY": {
                    thisLore = String.valueOf(thisLore) + "\u8010\u4e45";
                    break;
                }
                case "LOOT_BONUS_BLOCKS": {
                    thisLore = String.valueOf(thisLore) + "\u65f6\u8fd0";
                    break;
                }
                case "ARROW_DAMAGE": {
                    thisLore = String.valueOf(thisLore) + "\u529b\u91cf";
                    break;
                }
                case "ARROW_KNOCKBACK": {
                    thisLore = String.valueOf(thisLore) + "\u51b2\u51fb";
                    break;
                }
                case "ARROW_FIRE": {
                    thisLore = String.valueOf(thisLore) + "\u706b\u77e2";
                    break;
                }
                case "ARROW_INFINITE": {
                    thisLore = String.valueOf(thisLore) + "\u65e0\u9650";
                    break;
                }
                case "LUCK": {
                    thisLore = String.valueOf(thisLore) + "\u6d77\u4e4b\u7737\u987e";
                    break;
                }
                case "LURE": {
                    thisLore = String.valueOf(thisLore) + "\u9975\u9493";
                    break;
                }
                case "LOYALTY": {
                    thisLore = String.valueOf(thisLore) + "\u5fe0\u8bda";
                    break;
                }
                case "IMPALING": {
                    thisLore = String.valueOf(thisLore) + "\u7a7f\u523a";
                    break;
                }
                case "RIPTIDE": {
                    thisLore = String.valueOf(thisLore) + "\u6fc0\u6d41";
                    break;
                }
                case "CHANNELING": {
                    thisLore = String.valueOf(thisLore) + "\u5f15\u96f7";
                    break;
                }
                case "MULTISHOT": {
                    thisLore = String.valueOf(thisLore) + "\u6563\u5c04";
                    break;
                }
                case "QUICK_CHARGE": {
                    thisLore = String.valueOf(thisLore) + "\u5feb\u901f\u88c5\u586b";
                    break;
                }
                case "PIERCING": {
                    thisLore = String.valueOf(thisLore) + "\u7a7f\u900f";
                    break;
                }
                case "MENDING": {
                    thisLore = String.valueOf(thisLore) + "\u7ecf\u9a8c\u4fee\u8865";
                    break;
                }
                case "VANISHING_CURSE": {
                    thisLore = String.valueOf(thisLore) + "\u6d88\u5931\u8bc5\u5492";
                    break;
                }
                case "SOUL_SPEED": {
                    thisLore = String.valueOf(thisLore) + "\u7075\u9b42\u75be\u884c";
                    break;
                }
                default: {
                    thisLore = String.valueOf(thisLore) + enchant.getName().toLowerCase();
                }
            }
            if (enchant.getMaxLevel() > 1 || level > 1) {
                thisLore = String.valueOf(thisLore) + " " + EnchantUtil.intToRoman(level);
            }
            lore.add(String.valueOf(thisLore) + "\u00a70\u00a7m\ufe28");
        });
        if (im.hasLore()) {
            lore.addAll(im.getLore());
        }
        im.setLore(lore);
        is.setItemMeta(im);
        EnchantUtil.hideEnchants(is);
        return is;
    }
}

