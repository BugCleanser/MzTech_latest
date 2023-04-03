/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package mz.tech.command;

import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import mz.tech.mail.SystemMailbox;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SysmailboxCommand
extends MzTechCommand {
    public SysmailboxCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "sysmailbox";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (SystemMailbox.instance.manager == sender) {
                SystemMailbox.instance.manager = null;
                MzTech.sendMessage(sender, "\u00a7a\u60a8\u5df2\u53d6\u6d88\u7ba1\u7406\u7cfb\u7edf\u90ae\u7bb1");
            } else {
                if (SystemMailbox.instance.manager != null) {
                    MzTech.sendMessage((CommandSender)SystemMailbox.instance.manager, "\u00a7e\u7cfb\u7edf\u90ae\u7bb1\u7531" + sender.getName() + "\u63a5\u7ba1");
                    MzTech.sendMessage(sender, "\u00a7e\u539f\u90ae\u7bb1\u7ba1\u7406\u8005\uff08" + SystemMailbox.instance.manager.getName() + "\uff09\u5df2\u88ab\u9876\u66ff");
                }
                SystemMailbox.instance.manager = (Player)sender;
                MzTech.sendMessage(sender, "\u00a7a\u60a8\u73b0\u5728\u5f00\u59cb\u7ba1\u7406\u7cfb\u7edf\u90ae\u7bb1\uff0c\u518d\u6b21\u8f93\u5165\u8be5\u6307\u4ee4\u53d6\u6d88");
                MzTech.sendMessage(sender, "\u00a7a\u73b0\u5728\u6253\u5f00\u90ae\u7bb1\u754c\u9762\u4e3a\u7cfb\u7edf\u90ae\u7bb1");
            }
        } else {
            MzTech.sendMessage(sender, "\u53ea\u6709\u6e38\u620f\u4e2d\u7684\u73a9\u5bb6\u624d\u80fd\u4f7f\u7528\u8be5\u547d\u4ee4");
        }
        return true;
    }
}

