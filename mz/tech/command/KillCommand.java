/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventException
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.plugin.EventExecutor
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.RegisteredListener
 */
package mz.tech.command;

import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

public class KillCommand
extends MzTechCommand {
    public KillCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "kill <\u73a9\u5bb6> <\u6b7b\u4ea1\u6d88\u606f>";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player;
        if (args.length >= 2 && (player = Bukkit.getPlayer((String)args[0])) != null) {
            KillCommand.kill(player, MzTech.MergeStrings(1, 2, args)[1].replace("&", "\u00a7"));
            return true;
        }
        return false;
    }

    public static void kill(Player player, final String msg) {
        RegisteredListener listener = new RegisteredListener(new Listener(){}, new EventExecutor(){

            public void execute(Listener arg0, Event arg1) throws EventException {
                PlayerDeathEvent event = (PlayerDeathEvent)arg1;
                event.setDeathMessage(msg);
            }
        }, EventPriority.LOWEST, (Plugin)MzTech.instance, false);
        PlayerDeathEvent.getHandlerList().register(listener);
        player.setHealth(0.0);
        PlayerDeathEvent.getHandlerList().unregister(listener);
    }
}

