/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.PistonMoveReaction
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockExplodeEvent
 *  org.bukkit.event.block.BlockFormEvent
 *  org.bukkit.event.block.BlockFromToEvent
 *  org.bukkit.event.block.BlockPistonExtendEvent
 *  org.bukkit.event.block.BlockPistonRetractEvent
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.entity.ItemSpawnEvent
 *  org.bukkit.event.player.PlayerBucketEmptyEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.event;

import com.google.common.collect.Lists;
import java.util.List;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import mz.tech.MzTech;
import mz.tech.ReflectionWrapper;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class MachineBreakEvent
extends Event
implements Listener {
    private static boolean dropLook = false;
    private static final HandlerList handlers = new HandlerList();
    public Player player;
    public MzTechMachine machine;
    private List<ItemStack> drops;
    public static Event lastRawEvent;
    public Event rawEvent;
    public static Cause lastCause;
    public Cause cause;

    static {
        lastCause = null;
    }

    @EventHandler
    static void onDrop(ItemSpawnEvent event) {
        if (dropLook) {
            event.setCancelled(true);
            dropLook = false;
            return;
        }
    }

    public static void hookSetAir(Block block) {
        try {
            if (block.isEmpty() || MzTech.instance == null || !MzTech.instance.isEnabled()) {
                return;
            }
            BlockBreakEvent event = new BlockBreakEvent(block, null);
            MachineBreakEvent.onBlockBreak(event);
            if (!event.isDropItems()) {
                dropLook = true;
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public MachineBreakEvent(Player player, MzTechMachine machine, List<ItemStack> drops, Event rawEvent, Cause cause) {
        this.player = player;
        this.machine = machine;
        this.drops = drops;
        this.rawEvent = rawEvent;
        this.cause = cause;
    }

    public MachineBreakEvent() {
    }

    public boolean hasDrops() {
        return this.drops != null && !this.drops.isEmpty();
    }

    public List<ItemStack> getDrops() {
        return this.drops;
    }

    public void setDrops(List<ItemStack> drops) {
        this.drops = drops;
    }

    public void addDrops(ItemStack ... drops) {
        ItemStack[] itemStackArray = drops;
        int n = drops.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack is = itemStackArray[n2];
            this.drops.add(is);
            ++n2;
        }
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @EventHandler(priority=EventPriority.MONITOR)
    static void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        MzTechMachine machine = MzTechMachine.asMzTechCopy(event.getBlock());
        if (machine != null) {
            boolean silkTouch = event.getPlayer() != null && event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.SILK_TOUCH) > 0;
            MachineBreakEvent e = new MachineBreakEvent(event.getPlayer(), machine, null, lastRawEvent, lastCause);
            e.drops = silkTouch ? machine.getDropAccurate(e) : machine.getDropNaturally(e);
            Bukkit.getPluginManager().callEvent((Event)e);
            e.machine.remove();
            boolean d = false;
            if (event.getPlayer() == null || event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                d = true;
                event.setDropItems(false);
            }
            machine.onBreak(event.getPlayer(), silkTouch, e.hasDrops());
            if (d) {
                e.getDrops().forEach(drop -> {
                    if (!ItemStackBuilder.isEmpty(drop)) {
                        MzTech.dropItemStack(event.getBlock(), drop);
                    }
                });
                BlockState state = event.getBlock().getState();
                if (state instanceof InventoryHolder) {
                    Inventory inv = ((InventoryHolder)state).getInventory();
                    inv.forEach(is -> {
                        if (!ItemStackBuilder.isEmpty(is)) {
                            MzTech.dropItemStack(event.getBlock(), is);
                        }
                    });
                    inv.setContents(new ItemStack[inv.getSize()]);
                }
            }
            event.setExpToDrop(0);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.getEntityType() == EntityType.FALLING_BLOCK && event.getTo() != Material.AIR) {
            MachineBreakEvent.onBlockBreak(new BlockBreakEvent(event.getBlock(), null));
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    void onBlockFromTo(BlockFromToEvent event) {
        if (event.isCancelled() || !event.getToBlock().getChunk().isLoaded()) {
            return;
        }
        BlockBreakEvent e = new BlockBreakEvent(event.getToBlock(), null);
        e.setCancelled(false);
        e.setDropItems(true);
        MachineBreakEvent.onBlockBreak(e);
        if (!e.isDropItems()) {
            event.getToBlock().setType(Material.AIR);
            event.setCancelled(true);
        }
    }

    @Deprecated
    public static Block facingBlock(Block block) {
        if (block.getType() == Material.TORCH || block.getType() == Material.REDSTONE_TORCH_OFF || block.getType() == Material.REDSTONE_TORCH_ON) {
            switch (block.getData()) {
                case 1: {
                    return block.getLocation().add(-1.0, 0.0, 0.0).getBlock();
                }
                case 2: {
                    return block.getLocation().add(1.0, 0.0, 0.0).getBlock();
                }
                case 3: {
                    return block.getLocation().add(0.0, 0.0, -1.0).getBlock();
                }
                case 4: {
                    return block.getLocation().add(0.0, 0.0, 1.0).getBlock();
                }
                case 5: {
                    return block.getLocation().add(0.0, -1.0, 0.0).getBlock();
                }
            }
            return null;
        }
        switch (block.getData()) {
            case 0: {
                return block.getLocation().add(0.0, -1.0, 0.0).getBlock();
            }
            case 1: {
                return block.getLocation().add(0.0, 1.0, 0.0).getBlock();
            }
            case 2: {
                return block.getLocation().add(0.0, 0.0, -1.0).getBlock();
            }
            case 3: {
                return block.getLocation().add(0.0, 0.0, 1.0).getBlock();
            }
            case 4: {
                return block.getLocation().add(-1.0, 0.0, 0.0).getBlock();
            }
            case 5: {
                return block.getLocation().add(1.0, 0.0, 0.0).getBlock();
            }
        }
        return null;
    }

    @Deprecated
    public static Block getDependBlock(Block block) {
        block55: {
            if (block.getPistonMoveReaction() != PistonMoveReaction.BREAK) break block55;
            try {
                switch ((BlockFace)ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethodParent(block.getState().getData().getClass(), "getAttachedFace", new Class[0]), block.getState().getData(), new Object[0])) {
                    case DOWN: {
                        return block.getLocation().add(0.0, -1.0, 0.0).getBlock();
                    }
                    case UP: {
                        return block.getLocation().add(0.0, 1.0, 0.0).getBlock();
                    }
                    case EAST: {
                        return block.getLocation().add(1.0, 0.0, 0.0).getBlock();
                    }
                    case WEST: {
                        return block.getLocation().add(-1.0, 0.0, 0.0).getBlock();
                    }
                    case SOUTH: {
                        return block.getLocation().add(0.0, 0.0, 1.0).getBlock();
                    }
                    case NORTH: {
                        return block.getLocation().add(0.0, 0.0, -1.0).getBlock();
                    }
                }
            }
            catch (Throwable e) {
                switch (block.getType().name()) {
                    case "LEAVES": 
                    case "DRAGON_WALL_HEAD": 
                    case "OAK_LEAVES": 
                    case "PLAYER_HEAD": 
                    case "ZOMBIE_HEAD": 
                    case "DARK_OAK_LEAVES": 
                    case "JUNGLE_LEAVES": 
                    case "CREEPER_HEAD": 
                    case "PLAYER_WALL_HEAD": 
                    case "BIRCH_LEAVES": 
                    case "WEB": 
                    case "SKULL": 
                    case "LEGACY_SKULL": 
                    case "DRAGON_HEAD": 
                    case "ACACIA_LEAVES": 
                    case "LEGACY_LEAVES": 
                    case "CREEPER_WALL_HEAD": 
                    case "ZOMBIE_WALL_HEAD": 
                    case "COBWEB": 
                    case "SPRUCE_LEAVES": {
                        return block;
                    }
                }
                return block.getLocation().add(0.0, -1.0, 0.0).getBlock();
            }
        }
        return block;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        event.blockList().forEach(b -> {
            BlockBreakEvent e = new BlockBreakEvent(b, null);
            e.setDropItems(true);
            if (lastCause == null) {
                lastRawEvent = event;
                lastCause = Cause.ENTITY_EXPLODE;
            }
            MachineBreakEvent.onBlockBreak(e);
            lastRawEvent = null;
            lastCause = null;
            if (!e.isDropItems()) {
                b.setType(Material.AIR);
            }
        });
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onBlockExplode(BlockExplodeEvent event) {
        lastRawEvent = event;
        lastCause = Cause.BLOCK_EXPLODE;
        this.onEntityExplode(new EntityExplodeEvent(null, event.getBlock().getLocation(), event.blockList(), event.getYield()));
    }

    public List<Block> getAdjacentBlocks(Block block) {
        return Lists.newArrayList((Object[])new Block[]{block.getLocation().add(1.0, 0.0, 0.0).getBlock(), block.getLocation().add(-1.0, 0.0, 0.0).getBlock(), block.getLocation().add(0.0, 1.0, 0.0).getBlock(), block.getLocation().add(0.0, -1.0, 0.0).getBlock(), block.getLocation().add(0.0, 0.0, 1.0).getBlock(), block.getLocation().add(0.0, 0.0, -1.0).getBlock()});
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onBlockPistonExtend(BlockPistonExtendEvent event) {
        if (!event.isCancelled()) {
            event.getBlocks().forEach(b -> {
                switch (b.getPistonMoveReaction()) {
                    case BREAK: {
                        BlockBreakEvent e = new BlockBreakEvent(b, null);
                        MachineBreakEvent.onBlockBreak(e);
                        if (e.isDropItems()) break;
                        b.setType(Material.AIR);
                        break;
                    }
                    case IGNORE: {
                        break;
                    }
                }
            });
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onBlockPistonRetract(BlockPistonRetractEvent event) {
        this.onBlockPistonExtend(new BlockPistonExtendEvent(event.getBlock(), event.getBlocks(), event.getDirection().getOppositeFace()));
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        if (event.isCancelled()) {
            return;
        }
        BlockBreakEvent e = new BlockBreakEvent(event.getBlockClicked().getRelative(event.getBlockFace()), null);
        MachineBreakEvent.onBlockBreak(e);
        if (!e.isDropItems()) {
            event.getBlockClicked().getRelative(event.getBlockFace()).setType(Material.AIR);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onBlockForm(BlockFormEvent event) {
        if (event.isCancelled()) {
            return;
        }
        MachineBreakEvent.onBlockBreak(new BlockBreakEvent(event.getBlock(), null));
    }

    public static void init() {
        try {
            MzTech.instance.getLogger().info("\u5f00\u59cb\u5c1d\u8bd5\u4e3anms.Chunk.class\u6dfb\u52a0\u8865\u4e01");
            Throwable ex = null;
            try {
                CtMethod cm;
                ClassPool cp = ClassPool.getDefault();
                CtClass ct = cp.get(ReflectionWrapper.getNMSClassName("Chunk"));
                CtField hookSetAir = new CtField(cp.get("java.lang.reflect.Method"), "hookSetAir", ct);
                hookSetAir.setModifiers(9);
                ct.addField(hookSetAir);
                try {
                    cm = ct.getMethod("a", "(L" + ReflectionWrapper.getNMSClassName("BlockPosition").replace('.', '/') + ";L" + ReflectionWrapper.getNMSClassName("IBlockData").replace('.', '/') + ";)L" + ReflectionWrapper.getNMSClassName("IBlockData").replace('.', '/') + ";");
                }
                catch (Throwable e) {
                    cm = ct.getMethod("setType", "(L" + ReflectionWrapper.getNMSClassName("BlockPosition").replace('.', '/') + ";L" + ReflectionWrapper.getNMSClassName("IBlockData").replace('.', '/') + ";ZZ)L" + ReflectionWrapper.getNMSClassName("IBlockData").replace('.', '/') + ";");
                }
                cm.insertBefore("if(hookSetAir!=null&&iblockdata.getMaterial()==net.minecraft.server." + MzTech.MCVersion + ".Material.AIR)" + "\thookSetAir.invoke(null,new Object[]{new org.bukkit.Location(this.world.getWorld(),(double)blockposition.getX(),(double)blockposition.getY(),(double)blockposition.getZ()).getBlock()});");
                MzTech.loadClass(ReflectionWrapper.getNMSClassName("Chunk"), ct.toBytecode(), ClassLoader.getSystemClassLoader());
                MzTech.instance.getLogger().info("\u6210\u529f\u8865\u4e01nms.Chunk.class");
            }
            catch (Throwable e) {
                MzTech.instance.getLogger().info("nms.Chunk.class\u5df2\u7ecf\u88ab\u52a0\u8f7d\uff0c\u53ef\u80fd\u5df2\u8865\u4e01");
                ex = e;
            }
            MzTech.instance.getLogger().info("\u6b63\u5728\u6548\u9a8cnms.Chunk.class");
            try {
                ReflectionWrapper.setStaticFieldValue(ReflectionWrapper.getField(ReflectionWrapper.getNMSClass("Chunk"), "hookSetAir"), ReflectionWrapper.getMethod(MachineBreakEvent.class, "hookSetAir", Block.class));
                MzTech.instance.getLogger().info("\u5df2\u786e\u8ba4nms.Chunk.class\u7684\u8865\u4e01");
            }
            catch (Throwable e) {
                MzTech.instance.getLogger().warning("nms.Chunk.class\u8865\u4e01\u5931\u8d25\uff0c\u8bf7\u5728\u670d\u52a1\u5668\u542f\u52a8\u65f6\u52a0\u8f7d\u63d2\u4ef6");
                ex.printStackTrace();
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void unload() {
        try {
            ReflectionWrapper.setStaticFieldValue(ReflectionWrapper.getField(ReflectionWrapper.getNMSClass("Chunk"), "hookSetAir"), null);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static enum Cause {
        ENTITY_EXPLODE,
        BLOCK_EXPLODE;

    }
}

