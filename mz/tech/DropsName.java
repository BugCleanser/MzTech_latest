/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.reflect.TypeToken
 *  com.google.gson.Gson
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.ItemMergeEvent
 *  org.bukkit.event.entity.ItemSpawnEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package mz.tech;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import mz.tech.DataFile;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.ReflectionWrapper;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.MoreThrowable;
import mz.tech.util.TaskUtil;
import mz.tech.util.message.Message;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class DropsName
implements Listener {
    static boolean dropsName = true;
    static String customNameFormat = "[\u00a7o{Name}\u00a7r]";
    static String locNameFormat = "{Name}";
    static String defaultNameFormat = "{Name}";
    static String countFormat = "\u00a7r *{Count}";
    static Map<String, String> lang = null;
    static List<String> masks = null;
    static Class<?> craftItemStack = ReflectionWrapper.getCraftBukkitClass("inventory.CraftItemStack");
    static Method asNMSCopy = ReflectionWrapper.getMethod(craftItemStack, "asNMSCopy", ItemStack.class);
    static Class<?> nmsItemStack = ReflectionWrapper.getNMSClass("ItemStack");
    static Method getItem = ReflectionWrapper.getMethod(nmsItemStack, "getItem", new Class[0]);
    public static boolean growGrassTranslate = false;
    static Map<String, String> growGrassTranslations = new HashMap<String, String>();
    private static NBT versionManifest;
    private static JsonObject versionInfo;
    private static JsonObject versionAssets;

    static void reload(FileConfiguration fileConfiguration) {
        dropsName = (Boolean)fileConfiguration.get("dropsName");
        customNameFormat = (String)fileConfiguration.get("customNameFormat");
        locNameFormat = (String)fileConfiguration.get("locNameFormat");
        defaultNameFormat = (String)fileConfiguration.get("defaultNameFormat");
        countFormat = (String)fileConfiguration.get("countFormat");
        masks = (List)fileConfiguration.get("masks");
        TaskUtil.runAsyncTask((Plugin)MzTech.instance, () -> {
            try {
                File path = new File(String.valueOf(MzTech.instance.getDataFolder().getPath()) + "/lang");
                if (!path.exists()) {
                    path.mkdirs();
                }
                MzTech.instance.getLogger().info("\u6b63\u5728\u52a0\u8f7d\u8bed\u8a00\u6587\u4ef6...");
                lang = DropsName.getLang(fileConfiguration.getString("lang"));
                growGrassTranslate = fileConfiguration.getBoolean("growGrassTranslate");
                if (growGrassTranslate) {
                    MzTech.instance.getLogger().info("\u6b63\u5728\u52a0\u8f7d\u751f\u8349\u7ffb\u8bd1...");
                    growGrassTranslations = fileConfiguration.getConfigurationSection("growGrassTranslations").getValues(true);
                    Maps.newHashMap(growGrassTranslations).forEach((id, translation) -> {
                        growGrassTranslations.remove(id);
                        if (!id.contains(":")) {
                            id = String.valueOf(id) + ":0";
                        }
                        growGrassTranslations.put(id.toUpperCase(), translation.replace("&", "\u00a7"));
                    });
                    MzTech.instance.getLogger().info("\u6210\u529f\u52a0\u8f7d\u751f\u8349\u7ffb\u8bd1\u3002");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                DropsName.reload(fileConfiguration);
            }
        });
    }

    public static String getItemUnlocalizedName(ItemStack is) {
        String name;
        Object itemStack = ReflectionWrapper.invokeStaticMethod(asNMSCopy, is);
        Object item = ReflectionWrapper.invokeMethod(getItem, itemStack, new Object[0]);
        try {
            name = ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethodParent(item.getClass(), "a", nmsItemStack), item, itemStack) + ".name";
        }
        catch (Exception e) {
            name = (String)ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethodParent(item.getClass(), "f", nmsItemStack), item, itemStack);
        }
        return name;
    }

    public static String getGrowGrassName(ItemStack is) {
        String grassTranslation = growGrassTranslations.get(String.valueOf(is.getType().toString()) + ":" + (is.getType().getMaxDurability() == 0 ? is.getDurability() : (short)0));
        if (grassTranslation != null) {
            return grassTranslation;
        }
        return null;
    }

    public static String getShowName(ItemStack is) {
        String langName;
        String grassTranslation;
        if (ItemStackBuilder.isEmpty(is)) {
            return "\u7a7a\u6c14";
        }
        ItemMeta im = is.getItemMeta();
        if (im.hasDisplayName()) {
            return customNameFormat.replace("{Name}", im.getDisplayName());
        }
        if (im.hasLocalizedName()) {
            return ("\u00a7r" + locNameFormat).replace("{Name}", im.getLocalizedName());
        }
        if (growGrassTranslate && (grassTranslation = DropsName.getGrowGrassName(is)) != null) {
            return grassTranslation;
        }
        String string = langName = lang == null ? null : lang.get(DropsName.getItemUnlocalizedName(is));
        if (langName != null) {
            return defaultNameFormat.replace("{Name}", langName);
        }
        return DropsName.getItemUnlocalizedName(is);
    }

    public static String getDropName(ItemStack is) {
        String showName = DropsName.getShowName(is);
        if (showName != null) {
            if (is.getAmount() <= 1) {
                return showName;
            }
            return String.valueOf(showName) + countFormat.replace("{Count}", String.valueOf(is.getAmount()));
        }
        return null;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onItemSpawn(ItemSpawnEvent event) {
        if (dropsName) {
            event.getEntity().setCustomNameVisible(true);
            String name = DropsName.getDropName(event.getEntity().getItemStack());
            if (name != null) {
                event.getEntity().setCustomName(name);
                masks.forEach(t -> {
                    if (Pattern.matches(t, event.getEntity().getCustomName())) {
                        event.getEntity().setCustomNameVisible(false);
                        return;
                    }
                });
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onItemMerge(ItemMergeEvent event) {
        if (dropsName) {
            ItemStack is = event.getEntity().getItemStack().clone();
            is.setAmount(is.getAmount() + event.getTarget().getItemStack().getAmount());
            String name = DropsName.getDropName(is);
            if (name != null) {
                event.getTarget().setCustomName(name);
                masks.forEach(t -> {
                    if (Pattern.matches(t, event.getTarget().getCustomName())) {
                        event.getTarget().setCustomNameVisible(false);
                        return;
                    }
                });
            }
        }
    }

    public static NBT getVersionManifest() {
        NBT r;
        block18: {
            if (versionManifest != null) {
                return versionManifest;
            }
            MzTech.instance.getLogger().info("\u6b63\u5728\u4e0b\u8f7dMC\u7248\u672c\u6e05\u5355");
            r = null;
            try {
                try {
                    Throwable throwable = null;
                    Object var2_4 = null;
                    try (InputStream dis = DropsName.openConnectionCheckRedirects(new URL("https://bmclapi2.bangbang93.com/mc/game/version_manifest.json").openConnection());){
                        String json = new String(DataFile.readInputStream(dis), MzTech.UTF8);
                        r = new NBT(json);
                    }
                    catch (Throwable throwable2) {
                        if (throwable == null) {
                            throwable = throwable2;
                        } else if (throwable != throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        throw throwable;
                    }
                }
                catch (Throwable e) {
                    MzTech.throwException(e);
                    if (r != null) {
                        versionManifest = r;
                        break block18;
                    }
                    MzTech.instance.getLogger().warning("\u4e0b\u8f7dMC\u7248\u672c\u6e05\u5355\u5931\u8d25");
                }
            }
            finally {
                if (r != null) {
                    versionManifest = r;
                } else {
                    MzTech.instance.getLogger().warning("\u4e0b\u8f7dMC\u7248\u672c\u6e05\u5355\u5931\u8d25");
                }
            }
        }
        return r;
    }

    public static JsonObject getVersionInfo() {
        if (versionInfo != null) {
            return versionInfo;
        }
        MzTech.instance.getLogger().info("\u6b63\u5728\u4e0b\u8f7d\u5f53\u524dMC\u7248\u672c\u4fe1\u606f");
        JsonObject[] rn = new JsonObject[1];
        try {
            DropsName.getVersionManifest().getChildList("versions").forEach(v -> {
                if (v.getString("id").equals(Message.minecraftVersion)) {
                    try {
                        Throwable throwable = null;
                        Object var3_5 = null;
                        try (InputStream dis = DropsName.openConnectionCheckRedirects(new URL(v.getString("url").replace("https://launchermeta.mojang.com", "https://bmclapi2.bangbang93.com").replace("https://launcher.mojang.com", "https://bmclapi2.bangbang93.com")).openConnection());){
                            jsonObjectArray[0] = new JsonParser().parse(new String(DataFile.readInputStream(dis), MzTech.UTF8)).getAsJsonObject();
                        }
                        catch (Throwable throwable2) {
                            if (throwable == null) {
                                throwable = throwable2;
                            } else if (throwable != throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                            throw throwable;
                        }
                    }
                    catch (Throwable e) {
                        MzTech.throwException(e);
                    }
                }
            });
        }
        finally {
            if (rn[0] != null) {
                versionInfo = rn[0];
            } else {
                MzTech.instance.getLogger().warning("\u5f53\u524dMC\u7248\u672c\u4fe1\u606f\u4e0b\u8f7d\u5931\u8d25");
            }
        }
        return rn[0];
    }

    public static JsonObject getVersionAssets() {
        JsonObject r;
        block18: {
            if (versionAssets != null) {
                return versionAssets;
            }
            MzTech.instance.getLogger().info("\u6b63\u5728\u4e0b\u8f7dMC\u8d44\u6e90\u5217\u8868");
            r = null;
            try {
                try {
                    Throwable throwable = null;
                    Object var2_4 = null;
                    try (InputStream dis = DropsName.openConnectionCheckRedirects(new URL(DropsName.getVersionInfo().get("assetIndex").getAsJsonObject().get("url").getAsString().replace("https://launchermeta.mojang.com", "https://bmclapi2.bangbang93.com").replace("https://launcher.mojang.com", "https://bmclapi2.bangbang93.com")).openConnection());){
                        r = new JsonParser().parse(new String(DataFile.readInputStream(dis), MzTech.UTF8)).getAsJsonObject().get("objects").getAsJsonObject();
                    }
                    catch (Throwable throwable2) {
                        if (throwable == null) {
                            throwable = throwable2;
                        } else if (throwable != throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        throw throwable;
                    }
                }
                catch (Throwable e) {
                    MzTech.throwException(e);
                    if (r != null) {
                        versionAssets = r;
                        break block18;
                    }
                    MzTech.instance.getLogger().warning("\u4e0b\u8f7dMC\u8d44\u6e90\u5217\u8868\u5931\u8d25");
                }
            }
            finally {
                if (r != null) {
                    versionAssets = r;
                } else {
                    MzTech.instance.getLogger().warning("\u4e0b\u8f7dMC\u8d44\u6e90\u5217\u8868\u5931\u8d25");
                }
            }
        }
        return r;
    }

    public static DataInputStream getAsset(String file) {
        if (DropsName.getVersionAssets() == null) {
            return null;
        }
        JsonObject ass = DropsName.getVersionAssets().get(file).getAsJsonObject();
        String hash = ass.get("hash").getAsString();
        try {
            return new DataInputStream(DropsName.openConnectionCheckRedirects(new URL("http://bmclapi2.bangbang93.com/assets/" + hash.substring(0, 2) + "/" + hash).openConnection()));
        }
        catch (Throwable e) {
            return (DataInputStream)MzTech.throwException(e);
        }
    }

    public static Map<String, String> getLang(String lang) {
        Map r = null;
        try {
            Properties p = new Properties();
            p.load(new StringReader(new String(DataFile.readInputStream(DropsName.getAsset("minecraft/lang/" + lang.toLowerCase() + ".lang")), MzTech.UTF8)));
            r = (Map)MzTech.castBypassCheck(p);
        }
        catch (Throwable e) {
            try {
                String json = new String(DataFile.readInputStream(DropsName.getAsset("minecraft/lang/" + lang.toLowerCase() + ".json")), MzTech.UTF8);
                r = (Map)MzTech.castBypassCheck(new Gson().fromJson(json, new TypeToken<Map<String, String>>(){}.getType()));
            }
            catch (Throwable e1) {
                MzTech.throwException(new MoreThrowable(e, e1));
            }
        }
        return r;
    }

    public static InputStream openConnectionCheckRedirects(URLConnection c) throws IOException {
        InputStream in;
        boolean redir;
        int redirects = 0;
        do {
            HttpURLConnection http;
            int stat;
            if (c instanceof HttpURLConnection) {
                ((HttpURLConnection)c).setInstanceFollowRedirects(false);
            }
            in = c.getInputStream();
            redir = false;
            if (!(c instanceof HttpURLConnection) || (stat = (http = (HttpURLConnection)c).getResponseCode()) < 300 || stat > 307 || stat == 306 || stat == 304) continue;
            URL base = http.getURL();
            String loc = http.getHeaderField("Location");
            URL target = null;
            if (loc != null) {
                target = new URL(base, loc);
            }
            http.disconnect();
            if (target == null || redirects >= 5) {
                throw new SecurityException("illegal URL redirect");
            }
            redir = true;
            c = target.openConnection();
            ++redirects;
        } while (redir);
        return in;
    }
}

