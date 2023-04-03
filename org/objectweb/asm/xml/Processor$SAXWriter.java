/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.xml;

import java.io.IOException;
import java.io.Writer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

final class Processor$SAXWriter
extends DefaultHandler
implements LexicalHandler {
    private static final char[] OFF;
    private Writer w;
    private final boolean optimizeEmptyElements;
    private boolean openElement = false;
    private int ident = 0;

    Processor$SAXWriter(Writer writer, boolean bl) {
        this.w = writer;
        this.optimizeEmptyElements = bl;
    }

    public final void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        try {
            this.closeElement();
            this.writeIdent();
            this.w.write('<' + string3);
            if (attributes != null && attributes.getLength() > 0) {
                this.writeAttributes(attributes);
            }
            if (this.optimizeEmptyElements) {
                this.openElement = true;
            } else {
                this.w.write(">\n");
            }
            this.ident += 2;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    public final void endElement(String string, String string2, String string3) throws SAXException {
        this.ident -= 2;
        try {
            if (this.openElement) {
                this.w.write("/>\n");
                this.openElement = false;
            } else {
                this.writeIdent();
                this.w.write("</" + string3 + ">\n");
            }
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    public final void endDocument() throws SAXException {
        try {
            this.w.flush();
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    public final void comment(char[] cArray, int n, int n2) throws SAXException {
        try {
            this.closeElement();
            this.writeIdent();
            this.w.write("<!-- ");
            this.w.write(cArray, n, n2);
            this.w.write(" -->\n");
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    public final void startDTD(String string, String string2, String string3) throws SAXException {
    }

    public final void endDTD() throws SAXException {
    }

    public final void startEntity(String string) throws SAXException {
    }

    public final void endEntity(String string) throws SAXException {
    }

    public final void startCDATA() throws SAXException {
    }

    public final void endCDATA() throws SAXException {
    }

    private final void writeAttributes(Attributes attributes) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        int n = attributes.getLength();
        for (int i = 0; i < n; ++i) {
            stringBuffer.append(' ').append(attributes.getLocalName(i)).append("=\"").append(Processor$SAXWriter.esc(attributes.getValue(i))).append('\"');
        }
        this.w.write(stringBuffer.toString());
    }

    private static final String esc(String string) {
        StringBuffer stringBuffer = new StringBuffer(string.length());
        block6: for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '&': {
                    stringBuffer.append("&amp;");
                    continue block6;
                }
                case '<': {
                    stringBuffer.append("&lt;");
                    continue block6;
                }
                case '>': {
                    stringBuffer.append("&gt;");
                    continue block6;
                }
                case '\"': {
                    stringBuffer.append("&quot;");
                    continue block6;
                }
                default: {
                    if (c > '\u007f') {
                        stringBuffer.append("&#").append(Integer.toString(c)).append(';');
                        continue block6;
                    }
                    stringBuffer.append(c);
                }
            }
        }
        return stringBuffer.toString();
    }

    private final void writeIdent() throws IOException {
        int n = this.ident;
        while (n > 0) {
            if (n > OFF.length) {
                this.w.write(OFF);
                n -= OFF.length;
                continue;
            }
            this.w.write(OFF, 0, n);
            n = 0;
        }
    }

    private final void closeElement() throws IOException {
        if (this.openElement) {
            this.w.write(">\n");
        }
        this.openElement = false;
    }

    static {
        Processor$SAXWriter._clinit_();
        OFF = "                                                                                                        ".toCharArray();
    }

    static /* synthetic */ void _clinit_() {
    }
}

