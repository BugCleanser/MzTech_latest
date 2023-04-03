/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import mz.tech.machine.MzCable;

public class MzNetwork
extends ArrayList<MzCable> {
    private static final long serialVersionUID = 4381040687230851726L;
    public static List<MzNetwork> networks = new ArrayList<MzNetwork>();
    public int version = 1;
    public int energy;

    public MzNetwork(MzCable ... machines) {
        super(Lists.newArrayList((Object[])machines));
    }

    public void add() {
        networks.add(this);
    }

    public void remove() {
        networks.remove(this);
    }

    @Override
    public boolean add(MzCable e) {
        return super.add(e);
    }
}

