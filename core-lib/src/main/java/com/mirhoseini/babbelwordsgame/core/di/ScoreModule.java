package com.mirhoseini.babbelwordsgame.core.di;

import com.mirhoseini.babbelwordsgame.core.util.ScoreManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 07/06/16.
 */
@Module
public class ScoreModule {

    @Provides
    public ScoreManager provideScoreManager() {
        return new ScoreManager();
    }

}
