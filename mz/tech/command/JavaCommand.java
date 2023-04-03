/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package mz.tech.command;

import bsh.Interpreter;
import java.io.StringReader;
import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import org.bukkit.command.CommandSender;

public class JavaCommand
extends MzTechCommand {
    public JavaCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "java <Java7\u4ee3\u7801>";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            try {
                Interpreter interpreter = new Interpreter();
                String src = MzTech.MergeStrings(0, 1, args)[0];
                interpreter.getNameSpace().importPackage("java.lang");
                interpreter.getNameSpace().importPackage("org.bukkit");
                interpreter.getNameSpace().importPackage("org.bukkit.advancement");
                interpreter.getNameSpace().importPackage("org.bukkit.attribute");
                interpreter.getNameSpace().importPackage("org.bukkit.block");
                interpreter.getNameSpace().importPackage("org.bukkit.block.banner");
                interpreter.getNameSpace().importPackage("org.bukkit.block.structure");
                interpreter.getNameSpace().importPackage("org.bukkit.boss");
                interpreter.getNameSpace().importPackage("org.bukkit.command");
                interpreter.getNameSpace().importPackage("org.bukkit.command.defaults");
                interpreter.getNameSpace().importPackage("org.bukkit.configuration");
                interpreter.getNameSpace().importPackage("org.bukkit.configuration.file");
                interpreter.getNameSpace().importPackage("org.bukkit.configuration.serialization");
                interpreter.getNameSpace().importPackage("org.bukkit.conversations");
                interpreter.getNameSpace().importPackage("org.bukkit.enchantments");
                interpreter.getNameSpace().importPackage("org.bukkit.entity");
                interpreter.getNameSpace().importPackage("org.bukkit.entity.minecart");
                interpreter.getNameSpace().importPackage("org.bukkit.event");
                interpreter.getNameSpace().importPackage("org.bukkit.event.block");
                interpreter.getNameSpace().importPackage("org.bukkit.event.enchantment");
                interpreter.getNameSpace().importPackage("org.bukkit.event.entity");
                interpreter.getNameSpace().importPackage("org.bukkit.event.hanging");
                interpreter.getNameSpace().importPackage("org.bukkit.event.inventory");
                interpreter.getNameSpace().importPackage("org.bukkit.event.player");
                interpreter.getNameSpace().importPackage("org.bukkit.event.server");
                interpreter.getNameSpace().importPackage("org.bukkit.event.vehicle");
                interpreter.getNameSpace().importPackage("org.bukkit.event.weather");
                interpreter.getNameSpace().importPackage("org.bukkit.event.world");
                interpreter.getNameSpace().importPackage("org.bukkit.generator");
                interpreter.getNameSpace().importPackage("org.bukkit.help");
                interpreter.getNameSpace().importPackage("org.bukkit.inventory");
                interpreter.getNameSpace().importPackage("org.bukkit.inventory.meta");
                interpreter.getNameSpace().importPackage("org.bukkit.map");
                interpreter.getNameSpace().importPackage("org.bukkit.material");
                interpreter.getNameSpace().importPackage("org.bukkit.material.types");
                interpreter.getNameSpace().importPackage("org.bukkit.metadata");
                interpreter.getNameSpace().importPackage("org.bukkit.permissions");
                interpreter.getNameSpace().importPackage("org.bukkit.plugin");
                interpreter.getNameSpace().importPackage("org.bukkit.plugin.java");
                interpreter.getNameSpace().importPackage("org.bukkit.plugin.messaging");
                interpreter.getNameSpace().importPackage("org.bukkit.potion");
                interpreter.getNameSpace().importPackage("org.bukkit.projectiles");
                interpreter.getNameSpace().importPackage("org.bukkit.scheduler");
                interpreter.getNameSpace().importPackage("org.bukkit.scoreboard");
                interpreter.getNameSpace().importPackage("org.bukkit.util");
                interpreter.getNameSpace().importPackage("org.bukkit.util.io");
                interpreter.getNameSpace().importPackage("org.bukkit.util.noise");
                interpreter.getNameSpace().importPackage("org.bukkit.util.permissions");
                interpreter.getNameSpace().importPackage("mz.tech");
                interpreter.getNameSpace().importPackage("mz.tech.category");
                interpreter.getNameSpace().importPackage("mz.tech.command");
                interpreter.getNameSpace().importPackage("mz.tech.enchant");
                interpreter.getNameSpace().importPackage("mz.tech.event");
                interpreter.getNameSpace().importPackage("mz.tech.item");
                interpreter.getNameSpace().importPackage("mz.tech.luckyEffect");
                interpreter.getNameSpace().importPackage("mz.tech.machine");
                interpreter.getNameSpace().importPackage("mz.tech.machine.generator");
                interpreter.getNameSpace().importPackage("mz.tech.mail");
                interpreter.getNameSpace().importPackage("mz.tech.recipe");
                interpreter.getNameSpace().importPackage("mz.tech.util");
                interpreter.getNameSpace().importPackage("mz.tech.util.map");
                interpreter.getNameSpace().importPackage("mz.tech.util.message");
                interpreter.set("me", sender);
                interpreter.set("src", src);
                interpreter.set("interpreter", interpreter);
                Object r = interpreter.eval(new StringReader(src), interpreter.getNameSpace(), "java.java");
                if (r == null) {
                    MzTech.sendMessage(sender, "\u00a7a\u4ee3\u7801\u6267\u884c\u5b8c\u6bd5");
                } else {
                    MzTech.sendMessage(sender, "\u00a7a\u4ee3\u7801\u6267\u884c\u5b8c\u6bd5\uff0c\u8fd4\u56de\u503c\uff1a " + r.toString());
                }
            }
            catch (Throwable e) {
                MzTech.sendThrowable(sender, e);
            }
            return true;
        }
        return false;
    }
}

