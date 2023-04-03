/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.util.message;

import mz.tech.util.message.ClickMsgEvent;

public class ClickToSay
extends ClickMsgEvent {
    public String text;

    public ClickToSay(String text) {
        this.text = text;
    }

    @Override
    public String getAction() {
        return "run_command";
    }

    @Override
    public String getValue() {
        return this.text;
    }
}

