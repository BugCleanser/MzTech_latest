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
import mz.tech.item.MzTechItem;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LocnameCommand
extends MzTechCommand {
    public LocnameCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "locname <\u65b0\u672a\u7ffb\u8bd1\u7684\u540d\u79f0>";
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
            MzTechItem item = MzTechItem.asMzTechCopy(is);
            if (item != null) {
                MzTech.sendMessage(sender, "\u00a7e\u79d1\u6280\u7269\u54c1" + item.getName() + "\u00a7e\u66f4\u6539\u4e86\u6807\u8bc6\uff0c\uff08\u53ef\u80fd\uff09\u53d8\u4e3a\u4e86\u539f\u7248\u7269\u54c1");
            }
            ItemMeta im = is.getItemMeta();
            if (args.length > 0) {
                im.setLocalizedName(MzTech.MergeStrings(0, 1, args)[0].replace('&', '\u00a7'));
            } else {
                im.setLocalizedName(null);
            }
            is.setItemMeta(im);
            return true;
        } else {
            sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a74\u6e38\u620f\u4e2d\u7684\u73a9\u5bb6\u624d\u80fd\u4f7f\u7528\u8be5\u547d\u4ee4");
        }
        return true;
    }
}

