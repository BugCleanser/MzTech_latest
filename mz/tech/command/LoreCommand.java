/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package mz.tech.command;

import java.util.ArrayList;
import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LoreCommand
extends MzTechCommand {
    public LoreCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "lore <\u884c\u65700\u5f00\u59cb> [\u6587\u5b57]";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            return false;
        }
        if (sender instanceof Player) {
            ItemStack is = ((Player)sender).getItemInHand();
            if (is == null || is.getType() == Material.AIR) return false;
            ItemMeta im = is.getItemMeta();
            ArrayList<String> lore = im.getLore();
            if (lore == null) {
                lore = new ArrayList<String>();
            }
            if (args.length > 1) {
                if (lore.size() > Integer.parseInt(args[0])) {
                    lore.set(Integer.parseInt(args[0]), MzTech.MergeStrings(1, 2, args)[1].replace('&', '\u00a7'));
                } else {
                    lore.add(MzTech.MergeStrings(1, 2, args)[1].replace('&', '\u00a7'));
                }
            } else {
                lore.remove(Integer.parseInt(args[0]));
            }
            im.setLore(lore);
            is.setItemMeta(im);
            return true;
        } else {
            sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a74\u6e38\u620f\u4e2d\u7684\u73a9\u5bb6\u624d\u80fd\u4f7f\u7528\u8be5\u547d\u4ee4");
        }
        return true;
    }
}

