/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item;

import java.util.Random;
import mz.tech.item.MzTechItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public abstract class Tool
extends MzTechItem {
    public Tool(ItemStack itemStack) {
        super(itemStack);
    }

    public boolean damage() {
        if (this.getType().getMaxDurability() > 0) {
            if (!this.getItemMeta().isUnbreakable()) {
                Random random = new Random();
                if (random.nextDouble() <= 1.0 / (double)(this.getItemMeta().getEnchantLevel(Enchantment.DURABILITY) + 1)) {
                    this.setDurability((short)(this.getDurability() + 1));
                }
            }
            if (this.getDurability() > this.getType().getMaxDurability()) {
                this.setAmount(this.getAmount() - 1);
                return true;
            }
            return false;
        }
        return false;
    }
}

