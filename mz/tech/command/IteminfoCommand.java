/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package mz.tech.command;

import mz.tech.DropsName;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.command.MzTechCommand;
import mz.tech.item.baseMachine.ConversionTable;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IteminfoCommand
extends MzTechCommand {
    public IteminfoCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "iteminfo";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                if (((Player)sender).getItemInHand().getType() != Material.AIR) {
                    sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u7269\u54c1MID\uff1a " + ((Player)sender).getItemInHand().getType().name());
                    sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u7ffb\u8bd1\u524d\u540d\u79f0\uff1a " + DropsName.getItemUnlocalizedName(((Player)sender).getItemInHand()));
                    sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u6389\u843d\u7269\u540d\u79f0\uff1a " + DropsName.getDropName(((Player)sender).getItemInHand()).replace("\u00a7", "&"));
                    sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u7269\u54c1\u6570\u5b57ID\uff1a " + ((Player)sender).getItemInHand().getType().ordinal());
                    sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + (((Player)sender).getItemInHand().getType().getMaxDurability() > 0 ? "\u00a7a\u7269\u54c1\u635f\u574f\u5ea6\uff1a " : "\u00a7a\u7269\u54c1\u5b50ID\uff1a ") + ((Player)sender).getItemInHand().getDurability());
                    sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u539f\u7248\u7269\u54c1\uff1a " + (ConversionTable.isPureItem(((Player)sender).getItemInHand()) ? "\u662f" : "\u5426"));
                    sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u4ef7\u503c\uff1a " + ConversionTable.getItemValue(((Player)sender).getItemInHand()));
                    sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7aNBT\uff1a " + new NBT(((Player)sender).getItemInHand()).toString().replace("\u00a7", "&"));
                    sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a7aBukkit\u5bf9\u8c61\uff1a " + ((Player)sender).getItemInHand().toString().replace("\u00a7", "&"));
                } else {
                    sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a74\u4e3b\u624b\u4e2d\u6ca1\u6709\u7269\u54c1");
                }
            } else {
                sender.sendMessage(String.valueOf(MzTech.MzTechPrefix) + "\u00a74\u6e38\u620f\u4e2d\u7684\u73a9\u5bb6\u624d\u80fd\u4f7f\u7528\u8be5\u547d\u4ee4");
            }
            return true;
        }
        return false;
    }
}

