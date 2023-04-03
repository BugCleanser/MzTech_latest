/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.util.StringUtil
 */
package mz.tech.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mz.tech.MzTech;
import mz.tech.command.EnchantCommand;
import mz.tech.command.EnchantCommand2;
import mz.tech.command.GcCommand;
import mz.tech.command.GiveCommand;
import mz.tech.command.GuideCommand;
import mz.tech.command.InvseeCommand;
import mz.tech.command.IteminfoCommand;
import mz.tech.command.JavaCommand;
import mz.tech.command.JsCommand;
import mz.tech.command.KillCommand;
import mz.tech.command.KitCommand;
import mz.tech.command.LocnameCommand;
import mz.tech.command.LoreCommand;
import mz.tech.command.MachinesCommand;
import mz.tech.command.MailboxCommand;
import mz.tech.command.QsCommand;
import mz.tech.command.ReloadCommand;
import mz.tech.command.RenameCommand;
import mz.tech.command.SetsignCommand;
import mz.tech.command.SysmailboxCommand;
import mz.tech.command.TestCommand;
import mz.tech.command.UnbreakableCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public abstract class MzTechCommand {
    static Map<String, List<MzTechCommand>> commands = new LinkedHashMap<String, List<MzTechCommand>>();
    boolean onlyOp;

    static {
        commands.put("enchant", Lists.newArrayList((Object[])new MzTechCommand[]{new EnchantCommand(), new EnchantCommand2()}));
        commands.put("give", Lists.newArrayList((Object[])new MzTechCommand[]{new GiveCommand()}));
        commands.put("guide", Lists.newArrayList((Object[])new MzTechCommand[]{new GuideCommand()}));
        commands.put("iteminfo", Lists.newArrayList((Object[])new MzTechCommand[]{new IteminfoCommand()}));
        commands.put("locname", Lists.newArrayList((Object[])new MzTechCommand[]{new LocnameCommand()}));
        commands.put("machines", Lists.newArrayList((Object[])new MzTechCommand[]{new MachinesCommand()}));
        commands.put("reload", Lists.newArrayList((Object[])new MzTechCommand[]{new ReloadCommand()}));
        commands.put("rename", Lists.newArrayList((Object[])new MzTechCommand[]{new RenameCommand()}));
        commands.put("setsign", Lists.newArrayList((Object[])new MzTechCommand[]{new SetsignCommand()}));
        commands.put("qs", Lists.newArrayList((Object[])new MzTechCommand[]{new QsCommand()}));
        commands.put("unbreakable", Lists.newArrayList((Object[])new MzTechCommand[]{new UnbreakableCommand()}));
        commands.put("gc", Lists.newArrayList((Object[])new MzTechCommand[]{new GcCommand()}));
        commands.put("kill", Lists.newArrayList((Object[])new MzTechCommand[]{new KillCommand()}));
        commands.put("invsee", Lists.newArrayList((Object[])new MzTechCommand[]{new InvseeCommand()}));
        commands.put("lore", Lists.newArrayList((Object[])new MzTechCommand[]{new LoreCommand()}));
        commands.put("kit", Lists.newArrayList((Object[])new MzTechCommand[]{new KitCommand()}));
        commands.put("mailbox", Lists.newArrayList((Object[])new MzTechCommand[]{new MailboxCommand()}));
        commands.put("sysmailbox", Lists.newArrayList((Object[])new MzTechCommand[]{new SysmailboxCommand()}));
        commands.put("js", Lists.newArrayList((Object[])new MzTechCommand[]{new JsCommand()}));
        commands.put("java", Lists.newArrayList((Object[])new MzTechCommand[]{new JavaCommand()}));
        commands.put("test", Lists.newArrayList((Object[])new MzTechCommand[]{new TestCommand()}));
    }

    public static void cint() {
    }

    public void reg(String name) {
        if (!commands.containsKey(name)) {
            commands.put(name, new ArrayList());
        }
        commands.get(name).add(this);
    }

    public MzTechCommand(boolean onlyOp) {
        this.onlyOp = onlyOp;
    }

    public abstract String usage();

    public List<String> onTabComplite(CommandSender sender, String[] args) {
        return new ArrayList<String>();
    }

    public abstract boolean execute(CommandSender var1, String[] var2);

    public static List<MzTechCommand> matching(String name) {
        List[] rl = new List[]{new ArrayList()};
        commands.forEach((n, cs) -> {
            if (n.equalsIgnoreCase(name)) {
                listArray[0] = cs;
            }
        });
        return rl[0];
    }

    public static void sendUsagePrefix(CommandSender sender) {
        MzTech.sendMessage(sender, "\u00a76\u7528\u6cd5\uff1a");
    }

    public static boolean execute(CommandSender sender, Command command, String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("mztech")) {
            return false;
        }
        if (args.length == 0 || MzTechCommand.matching(args[0]) == null) {
            MzTechCommand.sendUsagePrefix(sender);
            commands.forEach((name, cs) -> sender.sendMessage("\u00a7a/" + alias + " \u00a7e" + name + " \u2026\u2026"));
        } else {
            boolean[] executed = new boolean[1];
            boolean[] noOp = new boolean[1];
            MzTechCommand.matching(args[0]).forEach(c -> {
                if (sender.isOp() || !c.onlyOp) {
                    if (c.execute(sender, Arrays.copyOfRange(args, 1, args.length))) {
                        blArray[0] = true;
                    }
                } else {
                    blArray2[0] = true;
                }
            });
            if (noOp[0]) {
                MzTechCommand.sendNoOp(sender);
            } else if (!executed[0]) {
                MzTechCommand.sendUsagePrefix(sender);
                MzTechCommand.matching(args[0]).forEach(c -> sender.sendMessage("\u00a7a/" + alias + " \u00a7e" + c.usage()));
            }
        }
        return true;
    }

    public static void sendNoOp(CommandSender sender) {
        MzTech.sendMessage(sender, "\u00a74\u4f60\u6ca1\u6709\u7ba1\u7406\u5458\u6743\u9650");
    }

    public static List<String> onTabCompleter(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> rl = new ArrayList<String>();
        if (args.length == 1) {
            commands.forEach((name, c) -> {
                if (StringUtil.startsWithIgnoreCase((String)name, (String)args[0])) {
                    rl.add((String)name);
                }
            });
        } else {
            MzTechCommand.matching(args[0]).forEach(c -> rl.addAll(c.onTabComplite(sender, Arrays.copyOfRange(args, 1, args.length))));
        }
        return rl;
    }
}

