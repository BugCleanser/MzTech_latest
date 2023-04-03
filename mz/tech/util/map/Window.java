/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package mz.tech.util.map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import mz.tech.util.map.DC;
import mz.tech.util.map.MapUtil;
import org.bukkit.entity.Player;

public class Window {
    public Window father;
    public ArrayList<Window> childs = new ArrayList();
    private String title = "";
    public int x;
    public int y;
    public int width;
    public int height;
    public DC DC;
    private boolean invalidated = true;

    public boolean isInvalidated() {
        if (this.invalidated) {
            return true;
        }
        for (Window c2 : this.childs) {
            if (!c2.isInvalidated()) continue;
            return true;
        }
        return false;
    }

    public void invalidate() {
        this.invalidated = true;
    }

    public Window(int x, int y, int width, int height) {
        this.move(x, y);
        this.setSize(width, height);
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.DC = new DC(new BufferedImage(width, height, 1));
        this.invalidate();
    }

    public boolean isTop() {
        return this.father == null ? true : this.father.childs.get(this.father.childs.size() - 1) == this;
    }

    public void setTop() {
        if (this.father != null) {
            this.father.childs.remove(this);
            this.father.childs.add(this);
        }
        this.invalidate();
    }

    public boolean isControlled() {
        return this.getMap().controlledWindow == this;
    }

    public void setControlled() {
        this.getMap().controlledWindow = this;
    }

    public DC getDC() {
        return this.DC;
    }

    public void createWindow(Window win) {
        this.childs.add(win);
        win.father = this;
        if (this.isControlled()) {
            win.setControlled();
        }
        win.create();
        this.invalidate();
    }

    public void create() {
    }

    public void view() {
        if (this.isInvalidated()) {
            this.redraw();
            this.invalidated = false;
        }
        this.childs.forEach(c2 -> c2.view());
        if (this.father != null) {
            int i = 0;
            while (i < this.width) {
                int j = 0;
                while (j < this.height) {
                    this.father.DC.setRawPixel(i + this.x, j + this.y, this.DC.getRawPixel(i, j));
                    ++j;
                }
                ++i;
            }
        }
    }

    public void redraw() {
    }

    public MapUtil getMap() {
        Window f = this.father;
        while (f != null) {
            if (f instanceof MapUtil) {
                return (MapUtil)f;
            }
            f = f.father;
        }
        return null;
    }

    public String getTitle() {
        return this.title;
    }

    public Window setTitle(String title) {
        this.title = title;
        this.invalidate();
        return this;
    }

    public void close() {
        this.childs.forEach(c2 -> c2.close());
        if (this.isControlled()) {
            this.father.setControlled();
        }
        this.father.childs.remove(this);
    }

    public void view(Player player, int id) {
        this.getMap().view(player, id);
    }
}

