/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.util.message;

import mz.tech.util.message.ClickMsgEvent;

public class ClickToSetChat
extends ClickMsgEvent {
    public String chat;

    public ClickToSetChat(String chat) {
        this.chat = chat;
    }

    @Override
    public String getAction() {
        return "suggest_command";
    }

    @Override
    public String getValue() {
        return this.chat;
    }
}

