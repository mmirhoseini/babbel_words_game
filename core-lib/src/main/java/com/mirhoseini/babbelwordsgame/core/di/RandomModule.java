package com.mirhoseini.babbelwordsgame.core.di;

import java.util.Random;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 07/06/16.
 */
@Module
public class RandomModule {

    @Singleton
    @Provides
    public Random provideRandom() {
        return new Random(System.currentTimeMillis());
    }

}
