package com.mirhoseini.babbelwordsgame.core.view;

/**
 * Created by Mohsen on 07/06/16.
 */
public interface MainView {

    void updateTimer(int time);

    void loadMainMenu();

    void loadGame();

    void gameOver(int score);

    void revealQuestion(String question, String answer);

    void showAnswerWasCorrect();

    void showAnswerWasWrong();

    void updateScore(int newScore);
}
