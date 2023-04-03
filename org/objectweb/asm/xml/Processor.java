/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.xml;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamSource;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.xml.Processor$ASMContentHandlerFactory;
import org.objectweb.asm.xml.Processor$ContentHandlerFactory;
import org.objectweb.asm.xml.Processor$EntryElement;
import org.objectweb.asm.xml.Processor$InputSlicingHandler;
import org.objectweb.asm.xml.Processor$OutputSlicingHandler;
import org.objectweb.asm.xml.Processor$ProtectedInputStream;
import org.objectweb.asm.xml.Processor$SAXWriter;
import org.objectweb.asm.xml.Processor$SAXWriterFactory;
import org.objectweb.asm.xml.Processor$SingleDocElement;
import org.objectweb.asm.xml.Processor$SubdocumentHandlerFactory;
import org.objectweb.asm.xml.Processor$TransformerHandlerFactory;
import org.objectweb.asm.xml.Processor$ZipEntryElement;
import org.objectweb.asm.xml.SAXClassAdapter;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLReaderFactory;

public class Processor {
    public static final int BYTECODE = 1;
    public static final int MULTI_XML = 2;
    public static final int SINGLE_XML = 3;
    private static final String SINGLE_XML_NAME = "classes.xml";
    private final int inRepresentation;
    private final int outRepresentation;
    private final InputStream input;
    private final OutputStream output;
    private final Source xslt;
    private int n = 0;

    public Processor(int n, int n2, InputStream inputStream, OutputStream outputStream, Source source) {
        this.inRepresentation = n;
        this.outRepresentation = n2;
        this.input = inputStream;
        this.output = outputStream;
        this.xslt = source;
    }

