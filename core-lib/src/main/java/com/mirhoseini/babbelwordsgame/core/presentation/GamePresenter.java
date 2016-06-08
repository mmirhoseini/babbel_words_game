package com.mirhoseini.babbelwordsgame.core.presentation;

import com.mirhoseini.babbelwordsgame.core.view.MainView;

/**
 * Created by Mohsen on 07/06/16.
 */
public interface GamePresenter {

    void setView(MainView view);

    void loadWordsData(String data);

    void startGame();

    void onCorrectAction();

    void onWrongAction();

    void stopGame();

}
