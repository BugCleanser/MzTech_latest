/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import mz.tech.DataFile;
import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import mz.tech.item.MzTechItem;
import mz.tech.item.sundry.PresentBox;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.PlayerUtil;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class KitCommand
extends MzTechCommand {
    KitListener listener = new KitListener();

    public KitCommand() {
        super(true);
        File f = new File(MzTech.instance.getDataFolder() + "/kits/newPlayers");
        if (!f.exists()) {
            f.mkdirs();
        }
        if (!(f = new File(MzTech.instance.getDataFolder() + "/kits/oldPlayers")).exists()) {
            f.mkdirs();
        }
        Bukkit.getPluginManager().registerEvents((Listener)this.listener, (Plugin)MzTech.instance);
    }

    @Override
    public String usage() {
        return "kit <old | new | all> <\u793c\u5305\u540d\u79f0>";
    }

    public static File getOldPlayersKitFile(String name) {
        return new File(MzTech.instance.getDataFolder() + "/kits/oldPlayers/" + name + ".kit");
    }

    public static File getNewPlayersKitFile(String name) {
        return new File(MzTech.instance.getDataFolder() + "/kits/newPlayers/" + name + ".kit");
    }

    public List<File> getKitFiles(String name) {
        ArrayList<File> rl = new ArrayList<File>();
        if (this.hasOldPlayersKit(name)) {
            rl.add(KitCommand.getOldPlayersKitFile(name));
        }
        if (this.hasNewPlayersKit(name)) {
            rl.add(KitCommand.getNewPlayersKitFile(name));
        }
        return rl;
    }

    public boolean hasNewPlayersKit(String name) {
        return KitCommand.getNewPlayersKitFile(name).isFile();
    }

    public boolean hasOldPlayersKit(String name) {
        return KitCommand.getOldPlayersKitFile(name).isFile();
    }

    public boolean hasAllPlayersKit(String name) {
        return this.hasNewPlayersKit(name) && this.hasOldPlayersKit(name);
    }

    public boolean hasKit(String name) {
        return this.hasNewPlayersKit(name) || this.hasOldPlayersKit(name);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        block14: {
            block18: {
                block15: {
                    block16: {
                        if (args.length <= 1) break block14;
                        if (!(sender instanceof Player)) break block15;
                        ItemStack is = new PlayerUtil((Player)sender).getItemInHand();
                        if (ItemStackBuilder.isEmpty(is)) break block16;
                        if (!(MzTechItem.asMzTechCopy(is) instanceof PresentBox)) {
                            MzTech.sendMessage(sender, "\u00a7e\u4f60\u624b\u6301\u7684\u4e0d\u662f\u4e00\u4e2a\u793c\u7269\u76d2\uff0c\u4f46\u4f9d\u7136\u7ee7\u7eed\u6267\u884c");
                        }
                        switch (args[0]) {
                            case "old": {
                                new Kit(MzTech.MergeStrings(1, 2, args)[1], is, true).save();
                                break;
                            }
                            case "new": {
                                new Kit(MzTech.MergeStrings(1, 2, args)[1], is, false).save();
                                break;
                            }
                            case "all": {
                                new Kit(MzTech.MergeStrings(1, 2, args)[1], is, true).save();
                                new Kit(MzTech.MergeStrings(1, 2, args)[1], is, false).save();
                                break;
                            }
                            default: {
                                return false;
                            }
                        }
                        Bukkit.getOnlinePlayers().forEach(p -> this.listener.onPlayerJoin(new PlayerJoinEvent(p, null)));
                        MzTech.sendMessage(sender, "\u00a7a\u793c\u5305" + MzTech.MergeStrings(1, 2, args)[1] + "\u6210\u529f\u53d1\u653e");
                        break block18;
                    }
                    MzTech.sendMessage(sender, "\u00a74\u8bf7\u624b\u6301\u4e00\u4e2a\u793c\u5305");
                    break block18;
                }
                MzTech.sendMessage(sender, "\u00a74\u53ea\u6709\u6e38\u620f\u4e2d\u7684\u73a9\u5bb6\u80fd\u6267\u884c\u8be5\u6307\u4ee4");
            }
            return true;
        }
        return false;
    }

    public static class Kit {
        boolean toOldPlayers;
        String name;
        ItemStack is;
        List<OfflinePlayer> playersGot = new ArrayList<OfflinePlayer>();

        public Kit(String name, ItemStack is, boolean toOldPlayers) {
            this.name = name;
            this.is = is;
            this.toOldPlayers = toOldPlayers;
        }

        public void save() {
            File file = this.toOldPlayers ? KitCommand.getOldPlayersKitFile(this.name) : KitCommand.getNewPlayersKitFile(this.name);
            if (!file.exists()) {
                TaskUtil.throwRuntime(() -> {
                    boolean bl = file.createNewFile();
                });
            }
            try {
                Throwable throwable = null;
                Object var3_5 = null;
                try (DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));){
                    DataFile.saveString(fos, this.name);
                    DataFile.writeItemStack(fos, this.is);
                    DataFile.saveList(fos, this.playersGot, (s, p) -> DataFile.saveString(s, p.getName()));
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
                e.printStackTrace();
            }
        }

        public static Kit load(File file, boolean toOldPlayers) {
            Kit k = null;
            try {
                Throwable throwable = null;
                Object var4_6 = null;
                try (DataInputStream fis = new DataInputStream(new FileInputStream(file));){
                    k = new Kit(DataFile.loadString(fis), DataFile.readItemStack(fis), toOldPlayers);
                    k.playersGot = DataFile.loadList(fis, s -> Bukkit.getOfflinePlayer((String)DataFile.loadString(fis)));
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
                e.printStackTrace();
            }
            return k;
        }

        public boolean hasGiven(OfflinePlayer player) {
            return this.playersGot.contains(player);
        }

        public void give(OfflinePlayer player) {
            this.playersGot.add(player);
        }
    }

    public class KitListener
    implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            if (!event.getPlayer().hasPlayedBefore()) {
                NewPlayersKit.loadAll().forEach(k -> {
                    if (!k.hasGiven((OfflinePlayer)event.getPlayer())) {
                        k.give((OfflinePlayer)event.getPlayer());
                        new PlayerUtil(event.getPlayer()).give(k.is);
                        MzTech.sendMessage((CommandSender)event.getPlayer(), "\u00a7a\u60a8\u9886\u53d6\u4e86\u00a76" + k.name);
                        k.save();
                    }
                });
                OldPlayersKit.loadAll().forEach(k -> {
                    if (!k.hasGiven((OfflinePlayer)event.getPlayer())) {
                        k.give((OfflinePlayer)event.getPlayer());
                        k.save();
                    }
                });
            } else {
                OldPlayersKit.loadAll().forEach(k -> {
                    if (!k.hasGiven((OfflinePlayer)event.getPlayer())) {
                        k.give((OfflinePlayer)event.getPlayer());
                        new PlayerUtil(event.getPlayer()).give(k.is);
                        MzTech.sendMessage((CommandSender)event.getPlayer(), "\u00a7a\u60a8\u9886\u53d6\u4e86\u00a76" + k.name);
                        k.save();
                    }
                });
                NewPlayersKit.loadAll().forEach(k -> {
                    if (!k.hasGiven((OfflinePlayer)event.getPlayer())) {
                        k.give((OfflinePlayer)event.getPlayer());
                        k.save();
                    }
                });
            }
        }
    }

    public static class NewPlayersKit
    extends Kit {
        public NewPlayersKit(String name, ItemStack is) {
            super(name, is, false);
        }

        public static Kit load(File file) {
            return Kit.load(file, false);
        }

        public static List<Kit> loadAll() {
            File dir2 = new File(MzTech.instance.getDataFolder() + "/kits/newPlayers");
            ArrayList<Kit> rl = new ArrayList<Kit>();
            File[] fileArray = dir2.listFiles();
            int n = fileArray.length;
            int n2 = 0;
            while (n2 < n) {
                File f = fileArray[n2];
                if (f.getName().endsWith(".kit")) {
                    rl.add(NewPlayersKit.load(f));
                }
                ++n2;
            }
            return rl;
        }
    }

    public static class OldPlayersKit
    extends Kit {
        public OldPlayersKit(String name, ItemStack is) {
            super(name, is, true);
        }

        public static Kit load(File file) {
            return Kit.load(file, true);
        }

        public static List<Kit> loadAll() {
            File dir2 = new File(MzTech.instance.getDataFolder() + "/kits/oldPlayers");
            ArrayList<Kit> rl = new ArrayList<Kit>();
            File[] fileArray = dir2.listFiles();
            int n = fileArray.length;
            int n2 = 0;
            while (n2 < n) {
                File f = fileArray[n2];
                if (f.getName().endsWith(".kit")) {
                    rl.add(OldPlayersKit.load(f));
                }
                ++n2;
            }
            return rl;
        }
    }
}

