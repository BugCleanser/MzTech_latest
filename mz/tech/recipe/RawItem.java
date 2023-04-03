/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.recipe;

import java.util.HashMap;
import mz.tech.item.MzTechItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RawItem
extends HashMap<ItemStack, ItemStack> {
    public RawItem(ItemStack ... raws) {
        ItemStack[] itemStackArray = raws;
        int n = raws.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack raw = itemStackArray[n2];
            this.put(raw, null);
            ++n2;
        }
    }

    public ItemStack get(ItemStack is) {
        return (ItemStack)super.get(is);
    }

    @Override
    public ItemStack put(ItemStack raw, ItemStack output) {
        return super.put(raw, output);
    }

    public RawItem add(Material raw, Material output) {
        return this.add(new ItemStack(raw), new ItemStack(output));
    }

    public RawItem add(ItemStack raw, ItemStack output) {
        this.put(raw, output);
        return this;
    }

    public ItemStack matching(ItemStack is) {
        ItemStack[] ri = new ItemStack[1];
        MzTechItem mzTechCopy = MzTechItem.asMzTechCopy(is);
        this.forEach((raw, out) -> {
            if (mzTechCopy != null && MzTechItem.asMzTechCopy(raw) != null && ((Object)((Object)mzTechCopy)).getClass() == ((Object)((Object)MzTechItem.asMzTechCopy(raw))).getClass() || (is == null || is.getType() == Material.AIR ? raw == null || raw.getType() == Material.AIR : raw.isSimilar(is) && raw.getAmount() <= is.getAmount())) {
                itemStackArray[0] = raw;
            }
        });
        return ri[0];
    }

    public RawItem setCount(int num) {
        this.forEach((i, o) -> {
            i.setAmount(num);
            if (o != null) {
                o.setAmount(num);
            }
        });
        return this;
    }
}

