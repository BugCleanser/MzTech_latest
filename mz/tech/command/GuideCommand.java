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
import mz.tech.item.CraftGuide;
import mz.tech.util.PlayerUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuideCommand
extends MzTechCommand {
    public GuideCommand() {
        super(false);
    }

    @Override
    public String usage() {
        return "guide";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (Player.class.isAssignableFrom(sender.getClass())) {
                new PlayerUtil((Player)sender).give(new CraftGuide());
                sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u60a8\u5f97\u5230\u4e86\u4e00\u4e2a\u5408\u6210\u6307\u5357");
            } else {
                sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a74\u5982\u4f55\u7ed9\u4e88\u63a7\u5236\u53f0\u4e00\u4e2a\u7269\u54c1\uff1f");
            }
            return true;
        }
        return false;
    }
}

