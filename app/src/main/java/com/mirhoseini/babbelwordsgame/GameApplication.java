package com.mirhoseini.babbelwordsgame;

import android.app.Application;

import com.mirhoseini.babbelwordsgame.di.AndroidModule;
import com.mirhoseini.babbelwordsgame.di.ApplicationComponent;
import com.mirhoseini.babbelwordsgame.di.DaggerApplicationComponent;

/**
 * Created by Mohsen on 07/06/16.
 */
public abstract class GameApplication extends Application {
    private static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

    protected AndroidModule getAndroidModule() {
        return new AndroidModule(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initApplication();

        component = DaggerApplicationComponent.builder()
                .androidModule(getAndroidModule())
                .build();
    }

    abstract void initApplication();
}
