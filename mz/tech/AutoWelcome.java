/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.plugin.Plugin
 */
package mz.tech;

import java.util.List;
import mz.tech.MzTech;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class AutoWelcome
implements Listener {
    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        if (((Boolean)MzTech.instance.getConfig().get("autoWelcome")).booleanValue() && !event.getPlayer().hasPlayedBefore()) {
            Bukkit.getOnlinePlayers().forEach(anotherPlayer -> {
                if (anotherPlayer != event.getPlayer()) {
                    TaskUtil.runTaskLater((Plugin)MzTech.instance, MzTech.rand.nextInt(190) + 10, () -> anotherPlayer.chat((String)MzTech.randValue((List)MzTech.instance.getConfig().get("welcome"))));
                }
            });
        }
    }
}

