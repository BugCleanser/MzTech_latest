/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.FireworkEffect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.AbstractArrow
 *  org.bukkit.entity.AbstractArrow$PickupStatus
 *  org.bukkit.entity.AnimalTamer
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Arrow$PickupStatus
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.Donkey
 *  org.bukkit.entity.Egg
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Firework
 *  org.bukkit.entity.Horse
 *  org.bukkit.entity.LightningStrike
 *  org.bukkit.entity.MagmaCube
 *  org.bukkit.entity.Ocelot
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Slime
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.entity.Wolf
 *  org.bukkit.inventory.meta.FireworkMeta
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 */
package mz.tech.luckyEffect;

import com.google.common.collect.Lists;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mz.tech.MzTech;
import mz.tech.luckyEffect.DropItemsLuckyEffect;
import mz.tech.luckyEffect.StructLuckyEffect;
import mz.tech.luckyEffect.TeleportLuckyEffect;
import mz.tech.util.TaskUtil;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public abstract class LuckyEffect {
    public static Map<List<LuckyEffect>, LuckyEffectType> effects = new LinkedHashMap<List<LuckyEffect>, LuckyEffectType>();

    static {
        LuckyEffect.regAll();
    }

    public static void reg(LuckyEffectType type, LuckyEffect ... effects) {
        LuckyEffect.reg(type, Lists.newArrayList((Object[])effects));
    }

    public static void reg(LuckyEffect ... effects) {
        LuckyEffect.reg(LuckyEffectType.ORDINARY, effects);
    }

    public static void reg(LuckyEffectType type, List<LuckyEffect> effects) {
        LuckyEffect.effects.put(effects, type);
    }

    public static void reg(List<LuckyEffect> effects) {
        LuckyEffect.reg(LuckyEffectType.ORDINARY, effects);
    }

    public void reg(LuckyEffectType type) {
        LuckyEffect.reg(type, this);
    }

    public void reg() {
        LuckyEffect.reg(LuckyEffectType.ORDINARY, this);
    }

    public abstract void toggle(Player var1, Location var2);

    public static void regAll() {
        DropItemsLuckyEffect.regAll();
        StructLuckyEffect.regAll();
        TeleportLuckyEffect.regAll();
        LuckyEffect.reg(LuckyEffectType.BAD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                BukkitTask[] r;
                int[] i = new int[1];
                r = new BukkitTask[]{TaskUtil.runTaskTimer((Plugin)MzTech.instance, 0L, 1L, () -> {
                    loc.getWorld().spawn(loc, Arrow.class, a -> {
                        a.setVelocity(player.getEyeLocation().subtract(loc).toVector().multiply(2).add(player.getEyeLocation().getDirection()));
                        a.setShooter((ProjectileSource)player);
                        a.setFireTicks(1000);
                        a.setCritical(true);
                        a.setBounce(true);
                        a.setKnockbackStrength(1);
                        try {
                            a.setPickupStatus(Arrow.PickupStatus.CREATIVE_ONLY);
                        }
                        catch (Throwable e) {
                            ((AbstractArrow)a).setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
                        }
                        a.setKnockbackStrength(1);
                    });
                    nArray[0] = i[0] + 1;
                    if (i[0] >= 20) {
                        r[0].cancel();
                    }
                })};
            }
        });
        LuckyEffect.reg(new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                try {
                    loc.getWorld().spawnEntity(loc, MzTech.randValue(EntityType.values()));
                }
                catch (Throwable e) {
                    this.toggle(player, loc);
                }
            }
        });
        LuckyEffect.reg(LuckyEffectType.BAD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                loc.getWorld().spawn(loc, TNTPrimed.class, tnt -> tnt.setFuseTicks(0));
            }
        });
        LuckyEffect.reg(LuckyEffectType.BAD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                loc.getWorld().spawn(loc, Slime.class, slime -> slime.setSize(8));
            }
        });
        LuckyEffect.reg(LuckyEffectType.BAD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                loc.getWorld().spawn(loc, MagmaCube.class, e -> e.setSize(8));
            }
        });
        LuckyEffect.reg(LuckyEffectType.GOOD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                int i = 0;
                while (i < 5) {
                    loc.getWorld().spawn(loc, Wolf.class, e -> e.setOwner((AnimalTamer)player));
                    ++i;
                }
            }
        });
        LuckyEffect.reg(LuckyEffectType.GOOD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                int i = 0;
                while (i < 5) {
                    loc.getWorld().spawn(loc, Ocelot.class, e -> {
                        try {
                            e.setOwner((AnimalTamer)player);
                        }
                        catch (Throwable throwable) {
                            // empty catch block
                        }
                    });
                    ++i;
                }
            }
        });
        LuckyEffect.reg(LuckyEffectType.GOOD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                int i = 0;
                while (i < 5) {
                    loc.getWorld().spawn(loc, Donkey.class, e -> e.setOwner((AnimalTamer)player));
                    ++i;
                }
            }
        });
        LuckyEffect.reg(LuckyEffectType.GOOD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                int i = 0;
                while (i < 5) {
                    loc.getWorld().spawn(loc, Horse.class, e -> e.setOwner((AnimalTamer)player));
                    ++i;
                }
            }
        });
        LuckyEffect.reg(new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                int i = 0;
                while (i < 20) {
                    loc.getWorld().spawn(loc, Egg.class, e -> {
                        e.setVelocity(new Vector((double)MzTech.rand.nextInt(100) / 100.0 - 0.5, 1.0, (double)MzTech.rand.nextInt(100) / 100.0 - 0.5));
                        e.setShooter((ProjectileSource)player);
                    });
                    ++i;
                }
            }
        });
        LuckyEffect.reg(LuckyEffectType.BAD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                int i = 0;
                while (i < 10) {
                    loc.getWorld().spawn(loc, TNTPrimed.class, e -> e.setVelocity(new Vector((double)MzTech.rand.nextInt(100) / 100.0 - 0.5, 1.0, (double)MzTech.rand.nextInt(100) / 100.0 - 0.5)));
                    ++i;
                }
            }
        });
        LuckyEffect.reg(LuckyEffectType.BAD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                loc.getBlock().setType(Material.REDSTONE_BLOCK);
                FallingBlock l = null;
                int i = 0;
                while (i < 10) {
                    FallingBlock ll = loc.getWorld().spawnFallingBlock(loc.add(0.0, 10.0, 0.0), new MaterialData(Material.TNT));
                    if (l != null) {
                        l.addPassenger((Entity)ll);
                    }
                    l = ll;
                    ++i;
                }
            }
        });
        LuckyEffect.reg(LuckyEffectType.BAD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                loc.getWorld().spawn(loc, Creeper.class);
                loc.getWorld().spawn(loc, LightningStrike.class);
            }
        });
        LuckyEffect.reg(LuckyEffectType.BAD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                loc.getWorld().spawn(loc, Firework.class, e -> {
                    FireworkMeta fm = e.getFireworkMeta();
                    fm.setPower(3);
                    fm.addEffect(FireworkEffect.builder().flicker(true).trail(true).withFade(MzTech.randColor()).withColor(MzTech.randColor()).build());
                    e.setFireworkMeta(fm);
                    e.addPassenger((Entity)player);
                });
            }
        });
        LuckyEffect.reg(LuckyEffectType.BAD, new LuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                loc.getWorld().spawn(loc, Firework.class, e -> {
                    FireworkMeta fm = e.getFireworkMeta();
                    fm.setPower(2);
                    e.setFireworkMeta(fm);
                    FallingBlock tnt = loc.getWorld().spawnFallingBlock(loc, new MaterialData(Material.TNT));
                    tnt.setDropItem(false);
                    tnt.addPassenger(loc.getWorld().spawn(loc, Arrow.class, a -> a.setFireTicks(Integer.MAX_VALUE)));
                    e.addPassenger((Entity)tnt);
                });
            }
        });
    }

    public static enum LuckyEffectType {
        ORDINARY,
        GOOD,
        BAD;

    }
}

