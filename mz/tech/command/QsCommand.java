/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.maxgamer.quickshop.Util.Util
 */
package mz.tech.command;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import mz.tech.DataFile;
import mz.tech.MzTech;
import mz.tech.ReflectionWrapper;
import mz.tech.command.MzTechCommand;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.maxgamer.quickshop.Util.Util;

public class QsCommand
extends MzTechCommand {
    static {
        QsCommand.testForQs((CommandSender)Bukkit.getConsoleSender());
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler(priority=EventPriority.MONITOR)
            public void onPlayerJoin(PlayerJoinEvent event) {
                if (event.getPlayer().isOp()) {
                    QsCommand.testForQs((CommandSender)event.getPlayer());
                }
            }
        }, (Plugin)MzTech.instance);
    }

    public QsCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "qs";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            return false;
        }
        if (Bukkit.getPluginManager().getPlugin("QuickShop") != null) {
            File quickShop = (File)ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(JavaPlugin.class, "getFile", new Class[0]), Bukkit.getPluginManager().getPlugin("QuickShop"), new Object[0]);
            TaskUtil.throwRuntime(() -> {
                Throwable throwable = null;
                Object var2_3 = null;
                try (JarFile src = new JarFile(quickShop);){
                    File out = new File("plugins/(\u5e26\u8865\u4e01)QuickShop.jar");
                    if (!out.exists()) {
                        out.createNewFile();
                    }
                    Throwable throwable2 = null;
                    Object var6_9 = null;
                    try (JarOutputStream output = new JarOutputStream(new FileOutputStream(out));){
                        Enumeration<JarEntry> entries = src.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            output.putNextEntry(new JarEntry(entry.getName()));
                            if (entry.getName().equals("org/maxgamer/quickshop/Util/Util.class")) {
                                output.write(DataFile.readInputStream(MzTech.instance.getResource("org/maxgamer/quickshop/Util/Util.class")));
                                continue;
                            }
                            output.write(DataFile.readInputStream(src.getInputStream(entry)));
                        }
                    }
                    catch (Throwable throwable3) {
                        if (throwable2 == null) {
                            throwable2 = throwable3;
                        } else if (throwable2 != throwable3) {
                            throwable2.addSuppressed(throwable3);
                        }
                        throw throwable2;
                    }
                }
                catch (Throwable throwable4) {
                    if (throwable == null) {
                        throwable = throwable4;
                    } else if (throwable != throwable4) {
                        throwable.addSuppressed(throwable4);
                    }
                    throw throwable;
                }
            });
            MzTech.sendMessage(sender, "\u00a7a\u6210\u529f\u4e3aQuickShop\u52a0\u5165\u8865\u4e01\uff0c\u6587\u4ef6\u4f4d\u4e8eplugins/(\u5e26\u8865\u4e01)QuickShop.jar\uff0c\u8bf7\u81ea\u884c\u66ff\u6362\u539f\u63d2\u4ef6");
        } else {
            MzTech.sendMessage(sender, "\u00a7e\u672a\u5b89\u88c5QuickShop");
        }
        return true;
    }

    public static void testForQs(CommandSender sender) {
        if (Bukkit.getPluginManager().getPlugin("QuickShop") != null) {
            try {
                if (Util.matches((ItemStack)new ItemStackBuilder(Material.STICK).setLocName("test").build(), (ItemStack)new ItemStackBuilder(Material.STICK).setLocName("test2").build())) {
                    MzTech.sendMessage(sender, "\u00a7e\u73b0\u5728\u6b63\u5728\u4f7f\u7528\u7684QuickShop\u6709\u4e00\u4e9b\u5176\u5999\u7684\u7279\u6027\uff0c\u8bf7\u4f7f\u7528/MzTech qs\u5b89\u88c5\u8865\u4e01");
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }
}

