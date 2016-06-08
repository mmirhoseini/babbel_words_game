package com.mirhoseini.babbelwordsgame.di;

import com.mirhoseini.babbelwordsgame.BuildConfig;
import com.mirhoseini.babbelwordsgame.core.util.SchedulerProvider;
import com.mirhoseini.babbelwordsgame.util.AppSchedulerProvider;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 07/06/16.
 */
@Module
public class ApplicationModule {
    @Provides
    @Singleton
    @Named("isDebug")
    public boolean provideIsDebug() {
        return BuildConfig.DEBUG;
    }

    @Provides
    @Singleton
    public SchedulerProvider provideAppScheduler() {
        return new AppSchedulerProvider();
    }

}
