/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package mz.tech.command;

import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import org.bukkit.command.CommandSender;

public class ReloadCommand
extends MzTechCommand {
    public ReloadCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "reload";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            MzTech.instance.reloadConfig();
            sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u90e8\u5206\u5df2\u91cd\u8f7d\u914d\u7f6e");
            sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7e\u8bf7\u4f7f\u7528\u63d2\u4ef6\u7ba1\u7406\u5668\u91cd\u8f7d\u6574\u4e2a\u63d2\u4ef6");
            return true;
        }
        return false;
    }
}

