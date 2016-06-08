package com.mirhoseini.babbelwordsgame.core.util;

import rx.Scheduler;

/**
 * Created by Mohsen on 07/06/16.
 */
public interface SchedulerProvider {

    Scheduler mainThread();

    Scheduler backgroundThread();

}
