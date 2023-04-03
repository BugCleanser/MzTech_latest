/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 */
package mz.tech.mail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import mz.tech.NBT;
import mz.tech.mail.Attachments;
import mz.tech.util.message.ClickToSay;
import mz.tech.util.message.ClickToSetChat;
import mz.tech.util.message.Message;
import mz.tech.util.message.MessageComponent;
import mz.tech.util.message.ShowTextOnMouse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Mail {
    public boolean read;
    public UUID id;
    public String title;
    public OfflinePlayer from;
    public OfflinePlayer to;
    public String time;
    public List<String> msgs;
    public Attachments attachments;

    public Mail() {
        this.read = false;
        this.updateTime();
        this.msgs = new ArrayList<String>();
        this.id = UUID.randomUUID();
    }

    public Mail(NBT nbt) {
        if (nbt.hasKey("read")) {
            this.read = nbt.getBoolean("read");
        }
        this.id = nbt.hasKey("id") ? nbt.getUUID("id") : UUID.randomUUID();
        if (nbt.hasKey("title")) {
            this.title = nbt.getString("title");
        }
        if (nbt.hasKey("from")) {
            this.from = Bukkit.getOfflinePlayer((String)nbt.getString("from"));
        }
        if (nbt.hasKey("to")) {
            this.to = Bukkit.getOfflinePlayer((String)nbt.getString("to"));
        }
        if (nbt.hasKey("time")) {
            this.time = nbt.getString("time");
        } else {
            this.updateTime();
        }
        if (nbt.hasKey("msgs")) {
            this.msgs = nbt.getStringList("msgs");
        }
        if (nbt.hasKey("attachments")) {
            this.attachments = new Attachments(nbt.getChild("attachments"));
        }
    }

    public NBT save() {
        return this.save(new NBT());
    }

    public NBT save(NBT nbt) {
        nbt.set("read", this.read);
        if (this.id != null) {
            nbt.set("id", this.id);
        }
        nbt.set("title", this.title);
        if (this.from != null) {
            nbt.set("from", this.from.getName());
        }
        if (this.to != null) {
            nbt.set("to", this.to.getName());
        }
        nbt.set("time", this.time);
        if (this.msgs != null) {
            nbt.set("msgs", this.msgs.toArray());
        }
        if (this.attachments != null && !this.attachments.isEmpty()) {
            nbt.set("attachments", this.attachments.save());
        }
        return nbt;
    }

    public void show(Player player) {
        if (this.attachments == null || this.attachments.isEmpty()) {
            this.read = true;
        }
        Message msg = new Message(new MessageComponent[0]);
        msg.add(new MessageComponent("\u250c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500"));
        msg.add(new MessageComponent("\n\u2502 "));
        msg.addAll(this.titleLine());
        msg.add(new MessageComponent("\n\u2502 \u00a77\u53d1\u4ef6\u4eba\uff1a "));
        if (this.from == null) {
            msg.add(new MessageComponent("\u4e0d\u7965"));
        } else {
            msg.add(new MessageComponent(this.from.getName()).setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u590d\u5236\u5230\u804a\u5929\u680f")).setClickMsgEvent(new ClickToSetChat(this.from.getName())));
        }
        if (this.time != null && this.time.length() > 0) {
            msg.add(new MessageComponent("\n\u2502 \u00a77\u53d1\u9001\u65f6\u95f4\uff1a " + this.time));
        }
        msg.add(new MessageComponent("\n\u2502 \u00a77\u6536\u4ef6\u4eba\uff1a "));
        msg.addAll(this.toLine());
        if (this.msgs != null && this.msgs.size() > 0) {
            msg.add(new MessageComponent("\n\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500"));
            int i = 0;
            while (i < this.msgs.size()) {
                msg.add(new MessageComponent("\n\u2502 "));
                msg.addAll(this.msgLine(this.msgs.get(i), i));
                ++i;
            }
        }
        msg.send(player);
        if (this.attachments != null && !this.attachments.isEmpty()) {
            player.sendMessage("\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500");
            this.attachments.show(player);
        }
        player.sendMessage("\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500");
        this.functionKey().send(player);
        player.sendMessage("\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500");
    }

    public Message titleLine() {
        return new Message(new MessageComponent(this.title).setBold(true));
    }

    public Message toLine() {
        return new Message(this.to == null ? new MessageComponent("\u4e0d\u7965") : new MessageComponent(this.to.getName()).setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u590d\u5236\u5230\u804a\u5929\u680f")).setClickMsgEvent(new ClickToSetChat(this.to.getName())));
    }

    public Message msgLine(String line, int num) {
        return new Message(new MessageComponent(line).setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u590d\u5236\u5230\u804a\u5929\u680f")).setClickMsgEvent(new ClickToSetChat(line)));
    }

    public Message functionKey() {
        Message msg = new Message(new MessageComponent("\u2502 "));
        if (this.attachments != null && !this.attachments.isEmpty()) {
            msg.add(new MessageComponent("\u00a7" + (this.attachments.gotten ? "7" : "e\u00a7l") + "[\u9886\u53d6\u9644\u4ef6]").setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u9886\u53d6\u9644\u4ef6")).setClickMsgEvent(new ClickToSay("/mztech mailbox get " + this.id.toString())));
            msg.add(new MessageComponent("  "));
        }
        msg.add(new MessageComponent("\u00a7e\u00a7l[\u5220\u9664\u90ae\u4ef6]").setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u00a74\u5220\u9664\u90ae\u4ef6")).setClickMsgEvent(new ClickToSay("/mztech mailbox delete " + this.id.toString())));
        return msg;
    }

    public void updateTime() {
        this.time = new SimpleDateFormat("yyyy\u5e74MM\u6708dd\u65e5  HH : mm").format(new Date());
    }

    public Attachments getAttachments() {
        if (this.attachments == null) {
            this.attachments = new Attachments();
        }
        return this.attachments;
    }
}

