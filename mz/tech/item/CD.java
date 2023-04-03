/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.Jukebox
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemFlag
 */
package mz.tech.item;

import mz.tech.MzTech;
import mz.tech.category.MzTechCategory;
import mz.tech.category.RecordCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;

public class CD
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new CD(), new Object[]{ItemStackBuilder.glassPane, 0, 0, 0, null, 0, 0, 0, 0}).reg("\u5149\u76d8");
    }

    public CD() {
        super(new ItemStackBuilder(ItemStackBuilder.record_strad).setLocName("\u5149\u76d8").setHideFlags(ItemFlag.HIDE_POTION_EFFECTS).build());
    }

    @Override
    public String getTypeName() {
        return "\u5149\u76d8";
    }

    @Override
    public MzTechCategory getCategory() {
        return RecordCategory.instance;
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        if (block.getType() == Material.JUKEBOX && !((Jukebox)block.getState()).isPlaying()) {
            MzTech.sendMessage((CommandSender)player, "\u00a7e\u8fd9\u662f\u4e00\u5f20\u7a7a\u7684\u5149\u76d8");
            return false;
        }
        return true;
    }
}

