/*
 * Decompiled with CFR 0.152.
 */
package javassist;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;
import javassist.ClassPath;
import javassist.JarClassPath;
import javassist.NotFoundException;

final class JarDirClassPath
implements ClassPath {
    JarClassPath[] jars;

    JarDirClassPath(String dirName) throws NotFoundException {
        File[] files = new File(dirName).listFiles(new FilenameFilter(){

            @Override
            public boolean accept(File dir2, String name) {
                return (name = name.toLowerCase()).endsWith(".jar") || name.endsWith(".zip");
            }
        });
        if (files != null) {
            this.jars = new JarClassPath[files.length];
            int i = 0;
            while (i < files.length) {
                this.jars[i] = new JarClassPath(files[i].getPath());
                ++i;
            }
        }
    }

    @Override
    public InputStream openClassfile(String classname) throws NotFoundException {
        if (this.jars != null) {
            int i = 0;
            while (i < this.jars.length) {
                InputStream is = this.jars[i].openClassfile(classname);
                if (is != null) {
                    return is;
                }
                ++i;
            }
        }
        return null;
    }

    @Override
    public URL find(String classname) {
        if (this.jars != null) {
            int i = 0;
            while (i < this.jars.length) {
                URL url = this.jars[i].find(classname);
                if (url != null) {
                    return url;
                }
                ++i;
            }
        }
        return null;
    }
}

