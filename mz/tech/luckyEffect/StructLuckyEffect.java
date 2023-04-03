/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.material.MaterialData
 *  org.bukkit.util.Vector
 */
package mz.tech.luckyEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import mz.tech.MzTech;
import mz.tech.item.sundry.LuckyBlockItem;
import mz.tech.luckyEffect.DropItemsLuckyEffect;
import mz.tech.luckyEffect.LuckyEffect;
import mz.tech.luckyEffect.TeleportLuckyEffect;
import mz.tech.machine.BigLuckyBlock;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public class StructLuckyEffect
extends LuckyEffect {
    public Map<Vector, ItemStack> blocks = new HashMap<Vector, ItemStack>();

    @Override
    public void toggle(Player player, Location loc) {
        this.blocks.forEach((v, i) -> {
            Block l = loc.add(v).getBlock();
            loc.subtract(v);
            ItemStackBuilder.toBlock(i, player, l);
        });
    }

    public StructLuckyEffect addBlock(Vector v, ItemStack is) {
        this.blocks.put(v, is);
        return this;
    }

    public StructLuckyEffect addBlock(Vector v, Material is) {
        return this.addBlock(v, new ItemStack(is));
    }

    public StructLuckyEffect addBlock(int x, int y, int z, ItemStack is) {
        return this.addBlock(new Vector(x, y, z), is);
    }

    public StructLuckyEffect addBlock(int x, int y, int z, Material is) {
        return this.addBlock(new Vector(x, y, z), is);
    }

    public StructLuckyEffect init(Consumer<StructLuckyEffect> initer) {
        initer.accept(this);
        return this;
    }

    public static void regAll() {
        StructLuckyEffect.reg(LuckyEffect.LuckyEffectType.GOOD, new StructLuckyEffect().init(e -> {
            int j;
            int i = -1;
            while (i < 2) {
                j = -1;
                while (j < 2) {
                    if (i != 0 || j != 0) {
                        e.addBlock(i, 0, j, Material.COBBLESTONE);
                        e.addBlock(i, 3, j, Material.COBBLESTONE);
                        e.addBlock(i, -1, j, Material.COBBLESTONE);
                    }
                    e.addBlock(i, -2, j, Material.IRON_BLOCK);
                    ++j;
                }
                ++i;
            }
            e.addBlock(0, -1, 0, Material.BEACON);
            i = -1;
            while (i < 2) {
                j = -1;
                while (j < 2) {
                    e.addBlock(i, 1, j, ItemStackBuilder.oakFence);
                    e.addBlock(i, 2, j, ItemStackBuilder.oakFence);
                    j += 2;
                }
                i += 2;
            }
            e.addBlock(0, 3, 0, Material.GLASS);
        }), new DropItemsLuckyEffect(new ItemStack(Material.DIAMOND, 6), new ItemStack(Material.EMERALD, 6), new ItemStack(Material.GOLD_INGOT, 6), new ItemStack(Material.IRON_INGOT, 6)));
        StructLuckyEffect.reg(LuckyEffect.LuckyEffectType.GOOD, new StructLuckyEffect(){

            @Override
            public void toggle(Player player, Location loc) {
                BigLuckyBlock.generate(player, loc);
            }
        }, new TeleportLuckyEffect(0, 8, 0));
        PlayerStructLuckyEffect.regAll();
    }

    public static class PlayerStructLuckyEffect
    extends StructLuckyEffect {
        @Override
        public void toggle(Player player, Location loc) {
            super.toggle(player, player.getLocation());
        }

        public static void regAll() {
            PlayerStructLuckyEffect.reg(LuckyEffect.LuckyEffectType.BAD, new TeleportLuckyEffect(0, 0, 0), new PlayerStructLuckyEffect().addBlock(-1, 0, -1, Material.OBSIDIAN).addBlock(0, 0, -1, Material.OBSIDIAN).addBlock(1, 0, -1, Material.OBSIDIAN).addBlock(-1, 0, 0, Material.OBSIDIAN).addBlock(0, 0, 0, Material.OBSIDIAN).addBlock(1, 0, 0, Material.OBSIDIAN).addBlock(-1, 0, 1, Material.OBSIDIAN).addBlock(0, 0, 1, Material.OBSIDIAN).addBlock(1, 0, 1, Material.OBSIDIAN).addBlock(-1, 1, -1, Material.OBSIDIAN).addBlock(-1, 1, 1, Material.OBSIDIAN).addBlock(1, 1, -1, Material.OBSIDIAN).addBlock(1, 1, 1, Material.OBSIDIAN).addBlock(0, 1, 1, ItemStackBuilder.yellowGlassPane).addBlock(-1, 1, 0, ItemStackBuilder.yellowGlassPane).addBlock(0, 1, -1, ItemStackBuilder.yellowGlassPane).addBlock(1, 1, 0, ItemStackBuilder.yellowGlassPane).addBlock(0, 1, 0, Material.WATER).addBlock(-1, 2, -1, Material.OBSIDIAN).addBlock(0, 2, -1, Material.OBSIDIAN).addBlock(1, 2, -1, Material.OBSIDIAN).addBlock(-1, 2, 0, Material.OBSIDIAN).addBlock(0, 2, 0, Material.OBSIDIAN).addBlock(1, 2, 0, Material.OBSIDIAN).addBlock(-1, 2, 1, Material.OBSIDIAN).addBlock(0, 2, 1, Material.OBSIDIAN).addBlock(1, 2, 1, Material.OBSIDIAN));
            new PlayerStructLuckyEffect(){

                @Override
                public void toggle(Player player, Location loc) {
                    Location l = player.getLocation();
                    while (l.getBlockY() >= 0) {
                        int i = -1;
                        while (i < 2) {
                            int j = -1;
                            while (j < 2) {
                                new ItemStackBuilder(Material.AIR).toBlock(player, l.clone().add((double)i, 0.0, (double)j).getBlock());
                                ++j;
                            }
                            ++i;
                        }
                        l.setY((double)(l.getBlockY() - 1));
                    }
                }
            }.reg(LuckyEffect.LuckyEffectType.BAD);
            LuckyEffect.reg(LuckyEffect.LuckyEffectType.GOOD, new PlayerStructLuckyEffect().init(e -> {
                int i = -2;
                while (i < 3) {
                    int j = -2;
                    while (j < 3) {
                        e.addBlock(i, 0, j, Material.SANDSTONE);
                        ++j;
                    }
                    ++i;
                }
                i = 1;
                while (i < 3) {
                    e.addBlock(-2, i, -2, Material.SANDSTONE);
                    e.addBlock(-1, i, -2, Material.SANDSTONE);
                    e.addBlock(0, i, -2, Material.SANDSTONE);
                    e.addBlock(1, i, -2, Material.SANDSTONE);
                    e.addBlock(2, i, -2, Material.SANDSTONE);
                    e.addBlock(-2, i, 2, Material.SANDSTONE);
                    e.addBlock(-1, i, 2, Material.SANDSTONE);
                    e.addBlock(0, i, 2, Material.SANDSTONE);
                    e.addBlock(1, i, 2, Material.SANDSTONE);
                    e.addBlock(2, i, 2, Material.SANDSTONE);
                    e.addBlock(-2, i, -1, Material.SANDSTONE);
                    e.addBlock(-2, i, 0, Material.SANDSTONE);
                    e.addBlock(-2, i, 1, Material.SANDSTONE);
                    e.addBlock(2, i, -1, Material.SANDSTONE);
                    e.addBlock(2, i, 0, Material.SANDSTONE);
                    e.addBlock(2, i, 1, Material.SANDSTONE);
                    ++i;
                }
                e.addBlock(-1, 1, -1, new LuckyBlockItem());
                e.addBlock(-1, 1, 1, new LuckyBlockItem());
                e.addBlock(1, 1, -1, new LuckyBlockItem());
                e.addBlock(1, 1, 1, new LuckyBlockItem());
                e.addBlock(-2, 3, -2, Material.GOLD_BLOCK);
                e.addBlock(-2, 3, 2, Material.GOLD_BLOCK);
                e.addBlock(2, 3, -2, Material.GOLD_BLOCK);
                e.addBlock(2, 3, 2, Material.GOLD_BLOCK);
            }), new TeleportLuckyEffect(0, 1, 0));
            PlayerStructLuckyEffect.reg(LuckyEffect.LuckyEffectType.BAD, new PlayerStructLuckyEffect(){

                @Override
                public void toggle(Player player, Location loc) {
                    super.toggle(player, loc);
                    loc = player.getLocation();
                    loc.setY(254.0);
                    player.getWorld().spawnFallingBlock(loc, new MaterialData(Material.ANVIL)).setHurtEntities(true);
                    loc.setY(255.0);
                    player.getWorld().spawnFallingBlock(loc, new MaterialData(Material.ANVIL)).setHurtEntities(true);
                    MzTech.sendMessage((CommandSender)player, "\u00a7e\u770b\u4f60\u5934\u4e0a\uff01");
                }
            }.init(e -> {
                int i = -1;
                while (i < 2) {
                    int j = -1;
                    while (j < 2) {
                        e.addBlock(i, -1, j, new ItemStackBuilder("DOUBLE_STONE_SLAB2", 0, "stone_bricks", 1));
                        if (i != 0 || j != 0) {
                            e.addBlock(i, 0, j, ItemStackBuilder.ironBars);
                            e.addBlock(i, 1, j, ItemStackBuilder.ironBars);
                            e.addBlock(i, 2, j, ItemStackBuilder.ironBars);
                        }
                        ++j;
                    }
                    ++i;
                }
            }), new TeleportLuckyEffect(0, 0, 0));
        }
    }
}

