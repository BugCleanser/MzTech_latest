/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  org.bukkit.configuration.Configuration
 *  org.bukkit.configuration.MemoryConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package mz.tech.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mz.tech.MzTech;
import mz.tech.item.MzTechItem;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public interface CustomItem {
    public static final Map<String, MzTechItem> loadedCustomItems = new HashMap<String, MzTechItem>();
    public static final MemoryConfiguration defaultItemInfo = new MemoryConfiguration(){
        {
            this.set("depend", new ArrayList());
        }
    };
    public static final File customItemsDir = new File(String.valueOf(MzTech.instance.getDataFolder().getAbsolutePath()) + "/customItem");

    public static void loadAll() {
        if (customItemsDir.exists()) {
            if (!customItemsDir.isDirectory()) {
                throw new RuntimeException("\u9700\u8981\u4e3a\u76ee\u5f55\u7684\u4f4d\u7f6e\u5b58\u5728\u4e00\u4e2a\u6587\u4ef6\uff1a " + customItemsDir.getAbsolutePath());
            }
        } else {
            File example = new File(customItemsDir, "Example");
            example.mkdirs();
            File j = new File(example, "Example.java");
            try {
                Throwable throwable = null;
                Object var3_5 = null;
                try (FileOutputStream fos = new FileOutputStream(j);){
                    fos.write("//\u793a\u4f8b\u4ee3\u7801\u6846\u67b6\r\n//Example.java\u4e0e\u6587\u4ef6\u5939Example\u7684\u540d\u79f0\u5bf9\u5e94\r\n//\u5fc5\u987b\u662fMzTechItem\u7684\u5b50\u7c7b\r\n//\u5982\u679c\u7ee7\u627f\u6216\u5b9e\u73b0\u7684\u7c7b\u5728\u522b\u7684\u81ea\u5b9a\u4e49\u7269\u54c1\u7684\u4ee3\u7801\u4e2d\uff0c\u9700\u8981\u5728info.yml\u4e2d\u5199\u4f9d\u8d56\r\npublic class Example extends MzTechItem\r\n{\t\r\n\t//\u5fc5\u987b\u521b\u5efa\u65e0\u53c2\u6570\u6784\u9020\u5668\r\n\tpublic void Example()\r\n\t{\t\r\n\t\t//\u5fc5\u987b\u8bbe\u7f6e\u552f\u4e00\u7684LocName\u4f5c\u4e3a\u79d1\u6280\u7269\u54c1\u7684\u6807\u8bc6\r\n\t\tsuper(new ItemStackBuilder().setLocName(\"\u00a7e\u81ea\u5b9a\u4e49\u7269\u54c1\u793a\u4f8b\").build());\r\n\t}\r\n\t@Override\r\n\tpublic String getTypeName()\r\n\t{\t\r\n\t\treturn \"\u81ea\u5b9a\u4e49\u7269\u54c1\u793a\u4f8b\";\r\n\t}\r\n\t@Override\r\n\tpublic MzTechCategory getCategory()\r\n\t{\t\r\n\t\t//\u8fd4\u56de\u5206\u7c7b\uff0c\u4f1a\u5728\u5408\u6210\u8868\u5bf9\u5e94\u7684\u5206\u7c7b\u4e2d\u663e\u793a\uff0c\u4e0d\u663e\u793a\u5219\u8fd4\u56denull\r\n\t\treturn null;\r\n\t}\r\n\t//\u53ef\u4ee5\u8986\u76d6onRightClickAir\u76d1\u542c\u624b\u6301\u53f3\u952e\u4e8b\u4ef6\r\n\t@Override\r\n\tpublic boolean onRightClickAir(Player player,EquipmentSlot hand)\r\n\t{\t\r\n\t\tMzTech.sendMessage(player,\"\u00a7a\u53f3\u952e\");\r\n\t\treturn true;\r\n\t}\r\n}\r\n".getBytes(MzTech.UTF8));
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
        loadedCustomItems.clear();
        HashMap itemsInfo = CustomItem.loadAll(customItemsDir);
        while (!itemsInfo.isEmpty()) {
            HashMap t = Maps.newHashMap(itemsInfo);
            itemsInfo.forEach((name, info) -> {
                for (String depend : info.getStringList("depend")) {
                    if (!t.containsKey(depend)) continue;
                    return;
                }
                t.remove(name);
                try {
                    CustomItem.load(name, info);
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            });
            itemsInfo = t;
        }
    }

    public static Map<String, Configuration> loadAll(File dir2) {
        HashMap<String, Configuration> itemsInfo = new HashMap<String, Configuration>();
        Lists.newArrayList((Object[])dir2.listFiles()).forEach(f -> {
            if (f.isDirectory()) {
                itemsInfo.put(f.getName(), CustomItem.loadInfo(f));
            }
        });
        return itemsInfo;
    }

    public static void load(String name, Configuration info) {
        CustomItem.load(new File(customItemsDir, name), info);
    }

    public static void load(File f, Configuration info) {
        for (String depend : info.getStringList("depend")) {
            if (loadedCustomItems.containsKey(depend)) continue;
            throw new RuntimeException(String.valueOf(f.getName()) + "\u9700\u8981\u4f9d\u8d56" + depend);
        }
    }

    public static Configuration loadInfo(File file) {
        YamlConfiguration info = YamlConfiguration.loadConfiguration((File)new File(file, "info.yml"));
        info.setDefaults((Configuration)defaultItemInfo);
        return info;
    }

    public static void reload() {
        MzTechItem.forEach(CustomItem.class, item -> MzTechItem.unreg((MzTechItem)((Object)((Object)item))));
        CustomItem.loadAll();
    }
}

