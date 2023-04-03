/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.material.MaterialData
 */
package mz.tech.machine;

import mz.tech.machine.AppleLeavesBlock;
import mz.tech.machine.Growable;
import mz.tech.machine.MzTechMachine;
import org.bukkit.material.MaterialData;

public class AppleSaplingBlock
extends Growable {
    @Override
    public String getType() {
        return "\u82f9\u679c\u6811\u82d7";
    }

    @Override
    public Class<? extends MzTechMachine> getBlockClass(MaterialData type) {
        switch (type.getItemType().toString()) {
            case "LEAVES": 
            case "OAK_LEAVES": 
            case "LEGACY_LEAVES": {
                return AppleLeavesBlock.class;
            }
        }
        return null;
    }
}

