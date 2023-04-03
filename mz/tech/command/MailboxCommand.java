/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import mz.tech.mail.Mail;
import mz.tech.mail.MailDraft;
import mz.tech.mail.Mailbox;
import mz.tech.mail.SystemMailbox;
import mz.tech.util.TaskUtil;
import mz.tech.util.message.ClickToSay;
import mz.tech.util.message.Message;
import mz.tech.util.message.MessageComponent;
import mz.tech.util.message.ShowTextOnMouse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MailboxCommand
extends MzTechCommand {
    public MailboxCommand() {
        super(false);
    }

    @Override
    public String usage() {
        return "mailbox";
    }

    @Override
    public List<String> onTabComplite(CommandSender sender, String[] args) {
        ArrayList<String> r = new ArrayList<String>();
        block0 : switch (args.length) {
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                switch (args[0]) {
                    case "edit": {
                        switch (args[1]) {
                            case "to": {
                                if (args[2].length() >= 3) {
                                    OfflinePlayer[] offlinePlayerArray = Bukkit.getOfflinePlayers();
                                    int n = offlinePlayerArray.length;
                                    int n2 = 0;
                                    while (n2 < n) {
                                        OfflinePlayer p = offlinePlayerArray[n2];
                                        if (p.getName().toLowerCase().startsWith(args[2].toLowerCase())) {
                                            r.add(p.getName());
                                        }
                                        ++n2;
                                    }
                                } else {
                                    for (OfflinePlayer p : Bukkit.getOnlinePlayers()) {
                                        if (!p.getName().toLowerCase().startsWith(args[2].toLowerCase())) continue;
                                        r.add(p.getName());
                                    }
                                }
                                break block0;
                            }
                        }
                    }
                }
            }
        }
        return r;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            MzTech.sendMessage(sender, "\u53ea\u6709\u73a9\u5bb6\u624d\u80fd\u4f7f\u7528\u8be5\u547d\u4ee4");
            return true;
        }
        Player player = (Player)sender;
        if (args.length <= 0) {
            Mailbox.get((OfflinePlayer)player).show(player);
            return true;
        }
        switch (args[0]) {
            case "get": {
                if (args.length != 2) return false;
                try {
                    Mail m = Mailbox.get((OfflinePlayer)player).getMail(UUID.fromString(args[1]));
                    if (m == null) {
                        MzTech.sendMessage((CommandSender)player, "\u00a7e\u90ae\u4ef6\u5df2\u5220\u9664");
                        return true;
                    }
                    m.getAttachments().give(player);
                    m.read = true;
                    MzTech.sendMessage((CommandSender)player, "\u00a7a\u6210\u529f\u9886\u53d6\u9644\u4ef6");
                    return true;
                }
                catch (Throwable e) {
                    MzTech.sendMessage((CommandSender)player, "\u00a74" + e.getMessage());
                    return true;
                }
            }
            case "delete": {
                switch (args.length) {
                    case 2: {
                        switch (args[1]) {
                            case "draft": {
                                new Message(new MessageComponent("\u00a7e\u00a7n\u70b9\u51fb\u8fd9\u6761\u6d88\u606f\u4ee5\u786e\u8ba4\u5220\u9664\u8349\u7a3f").setShowOnMouse(new ShowTextOnMouse("\u786e\u8ba4\u5220\u9664\u8349\u7a3f")).setClickMsgEvent(new ClickToSay("/mztech mailbox delete confirm"))).send(player);
                                return true;
                            }
                            case "confirm": {
                                Mailbox.get((OfflinePlayer)player).deleteDraft();
                                MzTech.sendMessage(sender, "\u00a7a\u6210\u529f\u5220\u9664\u8349\u7a3f");
                                return true;
                            }
                        }
                        try {
                            Mailbox.get((OfflinePlayer)player).delete(Mailbox.get((OfflinePlayer)player).getMail(UUID.fromString(args[1])));
                            MzTech.sendMessage(sender, "\u00a7a\u6210\u529f\u5220\u9664\u90ae\u4ef6");
                            return true;
                        }
                        catch (Throwable e) {
                            MzTech.sendMessage((CommandSender)player, "\u00a74" + e.getMessage());
                            return true;
                        }
                    }
                }
                return false;
            }
            case "remove": {
                int line;
                if (args.length != 2) return false;
                try {
                    line = Integer.parseInt(args[1]);
                }
                catch (Throwable e) {
                    return false;
                }
                if (line < 0) return false;
                if (Mailbox.get((OfflinePlayer)player).draft.msgs.size() - 1 <= line) return false;
                Mailbox.get((OfflinePlayer)player).draft.msgs.remove(line);
                Mailbox.get((OfflinePlayer)player).draft.show(player);
                return true;
            }
            case "edit": {
                if (args.length <= 1) return false;
                switch (args[1]) {
                    case "attachments": {
                        if (args.length != 2) return false;
                        Mailbox.get((OfflinePlayer)player).draft.getAttachments().open(player);
                        return true;
                    }
                    case "to": {
                        if (args.length <= 2) return false;
                        TaskUtil.runAsyncTask((Plugin)MzTech.instance, () -> {
                            OfflinePlayer to = Bukkit.getOfflinePlayer((String)MzTech.MergeStrings(2, 3, args)[2]);
                            if (to.isOnline() || to.hasPlayedBefore() || player == SystemMailbox.instance.manager && (to.getName().equals(SystemMailbox.allplayers.getName()) || to.getName().equals(SystemMailbox.newplayers.getName()) || to.getName().equals(SystemMailbox.oldplayers.getName()))) {
                                if (Mailbox.get((OfflinePlayer)player).draft.from == to) {
                                    MzTech.sendMessage((CommandSender)player, "\u00a74\u65e0\u6cd5\u8bbe\u7f6e\u6536\u4ef6\u4eba\u4e3a\u81ea\u5df1");
                                } else {
                                    Mailbox.get((OfflinePlayer)player).draft.to = to;
                                    Mailbox.get((OfflinePlayer)player).draft.show(player);
                                }
                            } else {
                                MzTech.sendMessage((CommandSender)player, "\u00a74\u65e0\u6cd5\u8bbe\u7f6e\u6536\u4ef6\u4eba\u4e3a" + to.getName() + "\uff0c\u56e0\u4e3a\u8be5\u73a9\u5bb6\u4ece\u672a\u8fdb\u5165\u8fc7\u6e38\u620f");
                            }
                        });
                        return true;
                    }
                    case "title": {
                        if (args.length <= 2) return false;
                        MailDraft draft = Mailbox.get((OfflinePlayer)player).draft;
                        draft.title = MzTech.MergeStrings(2, 3, args)[2];
                        draft.show(player);
                        return true;
                    }
                    case "msg": {
                        int line;
                        if (args.length <= 3) return false;
                        MailDraft draft = Mailbox.get((OfflinePlayer)player).draft;
                        try {
                            line = Integer.parseInt(args[2]);
                        }
                        catch (Throwable e) {
                            return false;
                        }
                        if (draft.msgs.size() - 1 <= line) return false;
                        if (line < 0) return false;
                        draft.msgs.set(line, MzTech.MergeStrings(3, 4, args)[3]);
                        draft.show(player);
                        return true;
                    }
                }
                return true;
            }
            case "insert": {
                int line;
                if (args.length != 2) return false;
                try {
                    line = Integer.parseInt(args[1]);
                }
                catch (Throwable e) {
                    return false;
                }
                MailDraft draft = Mailbox.get((OfflinePlayer)player).draft;
                if (draft.msgs.size() <= line) return false;
                if (line < 0) return false;
                draft.msgs.add(line, "\u70b9\u51fb\u7f16\u8f91\u65b0\u884c");
                draft.show(player);
                return true;
            }
            case "send": {
                if (args.length != 1) return false;
                try {
                    Mailbox.get((OfflinePlayer)player).send();
                    MzTech.sendMessage((CommandSender)player, "\u00a7a\u6210\u529f\u53d1\u9001\u90ae\u4ef6");
                    return true;
                }
                catch (Throwable e) {
                    MzTech.sendMessage((CommandSender)player, "\u00a74" + e.getMessage());
                    return true;
                }
            }
        }
        return false;
    }
}

