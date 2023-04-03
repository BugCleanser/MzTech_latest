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
import mz.tech.command.MzTechCommand;
import mz.tech.util.EnchantUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class EnchantCommand2
extends MzTechCommand {
    public EnchantCommand2() {
        super(true);
    }

    @Override
    public String usage() {
        return "enchant <\u9644\u9b54> <\u7b49\u7ea7>";
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

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Enchantment enchant = Enchantment.getByName(args[0].toUpperCase());
            Integer level = null;
            try {
                level = Integer.valueOf(args[1]);
            }
            finally {
                if (enchant == null || level == null) {
                    return false;
                }
                EnchantUtil.setEnchant(((Player)sender).getItemInHand(), enchant, level);
            }
        }
        return true;
    }
}

