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

import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RenameCommand
extends MzTechCommand {
    public RenameCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "rename <\u65b0\u540d\u79f0>";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            ItemStack is = ((Player)sender).getItemInHand();
            if (is == null || is.getType() == Material.AIR) return false;
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(MzTech.MergeStrings(0, 1, args)[0].replace('&', '\u00a7'));
            is.setItemMeta(im);
            return true;
        } else {
            MzTech.sendMessage(sender, "\u00a74\u6e38\u620f\u4e2d\u7684\u73a9\u5bb6\u624d\u80fd\u4f7f\u7528\u8be5\u547d\u4ee4");
        }
        return true;
    }
}