    public int process() throws TransformerException, IOException, SAXException {
        ZipEntry zipEntry;
        Object object;
        ZipInputStream zipInputStream = new ZipInputStream(this.input);
        ZipOutputStream zipOutputStream = new ZipOutputStream(this.output);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(zipOutputStream);
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        if (!transformerFactory.getFeature("http://javax.xml.transform.sax.SAXSource/feature") || !transformerFactory.getFeature("http://javax.xml.transform.sax.SAXResult/feature")) {
            return 0;
        }
        SAXTransformerFactory sAXTransformerFactory = (SAXTransformerFactory)transformerFactory;
        Templates templates = null;
        if (this.xslt != null) {
            templates = sAXTransformerFactory.newTemplates(this.xslt);
        }
        Processor$EntryElement processor$EntryElement = this.getEntryElement(zipOutputStream);
        Object object2 = null;
        switch (this.outRepresentation) {
            case 1: {
                object2 = new Processor$OutputSlicingHandler(new Processor$ASMContentHandlerFactory(zipOutputStream), processor$EntryElement, false);
                break;
            }
            case 2: {
                object2 = new Processor$OutputSlicingHandler(new Processor$SAXWriterFactory(outputStreamWriter, true), processor$EntryElement, true);
                break;
            }
            case 3: {
                object = new ZipEntry(SINGLE_XML_NAME);
                zipOutputStream.putNextEntry((ZipEntry)object);
                object2 = new Processor$SAXWriter(outputStreamWriter, false);
            }
        }
        object = templates == null ? object2 : new Processor$InputSlicingHandler("class", (ContentHandler)object2, new Processor$TransformerHandlerFactory(sAXTransformerFactory, templates, (ContentHandler)object2));
        Processor$SubdocumentHandlerFactory processor$SubdocumentHandlerFactory = new Processor$SubdocumentHandlerFactory((ContentHandler)object);
        if (object != null && this.inRepresentation != 3) {
            object.startDocument();
            object.startElement("", "classes", "classes", new AttributesImpl());
        }
        int n = 0;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            this.update(zipEntry.getName(), this.n++);
            if (this.isClassEntry(zipEntry)) {
                this.processEntry(zipInputStream, zipEntry, processor$SubdocumentHandlerFactory);
            } else {
                OutputStream outputStream = processor$EntryElement.openEntry(this.getName(zipEntry));
                this.copyEntry(zipInputStream, outputStream);
                processor$EntryElement.closeEntry();
            }
            ++n;
        }
        if (object != null && this.inRepresentation != 3) {
            object.endElement("", "classes", "classes");
            object.endDocument();
        }
        if (this.outRepresentation == 3) {
            zipOutputStream.closeEntry();
        }
        zipOutputStream.flush();
        zipOutputStream.close();
        return n;
    }

    private void copyEntry(InputStream inputStream, OutputStream outputStream) throws IOException {
        int n;
        if (this.outRepresentation == 3) {
            return;
        }
        byte[] byArray = new byte[2048];
        while ((n = inputStream.read(byArray)) != -1) {
            outputStream.write(byArray, 0, n);
        }
    }

    private boolean isClassEntry(ZipEntry zipEntry) {
        String string = zipEntry.getName();
        return this.inRepresentation == 3 && string.equals(SINGLE_XML_NAME) || string.endsWith(".class") || string.endsWith(".class.xml");
    }

    private void processEntry(ZipInputStream zipInputStream, ZipEntry zipEntry, Processor$ContentHandlerFactory processor$ContentHandlerFactory) {
        ContentHandler contentHandler = processor$ContentHandlerFactory.createContentHandler();
        try {
            boolean bl;
            boolean bl2 = bl = this.inRepresentation == 3;
            if (this.inRepresentation == 1) {
                ClassReader classReader = new ClassReader(Processor.readEntry(zipInputStream, zipEntry));
                classReader.accept(new SAXClassAdapter(contentHandler, bl), 0);
            } else {
                XMLReader xMLReader = XMLReaderFactory.createXMLReader();
                xMLReader.setContentHandler(contentHandler);
                xMLReader.parse(new InputSource(bl ? new Processor$ProtectedInputStream(zipInputStream) : new ByteArrayInputStream(Processor.readEntry(zipInputStream, zipEntry))));
            }
        }
        catch (Exception exception) {
            this.update(zipEntry.getName(), 0);
            this.update(exception, 0);
        }
    }

    private Processor$EntryElement getEntryElement(ZipOutputStream zipOutputStream) {
        if (this.outRepresentation == 3) {
            return new Processor$SingleDocElement(zipOutputStream);
        }
        return new Processor$ZipEntryElement(zipOutputStream);
    }

    private String getName(ZipEntry zipEntry) {
        String string = zipEntry.getName();
        if (this.isClassEntry(zipEntry)) {
            if (this.inRepresentation != 1 && this.outRepresentation == 1) {
                string = string.substring(0, string.length() - 4);
            } else if (this.inRepresentation == 1 && this.outRepresentation != 1) {
                string = string + ".xml";
            }
        }
        return string;
    }

    private static byte[] readEntry(InputStream inputStream, ZipEntry zipEntry) throws IOException {
        int n;
        long l = zipEntry.getSize();
        if (l > -1L) {
            int n2;
            byte[] byArray = new byte[(int)l];
            int n3 = 0;
            while ((n2 = inputStream.read(byArray, n3, byArray.length - n3)) > 0) {
                n3 += n2;
            }
            return byArray;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byArray = new byte[4096];
        while ((n = inputStream.read(byArray)) != -1) {
            byteArrayOutputStream.write(byArray, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

    protected void update(Object object, int n) {
        if (object instanceof Throwable) {
            ((Throwable)object).printStackTrace();
        } else if (n % 100 == 0) {
            System.err.println(n + " " + object);
        }
    }

    public static void main(String[] stringArray) throws Exception {
        if (stringArray.length < 2) {
            Processor.showUsage();
            return;
        }
        int n = Processor.getRepresentation(stringArray[0]);
        int n2 = Processor.getRepresentation(stringArray[1]);
        InputStream inputStream = System.in;
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(System.out);
        StreamSource streamSource = null;
        for (int i = 2; i < stringArray.length; ++i) {
            if ("-in".equals(stringArray[i])) {
                inputStream = new FileInputStream(stringArray[++i]);
                continue;
            }
            if ("-out".equals(stringArray[i])) {
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(stringArray[++i]));
                continue;
            }
            if ("-xslt".equals(stringArray[i])) {
                streamSource = new StreamSource(new FileInputStream(stringArray[++i]));
                continue;
            }
            Processor.showUsage();
            return;
        }
        if (n == 0 || n2 == 0) {
            Processor.showUsage();
            return;
        }
        Processor processor = new Processor(n, n2, inputStream, bufferedOutputStream, streamSource);
        long l = System.currentTimeMillis();
        int n3 = processor.process();
        long l2 = System.currentTimeMillis();
        System.err.println(n3);
        System.err.println(l2 - l + "ms  " + 1000.0f * (float)n3 / (float)(l2 - l) + " resources/sec");
    }

    private static int getRepresentation(String string) {
        if ("code".equals(string)) {
            return 1;
        }
        if ("xml".equals(string)) {
            return 2;
        }
        if ("singlexml".equals(string)) {
            return 3;
        }
        return 0;
    }

    private static void showUsage() {
        System.err.println("Usage: Main <in format> <out format> [-in <input jar>] [-out <output jar>] [-xslt <xslt fiel>]");
        System.err.println("  when -in or -out is omitted sysin and sysout would be used");
        System.err.println("  <in format> and <out format> - code | xml | singlexml");
    }
}

