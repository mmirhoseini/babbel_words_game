package com.mirhoseini.babbelwordsgame.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.mirhoseini.babbelwordsgame.GameApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 07/06/16.
 */
@Module
public class AndroidModule {
    private GameApplication gameApplication;

    public AndroidModule(GameApplication gameApplication) {
        this.gameApplication = gameApplication;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return gameApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    public Resources provideResources() {
        return gameApplication.getResources();
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(gameApplication);
    }

}
