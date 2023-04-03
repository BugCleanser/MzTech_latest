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
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.mail;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.mail.Mail;
import mz.tech.mail.MailDraft;
import mz.tech.mail.MailboxGuide;
import mz.tech.mail.SystemMailbox;
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
import org.bukkit.plugin.Plugin;

public class Mailbox {
    public static Map<OfflinePlayer, Mailbox> mailboxes;
    public MailDraft draft;
    public List<Mail> inbox;
    public static final File file;
    public static final File tempFile;

    static {
        if (MzTech.instance.isEnabled()) {
            Bukkit.getPluginManager().registerEvents(new Listener(){

                @EventHandler
                public void onPlayerJoin(PlayerJoinEvent event) {
                    TaskUtil.runTaskLater((Plugin)MzTech.instance, 100L, () -> {
                        int num;
                        if (event.getPlayer().isOnline() && (num = Mailbox.get((OfflinePlayer)event.getPlayer()).getUnreadMails().size()) > 0) {
                            new Message(new MessageComponent(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u60a8\u6709" + num + "\u5c01\u672a\u8bfb\u90ae\u4ef6  "), new MessageComponent("\u00a7e\u00a7l[\u70b9\u51fb\u67e5\u770b]").setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u6253\u5f00\u90ae\u7bb1\n\u4e5f\u53ef\u4ee5\u4f7f\u7528/MzTech mailbox")).setClickMsgEvent(new ClickToSay("/mztech mailbox"))).send(event.getPlayer());
                        }
                    });
                }
            }, (Plugin)MzTech.instance);
        }
        file = new File(MzTech.instance.getDataFolder(), "mailboxes.nbt");
        tempFile = new File(MzTech.instance.getDataFolder(), "mailboxes.temp");
    }

    public Mailbox(OfflinePlayer player) {
        this.draft = new MailDraft(player);
        this.inbox = new ArrayList<Mail>();
    }

    public Mailbox(OfflinePlayer player, NBT nbt) {
        this.draft = nbt.hasKey("draft") ? new MailDraft(nbt.getChild("draft")) : new MailDraft(player);
        this.inbox = nbt.hasKey("inbox") ? Lists.newArrayList(nbt.getChildList("inbox").stream().map(Mail::new).iterator()) : new ArrayList<Mail>();
    }

    public NBT save(NBT nbt) {
        if (!this.draft.isEmpty()) {
            nbt.set("draft", this.draft.save());
        }
        if (this.inbox != null && this.inbox.size() > 0) {
            nbt.set("inbox", Lists.newArrayList(this.inbox.stream().map(Mail::save).iterator()).toArray());
        }
        return nbt;
    }

    public NBT save() {
        return this.save(new NBT());
    }

    public static NBT saveAll(NBT nbt) {
        mailboxes.forEach((p, m) -> {
            if (!m.isEmpty()) {
                nbt.set(p.getName(), m.save());
            }
        });
        nbt.set(SystemMailbox.instance.getPlayer().getName(), SystemMailbox.instance.save());
        return nbt;
    }

    public boolean isEmpty() {
        return !(this.draft != null && !this.draft.isEmpty() || this.inbox != null && this.inbox.size() != 0);
    }

    public static void saveAll() {
        MzTech.instance.getLogger().info("\u6b63\u5728\u4fdd\u5b58\u73a9\u5bb6\u6536\u4ef6\u7bb1");
        try {
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }
            Throwable throwable = null;
            Object var1_3 = null;
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(tempFile));){
                Mailbox.saveAll(new NBT()).save(dos);
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            if (file.exists()) {
                file.delete();
            }
            tempFile.renameTo(file);
            MzTech.instance.getLogger().info("\u6210\u529f\u4fdd\u5b58\u73a9\u5bb6\u6536\u4ef6\u7bb1");
        }
        catch (Throwable e) {
            MzTech.instance.getLogger().warning("\u65e0\u6cd5\u4fdd\u5b58\u73a9\u5bb6\u90ae\u7bb1\uff08mztech\\mailboxes.nbt\uff09");
            e.printStackTrace();
        }
    }

    public static void loadAll(NBT nbt) {
        mailboxes = new HashMap<OfflinePlayer, Mailbox>();
        nbt.getKeyList().forEach(key -> {
            if (key.equals(SystemMailbox.instance.getPlayer().getName())) {
                SystemMailbox.instance = new SystemMailbox(nbt.getChild(SystemMailbox.instance.getPlayer().getName()));
            }
            mailboxes.put(Bukkit.getOfflinePlayer((String)key), new Mailbox(Bukkit.getOfflinePlayer((String)key), nbt.getChild((String)key)));
        });
    }

    public static void loadAll() {
        MzTech.instance.getLogger().info("\u6b63\u5728\u52a0\u8f7d\u73a9\u5bb6\u6536\u4ef6\u7bb1");
        if (file.exists()) {
            try {
                Throwable throwable = null;
                Object var1_3 = null;
                try (DataInputStream dis = new DataInputStream(new FileInputStream(file));){
                    Mailbox.loadAll(new NBT(dis));
                    MzTech.instance.getLogger().info("\u6210\u529f\u52a0\u8f7d\u73a9\u5bb6\u6536\u4ef6\u7bb1");
                }
                catch (Throwable throwable2) {
                    if (throwable == null) {
                        throwable = throwable2;
                    } else if (throwable != throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
            }
            catch (Throwable e) {
                MzTech.instance.getLogger().warning("\u65e0\u6cd5\u8bfb\u53d6\u73a9\u5bb6\u6536\u4ef6\u7bb1\uff08mztech\\mailboxes.nbt\uff09");
                mailboxes = new HashMap<OfflinePlayer, Mailbox>();
                e.printStackTrace();
            }
        } else {
            mailboxes = new HashMap<OfflinePlayer, Mailbox>();
        }
    }

    public List<Mail> getUnreadMails() {
        return Lists.newArrayList(this.inbox.stream().filter(m -> !m.read).iterator());
    }

    public List<Mail> getReadMails() {
        return Lists.newArrayList(this.inbox.stream().filter(m -> m.read).iterator());
    }

    public static Mailbox get(OfflinePlayer p) {
        if (p.isOnline() && SystemMailbox.instance.manager == p.getPlayer()) {
            return SystemMailbox.instance;
        }
        return Mailbox.getRaw(p);
    }

    public static Mailbox getRaw(OfflinePlayer p) {
        if (!mailboxes.containsKey(p)) {
            mailboxes.put(p, new Mailbox(p));
        }
        return mailboxes.get(p);
    }

    public void show(Player player) {
        new MailboxGuide(this).show(player);
    }

    public void delete(Mail mail) {
        if (mail == null || !this.inbox.contains(mail)) {
            throw new RuntimeException("\u90ae\u4ef6\u4e0d\u5b58\u5728");
        }
        if (mail.attachments != null && !mail.attachments.isEmpty() && !mail.attachments.gotten) {
            throw new RuntimeException("\u8bf7\u5148\u9886\u53d6\u9644\u4ef6");
        }
        this.inbox.remove(mail);
    }

    public Mail getMail(UUID id) {
        for (Mail m : this.inbox) {
            if (!m.id.equals(id)) continue;
            return m;
        }
        return null;
    }

    public Mail send() {
        Mail rm = this.draft.send();
        this.draft = new MailDraft(this.draft.from);
        Mailbox.getRaw((OfflinePlayer)rm.to).inbox.add(0, rm);
        if (rm.to.isOnline()) {
            new Message(new MessageComponent(String.valueOf(MzTech.MzTechPrefix) + "\u00a7a\u60a8\u6536\u5230\u4e00\u5c01\u6765\u81ea" + rm.from.getName() + "\u7684\u90ae\u4ef6\u201c\u00a7e" + rm.title + "\u00a7a\u201d  "), new MessageComponent("\u00a7e\u00a7l[\u70b9\u51fb\u67e5\u770b]").setShowOnMouse(new ShowTextOnMouse("\u70b9\u51fb\u67e5\u770b\u90ae\u7bb1\n\u4e5f\u53ef\u4ee5\u8f93\u5165/MzTech mailbox")).setClickMsgEvent(new ClickToSay("/mztech mailbox"))).send(rm.to.getPlayer());
        }
        return rm;
    }

    public OfflinePlayer getPlayer() {
        return this.draft.from;
    }

    public void deleteDraft() {
        if (this.draft.attachments != null) {
            this.draft.attachments.give(this.getPlayer().getPlayer());
        }
        this.draft = new MailDraft(this.getPlayer());
    }
}

