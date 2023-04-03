/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPhysicsEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.server.ServerCommandEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.StringUtil
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.event.MachineBreakEvent;
import mz.tech.event.MachineItemAutoMoveEvent;
import mz.tech.item.MzTechItem;
import mz.tech.machine.AppleLeavesBlock;
import mz.tech.machine.AppleSaplingBlock;
import mz.tech.machine.BauxiteBlock;
import mz.tech.machine.BigLuckyBlock;
import mz.tech.machine.BlockShowBoxBlock;
import mz.tech.machine.ChalcociteBlock;
import mz.tech.machine.ChalcopyriteBlock;
import mz.tech.machine.ChemicalFurnaceMachine;
import mz.tech.machine.CobblestoneDuplicatorMachine;
import mz.tech.machine.CrucibleMachine;
import mz.tech.machine.FffffFurnaceBlock;
import mz.tech.machine.GriddleMachine;
import mz.tech.machine.GunpowderBlock;
import mz.tech.machine.HematiteBlock;
import mz.tech.machine.LuckyBlock;
import mz.tech.machine.MachineChunk;
import mz.tech.machine.MagnetiteBlock;
import mz.tech.machine.MedicineBoxMachine;
import mz.tech.machine.MelonPultBlock;
import mz.tech.machine.MetronomeMachine;
import mz.tech.machine.MouseTrapMachine;
import mz.tech.machine.MzCable;
import mz.tech.machine.PlacedItem;
import mz.tech.machine.PotatoMineBlock;
import mz.tech.machine.RailDuplicatorMachine;
import mz.tech.machine.SmilingCraftingTableMachine;
import mz.tech.machine.SunflowerBlock;
import mz.tech.machine.TinOreBlock;
import mz.tech.machine.TrashMachine;
import mz.tech.machine.UraniumOreBlock;
import mz.tech.machine.VillagerNoseBlock;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;

public abstract class MzTechMachine {
    public static List<Class<? extends MzTechMachine>> types;
    private Block block;

