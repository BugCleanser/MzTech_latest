/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.FurnaceRecipe
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.ShapedRecipe
 *  org.bukkit.inventory.ShapelessRecipe
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mz.tech.DropsName;
import mz.tech.MzTech;
import mz.tech.category.MzTechCategory;
import mz.tech.item.Button;
import mz.tech.item.GuideView;
import mz.tech.item.MainGuideView;
import mz.tech.item.MzTechItem;
import mz.tech.item.baseMachine.ConversionTable;
import mz.tech.recipe.MzTechFurnaceRecipe;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.WorkBenchShapedRecipe;
import mz.tech.recipe.WorkBenchShapelessRecipe;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.PlayerUtil;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public class CraftGuide
extends MzTechItem {
    static Map<HumanEntity, List<GuideView>> views = new HashMap<HumanEntity, List<GuideView>>();

    static {
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler(priority=EventPriority.LOWEST)
            public void onInventoryClick(InventoryClickEvent event) {
                if (views.containsKey(event.getWhoClicked()) && views.get(event.getWhoClicked()).get(views.get(event.getWhoClicked()).size() - 1).getInventory().getViewers().contains(event.getWhoClicked())) {
                    event.setCancelled(true);
                    if (event.getView().getTopInventory() == event.getClickedInventory()) {
                        List<GuideView> v = views.get(event.getWhoClicked());
                        Map<Integer, Button> buttons = v.get((int)(v.size() - 1)).buttons;
                        if (buttons.containsKey(event.getSlot())) {
                            buttons.get(event.getSlot()).onClick(event.getWhoClicked());
                        }
                    }
                }
            }

            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent event) {
                if (!event.getPlayer().hasPlayedBefore()) {
                    new PlayerUtil(event.getPlayer()).give(new CraftGuide());
                }
            }
        }, (Plugin)MzTech.instance);
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 10L, 10L, () -> views.forEach((p, l) -> ((GuideView)l.get(l.size() - 1)).update((HumanEntity)p)));
    }

    public static List<MzTechRecipe> getRecipesForItem(ItemStack is) {
        ArrayList<MzTechRecipe> rl = new ArrayList<MzTechRecipe>();
        if (is == null) {
            return rl;
        }
        MzTechRecipe.forEach((MzTechRecipe recipe) -> {
            if (recipe.getResults().stream().filter(i -> ItemStackBuilder.equals(i, is)).count() > 0L) {
                rl.add((MzTechRecipe)recipe);
            }
        });
        if (ConversionTable.isPureItem(is)) {
            Bukkit.getRecipesFor((ItemStack)is).forEach(recipe -> {
                if (!ConversionTable.isPureItem(recipe.getResult())) {
                    return;
                }
                if (recipe instanceof FurnaceRecipe) {
                    rl.add(new MzTechFurnaceRecipe((FurnaceRecipe)recipe).setName(DropsName.getShowName(is)));
                } else if (recipe instanceof ShapedRecipe) {
                    rl.add(new WorkBenchShapedRecipe((ShapedRecipe)recipe).setName(DropsName.getShowName(is)));
                } else if (recipe instanceof ShapelessRecipe) {
                    rl.add(new WorkBenchShapelessRecipe((ShapelessRecipe)recipe).setName(DropsName.getShowName(is)));
                }
            });
        }
        return rl;
    }

    public CraftGuide() {
        super(new ItemStackBuilder(Material.COMPASS).setLocName("\u00a7bMz\u79d1\u6280\u5408\u6210\u6307\u5357").build());
    }

    @Override
    public String getTypeName() {
        return "\u5408\u6210\u6307\u5357";
    }

    @Override
    public MzTechCategory getCategory() {
        return null;
    }

    public static void open(Player player) {
        new MainGuideView().open((HumanEntity)player);
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        CraftGuide.open(player);
        return false;
    }

    @Override
    public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        CraftGuide.open(player);
        return false;
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        TaskUtil.runTask((Plugin)MzTech.instance, () -> {
            if (player.getOpenInventory().getTopInventory().getType() == InventoryType.CRAFTING) {
                CraftGuide.open(player);
            }
        });
        return true;
    }

    @Override
    public void onDisable() {
        views.forEach((p, v) -> {
            if (v.size() > 0) {
                Lists.newArrayList((Iterable)((GuideView)v.get(v.size() - 1)).getInventory().getViewers()).forEach(viewer -> viewer.closeInventory());
            }
        });
    }
}

