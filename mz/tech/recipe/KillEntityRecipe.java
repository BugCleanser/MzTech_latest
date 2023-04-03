/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Villager
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.recipe;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mz.tech.MzTech;
import mz.tech.item.SpawnEgg;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class KillEntityRecipe
extends MzTechRecipe {
    ItemStack[] entitis;
    int[] probabilities;
    ItemStack result;
    public static ItemStack machine;

    static {
        try {
            machine = new ItemStack(Material.GOLD_SWORD);
        }
        catch (Throwable e) {
            machine = new ItemStack(Enum.valueOf(Material.class, "GOLDEN_SWORD"));
        }
        ItemMeta im = machine.getItemMeta();
        im.setLocalizedName("\u51fb\u6740\u751f\u7269\u6389\u843d");
        machine.setItemMeta(im);
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler
            public void onEntityDeath(EntityDeathEvent event) {
                if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
                    if (event.getEntity() instanceof Villager && damager.getClass().getSimpleName().contains("Zombie")) {
                        return;
                    }
                }
                Random rand = new Random();
                MzTechRecipe.getRecipes().forEach(k -> {
                    if (k instanceof KillEntityRecipe) {
                        int[] index = new int[1];
                        Lists.newArrayList((Object[])((KillEntityRecipe)k).entitis).forEach(i -> {
                            if (new SpawnEgg((ItemStack)((KillEntityRecipe)mzTechRecipe).entitis[nArray[0]]).type.equals((Object)event.getEntityType()) && rand.nextInt() % 100 < ((KillEntityRecipe)mzTechRecipe).probabilities[index[0]]) {
                                event.getDrops().add(new ItemStack(((KillEntityRecipe)mzTechRecipe).result));
                            }
                            nArray[0] = index[0] + 1;
                        });
                    }
                });
            }
        }, (Plugin)MzTech.instance);
    }

    public KillEntityRecipe(ItemStack result, ItemStack[] entitis, int[] probabilities) {
        this.result = result;
        this.entitis = entitis;
        this.probabilities = probabilities;
    }

    public KillEntityRecipe(ItemStack result, ItemStack entitis, int probability) {
        this.result = result;
        this.entitis = new ItemStack[]{entitis};
        this.probabilities = new int[]{probability};
    }

    @Override
    public List<RawItem> getRaws() {
        ArrayList<RawItem> rl = new ArrayList<RawItem>();
        Lists.newArrayList((Object[])this.entitis).forEach(e -> rl.add(new RawItem((ItemStack)e)));
        return rl;
    }

    @Override
    public List<ItemStack> getResults() {
        return Lists.newArrayList((Object[])new ItemStack[]{this.result});
    }

    @Override
    public List<ItemStack> getMachines() {
        return Lists.newArrayList((Object[])new ItemStack[]{machine});
    }
}

