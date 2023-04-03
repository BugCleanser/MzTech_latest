/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.util.message;

import mz.tech.util.message.ShowOnMouse;

public class ShowTextOnMouse
extends ShowOnMouse {
    public String text;

    public ShowTextOnMouse(String text) {
        this.text = text;
    }

    @Override
    public String getAction() {
        return "show_text";
    }

    @Override
    public String getValue() {
        return this.text;
    }
}

