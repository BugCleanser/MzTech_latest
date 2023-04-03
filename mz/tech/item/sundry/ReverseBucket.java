/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.NamespacedKey
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.player.PlayerBucketFillEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.ShapedRecipe
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.RegisteredListener
 */
package mz.tech.item.sundry;

import java.util.List;
import mz.tech.MzTech;
import mz.tech.category.MzTechCategory;
import mz.tech.category.SundryCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.WorkBenchShapedRecipe;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

public class ReverseBucket
extends MzTechItem {
    static {
        new WorkBenchShapedRecipe(new ShapedRecipe(new NamespacedKey((Plugin)MzTech.instance, "reverse_bucket"), (ItemStack)new ReverseBucket()).shape(new String[]{" b ", "b b", "   "}).setIngredient('b', Material.IRON_INGOT)).reg("\u5012\u7740\u7684\u6876");
        new ReverseBucket().getCategory().addItem(new ReverseBucket());
    }

    public ReverseBucket() {
        super(new ItemStackBuilder(Material.BUCKET).setLocName("\u00a7f\u5012\u7740\u7684\u6876").build());
    }

    @Override
    public String getTypeName() {
        return "\u5012\u7740\u7684\u6876";
    }

    @Override
    public MzTechCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        super.onRightClickEntity(player, hand, entity);
        return false;
    }

    @Override
    public boolean onFill(PlayerBucketFillEvent event) {
        BlockPlaceEvent place = new BlockPlaceEvent(event.getPlayer().getLocation().getBlock(), event.getPlayer().getLocation().getBlock().getState(), event.getPlayer().getLocation().getBlock(), new ItemStack(Material.AIR), event.getPlayer(), true, EquipmentSlot.HAND);
        if (place.getBlock().getType() != Material.AIR) {
            return false;
        }
        List<RegisteredListener> ts = PlayerUtil.ignoreAntiCheat(BlockPlaceEvent.getHandlerList());
        try {
            Bukkit.getPluginManager().callEvent((Event)place);
        }
        finally {
            PlayerUtil.restoreAntiCheat(BlockPlaceEvent.getHandlerList(), ts);
        }
        if (place.isCancelled() || !place.canBuild()) {
            return false;
        }
        event.getPlayer().getLocation().getBlock().setType(event.getBlockClicked().getRelative(event.getBlockFace()).getType());
        event.getPlayer().updateInventory();
        return false;
    }
}

