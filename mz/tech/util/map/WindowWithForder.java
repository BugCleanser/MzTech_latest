/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.util.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import mz.tech.util.map.DC;
import mz.tech.util.map.Window;

public class WindowWithForder
extends Window {
    private Image icon = new ImageIcon(WindowWithForder.class.getResource("window.png")).getImage();

    public void setIcon(Image icon) {
        this.icon = icon;
        this.invalidate();
    }

    public WindowWithForder(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void create() {
        this.createWindow(new Window(2, 12, this.width - 4, this.height - 14){

            @Override
            public void close() {
                super.close();
                this.father.close();
            }

            @Override
            public boolean isTop() {
                return this.father.isTop();
            }

            @Override
            public void setTop() {
                this.father.setTop();
            }
        });
        this.getDC().setColor(new Color(255, 255, 255));
        this.getDC().fillRect(0, 0, this.width - 4, this.height - 14);
    }

    public Window getInternal() {
        return (Window)this.childs.get(0);
    }

    @Override
    public DC getDC() {
        return this.getInternal().getDC();
    }

    @Override
    public void redraw() {
        if (this.isTop()) {
            super.getDC().setColor(new Color(102, 203, 234));
        } else {
            super.getDC().setColor(new Color(100, 160, 240));
        }
        super.getDC().fillRect(0, 0, this.width, this.height);
        super.getDC().drawImage(this.icon, 2, 2, null);
        super.getDC().setColor(new Color(0, 0, 0));
        super.getDC().setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", 0, 11));
        super.getDC().drawString(this.getTitle(), 14, 10);
    }
}