    static {
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler(priority=EventPriority.LOWEST)
            public void on(PlayerCommandPreprocessEvent event) {
                String[] args = event.getMessage().split(" ");
                if ((args[0].equalsIgnoreCase("/setblock") || args[0].equalsIgnoreCase("/minecraft:setblock")) && MzTechMachine.forSetBlock((CommandSender)event.getPlayer(), MzTech.subArray(args, 1))) {
                    event.setCancelled(true);
                }
            }

            @EventHandler(priority=EventPriority.LOWEST)
            public void on(ServerCommandEvent event) {
                String[] args = event.getCommand().split(" ");
                if ((args[0].equalsIgnoreCase("/setblock") || args[0].equalsIgnoreCase("/minecraft:setblock")) && MzTechMachine.forSetBlock(event.getSender(), MzTech.subArray(args, 1))) {
                    event.setCancelled(true);
                }
            }
        }, (Plugin)MzTech.instance);
        types = null;
    }

    /*
     * Recovered potentially malformed switches.  Disable with '--allowmalformedswitch false'
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean forSetBlock(CommandSender sender, String[] command) {
        try {
            if (command.length < 4) return false;
            if (command.length > 5) {
                return false;
            }
            if (!StringUtil.startsWithIgnoreCase((String)command[3], (String)"MzTech:")) {
                return false;
            }
            b = MzTech.getCommandLocation(sender, command[0], command[1], command[2]).getBlock();
            if (command.length != 5) ** GOTO lbl42
            var3_4 = command[4];
            tmp = -1;
            switch (var3_4.hashCode()) {
                case 3287941: {
                    if (!var3_4.equals("keep")) break;
                    tmp = 1;
                    break;
                }
                case 1094496948: {
                    if (!var3_4.equals("replace")) break;
                    tmp = 2;
                    break;
                }
                case 1557370132: {
                    if (!var3_4.equals("destory")) break;
                    tmp = 3;
                    break;
                }
                case 1557372922: {
                    if (!var3_4.equals("destroy")) break;
                    tmp = 4;
                    break;
                }
            }
            switch (tmp) {
                case 3: {
                    MzTech.sendMessage(sender, "\u00a7e\u53c8\u4e00\u4e2a\u628adestroy\u5199\u6210destory\u7684\u5c51");
                    break;
                }
                case 1: {
                    if (b.isEmpty()) break;
                    sender.sendMessage("\u00a7c\u65e0\u6cd5\u653e\u7f6e\u65b9\u5757");
                    return true;
                }
                case 2: {
                    b.setType(Material.AIR);
                    break;
                }
                default: {
                    return false;
                }
lbl42:
                // 1 sources

                b.setType(Material.AIR);
                break;
                case 4: 
            }
            MzTechItem.get(command[3].substring("MzTech:".length())).toBlock(b);
            return true;
        }
        catch (Throwable e) {
            return false;
        }
    }

    public static void reg(Class<? extends MzTechMachine> type) {
        types.add(type);
    }

    public static void unreg(Class<? extends MzTechMachine> type) {
        types.remove(type);
    }

    public static void regAll() {
        types = Lists.newArrayList((Object[])new Class[]{AppleSaplingBlock.class, AppleLeavesBlock.class, CrucibleMachine.class, ChemicalFurnaceMachine.class, GriddleMachine.class, MetronomeMachine.class, RailDuplicatorMachine.class, SmilingCraftingTableMachine.class, TrashMachine.class, VillagerNoseBlock.class, CobblestoneDuplicatorMachine.class, PlacedItem.class, MedicineBoxMachine.class, MzCable.class, MouseTrapMachine.class, BlockShowBoxBlock.class, LuckyBlock.class, BigLuckyBlock.class, MelonPultBlock.class, SunflowerBlock.class, PotatoMineBlock.class, GunpowderBlock.class, HematiteBlock.class, MagnetiteBlock.class, ChalcopyriteBlock.class, TinOreBlock.class, BauxiteBlock.class, UraniumOreBlock.class, ChalcociteBlock.class, FffffFurnaceBlock.class});
    }

    @Deprecated
    public static Map<Chunk, MachineChunk> getChunks() {
        return MachineChunk.chunks;
    }

    @Deprecated
    public static MachineChunk getMachines(Chunk chunk) {
        return MachineChunk.chunks.get(chunk);
    }

    public MzTechMachine setBlock(Block block) {
        this.block = block;
        return this;
    }

    public void add() {
        if (this.block == null) {
            throw new RuntimeException("this.block is null\uff08class " + this.getClass() + "\uff09");
        }
        if (!MachineChunk.chunks.containsKey(this.block.getChunk())) {
            MachineChunk.chunks.put(this.block.getChunk(), new MachineChunk(this.block.getWorld()));
        }
        MachineChunk.chunks.get(this.block.getChunk()).add(this);
    }

    public void remove() {
        MachineChunk.chunks.get(this.getBlock().getChunk()).remove(this);
    }

    public boolean isLogged() {
        MachineChunk c2 = MachineChunk.chunks.get(this.getBlock().getChunk());
        if (c2 == null) {
            return false;
        }
        return c2.contains(this);
    }

    public abstract String getType();

    public Block getBlock() {
        return this.block;
    }

    public void move(Location loc) {
        if (this.onMove(loc)) {
            MachineChunk.chunks.get(this.block.getChunk()).remove(this);
            if (MachineChunk.chunks.get(this.block.getChunk()).isEmpty()) {
                MachineChunk.chunks.remove(this.block.getChunk());
            }
            this.block = loc.getBlock();
            if (!MachineChunk.chunks.containsKey(this.block.getChunk())) {
                MachineChunk.chunks.put(this.block.getChunk(), new MachineChunk(this.getBlock().getWorld()));
            }
            MachineChunk.chunks.get(this.block.getChunk()).add(this);
        }
    }

    public boolean onMove(Location loc) {
        return true;
    }

    public void onBreak(Player player, boolean silkTouch, boolean drop) {
    }

    public boolean onPlace(BlockPlaceEvent event) {
        return true;
    }

    public boolean onRightClick(PlayerInteractEvent event) {
        return true;
    }

    @Deprecated
    public static void forEach(Consumer<MzTechMachine> consumer) {
        MachineChunk.chunks.forEach((c2, m) -> m.forEach(machine -> consumer.accept((MzTechMachine)machine)));
    }

    public static <T> void forEach(Class<T> type, Consumer<T> consumer) {
        MachineChunk.chunks.forEach((c2, m) -> m.forEach(machine -> {
            if (type.isAssignableFrom(machine.getClass())) {
                consumer.accept(machine);
            }
        }));
    }

    public static MzTechMachine asMzTechCopy(Block block) {
        MzTechMachine[] rm = new MzTechMachine[1];
        MachineChunk ms = MachineChunk.chunks.get(block.getChunk());
        if (ms == null) {
            return null;
        }
        ms.forEach(m -> {
            if (m.getBlock().getLocation().equals((Object)block.getLocation())) {
                mzTechMachineArray[0] = m;
            }
        });
        return rm[0];
    }

    public List<ItemStack> getDrop(ItemStack tool) {
        if (tool != null && tool.getType() != Material.AIR && tool.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
            return this.getDropAccurate(null);
        }
        return this.getDropNaturally(null);
    }

    public List<ItemStack> getDropNaturally(MachineBreakEvent event) {
        return this.getDropAccurate(null);
    }

    public List<ItemStack> getDropAccurate(MachineBreakEvent event) {
        return Lists.newArrayList((Object[])new ItemStack[]{MzTechItem.get(this.getType())});
    }

    public boolean onGrow(List<BlockState> blocks, boolean byBonemeal, Player player) {
        return false;
    }

    @Deprecated
    public void loadOld(DataInputStream dfi) throws IOException {
    }

    public boolean onLeftClick(PlayerInteractEvent event) {
        return true;
    }

    public MzTechMachine load(NBT nbt) {
        return this;
    }

    public static MzTechMachine load(World world, NBT nbt) {
        String id = nbt.getString("id");
        Block b = new Location(world, (double)nbt.getInt("x").intValue(), (double)nbt.getInt("y").intValue(), (double)nbt.getInt("z").intValue()).getBlock();
        try {
            Class[] cls = (Class[])types.stream().filter(t -> {
                try {
                    return ((MzTechMachine)MzTech.unsafe.allocateInstance((Class<?>)t)).getType().equals(id);
                }
                catch (InstantiationException e) {
                    throw new RuntimeException("\u65e0\u6cd5\u5c06\u673a\u5668" + id + "\u5b9e\u4f8b\u5316");
                }
            }).toArray(Class[]::new);
            if (cls.length == 1) {
                return ((MzTechMachine)MzTech.unsafe.allocateInstance(cls[0])).setBlock(b).load(nbt.hasKey("data") ? nbt.getChild("data") : null);
            }
            if (cls.length < 1) {
                throw new RuntimeException("\u627e\u4e0d\u5230\u673a\u5668" + id + "\u7684\u7c7b");
            }
            throw new RuntimeException("ID\uff08" + id + "\uff09\u76f8\u540c\u7684\u7c7b\uff1a" + cls[0] + "\u548c" + cls[1]);
        }
        catch (Throwable e) {
            throw new RuntimeException("\u5728\u52a0\u8f7d\u673a\u5668" + id + "\uff08" + world.getName() + "\uff0c" + b.getX() + "\uff0c" + b.getY() + "\uff0c" + b.getZ() + "\uff09\u65f6\u51fa\u9519\uff1a " + e.getMessage(), e);
        }
    }

    public NBT save(NBT nbt) {
        return nbt;
    }

    public NBT save() {
        NBT nbt = new NBT().set("id", this.getType()).set("x", this.getBlock().getX()).set("y", this.getBlock().getY()).set("z", this.getBlock().getZ()).set("data", this.save(new NBT()));
        return nbt;
    }

    public boolean onPhysics(BlockPhysicsEvent event) {
        return true;
    }

    public boolean onItemAutoMove(MachineItemAutoMoveEvent e) {
        return true;
    }
}

