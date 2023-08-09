package com.bluewhaleyt.common.coroutines;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.Job;

/**
 * This class is completely for Java.
 * Due to the Kotlin coroutines requires additional parameters in Java after the translation, this class is completely
 * written in Java. Mainly still use {@link CoroutinesUtils} but with extra default functions settings.
 *
 * For Kotlin. please see
 * @see CoroutinesUtils
 */
public final class JCoroutinesUtils {

    private static final Continuation<?> continuation = CoroutinesUtils.Companion.getContinuation();

    public static Job runOnUI(Runnable runnable) {
        return (Job) CoroutinesUtils.Companion.runOnUI((coroutineScope, continuation) -> runnable);
    }

    public static Job runInBackground(Runnable runnable) {
        return (Job) CoroutinesUtils.Companion.runInBackground((coroutineScope, continuation) -> runnable);
    }

    public static Job asyncOnUI(Runnable runnable) {
        return (Job) CoroutinesUtils.Companion.asyncOnUI(((coroutineScope, continuation1) -> runnable));
    }

    public static Job asyncInBackground(Runnable runnable) {
        return (Job) CoroutinesUtils.Companion.asyncInBackground(((coroutineScope, continuation1) -> runnable));
    }

    public static Job withUI(Runnable runnable) {
        return (Job) CoroutinesUtils.Companion.withUI(continuation -> {
            runnable.run();
            return null;
        }, continuation);
    }

    public static Job withBackground(Runnable runnable) {
        return (Job) CoroutinesUtils.Companion.withBackground(continuation -> {
            runnable.run();
            return null;
        }, continuation);
    }

    public static Job withDefault(Runnable runnable) {
        return (Job) CoroutinesUtils.Companion.withDefault(continuation -> {
            runnable.run();
            return null;
        }, continuation);
    }

    public static Job withUnconfined(Runnable runnable) {
        return (Job) CoroutinesUtils.Companion.withUnconfined(continuation -> {
            runnable.run();
            return null;
        }, continuation);
    }

    public static void cancel(Job job) {
        CoroutinesUtils.Companion.cancel((Job) job);
    }
}
