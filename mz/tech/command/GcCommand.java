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

public class GcCommand
extends MzTechCommand {
    public GcCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "gc";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            long befor = Runtime.getRuntime().freeMemory();
            System.gc();
            MzTech.sendMessage(sender, "\u00a7a\u6210\u529f\u91ca\u653e " + (Runtime.getRuntime().freeMemory() - befor) / 1024L / 1024L + "MB \u5185\u5b58");
            return true;
        }
        return false;
    }
}

