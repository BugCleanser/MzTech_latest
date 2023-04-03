/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemFlag
 */
package mz.tech.item.sundry;

import mz.tech.category.SundryCategory;
import mz.tech.event.LingeringPotionBreakEvent;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class FrangibleGrenade
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new FrangibleGrenade(), new Object[]{null, ItemStackBuilder.gunpowder, 0, Material.BLAZE_POWDER, 3, 3, 0, Material.GLASS_BOTTLE, 0}).reg("\u71c3\u70e7\u74f6");
    }

    public FrangibleGrenade() {
        super(new ItemStackBuilder(Material.LINGERING_POTION).setPotionColor(Color.fromRGB((int)255, (int)47, (int)0)).setHideFlags(ItemFlag.HIDE_POTION_EFFECTS).setLocName("\u00a7c\u71c3\u70e7\u74f6").build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u71c3\u70e7\u74f6";
    }

    @Override
    public boolean onLingeringPotionBreak(LingeringPotionBreakEvent e) {
        if (e.entity.getShooter() instanceof Player) {
            Location loc = e.entity.getLocation();
            int i = -3;
            while (i < 4) {
                int j = -3;
                while (j < 4) {
                    int k = -3;
                    while (k < 4) {
                        Block b = new Location(loc.getWorld(), loc.getX() + (double)i, loc.getY() + (double)j, loc.getZ() + (double)k).getBlock();
                        if (b.getType() == Material.AIR) {
                            new ItemStackBuilder(Material.FIRE).toBlock((Player)e.entity.getShooter(), b);
                        }
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }
        return false;
    }
}

