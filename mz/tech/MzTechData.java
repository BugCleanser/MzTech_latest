/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package mz.tech;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.function.Consumer;
import mz.tech.DropsName;
import mz.tech.ImageMap;
import mz.tech.MzTech;
import mz.tech.ReflectionWrapper;
import mz.tech.machine.MachineChunk;
import mz.tech.mail.Mailbox;
import mz.tech.util.TaskUtil;
import org.bukkit.plugin.Plugin;

public final class MzTechData {
    public static Field fdClosed;
    public static Field fdHandle;

    static {
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 30000L, 30000L, () -> MzTechData.saveAll());
        fdClosed = ReflectionWrapper.getField(FileDescriptor.class, "closed");
        fdHandle = ReflectionWrapper.getField(FileDescriptor.class, "handle");
    }

    private MzTechData() {
    }

    public static void reload() {
        MzTech.instance.saveDefaultConfig();
        MzTech.instance.reloadConfig();
        MzTech.MzTechPrefix = (String)MzTech.instance.getConfig().get("MzTech");
        DropsName.reload(MzTech.instance.getConfig());
        ImageMap.reload();
    }

    public static void loadAll() {
        MachineChunk.loadAll();
        Mailbox.loadAll();
    }

    public static void saveAll() {
        MachineChunk.saveAll();
        Mailbox.saveAll();
    }

    public static void closeFile(FileOutputStream fos) {
        try {
            if (!((Boolean)ReflectionWrapper.getFieldValue(fdClosed, fos.getFD())).booleanValue()) {
                ReflectionWrapper.setFieldValue(fdClosed, fos.getFD(), true);
                Kernel32.INSTANCE.CloseHandle(new WinNT.HANDLE(new Pointer((Long)ReflectionWrapper.getFieldValue(fdHandle, fos.getFD()))));
            }
        }
        catch (Throwable e) {
            MzTech.throwException(e);
        }
    }

    public static void save(File file, Consumer<DataOutputStream> con) throws FileNotFoundException, IOException {
        if (!file.getParentFile().isDirectory()) {
            file.getParentFile().mkdirs();
        }
        if (!file.isFile()) {
            file.createNewFile();
        }
        Throwable throwable = null;
        Object var3_4 = null;
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));){
            con.accept(dos);
        }
        catch (Throwable throwable2) {
            if (throwable == null) {
                throwable = throwable2;
            } else if (throwable != throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
    }
}

