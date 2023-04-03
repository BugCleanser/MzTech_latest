/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package mz.tech.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import mz.tech.util.EnchantUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class EnchantCommand
extends MzTechCommand {
    public EnchantCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "enchant <\u9644\u9b54>";
    }

    @Override
    public List<String> onTabComplite(CommandSender sender, String[] args) {
        ArrayList<String> ret = new ArrayList<String>();
        switch (args.length) {
            case 1: {
                Lists.newArrayList((Object[])Enchantment.values()).forEach(s -> {
                    if (s.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                        ret.add(s.getName().toLowerCase());
                    }
                });
            }
        }
        return ret;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Enchantment enchant = Enchantment.getByName(args[0].toUpperCase());
            if (enchant == null) return false;
            EnchantUtil.setEnchant(((Player)sender).getItemInHand(), enchant, 1);
            return true;
        } else {
            sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a74\u6e38\u620f\u4e2d\u7684\u73a9\u5bb6\u624d\u80fd\u4f7f\u7528\u8be5\u547d\u4ee4");
        }
        return true;
    }
}

