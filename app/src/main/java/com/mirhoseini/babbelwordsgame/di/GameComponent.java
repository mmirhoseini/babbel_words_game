package com.mirhoseini.babbelwordsgame.di;

import com.mirhoseini.babbelwordsgame.core.di.ScoreModule;
import com.mirhoseini.babbelwordsgame.ui.activity.MainActivity;

import dagger.Subcomponent;

/**
 * Created by Mohsen on 07/06/16.
 */
@Subcomponent(modules = {GameModule.class, ScoreModule.class})
public interface GameComponent {
    void inject(MainActivity activity);
}
