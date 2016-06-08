package com.mirhoseini.babbelwordsgame.di;

import com.mirhoseini.babbelwordsgame.core.presentation.GamePresenter;
import com.mirhoseini.babbelwordsgame.core.presentation.GamePresenterImpl;
import com.mirhoseini.babbelwordsgame.core.view.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 07/06/16.
 */
@Module
public class GameModule {
    private MainView view;

    public GameModule(MainView view) {
        this.view = view;
    }

    @Provides
    public MainView provideView() {
        return view;
    }

    @Provides
    public GamePresenter providePresenter(GamePresenterImpl presenter) {
        presenter.setView(view);
        return presenter;
    }
}
