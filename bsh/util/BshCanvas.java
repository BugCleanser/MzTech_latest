/*
 * Decompiled with CFR 0.152.
 */
package bsh.util;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.This;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JComponent;

public class BshCanvas
extends JComponent {
    This ths;
    Image imageBuffer;

    public BshCanvas() {
    }

    public BshCanvas(This ths) {
        this.ths = ths;
    }

    @Override
    public void paintComponent(Graphics g) {
        block4: {
            if (this.imageBuffer != null) {
                g.drawImage(this.imageBuffer, 0, 0, this);
            }
            if (this.ths != null) {
                try {
                    this.ths.invokeMethod("paint", new Object[]{g});
                }
                catch (EvalError e) {
                    if (!Interpreter.DEBUG) break block4;
                    Interpreter.debug("BshCanvas: method invocation error:" + e);
                }
            }
        }
    }

    public Graphics getBufferedGraphics() {
        Dimension dim = this.getSize();
        this.imageBuffer = this.createImage(dim.width, dim.height);
        return this.imageBuffer.getGraphics();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
        super.setBounds(x, y, width, height);
    }
}

