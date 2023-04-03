/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 *  org.bukkit.block.Sign
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package mz.tech.command;

import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetsignCommand
extends MzTechCommand {
    public SetsignCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "setsign <\u884c\u6570> <\u5185\u5bb9>";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return false;
        }
        args = MzTech.MergeStrings(1, 2, args);
        if (sender instanceof Player) {
            Block block = ((Player)sender).getTargetBlock(null, 10);
            try {
                Sign state = (Sign)block.getState();
                state.setLine(new Integer(args[0]) - 1, args[1].replace("&", "\u00a7"));
                state.update();
            }
            catch (Throwable e) {
                sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a74\u8bf7\u4f7f\u7528\u6307\u9488\u5bf9\u51c6\u4e00\u4e2a\u544a\u793a\u724c");
            }
        } else {
            sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a74\u6e38\u620f\u4e2d\u7684\u73a9\u5bb6\u624d\u80fd\u4f7f\u7528\u8be5\u547d\u4ee4");
        }
        return true;
    }
}

