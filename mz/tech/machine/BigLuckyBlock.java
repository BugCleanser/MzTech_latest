/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.util.Vector
 */
package mz.tech.machine;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mz.tech.DataFile;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.event.MachineBreakEvent;
import mz.tech.item.BigLuckyBlockItem;
import mz.tech.machine.LuckyBlock;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.ToolUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BigLuckyBlock
extends MzTechMachine {
    public Location centre;

    public BigLuckyBlock(Location centre) {
        this.centre = centre;
    }

    @Override
    public NBT save(NBT nbt) {
        nbt.set("centre", new NBT(this.centre));
        return super.save(nbt);
    }

    @Override
    public MzTechMachine load(NBT nbt) {
        this.centre = nbt.getChild("centre").toLocation();
        return super.load(nbt);
    }

    @Override
    @Deprecated
    public void loadOld(DataInputStream dfi) throws IOException {
        this.centre = DataFile.loadLocation(dfi);
    }

    @Override
    public void onBreak(Player player, boolean silkTouch, boolean drop) {
        this.add();
        int i = -3;
        while (i < 5) {
            int j = 0;
            while (j < 8) {
                int k = -3;
                while (k < 5) {
                    MzTechMachine m;
                    Block b = this.centre.clone().add((double)i, (double)j, (double)k).getBlock();
                    if (!b.isEmpty() && (m = MzTechMachine.asMzTechCopy(b)) instanceof BigLuckyBlock && ((BigLuckyBlock)m).centre.equals((Object)this.centre)) {
                        m.remove();
                        ToolUtil.showBlockBreak(b);
                        b.setType(Material.AIR);
                        if (MzTech.rand.nextInt(64) == 0) {
                            new LuckyBlock().setBlock(b).onBreak(player, silkTouch, drop);
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }

    @Override
    public List<ItemStack> getDropAccurate(MachineBreakEvent event) {
        return new ArrayList<ItemStack>();
    }

    @Override
    public String getType() {
        return "\u5927\u5e78\u8fd0\u65b9\u5757";
    }

    public static void generate(Player player, Location loc) {
        int j;
        int[][] iss = new int[][]{{1, 1, 1, 1, 1, 1, 1, 1}, {1, 2, 2, 3, 3, 4, 2, 1}, {1, 2, 3, 4, 2, 3, 4, 1}, {1, 2, 2, 2, 3, 4, 2, 1}, {1, 2, 2, 3, 4, 2, 2, 1}, {1, 2, 2, 2, 2, 2, 2, 1}, {1, 2, 2, 3, 4, 2, 2, 1}, {1, 1, 1, 1, 1, 1, 1, 1}};
        HashMap<Vector, BigLuckyBlockItem> bs = new HashMap<Vector, BigLuckyBlockItem>();
        int i = 0;
        while (i < 8) {
            j = 0;
            while (j < 8) {
                bs.put(new Vector(i - 3, 0, j - 3), new BigLuckyBlockItem(iss[j][i], loc));
                bs.put(new Vector(i - 3, 7, j - 3), new BigLuckyBlockItem(iss[j][i], loc));
                bs.put(new Vector(-3, 7 - j, i - 3), new BigLuckyBlockItem(iss[j][i], loc));
                bs.put(new Vector(4, 7 - j, 4 - i), new BigLuckyBlockItem(iss[j][i], loc));
                bs.put(new Vector(4 - i, 7 - j, -3), new BigLuckyBlockItem(iss[j][i], loc));
                bs.put(new Vector(i - 3, 7 - j, 4), new BigLuckyBlockItem(iss[j][i], loc));
                ++j;
            }
            ++i;
        }
        i = 1;
        while (i < 7) {
            j = 1;
            while (j < 7) {
                int k = 1;
                while (k < 7) {
                    bs.put(new Vector(i - 3, j, k - 3), new BigLuckyBlockItem(1, loc));
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        bs.forEach((v, b) -> b.toBlock(loc.clone().add(v).getBlock()));
    }
}

