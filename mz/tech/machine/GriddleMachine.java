/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.material.MaterialData
 */
package mz.tech.machine;

import mz.tech.MzTech;
import mz.tech.machine.StackedBlock;
import mz.tech.recipe.GriddleRecipe;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;

public class GriddleMachine
extends StackedBlock {
    @Override
    public boolean onMove(Location loc) {
        return true;
    }

    @Override
    public String getType() {
        return "\u7b5b\u5b50";
    }

    @Override
    public MaterialData[] getFakeBlocksType() {
        return new MaterialData[]{new ItemStackBuilder("SANDSTONE_SLAB", "STEP", 1, 1).build().getData()};
    }

    @Override
    public boolean onRightClick(PlayerInteractEvent event) {
        if (new PlayerUtil(event.getPlayer()).canOpen()) {
            if (event.getItem() != null && event.getItem().getType() != Material.AIR) {
                GriddleRecipe.forEach(GriddleRecipe.class, recipe -> {
                    if (new ItemStackBuilder(event.getItem()).contains(new ItemStackBuilder(recipe.raw).setCount(1).build()) && (event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getItem().getAmount() >= recipe.raw.getAmount())) {
                        event.setCancelled(true);
                        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                            new PlayerUtil(event.getPlayer()).setSlot(event.getHand(), new ItemStackBuilder(event.getItem()).setCount(event.getItem().getAmount() - recipe.raw.getAmount()));
                        }
                        this.getBlock().getWorld().playSound(this.getBlock().getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0f, 1.0f);
                        if (MzTech.rand.nextInt(100) < recipe.probabilitie) {
                            recipe.getResults().forEach(drop -> MzTech.dropItemStack(this.getBlock(), drop));
                        }
                    }
                });
            }
            return event.isCancelled();
        }
        return true;
    }
}

