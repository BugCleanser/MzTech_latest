/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.command;

import mz.tech.MzTech;
import mz.tech.category.ToolCategory;
import mz.tech.command.MzTechCommand;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UnbreakableCommand
extends MzTechCommand {
    static {
        new SmilingCraftingTableRecipe((raws, output) -> {
            if (raws.size() == 9) {
                int i = 0;
                while (i < 9) {
                    if (raws.get(i) == null) {
                        return false;
                    }
                    ++i;
                }
                if (((ItemStack)raws.get(4)).getAmount() == 1 && ((ItemStack)raws.get(4)).getType().getMaxDurability() > 0 && !((ItemStack)raws.get(4)).getItemMeta().isUnbreakable()) {
                    i = 0;
                    while (i < 4) {
                        if (!new ItemStackBuilder((ItemStack)raws.get(i)).contains(new ItemStack(Material.BEDROCK, 64))) {
                            return false;
                        }
                        ++i;
                    }
                    i = 5;
                    while (i < 9) {
                        if (!new ItemStackBuilder((ItemStack)raws.get(i)).contains(new ItemStack(Material.BEDROCK, 64))) {
                            return false;
                        }
                        ++i;
                    }
                    output.add(new ItemStackBuilder((ItemStack)raws.get(4)).setUnbreakable(true).build());
                    raws.clear();
                    return true;
                }
            }
            return false;
        }, UnbreakableCommand.getUnbreakableItemIcon(), new ItemStack(Material.BEDROCK, 64), 0, 0, 0, new ItemStackBuilder(Material.DIAMOND_PICKAXE).setLocName("\u666e\u901a\u7684\u88c5\u5907").build(), 0, 0, 0, 0).reg("\u65e0\u6cd5\u7834\u574f");
        ToolCategory.instance.addItem(UnbreakableCommand.getUnbreakableItemIcon());
    }

    public static ItemStack getUnbreakableItemIcon() {
        return new ItemStackBuilder(Material.DIAMOND_PICKAXE).setLocName("\u00a7b\u65e0\u6cd5\u7834\u574f\u7684\u88c5\u5907").setUnbreakable(true).build();
    }

    public UnbreakableCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "unbreakable";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                ItemStack item = ((Player)sender).getInventory().getItemInMainHand();
                if (item != null && item.getType() != Material.AIR) {
                    if (item.getItemMeta().isUnbreakable()) {
                        item = new ItemStackBuilder(item).setUnbreakable(false).build();
                        MzTech.sendMessage(sender, "\u00a7a\u6210\u529f\u53d6\u6d88\u8be5\u7269\u54c1\u7684\u65e0\u6cd5\u7834\u574f");
                    } else {
                        item = new ItemStackBuilder(item).setUnbreakable(true).build();
                        MzTech.sendMessage(sender, "\u00a7a\u6210\u529f\u8bbe\u7f6e\u8be5\u7269\u54c1\u4e3a\u65e0\u6cd5\u7834\u574f");
                    }
                    ((Player)sender).getInventory().setItemInMainHand(item);
                } else {
                    MzTech.sendMessage(sender, "\u00a74\u4e3b\u624b\u5fc5\u987b\u6301\u6709\u4e00\u4e2a\u7269\u54c1");
                }
            } else {
                MzTech.sendMessage(sender, "\u00a74\u53ea\u6709\u6e38\u620f\u4e2d\u7684\u73a9\u5bb6\u80fd\u4f7f\u7528\u8be5\u547d\u4ee4");
            }
            return true;
        }
        return false;
    }
}

