/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Arrow
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityShootBowEvent
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.item;

import com.google.common.collect.Lists;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.luckyEffect.ArrowCarryingEnderPearl;
import mz.tech.luckyEffect.ArrowCarryingFire;
import mz.tech.luckyEffect.ArrowCarryingTnt;
import mz.tech.luckyEffect.ArrowRebound;
import mz.tech.luckyEffect.ArrowWithLightning;
import mz.tech.luckyEffect.HitToSetIce;
import mz.tech.luckyEffect.LuckyBowFlyEffect;
import mz.tech.luckyEffect.LuckyBowHitEffect;
import mz.tech.luckyEffect.LuckyBowShootEffect;
import mz.tech.luckyEffect.ShootConsecutively;
import mz.tech.luckyEffect.ShootToGoBack;
import mz.tech.luckyEffect.ShootToRide;
import mz.tech.luckyEffect.ShootUp;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.plugin.Plugin;

public class LuckyBow
extends MzTechItem {
    public static List<LuckyBowShootEffect> launchEffects;
    public static List<LuckyBowHitEffect> hitEffects;

    static {
        if (MzTech.instance.isEnabled()) {
            Bukkit.getPluginManager().registerEvents(new Listener(){

                @EventHandler(priority=EventPriority.HIGHEST)
                public void onEntityShootBow(EntityShootBowEvent event) {
                    MzTechItem mztech = MzTechItem.asMzTechCopy(event.getBow());
                    if (mztech instanceof LuckyBow) {
                        if (launchEffects.size() > 0 && MzTech.rand.nextInt(100) < 25) {
                            MzTech.randValue(launchEffects).toggle(event.getEntity(), (Arrow)event.getProjectile());
                        }
                        if (hitEffects.size() > 0 && MzTech.rand.nextInt(100) < 75) {
                            MzTech.randValue(hitEffects).bind((Arrow)event.getProjectile());
                        }
                    }
                }
            }, (Plugin)MzTech.instance);
        }
        launchEffects = Lists.newArrayList((Object[])new LuckyBowShootEffect[]{ShootToGoBack.newInstance(), ShootToRide.newInstance(), ShootUp.newInstance()});
        hitEffects = Lists.newArrayList((Object[])new LuckyBowHitEffect[]{ArrowCarryingTnt.newInstance(), ArrowCarryingFire.newInstance(), new LuckyBowFlyEffect(), new ArrowCarryingEnderPearl(), ArrowWithLightning.newInstance(), ShootConsecutively.newInstance(), ArrowRebound.newInstance(), HitToSetIce.newInstance()});
    }

    public LuckyBow() {
        super(new ItemStackBuilder(Material.BOW).setLocName("\u00a74\u5e78\u8fd0\u5f13").build());
    }

    @Override
    public String getTypeName() {
        return "\u5e78\u8fd0\u5f13";
    }

    @Override
    public MzTechCategory getCategory() {
        return null;
    }
}

