/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockDamageEvent
 *  org.bukkit.event.entity.EntityPickupItemEvent
 *  org.bukkit.event.inventory.BrewEvent
 *  org.bukkit.event.inventory.FurnaceSmeltEvent
 *  org.bukkit.event.inventory.PrepareItemCraftEvent
 *  org.bukkit.event.player.PlayerBucketFillEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import mz.tech.MzTech;
import mz.tech.ReflectionWrapper;
import mz.tech.category.MzTechCategory;
import mz.tech.event.LingeringPotionBreakEvent;
import mz.tech.item.BigLuckyBlockItem;
import mz.tech.item.BlockShowBox;
import mz.tech.item.CD;
import mz.tech.item.CraftGuide;
import mz.tech.item.CustomItem;
import mz.tech.item.DebugStick;
import mz.tech.item.Hammer;
import mz.tech.item.LuckyBow;
import mz.tech.item.LuckySword;
import mz.tech.item.Record;
import mz.tech.item.Test;
import mz.tech.item.baseMachine.ChemicalFurnace;
import mz.tech.item.baseMachine.CobblestoneDuplicator;
import mz.tech.item.baseMachine.ConversionTable;
import mz.tech.item.baseMachine.Crucible;
import mz.tech.item.baseMachine.FffffFurnace;
import mz.tech.item.baseMachine.Griddle;
import mz.tech.item.baseMachine.MedicineBox;
import mz.tech.item.baseMachine.Metronome;
import mz.tech.item.baseMachine.MouseTrap;
import mz.tech.item.baseMachine.RailDuplicator;
import mz.tech.item.baseMachine.SmilingCraftingTable;
import mz.tech.item.baseMachine.Trash;
import mz.tech.item.decoration.BugJumpBlock;
import mz.tech.item.decoration.ChimneyTop;
import mz.tech.item.decoration.ChristmasHat;
import mz.tech.item.decoration.ChristmasLeavesCandy;
import mz.tech.item.decoration.ChristmasPresentBrown;
import mz.tech.item.decoration.ChristmasPresentCyan;
import mz.tech.item.decoration.ChristmasPresentGray;
import mz.tech.item.decoration.ChristmasPresentGreen;
import mz.tech.item.decoration.ChristmasPresentMagenta;
import mz.tech.item.decoration.ChristmasPresentOrange;
import mz.tech.item.decoration.ChristmasPresentPink;
import mz.tech.item.decoration.ChristmasPresentPurple;
import mz.tech.item.decoration.ChristmasPresentRed;
import mz.tech.item.decoration.ChristmasPresentYellow;
import mz.tech.item.decoration.ChristmasSnowGlobe;
import mz.tech.item.decoration.ChristmasTreeBell;
import mz.tech.item.decoration.Doge;
import mz.tech.item.decoration.Funny;
import mz.tech.item.decoration.IllusionBlock;
import mz.tech.item.decoration.MiniBedrock;
import mz.tech.item.decoration.MiniCactus;
import mz.tech.item.decoration.MiniCauldron;
import mz.tech.item.decoration.MiniCocoa;
import mz.tech.item.decoration.MiniLava;
import mz.tech.item.decoration.MiniMelon;
import mz.tech.item.decoration.MiniPeonyPot;
import mz.tech.item.decoration.MiniPufferfish;
import mz.tech.item.decoration.MiniPumpkin;
import mz.tech.item.decoration.MiniPumpkinCarved;
import mz.tech.item.decoration.MiniRedstoneLamp;
import mz.tech.item.decoration.MiniSlimeBall;
import mz.tech.item.decoration.MiniWater;
import mz.tech.item.decoration.SkeletonSkull;
import mz.tech.item.electricity.MachineFrame;
import mz.tech.item.food.CandyBar;
import mz.tech.item.food.CandyBlue;
import mz.tech.item.food.CandyGold;
import mz.tech.item.food.CandyPink;
import mz.tech.item.food.CandyRed;
import mz.tech.item.food.Cheese;
import mz.tech.item.food.Chips;
import mz.tech.item.food.Chocolate;
import mz.tech.item.food.ChocolateGold;
import mz.tech.item.food.Flour;
import mz.tech.item.food.GingerbreadMan;
import mz.tech.item.food.Hamburger;
import mz.tech.item.food.IceCream;
import mz.tech.item.food.MoonCake;
import mz.tech.item.food.MouseTailJuice;
import mz.tech.item.food.MzStew;
import mz.tech.item.food.PoopBlock;
import mz.tech.item.food.SheepMilk;
import mz.tech.item.pvz.MelonPult;
import mz.tech.item.pvz.PotatoMine;
import mz.tech.item.pvz.Sunflower;
import mz.tech.item.pvz.Sunshine;
import mz.tech.item.resource.Au;
import mz.tech.item.resource.Bauxite;
import mz.tech.item.resource.CO2;
import mz.tech.item.resource.CaO;
import mz.tech.item.resource.CaOH2;
import mz.tech.item.resource.Chalcocite;
import mz.tech.item.resource.Chalcopyrite;
import mz.tech.item.resource.Fe;
import mz.tech.item.resource.Fe2O3;
import mz.tech.item.resource.Fe3O4;
import mz.tech.item.resource.H2;
import mz.tech.item.resource.H2SO4;
import mz.tech.item.resource.Hematite;
import mz.tech.item.resource.KNO3;
import mz.tech.item.resource.Magnetite;
import mz.tech.item.resource.Na2CO3;
import mz.tech.item.resource.NaCl;
import mz.tech.item.resource.S;
import mz.tech.item.resource.SO3;
import mz.tech.item.resource.Saline;
import mz.tech.item.resource.TinOre;
import mz.tech.item.resource.UraniumOre;
import mz.tech.item.sundry.AppleLeaves;
import mz.tech.item.sundry.AppleSapling;
import mz.tech.item.sundry.Bag;
import mz.tech.item.sundry.Baseball;
import mz.tech.item.sundry.BaseballBat;
import mz.tech.item.sundry.BaseballGlove;
import mz.tech.item.sundry.BigBag;
import mz.tech.item.sundry.CNMB;
import mz.tech.item.sundry.EmptySpawnEgg;
import mz.tech.item.sundry.FrangibleGrenade;
import mz.tech.item.sundry.GunpowderBlockItem;
import mz.tech.item.sundry.LuckyBlockItem;
import mz.tech.item.sundry.Medicine;
import mz.tech.item.sundry.MouseTail;
import mz.tech.item.sundry.Pigment;
import mz.tech.item.sundry.PresentBox;
import mz.tech.item.sundry.RedPacket;
import mz.tech.item.sundry.ReverseBucket;
import mz.tech.item.sundry.VillagerNose;
import mz.tech.item.tool.BedrockPickaxe;
import mz.tech.item.tool.HammerDiamond;
import mz.tech.item.tool.HammerGold;
import mz.tech.item.tool.HammerIron;
import mz.tech.item.tool.HammerStone;
import mz.tech.item.tool.Hook;
import mz.tech.item.tool.ObsidianPickaxe;
import mz.tech.item.tool.PortableCraftingTable;
import mz.tech.item.tool.PortableEnderChest;
import mz.tech.item.tool.Wrench;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public abstract class MzTechItem
extends ItemStackBuilder {
    private static List<MzTechItem> items = new ArrayList<MzTechItem>();

    public static void reg(MzTechItem item) {
        MzTechItem.add(item);
    }

    public static void unreg(MzTechItem item) {
        items.remove((Object)item);
    }

    public static List<MzTechItem> getTypes() {
        return items;
    }

    public static void regAll() {
        MzTechItem.reg(new BedrockPickaxe());
        Hammer.init();
        MzTechItem.reg(new HammerDiamond());
        MzTechItem.reg(new HammerGold());
        MzTechItem.reg(new HammerIron());
        MzTechItem.reg(new HammerStone());
        MzTechItem.reg(new Hook());
        MzTechItem.reg(new ObsidianPickaxe());
        MzTechItem.reg(new PortableCraftingTable());
        MzTechItem.reg(new PortableEnderChest());
        MzTechItem.reg(new Wrench());
        MzTechItem.reg(new AppleLeaves());
        MzTechItem.reg(new AppleSapling());
        MzTechItem.reg(new Bag());
        MzTechItem.reg(new Baseball());
        MzTechItem.reg(new BaseballBat());
        MzTechItem.reg(new BaseballGlove());
        MzTechItem.reg(new BigBag());
        MzTechItem.reg(new CNMB());
        MzTechItem.reg(new EmptySpawnEgg());
        MzTechItem.reg(new FrangibleGrenade());
        MzTechItem.reg(new GunpowderBlockItem());
        MzTechItem.reg(new LuckyBlockItem());
        MzTechItem.reg(new Medicine());
        MzTechItem.reg(new MouseTail());
        MzTechItem.reg(new Pigment());
        MzTechItem.reg(new PresentBox());
        MzTechItem.reg(new RedPacket());
        MzTechItem.reg(new ReverseBucket());
        MzTechItem.reg(new VillagerNose());
        MzTechItem.reg(new ChemicalFurnace());
        MzTechItem.reg(new CobblestoneDuplicator());
        MzTechItem.reg(new ConversionTable());
        MzTechItem.reg(new Crucible());
        MzTechItem.reg(new FffffFurnace());
        MzTechItem.reg(new Griddle());
        MzTechItem.reg(new MedicineBox());
        MzTechItem.reg(new Metronome());
        MzTechItem.reg(new MouseTrap());
        MzTechItem.reg(new RailDuplicator());
        MzTechItem.reg(new SmilingCraftingTable());
        MzTechItem.reg(new Trash());
        MzTechItem.reg(new Au());
        MzTechItem.reg(new Bauxite());
        MzTechItem.reg(new CaO());
        MzTechItem.reg(new CaOH2());
        MzTechItem.reg(new Chalcocite());
        MzTechItem.reg(new Chalcopyrite());
        MzTechItem.reg(new CO2());
        MzTechItem.reg(new Fe());
        MzTechItem.reg(new Fe2O3());
        MzTechItem.reg(new Fe3O4());
        MzTechItem.reg(new H2());
        MzTechItem.reg(new H2SO4());
        MzTechItem.reg(new Hematite());
        MzTechItem.reg(new KNO3());
        MzTechItem.reg(new Magnetite());
        MzTechItem.reg(new Na2CO3());
        MzTechItem.reg(new NaCl());
        MzTechItem.reg(new S());
        MzTechItem.reg(new Saline());
        MzTechItem.reg(new SO3());
        MzTechItem.reg(new TinOre());
        MzTechItem.reg(new UraniumOre());
        MzTechItem.reg(new CandyBar());
        MzTechItem.reg(new CandyBlue());
        MzTechItem.reg(new CandyGold());
        MzTechItem.reg(new CandyPink());
        MzTechItem.reg(new CandyRed());
        MzTechItem.reg(new Cheese());
        MzTechItem.reg(new Chips());
        MzTechItem.reg(new Chocolate());
        MzTechItem.reg(new ChocolateGold());
        MzTechItem.reg(new Flour());
        MzTechItem.reg(new GingerbreadMan());
        MzTechItem.reg(new Hamburger());
        MzTechItem.reg(new IceCream());
        MzTechItem.reg(new MoonCake());
        MzTechItem.reg(new MouseTailJuice());
        try {
            MzTechItem.reg(new MzStew());
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        MzTechItem.reg(new PoopBlock());
        MzTechItem.reg(new SheepMilk());
        MzTechItem.reg(new BugJumpBlock());
        MzTechItem.reg(new ChimneyTop());
        MzTechItem.reg(new ChristmasHat());
        MzTechItem.reg(new ChristmasLeavesCandy());
        MzTechItem.reg(new ChristmasPresentBrown());
        MzTechItem.reg(new ChristmasPresentCyan());
        MzTechItem.reg(new ChristmasPresentGray());
        MzTechItem.reg(new ChristmasPresentGreen());
        MzTechItem.reg(new ChristmasPresentMagenta());
        MzTechItem.reg(new ChristmasPresentOrange());
        MzTechItem.reg(new ChristmasPresentPink());
        MzTechItem.reg(new ChristmasPresentPurple());
        MzTechItem.reg(new ChristmasPresentRed());
        MzTechItem.reg(new ChristmasPresentYellow());
        MzTechItem.reg(new ChristmasSnowGlobe());
        MzTechItem.reg(new ChristmasTreeBell());
        MzTechItem.reg(new Doge());
        MzTechItem.reg(new Funny());
        MzTechItem.reg(new IllusionBlock());
        MzTechItem.reg(new MiniBedrock());
        MzTechItem.reg(new MiniCactus());
        MzTechItem.reg(new MiniCauldron());
        MzTechItem.reg(new MiniCocoa());
        MzTechItem.reg(new MiniLava());
        MzTechItem.reg(new MiniMelon());
        MzTechItem.reg(new MiniPeonyPot());
        MzTechItem.reg(new MiniPufferfish());
        MzTechItem.reg(new MiniPumpkin());
        MzTechItem.reg(new MiniPumpkinCarved());
        MzTechItem.reg(new MiniRedstoneLamp());
        MzTechItem.reg(new MiniSlimeBall());
        MzTechItem.reg(new MiniWater());
        MzTechItem.reg(new SkeletonSkull());
        MzTechItem.reg(new MelonPult());
        MzTechItem.reg(new PotatoMine());
        MzTechItem.reg(new Sunflower());
        MzTechItem.reg(new Sunshine());
        MzTechItem.reg(new MachineFrame());
        MzTechItem.reg(new CD());
        MzTechItem.reg(new Record());
        MzTechItem.reg(new CraftGuide());
        MzTechItem.reg(new Test());
        MzTechItem.reg(new DebugStick());
        MzTechItem.reg(new BlockShowBox());
        MzTechItem.reg(new BigLuckyBlockItem());
        MzTechItem.reg(new LuckySword());
        MzTechItem.reg(new LuckyBow());
        CustomItem.loadAll();
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler
            public void onFurnaceSmelt(FurnaceSmeltEvent event) {
                MzTechItem copy = MzTechItem.asMzTechCopy(event.getSource());
                if (copy != null) {
                    event.setCancelled(true);
                }
            }

            @EventHandler
            public void onBlockDamage(BlockDamageEvent event) {
                MzTechItem mzTechCopy = MzTechItem.asMzTechCopy(event.getItemInHand());
                if (mzTechCopy != null) {
                    event.setCancelled(!mzTechCopy.onStartBreakBlock(event));
                }
            }

            @EventHandler
            public void onBlockBreak(BlockBreakEvent event) {
                if (event.isCancelled() || event.getPlayer() == null) {
                    return;
                }
                MzTechItem mzTechCopy = MzTechItem.asMzTechCopy(event.getPlayer().getInventory().getItemInMainHand());
                if (mzTechCopy != null) {
                    event.setCancelled(!mzTechCopy.onBlockBreak(event));
                }
            }

            @EventHandler
            void onPrepareItemCraft(PrepareItemCraftEvent event) {
                if (event.isRepair()) {
                    String[] item = new String[1];
                    Lists.newArrayList((Object[])event.getInventory().getMatrix()).forEach(i -> {
                        if (i != null && i.getType() != Material.AIR) {
                            if (i.getItemMeta().getLocalizedName() == null) {
                                if (item[0] == null) {
                                    stringArray[0] = "";
                                } else if (!item[0].equals("")) {
                                    event.getInventory().setResult(new ItemStack(Material.AIR));
                                    return;
                                }
                            } else {
                                MzTechItem it = MzTechItem.asMzTechCopy(i);
                                if (it != null) {
                                    if (item[0] == null) {
                                        stringArray[0] = it.getTypeName();
                                    } else if (!item[0].equals(it.getTypeName())) {
                                        event.getInventory().setResult(new ItemStack(Material.AIR));
                                    }
                                }
                            }
                        }
                    });
                    if (item[0] != null && !item[0].equals("") && event.getInventory().getResult().getType() != Material.AIR) {
                        ItemStack is = new ItemStack((ItemStack)MzTechItem.get(item[0]));
                        is.setDurability(event.getInventory().getResult().getDurability());
                        event.getInventory().setResult(is);
                    }
                } else {
                    Lists.newArrayList((Object[])event.getInventory().getMatrix()).forEach(i -> {
                        if (i != null && i.getType() != Material.AIR && i.getItemMeta().hasLocalizedName() && (i.getType().getMaxDurability() <= 0 || i.getDurability() <= 0)) {
                            event.getInventory().setResult(new ItemStack(Material.AIR));
                        }
                    });
                }
            }
        }, (Plugin)MzTech.instance);
    }

    @Override
    public MzTechItem clone() {
        return (MzTechItem)super.clone();
    }

    public static MzTechItem get(String name) {
        MzTechItem[] rm = new MzTechItem[1];
        items.forEach(item -> {
            if (item.getTypeName().equals(name)) {
                mzTechItemArray[0] = item;
            }
        });
        return rm[0];
    }

    public static List<MzTechItem> get(Class<?> c2) {
        return Lists.newArrayList((Object[])((MzTechItem[])Lists.newArrayList(items).stream().filter(item -> c2.isAssignableFrom(((Object)item).getClass())).toArray(MzTechItem[]::new)));
    }

    public static MzTechItem getOrDefault(String name, ItemStack def) {
        MzTechItem[] rm = new MzTechItem[]{MzTechItem.asMzTechCopy(def)};
        items.forEach(item -> {
            if (item.getTypeName().equals(name)) {
                mzTechItemArray[0] = item;
            }
        });
        return rm[0];
    }

    public static void add(MzTechItem item) {
        items.add(item);
    }

    public static void forEach(Consumer<? super MzTechItem> consumer) {
        items.forEach(consumer);
    }

    public static <T> void forEach(Class<T> c2, Consumer<T> consumer) {
        items.forEach(item -> {
            if (c2.isAssignableFrom(((Object)item).getClass())) {
                consumer.accept(item);
            }
        });
    }

    public MzTechItem(ItemStack itemStack) {
        super(itemStack);
    }

    public void setName(String name) {
        ItemMeta im = this.getItemMeta();
        im.setLocalizedName(name);
        this.setItemMeta(im);
    }

    public String getName() {
        return this.getItemMeta().getLocalizedName();
    }

    public MzTechItem clone(int num) {
        MzTechItem copy = this.clone();
        copy.setAmount(num);
        return copy;
    }

    public MzTechItem setNum(int num) {
        this.setAmount(num);
        return this;
    }

    public static MzTechItem asMzTechCopy(ItemStack is) {
        if (is == null) {
            return null;
        }
        MzTechItem[] ri = new MzTechItem[1];
        if (is.hasItemMeta() && is.getItemMeta().hasLocalizedName()) {
            items.forEach(item -> {
                if (item.getName().equals(is.getItemMeta().getLocalizedName())) {
                    mzTechItemArray[0] = item.copy(is);
                }
            });
        }
        return ri[0];
    }

    public abstract String getTypeName();

    public abstract MzTechCategory getCategory();

    public MzTechMachine toMachine(Block block) {
        MzTechMachine[] rm = new MzTechMachine[1];
        MzTechMachine.types.forEach(type -> TaskUtil.throwRuntime(() -> {
            MzTechMachine mzTechMachine = (MzTechMachine)MzTech.unsafe.allocateInstance((Class<?>)type);
            mzTechMachine.setBlock(block);
            if (mzTechMachine != null && mzTechMachine.getType() != null && mzTechMachine.getType().equals(this.getTypeName())) {
                mzTechMachineArray[0] = mzTechMachine;
            }
        }));
        return rm[0];
    }

    public boolean onLeftClickAir(Player player, EquipmentSlot hand) {
        return true;
    }

    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        return true;
    }

    public boolean onLeftClickBlock(Player player, EquipmentSlot hand, Block block) {
        return true;
    }

    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        return true;
    }

    public boolean onLeftClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        return true;
    }

    public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        return true;
    }

    public boolean onStartBreakBlock(BlockDamageEvent event) {
        return true;
    }

    public boolean onBlockBreak(BlockBreakEvent event) {
        return true;
    }

    public boolean canConsume(Player player) {
        return true;
    }

    public void onConsume(Player player) {
    }

    public void onDisable() {
    }

    public static void disable() {
        items.forEach(i -> i.onDisable());
    }

    public MzTechItem copy(ItemStack is) {
        MzTechItem clone = (MzTechItem)((Object)ReflectionWrapper.newInstance(ReflectionWrapper.getConstructor(((Object)((Object)this)).getClass(), new Class[0]), new Object[0]));
        clone.setType(is.getType());
        clone.setDurability(is.getDurability());
        clone.setAmount(is.getAmount());
        clone.setData(is.getData());
        clone.setItemMeta(is.getItemMeta());
        return clone;
    }

    public boolean onUseForBrew(BrewEvent event) {
        return false;
    }

    public boolean onEntityPickup(EntityPickupItemEvent event) {
        return true;
    }

    public boolean onFill(PlayerBucketFillEvent event) {
        return false;
    }

    public boolean giveCommand(String[] args) {
        return args.length == 0;
    }

    public List<String> giveCommandTab(String[] args) {
        return new ArrayList<String>();
    }

    public String giveCommandArgs() {
        return null;
    }

    @Override
    public void toBlock(Block block) {
        MzTechMachine machine = this.toMachine(block);
        if (machine != null) {
            super.toBlock(block);
            machine.add();
        }
    }

    public boolean onPlace(Player player, Block block) {
        return true;
    }

    public boolean onLingeringPotionBreak(LingeringPotionBreakEvent e) {
        return true;
    }
}

