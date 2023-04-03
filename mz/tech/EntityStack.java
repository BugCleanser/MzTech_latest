/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Marker
 *  org.apache.logging.log4j.core.Filter
 *  org.apache.logging.log4j.core.Filter$Result
 *  org.apache.logging.log4j.core.LifeCycle$State
 *  org.apache.logging.log4j.core.LogEvent
 *  org.apache.logging.log4j.core.Logger
 *  org.apache.logging.log4j.message.Message
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.ArmorStand
 *  org.bukkit.entity.Chicken
 *  org.bukkit.entity.Damageable
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.Villager
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityPickupItemEvent
 *  org.bukkit.event.entity.EntitySpawnEvent
 *  org.bukkit.event.entity.ItemSpawnEvent
 *  org.bukkit.event.player.PlayerInteractAtEntityEvent
 *  org.bukkit.event.world.ChunkPopulateEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.scheduler.BukkitRunnable
 */
package mz.tech;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.lang.reflect.Method;
import java.util.ArrayList;
import mz.tech.DropsName;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.ReflectionWrapper;
import mz.tech.util.TaskUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityStack
implements Listener {
    static boolean spawnLock = false;
    static Method nmsEntityB = null;
    static Class<?> entityTypes = null;
    static Method entityTypeF = null;
    static Method entitygetEntityType = null;
    static boolean dropLock = false;
    public static Class<?> entityClass = ReflectionWrapper.getNMSClass("Entity");
    public static Method entitySetLocation = ReflectionWrapper.getMethod(entityClass, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
    public static Method entityDeathEventIsCancelled;

    static {
        try {
            entityDeathEventIsCancelled = ReflectionWrapper.getMethod(EntityDeathEvent.class, "isCancelled", new Class[0]);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            nmsEntityB = ReflectionWrapper.getNMSClass("EntityTypes").getMethod("b", entityClass);
        }
        catch (Exception e) {
            entityTypes = ReflectionWrapper.getNMSClass("EntityTypes");
            entityTypeF = ReflectionWrapper.getMethod(entityTypes, "f", new Class[0]);
            entitygetEntityType = ReflectionWrapper.getMethodParent(entityClass, "getEntityType", new Class[0]);
        }
        ((Logger)LogManager.getRootLogger()).addFilter(new Filter(){

            public LifeCycle.State getState() {
                return LifeCycle.State.STARTED;
            }

            public void initialize() {
            }

            public boolean isStarted() {
                return true;
            }

            public boolean isStopped() {
                return false;
            }

            public void start() {
            }

            public void stop() {
            }

            public Filter.Result filter(LogEvent var1) {
                if (var1.getLevel() == Level.WARN && var1.getMessage().getFormattedMessage().startsWith("Tried to add entity ") && var1.getMessage().getFormattedMessage().endsWith(" but it was marked as removed already")) {
                    return Filter.Result.DENY;
                }
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, String var4, Object ... var5) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, String var4, Object var5) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, Object var4, Throwable var5) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, Message var4, Throwable var5) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, String var4, Object var5, Object var6) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, String var4, Object var5, Object var6, Object var7) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, String var4, Object var5, Object var6, Object var7, Object var8) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, String var4, Object var5, Object var6, Object var7, Object var8, Object var9) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, String var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, String var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, String var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, String var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result filter(Logger var1, Level var2, Marker var3, String var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13, Object var14) {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result getOnMatch() {
                return Filter.Result.NEUTRAL;
            }

            public Filter.Result getOnMismatch() {
                return Filter.Result.NEUTRAL;
            }
        });
        new BukkitRunnable(){

            public void run() {
                Bukkit.getWorlds().forEach(world -> world.getEntitiesByClass(Damageable.class).forEach(entity -> {
                    if (entity.isDead()) {
                        return;
                    }
                    entity.getNearbyEntities(5.0, 5.0, 5.0).forEach(e -> {
                        if (!e.isDead() && e instanceof Damageable && EntityStack.canStack(entity, (Damageable)e)) {
                            EntityStack.setEntityNum(entity, EntityStack.getEntityNum(entity) + EntityStack.getEntityNum((Damageable)e));
                            e.remove();
                        }
                    });
                }));
            }
        }.runTaskTimer((Plugin)MzTech.instance, 0L, 600L);
    }

    public static void setNmsEntityLocation(Object nmsEntity, double x, double y, double z, float yaw, float pitch) {
        ReflectionWrapper.invokeMethod(entitySetLocation, nmsEntity, x, y, z, Float.valueOf(yaw), Float.valueOf(pitch));
    }

    @EventHandler(priority=EventPriority.LOWEST)
    void onChunkPopulate(ChunkPopulateEvent event) {
        Entity[] entityArray = event.getChunk().getEntities();
        int n = entityArray.length;
        int n2 = 0;
        while (n2 < n) {
            Entity e = entityArray[n2];
            if (e instanceof Damageable && !(e instanceof ArmorStand)) {
                EntityStack.setEntityNum((Damageable)e, 1);
            }
            ++n2;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    void onEntitySpawn(EntitySpawnEvent event) {
        if (event.isCancelled() || spawnLock) {
            return;
        }
        if (event.getEntity() instanceof Damageable && !(event.getEntity() instanceof ArmorStand)) {
            EntityStack.setEntityNum((Damageable)event.getEntity(), 1);
            TaskUtil.runTask((Plugin)MzTech.instance, () -> event.getEntity().getNearbyEntities(5.0, 5.0, 5.0).forEach(entity -> {
                if (!event.getEntity().isDead() && !entity.isDead() && entity instanceof Damageable && EntityStack.canStack((Damageable)entity, (Damageable)event.getEntity())) {
                    EntityStack.setEntityNum((Damageable)entity, EntityStack.getEntityNum((Damageable)entity) + EntityStack.getEntityNum((Damageable)event.getEntity()));
                    event.getEntity().remove();
                }
            }));
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    Damageable onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        int num;
        ProjectileSource entity;
        if (event.isCancelled()) {
            return null;
        }
        if (event.getCause() != null) {
            switch (event.getCause()) {
                case ENTITY_ATTACK: 
                case PROJECTILE: {
                    break;
                }
                default: {
                    return null;
                }
            }
        }
        if (event.getDamager() instanceof Damageable) {
            if (EntityStack.getEntityNum((Damageable)event.getDamager()) > 1) {
                event.setDamage(event.getFinalDamage() * (double)EntityStack.getEntityNum((Damageable)event.getDamager()));
            }
        } else if (event.getDamager() instanceof Projectile && (entity = ((Projectile)event.getDamager()).getShooter()) instanceof Damageable && EntityStack.getEntityNum((Damageable)entity) > 1) {
            event.setDamage(event.getFinalDamage() * (double)EntityStack.getEntityNum((Damageable)entity));
        }
        if (event.getEntity() instanceof Damageable && !(event.getEntity() instanceof Player) && (num = EntityStack.getEntityNum((Damageable)(entity = (Damageable)event.getEntity()))) > 1) {
            EntityStack.setEntityNum((Damageable)entity, 1);
            spawnLock = true;
            EntityStack.setEntityNum(EntityStack.cloneEntity((Damageable)entity), num - 1);
            spawnLock = false;
            return entity;
        }
        return null;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onEntityDie(EntityDeathEvent event) {
        int n;
        if (entityDeathEventIsCancelled != null && ((Boolean)ReflectionWrapper.invokeMethod(entityDeathEventIsCancelled, event, new Object[0])).booleanValue()) {
            return;
        }
        if (event.getEntity() instanceof Villager && event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager().getType().name().contains("zombie")) {
            EntityStack.setEntityNum((Damageable)event.getEntity(), EntityStack.getEntityNum((Damageable)event.getEntity()));
            return;
        }
        if (dropLock) {
            return;
        }
        if (event.getEntity() instanceof Damageable && !(event.getEntity() instanceof Player) && (n = EntityStack.getEntityNum((Damageable)event.getEntity())) > 1) {
            event.setDroppedExp(event.getDroppedExp() * n);
            ArrayList newDrops = new ArrayList();
            event.getDrops().forEach(itemStack -> {
                int max = itemStack.getType().getMaxStackSize();
                int num = itemStack.getAmount() * n;
                while (num > max) {
                    ItemStack clone = itemStack.clone();
                    clone.setAmount(max);
                    num -= max;
                    newDrops.add(clone);
                }
                itemStack.setAmount(num);
                newDrops.add(itemStack);
            });
            event.getDrops().clear();
            event.getDrops().addAll(newDrops);
            dropLock = true;
            int i = 1;
            while (i < n) {
                Bukkit.getPluginManager().callEvent((Event)event);
                ++i;
            }
            dropLock = false;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.isCancelled()) {
            return;
        }
        this.onEntityDamageByEntity(new EntityDamageByEntityEvent(null, (Entity)event.getEntity(), null, 0.0));
    }

    @EventHandler(priority=EventPriority.LOWEST)
    void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Damageable entity = this.onEntityDamageByEntity(new EntityDamageByEntityEvent(null, event.getRightClicked(), null, 0.0));
        if (entity != null) {
            this.onEntitySpawn(new EntitySpawnEvent((Entity)entity));
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    void onItemSpawn(ItemSpawnEvent event) {
        StackTraceElement[] s;
        if (event.isCancelled()) {
            return;
        }
        StackTraceElement[] stackTraceElementArray = s = new Throwable().getStackTrace();
        int n = s.length;
        int n2 = 0;
        while (n2 < n) {
            StackTraceElement i = stackTraceElementArray[n2];
            if (i.getClassName().equals(ReflectionWrapper.getNMSClass("EntityChicken").getName())) {
                Chicken[] chicken = new Chicken[1];
                event.getEntity().getWorld().getEntitiesByClass(Chicken.class).forEach(chickens -> {
                    if (chickens instanceof Chicken && (chicken[0] == null || chickens.getLocation().distance(event.getEntity().getLocation()) < chicken[0].getLocation().distance(event.getEntity().getLocation()))) {
                        chickenArray[0] = chickens;
                    }
                });
                if (chicken[0] == null || event.getEntity().getItemStack().getAmount() != 1) break;
                int num = EntityStack.getEntityNum((Damageable)chicken[0]);
                if (num <= Material.EGG.getMaxStackSize()) {
                    event.getEntity().setItemStack(new ItemStack(Material.EGG, num));
                    break;
                }
                event.getEntity().setItemStack(new ItemStack(Material.EGG, Material.EGG.getMaxStackSize()));
                num -= Material.EGG.getMaxStackSize();
                while (num > Material.EGG.getMaxStackSize()) {
                    chicken[0].getWorld().dropItemNaturally(chicken[0].getLocation(), new ItemStack(Material.EGG, Material.EGG.getMaxStackSize()));
                    num -= Material.EGG.getMaxStackSize();
                }
                chicken[0].getWorld().dropItemNaturally(chicken[0].getLocation(), new ItemStack(Material.EGG, num));
                break;
            }
            ++n2;
        }
    }

    public static Damageable cloneEntity(Damageable entity) {
        Damageable copy;
        ArrayList ls = Lists.newArrayList((Object[])EntitySpawnEvent.getHandlerList().getRegisteredListeners());
        ls.forEach(arg_0 -> ((HandlerList)EntitySpawnEvent.getHandlerList()).unregister(arg_0));
        try {
            copy = (Damageable)entity.getWorld().spawnEntity(entity.getLocation(), entity.getType());
        }
        catch (Throwable throwable) {
            ls.forEach(arg_0 -> ((HandlerList)EntitySpawnEvent.getHandlerList()).register(arg_0));
            throw throwable;
        }
        ls.forEach(arg_0 -> ((HandlerList)EntitySpawnEvent.getHandlerList()).register(arg_0));
        copy.setCustomName(entity.getCustomName());
        copy.setCustomNameVisible(entity.isCustomNameVisible());
        new NBT((Entity)entity).remove("WorldUUIDMost").remove("WorldUUIDLeast").remove("UUID").remove("UUIDLeast").remove("UUIDMost").set((Entity)copy);
        copy.setVelocity(entity.getVelocity());
        return copy;
    }

    public static NBT removeUselessNBT(NBT nbt) {
        nbt.remove("WorldUUIDMost");
        nbt.remove("WorldUUIDLeast");
        nbt.remove("Pos");
        nbt.remove("Motion");
        nbt.remove("Rotation");
        nbt.remove("PortalCooldown");
        nbt.remove("UUIDLeast");
        nbt.remove("UUID");
        nbt.remove("UUIDMost");
        nbt.remove("AbsorptionAmount");
        nbt.remove("HurtTime");
        nbt.remove("HurtByTimestamp");
        nbt.remove("SleepingX");
        nbt.remove("SleepingY");
        nbt.remove("SleepingZ");
        nbt.remove("LeftHanded");
        nbt.remove("LoveCause");
        nbt.remove("LoveCauseLeast");
        nbt.remove("LoveCauseMost");
        nbt.remove("FallDistance");
        nbt.remove("Spigot.ticksLived");
        nbt.remove("Tags");
        nbt.remove("Air");
        nbt.remove("OnGround");
        nbt.remove("EggLayTime");
        nbt.remove("Strength");
        nbt.remove("TravelPosX");
        nbt.remove("TravelPosY");
        nbt.remove("TravelPosZ");
        nbt.remove("HomePosX");
        nbt.remove("HomePosY");
        nbt.remove("HomePosZ");
        nbt.remove("APX");
        nbt.remove("APY");
        nbt.remove("APZ");
        nbt.remove("Bukkit.updateLevel");
        nbt.remove("Paper.SpawnReason");
        nbt.remove("Paper.Origin");
        nbt.remove("Bukkit.MaxDomestication");
        nbt.remove("Bukkit.Aware");
        nbt.remove("Spigot.ticksLived");
        nbt.remove("Attributes");
        nbt.remove("Fire");
        nbt.remove("CustomName");
        nbt.remove("CustomNameVisible");
        nbt.remove("Purpur.ticksSinceLastInteraction");
        return nbt;
    }

    public static boolean canStack(Damageable a, Damageable b) {
        if (a.getType() != b.getType() || a.getType() == EntityType.VILLAGER || a.getType() == EntityType.ARMOR_STAND) {
            return false;
        }
        if (a.getPassengers().size() > 0 || b.getPassengers().size() > 0) {
            return false;
        }
        if (!EntityStack.getEntityName(a).equals(EntityStack.getEntityName(b))) {
            return false;
        }
        if (!a.getPassengers().isEmpty() || !b.getPassengers().isEmpty()) {
            return false;
        }
        NBT ao = new NBT((Entity)a);
        NBT bo = new NBT((Entity)b);
        EntityStack.removeUselessNBT(ao);
        EntityStack.removeUselessNBT(bo);
        return ao.equals(bo);
    }

    public static int getEntityNum(Damageable entity) {
        int[] ri = new int[]{1};
        entity.getScoreboardTags().forEach(tag -> {
            if (tag.startsWith("MzTech.num.")) {
                nArray[0] = Integer.valueOf(tag.substring("MzTech.num.".length()));
            }
        });
        return ri[0];
    }

    public static boolean isRenamed(Damageable entity) {
        return entity.getCustomName() != null && !entity.getCustomName().startsWith("\u00a7\u65e0");
    }

    public static void setEntityNum(Damageable entity, int num) {
        if (num <= 0) {
            entity.remove();
        } else {
            Sets.newConcurrentHashSet((Iterable)entity.getScoreboardTags()).forEach(tag -> {
                if (tag.startsWith("MzTech.num.")) {
                    entity.removeScoreboardTag(tag);
                }
            });
            if (num > 1) {
                entity.addScoreboardTag("MzTech.num." + num);
                entity.setCustomName(String.valueOf(EntityStack.isRenamed(entity) ? "" : "\u00a7\u65e0") + EntityStack.getEntityName(entity) + " x" + num);
            } else {
                entity.setCustomName(String.valueOf(EntityStack.isRenamed(entity) ? "" : "\u00a7\u65e0") + EntityStack.getEntityName(entity));
            }
            entity.setCustomNameVisible(true);
        }
    }

    public static String getEntityUnlocalizedName(Entity entity) {
        if (nmsEntityB != null) {
            return "entity." + ReflectionWrapper.invokeStaticMethod(nmsEntityB, ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethodParent(entity.getClass(), "getHandle", new Class[0]), entity, new Object[0])) + ".name";
        }
        Object nmsEntity = ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethodParent(entity.getClass(), "getHandle", new Class[0]), entity, new Object[0]);
        Object entityType = ReflectionWrapper.invokeMethod(entitygetEntityType, nmsEntity, new Object[0]);
        return (String)ReflectionWrapper.invokeMethod(entityTypeF, entityType, new Object[0]);
    }

    public static String getEntityName(Damageable entity) {
        if (EntityStack.isRenamed(entity)) {
            if (entity.getCustomName().contains(" x")) {
                return entity.getCustomName().substring(0, entity.getCustomName().lastIndexOf(" x"));
            }
            return entity.getCustomName();
        }
        try {
            return DropsName.lang.getOrDefault(EntityStack.getEntityUnlocalizedName((Entity)entity), EntityStack.getEntityUnlocalizedName((Entity)entity));
        }
        catch (NullPointerException e) {
            return EntityStack.getEntityUnlocalizedName((Entity)entity);
        }
        catch (Exception e) {
            MzTech.throwException(e);
            return EntityStack.getEntityUnlocalizedName((Entity)entity);
        }
    }

    public static boolean hasCustomName(Damageable entity) {
        if (entity.getCustomName() == null) {
            return false;
        }
        return !EntityStack.getEntityName(entity).equals(DropsName.lang == null ? entity.getName() : DropsName.lang.getOrDefault(EntityStack.getEntityUnlocalizedName((Entity)entity), entity.getName()));
    }
}

