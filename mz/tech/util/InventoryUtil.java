/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.util;

import java.util.HashMap;
import java.util.List;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryUtil
implements Inventory {
    public InventoryHolder holder;
    private List<HumanEntity> viewers;

    public InventoryUtil(InventoryHolder holder) {
        this.holder = holder;
    }

    public HashMap<Integer, ItemStack> addItem(ItemStack ... iss) throws IllegalArgumentException {
        if (iss == null) {
            throw new IllegalArgumentException("items must not  be null");
        }
        HashMap<Integer, ItemStack> rm = new HashMap<Integer, ItemStack>();
        int j = 0;
        while (j < iss.length) {
            ItemStack is = iss[j];
            if (is == null) {
                throw new IllegalArgumentException("Any item stack in items must not  be null");
            }
            if (!ItemStackBuilder.isEmpty(is)) {
                int i = 0;
                while (i < this.getSize()) {
                    if (is.isSimilar(this.getItem(i))) {
                        ItemStack tis;
                        int t = is.getType().getMaxStackSize() - this.getItem(i).getAmount();
                        if (t < is.getAmount()) {
                            is.setAmount(is.getAmount() - t);
                            tis = this.getItem(i);
                            tis.setAmount(is.getType().getMaxStackSize());
                            this.setItem(i, tis);
                        } else {
                            tis = this.getItem(i);
                            tis.setAmount(is.getAmount());
                            this.setItem(i, tis);
                            is.setAmount(0);
                            break;
                        }
                    }
                    ++i;
                }
                if (!ItemStackBuilder.isEmpty(is)) {
                    i = 0;
                    while (i < this.getSize()) {
                        if (ItemStackBuilder.isEmpty(this.getItem(i))) {
                            this.setItem(i, is);
                            is.setAmount(0);
                            break;
                        }
                        ++i;
                    }
                }
                if (!ItemStackBuilder.isEmpty(is)) {
                    rm.put(j, is);
                }
            }
            ++j;
        }
        return rm;
    }

    @Deprecated
    public HashMap<Integer, ? extends ItemStack> all(int id) {
        HashMap<Integer, ItemStack> rm = new HashMap<Integer, ItemStack>();
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).getTypeId() == id) {
                rm.put(i, this.getItem(i));
            }
            ++i;
        }
        return rm;
    }

    public HashMap<Integer, ? extends ItemStack> all(Material type) throws IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException("type must not  be null");
        }
        HashMap<Integer, ItemStack> rm = new HashMap<Integer, ItemStack>();
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).getType() == type) {
                rm.put(i, this.getItem(i));
            }
            ++i;
        }
        return rm;
    }

    public HashMap<Integer, ? extends ItemStack> all(ItemStack ex) {
        HashMap<Integer, ItemStack> rm = new HashMap<Integer, ItemStack>();
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).equals((Object)ex)) {
                rm.put(i, this.getItem(i));
            }
            ++i;
        }
        return rm;
    }

    public void clear() {
        int i = 0;
        while (i < this.getSize()) {
            this.clear(i);
            ++i;
        }
    }

    public void clear(int slot) {
        this.setItem(slot, ItemStackBuilder.air);
    }

    @Deprecated
    public boolean contains(int id) {
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).getTypeId() == id) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public boolean contains(Material type) throws IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException("type must not  be null");
        }
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).getType() == type) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public boolean contains(ItemStack is) {
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).equals((Object)is)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    @Deprecated
    public boolean contains(int id, int num) {
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).getTypeId() == id && this.getItem(i).getAmount() == num) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public boolean contains(Material type, int num) throws IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException("type must not  be null");
        }
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).getType() == type && this.getItem(i).getAmount() == num) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public boolean contains(ItemStack is, int num) {
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).isSimilar(is) && this.getItem(i).getAmount() == num) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public boolean containsAtLeast(ItemStack is, int num) {
        if (is == null) {
            return false;
        }
        if (num < 1) {
            return true;
        }
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).isSimilar(is) && this.getItem(i).getAmount() <= num) {
                return true;
            }
            ++i;
        }
        return false;
    }

    @Deprecated
    public int first(int id) {
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).getTypeId() == id) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public int first(Material type) throws IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException("type must not  be null");
        }
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).getType() == type) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public int first(ItemStack is) {
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).equals((Object)is)) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public int firstEmpty() {
        int i = 0;
        while (i < this.getSize()) {
            if (ItemStackBuilder.isEmpty(this.getItem(i))) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public InventoryHolder getHolder() {
        return this.holder;
    }

    public int getMaxStackSize() {
        return 64;
    }

    public ItemStack[] getStorageContents() {
        return this.getContents();
    }

    public InventoryType getType() {
        return InventoryType.CHEST;
    }

    public List<HumanEntity> getViewers() {
        return this.viewers;
    }

    @Deprecated
    public void remove(int id) {
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).getTypeId() == id) {
                this.clear(i);
            }
            ++i;
        }
    }

    public void remove(Material type) throws IllegalArgumentException {
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).getType() == type) {
                this.clear(i);
            }
            ++i;
        }
    }

    public void remove(ItemStack is) {
        int i = 0;
        while (i < this.getSize()) {
            if (this.getItem(i) != null && this.getItem(i).equals((Object)is)) {
                this.clear(i);
            }
            ++i;
        }
    }

    public HashMap<Integer, ItemStack> removeItem(ItemStack ... iss) throws IllegalArgumentException {
        if (iss == null) {
            throw new IllegalArgumentException("items must not be null");
        }
        HashMap<Integer, ItemStack> rm = new HashMap<Integer, ItemStack>();
        int j = 0;
        while (j < iss.length) {
            ItemStack is = iss[j];
            int i = 0;
            while (i < this.getSize()) {
                if (this.getItem(i) != null && this.getItem(i).equals((Object)is)) {
                    this.clear(i);
                }
                ++i;
            }
            if (i == this.getSize()) {
                rm.put(j, is);
            }
            ++j;
        }
        return rm;
    }

    public void setStorageContents(ItemStack[] iss) throws IllegalArgumentException {
        this.setContents(iss);
    }
}

