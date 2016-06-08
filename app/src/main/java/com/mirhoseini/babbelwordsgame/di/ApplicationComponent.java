package com.mirhoseini.babbelwordsgame.di;


import com.mirhoseini.babbelwordsgame.core.di.GsonModule;
import com.mirhoseini.babbelwordsgame.core.di.RandomModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mohsen on 07/06/16.
 */
@Singleton
@Component(modules = {
        AndroidModule.class,
        ApplicationModule.class,
        GsonModule.class,
        RandomModule.class
})
public interface ApplicationComponent {
    GameComponent plus(GameModule module);
}