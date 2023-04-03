/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.HumanEntity
 */
package mz.tech.item;

import mz.tech.item.GuideView;
import org.bukkit.entity.HumanEntity;

abstract class Button {
    Button() {
    }

    public void set(GuideView view, int slot) {
        view.buttons.put(slot, this);
    }

    public void onClick(HumanEntity humanEntity) {
    }
}

