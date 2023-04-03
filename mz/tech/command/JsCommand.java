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
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;

public class JsCommand
extends MzTechCommand {
    public static Context context = Context.enter();

    static {
        context.setOptimizationLevel(9);
        context.setLanguageVersion(200);
    }

    public JsCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "js <js\u4ee3\u7801>";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            try {
                ImporterTopLevel scope = new ImporterTopLevel(context, false);
                String src = MzTech.MergeStrings(0, 1, args)[0];
                scope.put("me", (Scriptable)scope, (Object)new NativeJavaObject(scope, sender, sender.getClass()));
                scope.put("src", (Scriptable)scope, (Object)new NativeJavaObject(scope, src, String.class));
                scope.put("context", (Scriptable)scope, (Object)new NativeJavaObject(scope, context, context.getClass()));
                scope.put("scope", (Scriptable)scope, (Object)new NativeJavaObject(scope, scope, scope.getClass()));
                context.evaluateString(scope, "importPackage(java.lang);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.advancement);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.attribute);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.block);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.block.banner);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.block.structure);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.boss);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.command);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.command.defaults);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.configuration);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.configuration.file);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.configuration.serialization);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.conversations);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.enchantments);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.entity);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.entity.minecart);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.event);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.event.block);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.event.enchantment);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.event.entity);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.event.hanging);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.event.inventory);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.event.player);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.event.server);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.event.vehicle);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.event.weather);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.event.world);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.generator);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.help);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.inventory);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.inventory.meta);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.map);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.material);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.material.types);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.metadata);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.permissions);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.plugin);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.plugin.java);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.plugin.messaging);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.potion);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.projectiles);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.scheduler);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.scoreboard);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.util);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.util.io);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.util.noise);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(org.bukkit.util.permissions);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.category);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.command);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.enchant);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.event);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.item);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.luckyEffect);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.machine);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.machine.generator);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.mail);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.recipe);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.util);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.util.map);", "main.js", 1, null);
                context.evaluateString(scope, "importPackage(Packages.mz.tech.util.message);", "main.js", 1, null);
                Object r = context.evaluateString(scope, src, "main.js", 1, null);
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

