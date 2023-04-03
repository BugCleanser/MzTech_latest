/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 */
package mz.tech.mail;

import java.util.ArrayList;
import mz.tech.NBT;
import mz.tech.mail.Mail;
import mz.tech.util.message.ClickToSay;
import mz.tech.util.message.ClickToSetChat;
import mz.tech.util.message.Message;
import mz.tech.util.message.MessageComponent;
import mz.tech.util.message.ShowTextOnMouse;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class MailDraft
extends Mail {
    public MailDraft(NBT nbt) {
        super(nbt);
        if (this.msgs == null || this.msgs.size() == 0) {
            this.msgs = new ArrayList();
        }
        this.msgs.add(null);
        this.time = "";
    }

    public MailDraft(OfflinePlayer player) {
        if (this.msgs == null || this.msgs.size() == 0) {
            this.msgs = new ArrayList();
        }
        this.msgs.add(null);
        this.time = "";
        this.from = player;
    }

    @Override
    public void show(Player player) {
        super.show(player);
    }

    public boolean isEmpty() {
        if (this.title != null && this.title.length() > 0) {
            return false;
        }
        if (this.to != null) {
            return false;
        }
        if (this.msgs.size() > 1) {
            return false;
        }
        return this.attachments == null || this.attachments.isEmpty();
    }

    @Override
    public Message functionKey() {
        Message msg = new Message(new MessageComponent("\u2502 "));
        msg.add(new MessageComponent("\u00a7e\u00a7l[\u7f16\u8f91\u9644\u4ef6]").setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u7f16\u8f91\u9644\u4ef6")).setClickMsgEvent(new ClickToSay("/mztech mailbox edit attachments")));
        msg.add(new MessageComponent("  "));
        msg.add(new MessageComponent("\u00a74\u00a7l[\u5220\u9664\u8349\u7a3f]").setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u00a74\u5220\u9664\u8349\u7a3f")).setClickMsgEvent(new ClickToSay("/mztech mailbox delete draft")));
        msg.add(new MessageComponent("  "));
        msg.add(new MessageComponent("\u00a7b\u00a7l[\u53d1\u9001\u90ae\u4ef6]").setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u00a7b\u53d1\u9001\u90ae\u4ef6")).setClickMsgEvent(new ClickToSay("/mztech mailbox send")));
        return msg;
    }

    @Override
    public Message toLine() {
        return new Message((this.to == null ? new MessageComponent("\u70b9\u51fb\u8f93\u5165") : new MessageComponent(this.to.getName()).setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u590d\u5236\u5230\u804a\u5929\u680f")).setClickMsgEvent(new ClickToSetChat(this.to.getName()))).setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u7f16\u8f91\u6536\u4ef6\u4eba")).setClickMsgEvent(new ClickToSetChat("/mztech mailbox edit to " + (this.to == null ? "" : this.to.getName()))));
    }

    @Override
    public Message msgLine(String line, int num) {
        Message msg = new Message(new MessageComponent[0]);
        msg.add(new MessageComponent("\u00a7a\u00a7l[+] ").setShowOnMouse(new ShowTextOnMouse(line == null ? "\u00a7a\u6dfb\u52a0\u65b0\u884c" : "\u00a7a\u5728\u8be5\u884c\u4e4b\u524d\u63d2\u5165\u884c")).setClickMsgEvent(new ClickToSay("/mztech mailbox insert " + num)));
        if (line != null) {
            msg.add(new MessageComponent(line).setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u7f16\u8f91")).setClickMsgEvent(new ClickToSetChat("/mztech mailbox edit msg " + num + " " + line)));
            msg.add(new MessageComponent(" \u00a74\u00a7l[-]").setShowOnMouse(new ShowTextOnMouse("\u00a74\u5220\u9664\u8be5\u884c")).setClickMsgEvent(new ClickToSay("/mztech mailbox remove " + num)));
        }
        return msg;
    }

    @Override
    public Message titleLine() {
        return new Message(new MessageComponent(this.title == null ? "\u70b9\u51fb\u586b\u5199\u6807\u9898" : this.title).setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u7f16\u8f91\u6807\u9898")).setBold(true).setClickMsgEvent(new ClickToSetChat("/mztech mailbox edit title " + (this.title == null ? "" : this.title))).setBold(true));
    }

    public Mail send() {
        if (this.from.isOnline()) {
            this.from.getPlayer().closeInventory();
        }
        if (this.to == null) {
            throw new RuntimeException("\u8bf7\u586b\u5199\u6536\u4ef6\u4eba");
        }
        OfflinePlayer t = this.to;
        this.to = null;
        if (this.isEmpty()) {
            this.to = t;
            throw new RuntimeException("\u8bf7\u6dfb\u52a0\u6587\u5b57\u6216\u9644\u4ef6");
        }
        this.to = t;
        Mail rm = new Mail();
        rm.read = false;
        if (this.title == null) {
            this.title = "\u65e0\u6807\u9898";
        }
        rm.title = this.title;
        rm.from = this.from;
        rm.to = this.to;
        this.msgs.remove(this.msgs.size() - 1);
        rm.msgs = this.msgs;
        rm.attachments = this.attachments;
        if (rm.attachments != null) {
            rm.attachments.gotten = false;
        }
        rm.updateTime();
        return rm;
    }
}

