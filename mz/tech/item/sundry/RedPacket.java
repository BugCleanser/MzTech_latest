/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 */
package mz.tech.item.sundry;

import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.category.MzTechCategory;
import mz.tech.category.SundryCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.RedPacketInventory;
import mz.tech.item.baseMachine.ConversionTable;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public class RedPacket
extends MzTechItem {
    static {
        MzTech.loadClassUsingNMS("mz.tech.item.RedPacketInventory");
    }

    public RedPacket() {
        super(new ItemStackBuilder(Material.LEATHER).setLocName("\u00a7c\u00a7l\u7ea2\u5305").setLoreList("\u00a77\u00a7m\u7269\u54c1\u5f62\u5f0f\u7684\u8f6c\u8d26").build());
        this.setMoney(0.0f);
    }

    @Override
    public String getTypeName() {
        return "\u7ea2\u5305";
    }

    @Override
    public MzTechCategory getCategory() {
        return SundryCategory.instance;
    }

    public void setMoney(float m) {
        this.setInfoToLore("\u91d1\u989d: ", "" + m);
    }

    public float getMoney() {
        return Float.parseFloat(this.infoFromLore("\u91d1\u989d: "));
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        return this.onRightClickAir(player, hand);
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        NBT nbt = new NBT(this);
        if (nbt.hasKey("tag") && (nbt = nbt.getChild("tag")).hasKey("value")) {
            float f = nbt.getFloat("value").floatValue();
            MzTech.sendMessage((CommandSender)player, "\u00a7a\u4f60\u9886\u53d6\u4e86\u7ea2\u5305\uffe5" + f);
            ConversionTable.econ.depositPlayer((OfflinePlayer)player, (double)f);
            this.setAmount(0);
            return false;
        }
        new RedPacketInventory((RedPacket)this.clone()).open(player);
        this.setAmount(0);
        return false;
    }
}

