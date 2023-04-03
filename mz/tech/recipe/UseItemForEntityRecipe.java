/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.collect.Lists
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package mz.tech.recipe;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import java.util.List;
import mz.tech.item.SpawnEgg;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import mz.tech.util.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UseItemForEntityRecipe
extends MzTechRecipe
implements Listener {
    public ItemStack input;
    public boolean consume;
    public ItemStack entity;
    public ItemStack output;
    public static ItemStack machine = new ItemStack(Material.ARROW);

    static {
        ItemMeta im = machine.getItemMeta();
        im.setLocalizedName("\u00a7d\u5bf9\u5b9e\u4f53\u4f7f\u7528\u7269\u54c1\u83b7\u5f97");
        machine.setItemMeta(im);
    }

    public UseItemForEntityRecipe() {
    }

    public UseItemForEntityRecipe(ItemStack input, boolean consume, ItemStack entity, ItemStack output) {
        this.input = input;
        this.consume = consume;
        this.entity = entity;
        this.output = output;
    }

    @EventHandler
    void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!event.isCancelled()) {
            ItemStack hand = event.getPlayer().getItemInHand();
            MzTechRecipe.getRecipes().forEach(r -> {
                if (r instanceof UseItemForEntityRecipe && hand.getAmount() >= ((UseItemForEntityRecipe)r).input.getAmount() && hand.getType().equals((Object)((UseItemForEntityRecipe)r).input.getType()) && Objects.equal((Object)hand.getItemMeta().getLocalizedName(), (Object)((UseItemForEntityRecipe)r).input.getItemMeta().getLocalizedName()) && event.getRightClicked().getType().equals((Object)new SpawnEgg((ItemStack)((UseItemForEntityRecipe)r).entity).type)) {
                    if (((UseItemForEntityRecipe)r).consume && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                        hand.setAmount(hand.getAmount() - ((UseItemForEntityRecipe)r).input.getAmount());
                    }
                    new PlayerUtil(event.getPlayer()).give(((UseItemForEntityRecipe)r).output);
                }
            });
        }
    }

    @Override
    public List<RawItem> getRaws() {
        return Lists.newArrayList((Object[])new RawItem[]{new RawItem(this.entity), new RawItem(this.input)});
    }

    @Override
    public List<ItemStack> getResults() {
        return Lists.newArrayList((Object[])new ItemStack[]{this.output});
    }

    @Override
    public List<ItemStack> getMachines() {
        return Lists.newArrayList((Object[])new ItemStack[]{machine});
    }
}

