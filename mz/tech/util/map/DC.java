/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.map.MapPalette
 */
package mz.tech.util.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import org.bukkit.map.MapPalette;

public class DC
implements AutoCloseable {
    private final BufferedImage image;
    private Graphics graphics;

    public DC(BufferedImage img) {
        this.image = img;
        this.graphics = img.getGraphics();
    }

    public Graphics getDelegate() {
        return this.graphics;
    }

    public void renew() {
        try {
            this.graphics.dispose();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        this.graphics = this.image.getGraphics();
    }

    public int getRawPixel(int x, int y) {
        return this.image.getRGB(x, y);
    }

    public Color getPixel(int x, int y) {
        return MapPalette.getColor((byte)((byte)this.getRawPixel(x, y)));
    }

    public void setRawPixel(int x, int y, int color) {
        this.image.setRGB(x, y, color);
    }

    public void setPixel(int x, int y, Color color) {
        this.setRawPixel(x, y, MapPalette.matchColor((Color)color));
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public Graphics create() {
        return this.graphics.create();
    }

    public Graphics create(int x, int y, int width, int height) {
        return this.graphics.create(x, y, width, height);
    }

    public void translate(int x, int y) {
        this.graphics.translate(x, y);
    }

    public Color getColor() {
        return this.graphics.getColor();
    }

    public void setColor(Color c2) {
        this.graphics.setColor(new Color(MapPalette.matchColor((Color)c2)));
    }

    public void setPaintMode() {
        this.graphics.setPaintMode();
    }

    public void setXORMode(Color c1) {
        this.graphics.setXORMode(c1);
    }

    public Font getFont() {
        return this.graphics.getFont();
    }

    public void setFont(Font font) {
        this.graphics.setFont(font);
    }

    public FontMetrics getFontMetrics() {
        return this.graphics.getFontMetrics();
    }

    public FontMetrics getFontMetrics(Font f) {
        return this.graphics.getFontMetrics(f);
    }

    public Rectangle getClipBounds() {
        return this.graphics.getClipBounds();
    }

    public void clipRect(int x, int y, int width, int height) {
        this.graphics.clipRect(x, y, width, height);
    }

    public void setClip(int x, int y, int width, int height) {
        this.graphics.setClip(x, y, width, height);
    }

    public Shape getClip() {
        return this.graphics.getClip();
    }

    public void setClip(Shape clip) {
        this.graphics.setClip(clip);
    }

    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        this.graphics.copyArea(x, y, width, height, dx, dy);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        this.graphics.drawLine(x1, y1, x2, y2);
    }

    public void fillRect(int x, int y, int width, int height) {
        this.graphics.fillRect(x, y, width, height);
    }

    public void drawRect(int x, int y, int width, int height) {
        this.graphics.drawRect(x, y, width, height);
    }

    public void clearRect(int x, int y, int width, int height) {
        this.graphics.clearRect(x, y, width, height);
    }

    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        this.graphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        this.graphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void draw3DRect(int x, int y, int width, int height, boolean raised) {
        this.graphics.draw3DRect(x, y, width, height, raised);
    }

    public void fill3DRect(int x, int y, int width, int height, boolean raised) {
        this.graphics.fill3DRect(x, y, width, height, raised);
    }

    public void drawOval(int x, int y, int width, int height) {
        this.graphics.drawOval(x, y, width, height);
    }

    public void fillOval(int x, int y, int width, int height) {
        this.graphics.fillOval(x, y, width, height);
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        this.graphics.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        this.graphics.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        this.graphics.drawPolyline(xPoints, yPoints, nPoints);
    }

    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        this.graphics.drawPolygon(xPoints, yPoints, nPoints);
    }

    public void drawPolygon(Polygon p) {
        this.graphics.drawPolygon(p);
    }

    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        this.graphics.fillPolygon(xPoints, yPoints, nPoints);
    }

    public void fillPolygon(Polygon p) {
        this.graphics.fillPolygon(p);
    }

    public void drawString(String str, int x, int y) {
        this.graphics.drawString(str, x, y);
    }

    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        this.graphics.drawString(iterator, x, y);
    }

    public void drawChars(char[] data, int offset, int length, int x, int y) {
        this.graphics.drawChars(data, offset, length, x, y);
    }

    public void drawBytes(byte[] data, int offset, int length, int x, int y) {
        this.graphics.drawBytes(data, offset, length, x, y);
    }

    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        if (img == null) {
            return false;
        }
        BufferedImage buf = new BufferedImage(img.getWidth(observer), img.getHeight(observer), 2);
        Graphics g = buf.getGraphics();
        g.drawImage(img, 0, 0, observer);
        g.dispose();
        int i = 0;
        while (i < img.getWidth(observer)) {
            int j = 0;
            while (j < img.getHeight(observer)) {
                this.setPixel(x + i, y + j, new Color(buf.getRGB(i, j)));
                ++j;
            }
            ++i;
        }
        return true;
    }

    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return this.graphics.drawImage(img, x, y, width, height, observer);
    }

    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return this.graphics.drawImage(img, x, y, bgcolor, observer);
    }

    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return this.graphics.drawImage(img, x, y, width, height, bgcolor, observer);
    }

    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        return this.graphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
    }

    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
        return this.graphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
    }

    public void dispose() {
        this.graphics.dispose();
    }

    public Rectangle getClipRect() {
        return this.graphics.getClipRect();
    }

    public boolean hitClip(int x, int y, int width, int height) {
        return this.graphics.hitClip(x, y, width, height);
    }

    public Rectangle getClipBounds(Rectangle r) {
        return this.graphics.getClipBounds(r);
    }

    @Override
    public void close() throws Exception {
        this.graphics.dispose();
    }
}

