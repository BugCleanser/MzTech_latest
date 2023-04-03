/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.event.MachineItemAutoMoveEvent;
import mz.tech.event.MoveItemEvent;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.PlayerUtil;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public interface GuiMachine {
    default public String getTitle() {
        return ((MzTechMachine)((Object)this)).getType();
    }

    public int getSize();

    public void initInventory(Inventory var1);

    default public boolean onRightClick(PlayerInteractEvent event) {
        if (new PlayerUtil(event.getPlayer()).canOpen()) {
            Gui g = new Gui(this);
            g.inv = Bukkit.createInventory((InventoryHolder)g, (int)this.getSize(), (String)this.getTitle());
            this.initInventory(g.inv);
            event.getPlayer().openInventory(g.inv);
            this.getViewers().add(g.inv);
            return false;
        }
        return true;
    }

    public List<Integer> getLock();

    default public void lock(int slot) {
        this.getLock().add(slot);
    }

    default public void unlock(int slot) {
        this.getLock().remove((Object)slot);
    }

    default public boolean isLocked(int slot) {
        return this.getLock().contains(slot);
    }

    public Runnable onClick(Inventory var1, boolean var2, int var3);

    public static void closeAll() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p.getOpenInventory().getTopInventory().getHolder() instanceof Gui) {
                p.closeInventory();
            }
        });
    }

    public List<Inventory> getViewers();

    default public void remove() {
        Lists.newArrayList(this.getViewers()).forEach(v -> ((HumanEntity)v.getViewers().get(0)).closeInventory());
    }

    default public boolean onItemAutoMove(MachineItemAutoMoveEvent e) {
        return false;
    }

    default public void waitUnlock(Integer slot, Runnable task) {
        BukkitTask[] t;
        t = new BukkitTask[]{TaskUtil.runTaskTimer((Plugin)MzTech.instance, 0L, 1L, () -> {
            if (!((MzTechMachine)((Object)this)).isLogged()) {
                t[0].cancel();
                return;
            }
            if (slot == null && this.getLock().isEmpty() || slot != null && !this.isLocked(slot)) {
                t[0].cancel();
                task.run();
            }
        })};
        TaskUtil.run(t[0]);
    }

    public static class Gui
    implements InventoryHolder {
        public Inventory inv;
        public GuiMachine machine;
        public List<Runnable> tasks;
        public int taskSlot;

        static {
            Bukkit.getPluginManager().registerEvents(new Listener(){

                @EventHandler(priority=EventPriority.HIGHEST)
                public void onInventoryClose(InventoryCloseEvent event) {
                    if (event.getInventory().getHolder() instanceof Gui) {
                        Gui g = (Gui)event.getInventory().getHolder();
                        if (!g.tasks.isEmpty()) {
                            g.tasks.forEach(t -> t.run());
                            g.machine.unlock(g.taskSlot);
                        }
                        g.machine.getViewers().remove(g.inv);
                    }
                }

                @EventHandler(priority=EventPriority.HIGHEST)
                public void onMoveItem(MoveItemEvent event) {
                    if (event.isCancelled()) {
                        return;
                    }
                    if (event.getFrom().getInventory() != null && event.getFrom().getInventory().getHolder() instanceof Gui) {
                        Gui g = (Gui)event.getFrom().getInventory().getHolder();
                        if (g.machine.isLocked(event.getFrom().getSlot())) {
                            event.setCancelled(true);
                            return;
                        }
                        Runnable task = g.machine.onClick(g.inv, false, event.getFrom().getSlot());
                        if (task != null) {
                            g.tasks.add(task);
                            g.machine.lock(event.getFrom().getSlot());
                            g.taskSlot = event.getFrom().getSlot();
                            TaskUtil.runTask((Plugin)MzTech.instance, () -> {
                                task.run();
                                gui.tasks.remove(task);
                                gui.machine.unlock(event.getFrom().getSlot());
                            });
                        } else {
                            event.setCancelled(true);
                        }
                    } else if (event.getTo().getInventory() != null && event.getTo().getInventory().getHolder() instanceof Gui) {
                        Gui g = (Gui)event.getTo().getInventory().getHolder();
                        if (g.machine.isLocked(event.getTo().getSlot())) {
                            event.setCancelled(true);
                            return;
                        }
                        Runnable task = g.machine.onClick(g.inv, true, event.getTo().getSlot());
                        if (task != null) {
                            g.tasks.add(task);
                            g.machine.lock(event.getTo().getSlot());
                            g.taskSlot = event.getTo().getSlot();
                            TaskUtil.runTask((Plugin)MzTech.instance, () -> {
                                task.run();
                                gui.tasks.remove(task);
                                gui.machine.unlock(event.getTo().getSlot());
                            });
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            }, (Plugin)MzTech.instance);
        }

        public Inventory getInventory() {
            return this.inv;
        }

        public Gui(GuiMachine machine) {
            this.machine = machine;
            this.tasks = new ArrayList<Runnable>();
        }
    }
}

