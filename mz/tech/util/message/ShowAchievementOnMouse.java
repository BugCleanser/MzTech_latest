/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.util.message;

import mz.tech.util.message.ShowOnMouse;

public class ShowAchievementOnMouse
extends ShowOnMouse {
    String achievement;

    public ShowAchievementOnMouse(String achievement) {
        this.achievement = achievement;
    }

    @Override
    public String getAction() {
        return "show_achievement";
    }

    @Override
    public String getValue() {
        return "stat." + this.achievement;
    }
}

