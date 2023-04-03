/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package mz.tech.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import mz.tech.item.MzTechItem;
import mz.tech.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand
extends MzTechCommand {
    public GiveCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "give <\u73a9\u5bb6> <\u7269\u54c1> [\u7269\u54c1\u53c2\u6570]";
    }

    @Override
    public List<String> onTabComplite(CommandSender sender, String[] args) {
        ArrayList<String> ret = new ArrayList<String>();
        switch (args.length) {
            case 1: {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if (p.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                        ret.add(p.getName());
                    }
                });
                break;
            }
            case 2: {
                MzTechItem.forEach(i -> {
                    if (i.getTypeName().toLowerCase().startsWith(args[1].toLowerCase())) {
                        ret.add(i.getTypeName());
                    }
                });
                break;
            }
            default: {
                MzTechItem item = MzTechItem.get(args[1]);
                if (item == null) {
                    ret.add(String.valueOf(args[1]) + "\u4e0d\u662f\u4e00\u4e2a\u7269\u54c1\u8865\u5168\u60a8\u9a6c");
                    break;
                }
                ret.addAll(item.giveCommandTab(Arrays.copyOfRange(args, 2, args.length)));
            }
        }
        return ret;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return false;
        }
        Player player = Bukkit.getPlayer((String)args[0]);
        if (player != null) {
            MzTechItem item = MzTechItem.get(args[1]);
            if (item == null) {
                return false;
            }
            if (!(item = item.copy(item)).giveCommand(Arrays.copyOfRange(args, 2, args.length))) {
                if (item.giveCommandArgs() == null) {
                    MzTech.sendMessage(sender, "\u00a76\u7528\u6cd5\uff1a\u00a7a/MzTech give " + args[1]);
                } else {
                    MzTech.sendMessage(sender, "\u00a76\u7528\u6cd5\uff1a\u00a7a/MzTech give " + args[1] + " " + item.giveCommandArgs());
                }
                return true;
            }
            new PlayerUtil(player).give(item);
            sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u7ed9\u4e88\u4e86" + player.getName() + " " + "1\u4e2a" + item.getItemMeta().getLocalizedName());
        } else {
            sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a74\u8be5\u73a9\u5bb6\u4e0d\u5728\u7ebf");
        }
        return true;
    }
}

