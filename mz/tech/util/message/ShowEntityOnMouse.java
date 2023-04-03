/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Damageable
 *  org.bukkit.entity.Entity
 */
package mz.tech.util.message;

import mz.tech.EntityStack;
import mz.tech.util.message.ShowOnMouse;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;

public class ShowEntityOnMouse
extends ShowOnMouse {
    String type;
    String name;
    String id;

    public ShowEntityOnMouse(Entity entity) {
        this.type = entity.getType().toString();
        this.name = entity instanceof Damageable ? EntityStack.getEntityName((Damageable)entity) : entity.getName();
        this.id = entity.getUniqueId().toString();
    }

    public ShowEntityOnMouse(String type, String name, String id) {
        this.type = type;
        this.name = name;
        this.id = id;
    }

    @Override
    public String getAction() {
        return "show_entity";
    }

    @Override
    public String getValue() {
        return "{type:\"" + this.type + "\",name:\"" + this.name + "\",id:\"" + this.id + "\"}";
    }
}

