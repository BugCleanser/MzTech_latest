/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.player.PlayerInteractEvent
 */
package mz.tech.machine;

import mz.tech.machine.MzTechMachine;
import org.bukkit.event.player.PlayerInteractEvent;

public class BauxiteBlock
extends MzTechMachine {
    @Override
    public String getType() {
        return "\u94dd\u571f\u77ff";
    }

    @Override
    public boolean onRightClick(PlayerInteractEvent event) {
        return false;
    }
}

