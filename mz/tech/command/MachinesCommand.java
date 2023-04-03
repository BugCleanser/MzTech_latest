/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package mz.tech.command;

import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import mz.tech.machine.MachineChunk;
import org.bukkit.command.CommandSender;

public class MachinesCommand
extends MzTechCommand {
    public MachinesCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "machines";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u5df2\u52a0\u8f7d\u7684\u542b\u673a\u5668\u7684\u533a\u5757\uff1a");
            MachineChunk.chunks.forEach((c, mc) -> MzTech.sendMessage(sender, "  " + c.getWorld().getName() + "," + c.getX() + "," + c.getZ()));
            return true;
        }
        return false;
    }
}

