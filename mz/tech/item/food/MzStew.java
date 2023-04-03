/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package mz.tech.item.food;

import mz.tech.MzTech;
import mz.tech.category.FoodCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MzStew
extends MzTechItem {
    static {
        RawItem rawFlower = new RawItem(new ItemStack(Material.YELLOW_FLOWER), new ItemStack(Material.RED_ROSE, 1, 0), new ItemStack(Material.RED_ROSE, 1, 1), new ItemStack(Material.RED_ROSE, 1, 2), new ItemStack(Material.RED_ROSE, 1, 3), new ItemStack(Material.RED_ROSE, 1, 4), new ItemStack(Material.RED_ROSE, 1, 5), new ItemStack(Material.RED_ROSE, 1, 6), new ItemStack(Material.RED_ROSE, 1, 7), new ItemStack(Material.RED_ROSE, 1, 8));
        new SmilingCraftingTableRecipe((raws, output) -> {
            if (raws.size() < 4) {
                return false;
            }
            if (raws.get(3) == null || !((ItemStack)raws.get(3)).isSimilar(new ItemStack(Material.MUSHROOM_SOUP))) {
                return false;
            }
            ItemStack flower = rawFlower.matching((ItemStack)raws.get(0));
            if (flower == null) {
                return false;
            }
            ((ItemStack)raws.get(0)).setAmount(((ItemStack)raws.get(0)).getAmount() - 1);
            ((ItemStack)raws.get(3)).setAmount(((ItemStack)raws.get(3)).getAmount() - 1);
            MzStew result = new MzStew();
            char[] lore = result.getLore(0).toCharArray();
            lore[lore.length - 1] = MzStew.getFlowerChar(flower);
            result.setLore(0, new String(lore));
            output.add(result);
            return true;
        }, (ItemStack)new MzStew(), rawFlower, null, null, Material.MUSHROOM_SOUP).reg("Mz\u7096\u83dc");
    }

    public MzStew() {
        super(new ItemStackBuilder(Material.MUSHROOM_SOUP).setLocName("\u00a7eMz\u7096\u83dc").setLoreList("\u00a77\u8ff7\u4e4b\u7096\u83dc\u00a7?").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "Mz\u7096\u83dc";
    }

    public static char getFlowerChar(ItemStack flower) {
        switch (flower.getType().name().toLowerCase()) {
            case "red_rose": {
                return ("" + flower.getDurability()).charAt(0);
            }
            case "yellow_flower": {
                return '9';
            }
        }
        return '?';
    }

    @Override
    public void onConsume(Player player) {
        PotionEffect effect = null;
        if (((String)this.getItemMeta().getLore().get(0)).length() > 7) {
            switch (((String)this.getItemMeta().getLore().get(0)).charAt(7)) {
                case '0': {
                    effect = new PotionEffect(PotionEffectType.NIGHT_VISION, 800, 0);
                    break;
                }
                case '1': 
                case '9': {
                    effect = new PotionEffect(PotionEffectType.SATURATION, 600, 0);
                    break;
                }
                case '2': {
                    effect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 0);
                    break;
                }
                case '3': {
                    effect = new PotionEffect(PotionEffectType.BLINDNESS, 1200, 0);
                    break;
                }
                case '4': 
                case '5': 
                case '6': 
                case '7': {
                    effect = new PotionEffect(PotionEffectType.WEAKNESS, 1400, 0);
                    break;
                }
                case '8': {
                    effect = new PotionEffect(PotionEffectType.REGENERATION, 1200, 0);
                }
            }
        }
        if (effect != null) {
            player.addPotionEffect(effect);
        } else {
            MzTech.sendMessage((CommandSender)player, "\u00a7e\u6ca1\u6709\u6548\u679c");
        }
    }

    @Override
    public String giveCommandArgs() {
        return "<\u6548\u679c\u5e8f\u53f7\uff080~8\uff09>";
    }

    @Override
    public boolean giveCommand(String[] args) {
        if (args.length == 1 && args[0].length() == 1) {
            char[] lore = this.getLore(0).toCharArray();
            lore[lore.length - 1] = args[0].charAt(0);
            this.setLore(0, new String(lore));
            return true;
        }
        return false;
    }
}

