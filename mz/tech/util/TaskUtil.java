/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package mz.tech.util;

import com.google.common.collect.Lists;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.ReflectionWrapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public final class TaskUtil {
    public static Class<?> craftTask = ReflectionWrapper.getCraftBukkitClass("scheduler.CraftTask");
    public static Method craftTaskRun = ReflectionWrapper.getMethod(craftTask, "run", new Class[0]);
    public static List<BukkitTask> necessaryTasks = new ArrayList<BukkitTask>();

    private TaskUtil() {
    }

    public static BukkitTask runTask(Plugin plugin, Runnable function) {
        return Bukkit.getScheduler().runTask(plugin, function);
    }

    public static BukkitTask runTaskLater(Plugin plugin, long delay, Runnable function) {
        return Bukkit.getScheduler().runTaskLater(plugin, function, delay);
    }

    public static BukkitTask runTaskTimer(Plugin plugin, long delay, long period, Runnable function) {
        return Bukkit.getScheduler().runTaskTimer(plugin, function, delay, period);
    }

    public static BukkitTask runAsyncTask(Plugin plugin, Runnable function) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, function);
    }

    public static void throwRuntime(Function function) {
        function.runAndThrow();
    }

    public static int runAndReturn0(Function function) {
        function.runAndThrow();
        return 0;
    }

    public static void runAll() {
        Lists.newArrayList(necessaryTasks).forEach(t -> TaskUtil.run(t));
    }

    public static void run(BukkitTask t) {
        ReflectionWrapper.invokeMethod(craftTaskRun, t, new Object[0]);
    }

    @FunctionalInterface
    public static interface Function {
        public void run() throws Throwable;

        default public void runAndThrow() {
            try {
                this.run();
            }
            catch (Throwable e) {
                MzTech.throwException(e);
            }
        }
    }
}

