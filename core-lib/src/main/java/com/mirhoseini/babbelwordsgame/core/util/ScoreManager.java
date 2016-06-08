package com.mirhoseini.babbelwordsgame.core.util;

/**
 * Created by Mohsen on 07/06/16.
 */
public class ScoreManager {

    int score = 0;

    public void resetScore() {
        score = 0;
    }

    public int incScore() {
        score += 10;
        return score;
    }

    public int decScore() {
        score -= 5;
        return score;
    }

    public int getScore() {
        return score;
    }
}
