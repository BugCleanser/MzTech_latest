/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.machine;

import mz.tech.machine.MzTechMachine;

public interface Trigger {
    public boolean toggle();

    public static void toggleAll() {
        MzTechMachine.forEach(Trigger.class, toggleable -> toggleable.toggle());
    }
}

