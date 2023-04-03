/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  com.comphenix.protocol.wrappers.BlockPosition
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.Jukebox
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionType
 */
package mz.tech.item;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.google.common.collect.Lists;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import mz.tech.MzTech;
import mz.tech.category.MzTechCategory;
import mz.tech.category.RecordCategory;
import mz.tech.item.CD;
import mz.tech.item.MzTechItem;
import mz.tech.item.decoration.ChristmasTreeBell;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.MidiUtil;
import mz.tech.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionType;

public class Record
extends MzTechItem {
    public static Map<String, Supplier<InputStream>> midis = new HashMap<String, Supplier<InputStream>>();
    public static Record allFallsDown = new Record().setMaterial(ItemStackBuilder.record_mellohi.getType()).setAuthorAndName("Alan Olav Walker", "All Falls Down").addRecipe((Object)new ItemStackBuilder("thin_glass", 0, "glass_pane", 64)).setDefaultMidi();
    public static Record alone = new Record().setMaterial(ItemStackBuilder.record_strad.getType()).setAuthorAndName("Alan Olav Walker", "Alone").setDefaultMidi();
    public static Record astronomia = new Record().setMaterial(ItemStackBuilder.record_stal.getType()).setAuthorAndName("Tony Igy", "Astronomia").addRecipe(new ItemStack(Material.CHEST, 64)).setDefaultMidi();
    public static Record badApple = new Record().setMaterial(ItemStackBuilder.record_chirp.getType()).setAuthorAndName("\u4e0a\u6d77\u30a2\u30ea\u30b9\u5e7b\u4e50\u56e2\u3001Masayoshi Minoshima/ZUN", "Bad Apple").addRecipe(new ItemStack(Material.APPLE, 64)).setDefaultMidi();
    public static Record closer = new Record().setMaterial(ItemStackBuilder.record_mellohi.getType()).setAuthorAndName("\u5b89\u5fb7\u9c81\u00b7\u5854\u683c\u7279", "Closer").addRecipe((Object)new ItemStackBuilder("PISTON_STICKY_BASE", 0, "sticky_piston", 64)).setDefaultMidi();
    public static Record fade = new Record().setMaterial(ItemStackBuilder.record_ward.getType()).setAuthorAndName("Alan Olav Walker", "Fade").addRecipe(new ItemStack(Material.IRON_NUGGET, 64), new RawItem(ItemStackBuilder.clone(ItemStackBuilder.leaves)).setCount(64)).setDefaultMidi();
    public static Record hop = new Record().setMaterial(ItemStackBuilder.record_chirp.getType()).setAuthorAndName("Azis", "Hop").addRecipe(new ItemStackBuilder(ItemStackBuilder.yellowDye).setCount(64).build(), new ItemStack(Material.MILK_BUCKET)).setDefaultMidi();
    public static Record jingleBells = new Record().setMaterial(ItemStackBuilder.record_13.getType()).setAuthorAndName("Shout", "Jingle Bells").addRecipe((Object)new ChristmasTreeBell()).setDefaultMidi();
    public static Record MEGALOVANIA = new Record().setMaterial(ItemStackBuilder.record_11.getType()).setAuthorAndName("TobyFox", "MEGALOVANIA").addRecipe(new ItemStack(Material.LEVER, 64), new ItemStack(Material.TRIPWIRE_HOOK, 64)).setDefaultMidi();
    public static Record nevada = new Record().setMaterial(ItemStackBuilder.record_blocks.getType()).setAuthorAndName("Vicetone", "Nevada").addRecipe(new ItemStack(Material.SHULKER_SHELL, 64), (Object)new ItemStackBuilder("redstone_torch_on", 0, "redstone_torch", 64)).setDefaultMidi();
    public static Record seeYouAgain = new Record().setMaterial(ItemStackBuilder.record_wait.getType()).setAuthorAndName("\u67e5\u7406\u00b7\u666e\u65af", "See you again").addRecipe(new ItemStackBuilder(Material.POTION).setPotionType(PotionType.SPEED).build(), new ItemStack(Material.POWERED_RAIL, 64)).setDefaultMidi();
    public static Record spectre = new Record().setMaterial(ItemStackBuilder.record_chirp.getType()).setAuthorAndName("Alan Olav Walker", "Spectre").addRecipe(new ItemStack(Material.GHAST_TEAR, 64)).setDefaultMidi();
    public static Record theTruthThatYouLeave = new Record().setMaterial(ItemStackBuilder.record_mall.getType()).setAuthorAndName("Pianoboy", "The truth that you leave").addRecipe((Object)new ItemStackBuilder("double_plant", 0, "sunflower", 64), new ItemStack(Material.GHAST_TEAR, 64)).setDefaultMidi();
    public static Record unravel = new Record().setMaterial(ItemStackBuilder.record_blocks.getType()).setAuthorAndName("Animenz", "Unravel").addRecipe(new ItemStack(Material.ROTTEN_FLESH, 64)).setDefaultMidi();
    public static Record \u75c5\u540d\u4e3a\u7231 = new Record().setMaterial(ItemStackBuilder.record_chirp.getType()).setAuthorAndName("Neru\u3001z'5", "\u75c5\u540d\u4e3a\u7231").addRecipe((Object)new ItemStackBuilder("totem", 0, "totem_of_undying", 1)).setDefaultMidi();
    public static Record \u6253\u4e0a\u82b1\u706b = new Record().setMaterial(ItemStackBuilder.record_far.getType()).setAuthorAndName("\u7c73\u6d25\u7384\u5e08", "\u6253\u4e0a\u82b1\u706b").addRecipe(new ItemStackBuilder("firework", 0, "firework_rocket", 1).setPower(1).setCount(64).build()).setDefaultMidi();
    public static Record \u7a3b\u9999 = new Record().setMaterial(ItemStackBuilder.record_13.getType()).setAuthorAndName("\u5468\u6770\u4f26", "\u7a3b\u9999").addRecipe(new ItemStack(Material.WHEAT, 64)).setDefaultMidi();
    public static Record \u604b\u7231\u30b5\u30fc\u30ad\u30e5\u30ec\u30fc\u30b7\u30e7\u30f3 = new Record().setMaterial(ItemStackBuilder.record_chirp.getType()).setAuthorAndName("\u795e\u524d\u6653", "\u604b\u7231\u30b5\u30fc\u30ad\u30e5\u30ec\u30fc\u30b7\u30e7\u30f3").addRecipe(new ItemStack(Material.RED_GLAZED_TERRACOTTA, 64)).setDefaultMidi();
    public static Record \u5e73\u51e1\u4e4b\u8def = new Record().setMaterial(ItemStackBuilder.record_strad.getType()).setAuthorAndName("\u6734\u6811", "\u5e73\u51e1\u4e4b\u8def").addRecipe((Object)new ItemStackBuilder("rail", 0, "rails", 64)).setDefaultMidi();
    public static Record \u8d77\u98ce\u4e86 = new Record().setMaterial(ItemStackBuilder.record_far.getType()).setAuthorAndName("\u4e70\u8fa3\u6912\u4e5f\u7528\u5238", "\u8d77\u98ce\u4e86").addRecipe(new ItemStackBuilder(Material.FEATHER).setCount(64).build(), new RawItem(ItemStackBuilder.clone(ItemStackBuilder.leaves)).setCount(64)).setDefaultMidi();
    public static Record \u5a01\u98ce\u5802\u3005 = new Record().setMaterial(ItemStackBuilder.record_13.getType()).setAuthorAndName("\u6885\u3068\u3089", "\u5a01\u98ce\u5802\u3005").addRecipe(new ItemStackBuilder(ItemStackBuilder.yellowDye).setCount(64).build()).setDefaultMidi();
    static boolean putting;

    static {
        new SmilingCraftingTableRecipe(alone, new Object[]{new CD()}).reg("\u5531\u7247Alone");
        putting = false;
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler(priority=EventPriority.MONITOR)
            void onBlockBreak(BlockBreakEvent event) {
                if (event.isCancelled()) {
                    return;
                }
                if (event.getBlock().getType() == Material.JUKEBOX) {
                    MidiUtil.stop(event.getBlock().getLocation());
                }
            }

            @EventHandler(priority=EventPriority.MONITOR)
            void onPlayerInteract(PlayerInteractEvent event) {
                if (event.isCancelled() || putting) {
                    putting = false;
                    return;
                }
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK && new PlayerUtil(event.getPlayer()).canOpen()) {
                    this.onBlockBreak(new BlockBreakEvent(event.getClickedBlock(), event.getPlayer()));
                }
            }
        }, (Plugin)MzTech.instance);
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.NORMAL, new PacketType[]{PacketType.Play.Server.WORLD_EVENT}){

            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.WORLD_EVENT && MidiUtil.isPlaying(((BlockPosition)event.getPacket().getBlockPositionModifier().read(0)).toLocation(event.getPlayer().getWorld()))) {
                    event.setCancelled(true);
                }
            }
        });
    }

    public Record() {
        super(new ItemStackBuilder(ItemStackBuilder.record_strad).setLocName("\u00a7b\u97f3\u4e50\u5531\u7247").setHideFlags(ItemFlag.HIDE_POTION_EFFECTS).build());
    }

    public Record setAuthorAndName(String author, String name) {
        return this.setAuthorAndName(String.valueOf(author) + " - " + name);
    }

    public Record setAuthorAndName(String authorAndName) {
        super.setLoreList("\u00a77" + authorAndName);
        return this;
    }

    @Override
    public boolean giveCommand(String[] args) {
        String authorAndName;
        if (args.length > 0 && (authorAndName = MzTech.MergeStrings(0, 1, args)[0]).contains(" - ")) {
            this.setAuthorAndName(authorAndName);
            return true;
        }
        return false;
    }

    @Override
    public String giveCommandArgs() {
        return "<\u4f5c\u8005> - <\u540d\u79f0>";
    }

    @Override
    public MzTechCategory getCategory() {
        return RecordCategory.instance;
    }

    public Record setMidi(Supplier<InputStream> midi) {
        midis.put(this.getAuthorAndName(), midi);
        return this;
    }

    public Record setDefaultMidi() {
        String n = this.getMidiName();
        return this.setMidi(() -> MzTech.class.getClassLoader().getResourceAsStream("mz/tech/resource/midi/" + n + ".mid"));
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, final Block block) {
        if (block.getType() == Material.JUKEBOX && !((Jukebox)block.getState()).isPlaying()) {
            putting = true;
            try {
                InputStream midi = this.getMidi();
                MidiUtil.playMidi(midi, 1.0f, (Object)block.getLocation(), new MidiUtil.PlaySoundAble(){

                    @Override
                    public void playSound(Sound var1, float var2, float var3) {
                        block.getLocation().getWorld().playSound(block.getLocation(), var1, var2, var3);
                    }
                });
            }
            catch (NullPointerException e) {
                MzTech.sendMessage((CommandSender)player, "\u00a74\u9519\u8bef\u7684\u5531\u7247\uff0c\u6ca1\u6709\u6307\u5b9a\u5531\u7247\u7684\u97f3\u9891\u6587\u4ef6");
            }
        }
        return true;
    }

    @Override
    public List<String> giveCommandTab(String[] args) {
        return MzTech.tabComplement(Lists.newArrayList(midis.keySet()), MzTech.MergeStrings(0, 1, args)[0].toLowerCase());
    }

    public InputStream getMidi() {
        return midis.get(this.getAuthorAndName()).get();
    }

    public String getAuthor() {
        return this.getLore(0).split(" - ")[0].substring(2);
    }

    public String getAuthorAndName() {
        return this.getLore(0).substring(2);
    }

    public String getMidiName() {
        return this.getLore(0).split(" - ")[1];
    }

    @Override
    public String getTypeName() {
        return "\u5531\u7247";
    }

    @Override
    public Record setMaterial(Material m) {
        super.setMaterial(m);
        return this;
    }

    public Record addRecipe(Object a) {
        return this.addRecipe(a, a);
    }

    public Record addRecipe(Object a, Object b) {
        new SmilingCraftingTableRecipe(this, new Object[]{a, b, 0, 1, new CD(), 1, 0, 1, 0}).reg("\u5531\u7247" + this.getMidiName());
        return this;
    }
}

