/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.Interpreter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class TestUtil {
    public static <T extends Serializable> T serDeser(T orgig) {
        try {
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
            new ObjectOutputStream(byteOS).writeObject(orgig);
            return (T)((Serializable)new ObjectInputStream(new ByteArrayInputStream(byteOS.toByteArray())).readObject());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void cleanUp() {
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
    }

    public static long measureConcurrentTime(Runnable runnable, int threadCount, int taskCount, int iterationCount) throws InterruptedException {
        long duration = TestUtil._measureConcurrentTime(runnable, threadCount, taskCount, iterationCount);
        TestUtil.cleanUp();
        return duration;
    }

    static long _measureConcurrentTime(Runnable runnable, int threadCount, int taskCount, int iterationCount) throws InterruptedException {
        if (threadCount < 1) {
            throw new IllegalArgumentException("thread count must be at least 1");
        }
        if (taskCount < threadCount) {
            throw new IllegalArgumentException("task count below thread count");
        }
        Exception callerStack = new Exception("called from");
        CountDownLatch countDownLatch = new CountDownLatch(threadCount + 1);
        AtomicReference exceptionHolder = new AtomicReference();
        MeasureRunnable toMeasure = new MeasureRunnable(countDownLatch, runnable, iterationCount, exceptionHolder);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < taskCount; ++i) {
            executorService.submit(toMeasure);
        }
        TestUtil.cleanUp();
        long startTime = System.nanoTime();
        countDownLatch.countDown();
        executorService.shutdown();
        executorService.awaitTermination(3600L, TimeUnit.SECONDS);
        Throwable throwable = (Throwable)exceptionHolder.get();
        if (throwable instanceof RuntimeException) {
            throw TestUtil.combineTraces((RuntimeException)throwable, callerStack);
        }
        if (throwable instanceof Error) {
            throw TestUtil.combineTraces((Error)throwable, callerStack);
        }
        if (throwable != null) {
            throw TestUtil.combineTraces(new RuntimeException(throwable), callerStack);
        }
        return System.nanoTime() - startTime;
    }

    static <T extends Throwable> T combineTraces(T throwable, Exception cause) {
        Object rootCause = throwable;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        rootCause.initCause(cause);
        return throwable;
    }

    public static Object eval(String ... code) throws Exception {
        return TestUtil.eval(Collections.emptyMap(), code);
    }

    public static Object eval(Map<? extends String, ?> params, String ... code) throws Exception {
        StringBuffer buffer = new StringBuffer();
        for (String s : code) {
            buffer.append(s).append('\n');
        }
        Interpreter interpreter = new Interpreter();
        for (Map.Entry<String, ?> entry : params.entrySet()) {
            interpreter.set(entry.getKey(), entry.getValue());
        }
        return interpreter.eval(buffer.toString());
    }

    static class MeasureRunnable
    implements Runnable {
        private final CountDownLatch _countDownLatch;
        private final Runnable _task;
        private final int _iterationCount;
        private final AtomicReference<Throwable> _exception;

        private MeasureRunnable(CountDownLatch countDownLatch, Runnable task, int iterationCount, AtomicReference<Throwable> exception) {
            this._countDownLatch = countDownLatch;
            this._task = task;
            this._iterationCount = iterationCount;
            this._exception = exception;
        }

        @Override
        public void run() {
            try {
                this._countDownLatch.countDown();
                for (int i = 0; i < this._iterationCount; ++i) {
                    this._task.run();
                }
            }
            catch (RuntimeException e) {
                this._exception.compareAndSet(null, e);
                throw e;
            }
            catch (Error e) {
                this._exception.compareAndSet(null, e);
                throw e;
            }
        }
    }
}

