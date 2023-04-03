/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.item;

import mz.tech.item.Button;
import mz.tech.item.GuideView;
import mz.tech.util.ItemStackBuilder;

class GlassPane
extends Button {
    String colorName;
    short colorId;

    public GlassPane(String colorName, int colorId) {
        this.colorName = colorName;
        this.colorId = (short)colorId;
    }

    @Override
    public void set(GuideView view, int slot) {
        view.getInventory().setItem(slot, new ItemStackBuilder("STAINED_GLASS_PANE", this.colorId, String.valueOf(this.colorName.toUpperCase()) + "_STAINED_GLASS_PANE", 1).setLocName("\u00a7r").build());
        super.set(view, slot);
    }
}

