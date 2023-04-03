/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.util.message;

import mz.tech.util.message.ClickMsgEvent;
import mz.tech.util.message.ShowOnMouse;

public class MessageComponent {
    private String text;
    private ShowOnMouse som;
    private ClickMsgEvent cme;
    private boolean bold = false;
    private boolean italic = false;
    private boolean underlined = false;
    private boolean strikethrough = false;
    private boolean obfuscated = false;

    public ShowOnMouse getShowOnMouse() {
        return this.som;
    }

    public MessageComponent setShowOnMouse(ShowOnMouse som) {
        this.som = som;
        return this;
    }

    public ClickMsgEvent getClickMsgEvent() {
        return this.cme;
    }

    public MessageComponent setClickMsgEvent(ClickMsgEvent cme) {
        this.cme = cme;
        return this;
    }

    public MessageComponent(String text) {
        this.text = text == null ? "" : text;
    }

    public boolean isBold() {
        return this.bold;
    }

    public MessageComponent setBold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public MessageComponent setItalic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public boolean isUnderlined() {
        return this.underlined;
    }

    public MessageComponent setUnderlined(boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    public boolean isStrikethrough() {
        return this.strikethrough;
    }

    public MessageComponent setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    public boolean isObfuscated() {
        return this.obfuscated;
    }

    public MessageComponent setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }

    public String toString() {
        return "{\"text\":\"" + this.text + "\",\"bold\":\"" + this.bold + "\",\"italic\":\"" + this.italic + "\",\"underlined\":\"" + this.underlined + "\",\"strikethrough\":\"" + this.strikethrough + "\",\"obfuscated\":\"" + this.obfuscated + "\"" + (this.som == null ? "" : ",\"hoverEvent\":" + this.som.toString()) + (this.cme == null ? "" : ",\"clickEvent\":" + this.cme.toString()) + "}";
    }
}

