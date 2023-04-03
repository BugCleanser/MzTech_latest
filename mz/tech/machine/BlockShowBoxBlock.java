/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.material.MaterialData
 */
package mz.tech.machine;

import mz.tech.machine.StackedBlock;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class BlockShowBoxBlock
extends StackedBlock {
    @Override
    public MaterialData[] getFakeBlocksType() {
        return new MaterialData[]{new MaterialData(Material.STONE)};
    }

    @Override
    public boolean onRightClick(PlayerInteractEvent event) {
        PlayerUtil player = new PlayerUtil(event.getPlayer());
        if (player.canOpen()) {
            ItemStack hand = player.getSlot(player.getUsedHand());
            if (!ItemStackBuilder.isEmpty(hand) && hand.getType().isBlock()) {
                super.getFakeBlocks().forEach(block -> block.remove());
                super.getFakeBlocks().clear();
                this.addFakeBlock(hand.getData());
            }
            return false;
        }
        return true;
    }

    @Override
    public String getType() {
        return "\u65b9\u5757\u5c55\u793a\u6846";
    }
}

