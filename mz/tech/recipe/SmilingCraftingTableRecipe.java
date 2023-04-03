/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nonnull
 *  org.bukkit.Material
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package mz.tech.recipe;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import javax.annotation.Nonnull;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SmilingCraftingTableRecipe
extends MzTechRecipe
implements Listener {
    ItemStack result;
    List<RawItem> raws;
    public BiPredicate<List<ItemStack>, List<ItemStack>> filter;
    public static ItemStack machine = new ItemStack(Material.DROPPER);
    public static String smilingCraftingTable = null;

    static {
        ItemMeta im = machine.getItemMeta();
        im.setLocalizedName("\u00a76\u5fae\u7b11\u7684\u5408\u6210\u53f0");
        im.setLore((List)Lists.newArrayList((Object[])new String[]{"\u00a77\u5728\u00a76\u5fae\u7b11\u7684\u5408\u6210\u53f0\u00a77\u4e2d\u5408\u6210"}));
        machine.setItemMeta(im);
    }

    public SmilingCraftingTableRecipe(BiPredicate<List<ItemStack>, List<ItemStack>> filter, ItemStack result, Object ... raws) {
        this.result = result;
        this.raws = new ArrayList<RawItem>();
        Lists.newArrayList((Object[])raws).forEach(raw -> {
            if (raw == null || raw instanceof RawItem) {
                this.raws.add((RawItem)raw);
            } else if (raw instanceof ItemStack) {
                this.raws.add(new RawItem((ItemStack)raw));
            } else if (raw instanceof Material) {
                this.raws.add(new RawItem(new ItemStack((Material)raw)));
            } else if (raw instanceof Integer) {
                this.raws.add(this.raws.get((Integer)raw));
            } else {
                throw new RuntimeException("\u9519\u8bef\u7684\u5fae\u7b11\u5408\u6210\u53f0\u539f\u6599");
            }
        });
        this.filter = filter;
    }

    public SmilingCraftingTableRecipe(boolean shaped, ItemStack result, Object ... raws) {
        this((BiPredicate<List<ItemStack>, List<ItemStack>>)null, result, raws);
        this.filter = shaped ? (usedRaws, craftResult) -> {
            int i = 0;
            while (i < 9) {
                if ((this.raws.size() <= i || this.raws.get(i) == null) && usedRaws.get(i) != null && ((ItemStack)usedRaws.get(i)).getType() != Material.AIR || this.raws.size() > i && this.raws.get(i) != null && this.raws.get(i).matching((ItemStack)usedRaws.get(i)) == null) {
                    return false;
                }
                ++i;
            }
            i = 0;
            while (i < 9) {
                if (usedRaws.get(i) != null && ((ItemStack)usedRaws.get(i)).getType() != Material.AIR) {
                    if (((ItemStack)usedRaws.get(i)).getAmount() > 1) {
                        ItemStack t = this.raws.get(i).get(this.raws.get(i).matching((ItemStack)usedRaws.get(i)));
                        if (t != null) {
                            craftResult.add(t);
                        }
                        ((ItemStack)usedRaws.get(i)).setAmount(((ItemStack)usedRaws.get(i)).getAmount() - this.raws.get(i).matching((ItemStack)usedRaws.get(i)).getAmount());
                    } else {
                        usedRaws.set(i, this.raws.get(i).get(this.raws.get(i).matching((ItemStack)usedRaws.get(i))));
                    }
                }
                ++i;
            }
            craftResult.add(this.result);
            return true;
        } : (usedRaws, craftResult) -> {
            ArrayList copy = Lists.newArrayList(this.raws);
            boolean[] tt = new boolean[]{true};
            usedRaws.forEach(usedRaw -> {
                if (usedRaw != null && usedRaw.getType() != Material.AIR && usedRaw.getAmount() != 0) {
                    boolean t = true;
                    int[] i = new int[1];
                    while (i[0] < copy.size()) {
                        if (((RawItem)copy.get(i[0])).matching((ItemStack)usedRaw) != null) {
                            copy.remove(i[0]);
                            i[0] = i[0] - 1;
                            t = false;
                        }
                        i[0] = i[0] + 1;
                    }
                    if (t) {
                        blArray[0] = false;
                    }
                }
            });
            if (tt[0] && copy.isEmpty()) {
                usedRaws.forEach(usedRaw -> {
                    int[] i = new int[1];
                    while (i[0] < this.raws.size()) {
                        if (this.raws.get(i[0]).matching((ItemStack)usedRaw) != null) {
                            usedRaw.setAmount(usedRaw.getAmount() - this.raws.get(i[0]).matching((ItemStack)usedRaw).getAmount());
                            ItemStack temp = this.raws.get(i[0]).get(this.raws.get(i[0]).matching((ItemStack)usedRaw));
                            if (temp != null) {
                                if (usedRaw.getType() == Material.AIR || usedRaw.getAmount() == 0) {
                                    usedRaw.setAmount(temp.getAmount());
                                    usedRaw.setData(temp.getData());
                                    usedRaw.setDurability(temp.getDurability());
                                    usedRaw.setItemMeta(temp.getItemMeta());
                                    usedRaw.setType(temp.getType());
                                } else {
                                    craftResult.add(temp);
                                }
                            }
                        }
                        i[0] = i[0] + 1;
                    }
                });
                craftResult.add(this.result);
                return true;
            }
            return false;
        };
    }

    public SmilingCraftingTableRecipe(ItemStack result, Object ... raws) {
        this(true, result, raws);
    }

    @Override
    public List<RawItem> getRaws() {
        return this.raws;
    }

    @Override
    public List<ItemStack> getResults() {
        return Lists.newArrayList((Object[])new ItemStack[]{this.result});
    }

    @Override
    public List<ItemStack> getMachines() {
        return Lists.newArrayList((Object[])new ItemStack[]{machine});
    }

    public boolean filt(@Nonnull List<ItemStack> raws, @Nonnull List<ItemStack> results) {
        if (this.filter == null) {
            return false;
        }
        return this.filter.test(raws, results);
    }
}

