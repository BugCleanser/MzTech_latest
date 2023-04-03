/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.mail;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.mail.Mail;
import mz.tech.mail.MailDraft;
import mz.tech.mail.Mailbox;
import mz.tech.util.TaskUtil;
import mz.tech.util.message.ClickToSay;
import mz.tech.util.message.Message;
import mz.tech.util.message.MessageComponent;
import mz.tech.util.message.ShowTextOnMouse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class SystemMailbox
extends Mailbox
implements Listener {
    public static SystemMailbox instance = new SystemMailbox();
    public static OfflinePlayer allplayers;
    public static OfflinePlayer oldplayers;
    public static OfflinePlayer newplayers;
    public Player manager;

    static {
        if (MzTech.instance.isEnabled()) {
            Bukkit.getPluginManager().registerEvents((Listener)instance, (Plugin)MzTech.instance);
        }
        allplayers = Bukkit.getOfflinePlayer((String)"<allplayers>");
        oldplayers = Bukkit.getOfflinePlayer((String)"<oldplayers>");
        newplayers = Bukkit.getOfflinePlayer((String)"<newplayers>");
    }

    @EventHandler
    static void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer() == SystemMailbox.instance.manager) {
            SystemMailbox.instance.manager = null;
        }
    }

    public SystemMailbox() {
        super(Bukkit.getOfflinePlayer((String)"<\u7cfb\u7edf>"));
    }

    public SystemMailbox(NBT nbt) {
        this();
        this.draft = nbt.hasKey("draft") ? new MailDraft(nbt.getChild("draft")) : new MailDraft(instance.getPlayer());
        this.inbox = nbt.hasKey("inbox") ? Lists.newArrayList(nbt.getChildList("inbox").stream().map(MailDraft::new).iterator()) : new ArrayList();
    }

    @Override
    public void deleteDraft() {
        this.draft = new MailDraft(this.getPlayer());
    }

    @Override
    public Mail send() {
        if (this.draft.to.getName().equals(oldplayers.getName())) {
            OfflinePlayer[] offlinePlayerArray = Bukkit.getOfflinePlayers();
            int n = offlinePlayerArray.length;
            int n2 = 0;
            while (n2 < n) {
                OfflinePlayer p = offlinePlayerArray[n2];
                MailDraft copy = new MailDraft(this.draft.save());
                copy.to = p;
                Mailbox.getRaw((OfflinePlayer)Bukkit.getOfflinePlayer((String)p.getName())).inbox.add(0, copy.send());
                if (p.isOnline()) {
                    new Message(new MessageComponent(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u60a8\u6536\u5230\u4e00\u5c01\u6765\u81ea" + this.draft.from.getName() + "\u7684\u90ae\u4ef6\u201c\u00a7e" + this.draft.title + "\u00a7a\u201d  "), new MessageComponent("\u00a7e\u00a7l[\u70b9\u51fb\u67e5\u770b]").setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u67e5\u770b\u90ae\u7bb1\n\u4e5f\u53ef\u4ee5\u8f93\u5165/MzTech mailbox")).setClickMsgEvent(new ClickToSay("/mztech mailbox"))).send(p.getPlayer());
                }
                ++n2;
            }
            this.draft.to = oldplayers;
            return this.draft;
        }
        if (this.draft.to.getName().equals(newplayers.getName())) {
            this.inbox.add(0, new MailDraft(this.draft.save()));
            return this.draft;
        }
        if (this.draft.to.getName().equals(allplayers.getName())) {
            this.draft.to = oldplayers;
            this.send();
            this.draft.to = newplayers;
            this.send();
            this.draft.to = allplayers;
            return this.draft;
        }
        return super.send();
    }

    @EventHandler
    static void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            TaskUtil.runTaskLater((Plugin)MzTech.instance, 200L, () -> SystemMailbox.instance.inbox.forEach(m -> {
                MailDraft copy = new MailDraft(m.save());
                copy.to = event.getPlayer();
                Mailbox.getRaw((OfflinePlayer)Bukkit.getOfflinePlayer((String)playerJoinEvent.getPlayer().getName())).inbox.add(0, copy.send());
                new Message(new MessageComponent(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u60a8\u6536\u5230\u4e00\u5c01\u6765\u81ea" + copy.from.getName() + "\u7684\u90ae\u4ef6\u201c\u00a7e" + copy.title + "\u00a7a\u201d  "), new MessageComponent("\u00a7e\u00a7l[\u70b9\u51fb\u67e5\u770b]").setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u67e5\u770b\u90ae\u7bb1\n\u4e5f\u53ef\u4ee5\u8f93\u5165/MzTech mailbox")).setClickMsgEvent(new ClickToSay("/mztech mailbox")), new MessageComponent("\u00a7e\u00a7l\u2190\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u65b0\u4eba\u770b\u8fd9\u91cc\uff01")).send(event.getPlayer());
            }));
        }
    }

    @Override
    public void delete(Mail mail) {
        if (mail == null || !this.inbox.contains(mail)) {
            throw new RuntimeException("\u90ae\u4ef6\u4e0d\u5b58\u5728");
        }
        this.inbox.remove(mail);
    }
}

