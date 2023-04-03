/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Firework
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.sundry;

import java.util.List;
import java.util.function.BiPredicate;
import mz.tech.MzTech;
import mz.tech.category.SundryCategory;
import mz.tech.item.Consumeable;
import mz.tech.item.MzTechItem;
import mz.tech.item.sundry.BigBag;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PresentBox
extends MzTechItem
implements Consumeable {
    static {
        SmilingCraftingTableRecipe recipe = new SmilingCraftingTableRecipe(new PresentBox(), new Object[]{new ItemStackBuilder("ink_sack", 4, "blue_dye", 1), new ItemStackBuilder("firework", 0, "firework_rocket", 1).setPower(1).build(), 0, new ItemStackBuilder("ink_sack", 11, "yellow_dye", 1), new BigBag(), 3, 0, 3, 0});
        BiPredicate<List<ItemStack>, List<ItemStack>> lastFilter = recipe.filter;
        recipe.filter = (raws, output) -> {
            MzTechItem bag;
            if (raws.size() >= 4 && (bag = MzTechItem.asMzTechCopy((ItemStack)raws.get(4))) instanceof BigBag) {
                raws.set(4, new BigBag().setCount(bag.getAmount()));
                if (lastFilter.test((List<ItemStack>)raws, (List<ItemStack>)output)) {
                    output.set(0, new ItemStackBuilder((ItemStack)output.get(0)).setInfoToLore("\u00a70ID: ", ((BigBag)bag).getId()).build());
                    return true;
                }
            }
            return false;
        };
        recipe.reg("\u793c\u7269\u76d2");
    }

    public PresentBox() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(87867435371045L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU2MTJkYzdiODZkNzFhZmMxMTk3MzAxYzE1ZmQ5NzllOWYzOWU3YjFmNDFkOGYxZWJkZjgxMTU1NzZlMmUifX19").setLocName("\u00a7a\u793c\u7269\u76d2").setLoreList("\u00a74\u53f3\u952e \u00a77\u62c6\u5f00").build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u793c\u7269\u76d2";
    }

    @Override
    public Sound getSound() {
        try {
            return Sound.ENTITY_IRONGOLEM_ATTACK;
        }
        catch (Throwable e) {
            return Enum.valueOf(Sound.class, "ENTITY_IRON_GOLEM_ATTACK");
        }
    }

    @Override
    public boolean canConsume(Player player) {
        return true;
    }

    @Override
    public void onConsume(Player player) {
        super.onConsume(player);
        player.getWorld().spawn(player.getEyeLocation(), Firework.class);
        BigBag bag = new BigBag();
        bag.setId(this.infoFromLore("\u00a70ID: "));
        bag.getInventory().forEach((T is) -> {
            if (is != null && is.getType() != Material.AIR) {
                player.getWorld().dropItemNaturally(player.getEyeLocation(), is);
            }
        });
    }

    @Override
    public int getFoodLevel() {
        return 0;
    }

    @Override
    public boolean giveCommand(String[] args) {
        switch (args.length) {
            case 1: {
                this.setInfoToLore("\u00a70ID: ", args[0]);
                return true;
            }
        }
        return false;
    }

    @Override
    public String giveCommandArgs() {
        return "[\u7269\u54c1\u680fID]";
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        return Consumeable.super.onRightClickAir(player, hand);
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        return Consumeable.super.onRightClickBlock(player, hand, block);
    }

    @Override
    public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        return Consumeable.super.onRightClickEntity(player, hand, entity);
    }
}

