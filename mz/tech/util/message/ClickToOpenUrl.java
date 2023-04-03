/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.util.message;

import mz.tech.util.message.ClickMsgEvent;

public class ClickToOpenUrl
extends ClickMsgEvent {
    public String url;

    public ClickToOpenUrl(String url) {
        this.url = url;
    }

    @Override
    public String getAction() {
        return "open_url";
    }

    @Override
    public String getValue() {
        return this.url;
    }
}

