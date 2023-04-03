/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package mz.tech.command;

import mz.tech.command.MzTechCommand;
import org.bukkit.command.CommandSender;

public class TestCommand
extends MzTechCommand {
    public TestCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "test";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        return true;
    }
}

