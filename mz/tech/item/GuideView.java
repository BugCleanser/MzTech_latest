/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.inventory.Inventory
 */
package mz.tech.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mz.tech.item.Button;
import mz.tech.item.CraftGuide;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

abstract class GuideView {
    private Inventory inv;
    public int page;
    public Map<Integer, Button> buttons = new HashMap<Integer, Button>();

    public abstract int getMaxPage();

    public void update(HumanEntity player) {
    }

    public Inventory getInventory() {
        return this.inv;
    }

    public GuideView(Inventory inv, int page) {
        this.inv = inv;
        this.page = page;
    }

    public void open(HumanEntity entity) {
        entity.openInventory(this.getInventory());
        this.update(entity);
        if (!CraftGuide.views.containsKey(entity)) {
            CraftGuide.views.put(entity, new ArrayList());
        }
        if (!CraftGuide.views.get(entity).contains(this)) {
            CraftGuide.views.get(entity).add(this);
        }
        try {
            entity.getWorld().playSound(entity.getEyeLocation(), Sound.BLOCK_NOTE_HAT, 1.0f, 1.0f);
        }
        catch (Throwable e) {
            entity.getWorld().playSound(entity.getEyeLocation(), Enum.valueOf(Sound.class, "BLOCK_NOTE_BLOCK_HAT"), 1.0f, 1.0f);
        }
    }

    public abstract GuideView anotherPage(int var1);
}

