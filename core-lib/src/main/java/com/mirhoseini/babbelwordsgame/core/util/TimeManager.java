package com.mirhoseini.babbelwordsgame.core.util;

/**
 * Created by Mohsen on 07/06/16.
 */
public class TimeManager {

    public static final int GAME_TIME_SECONDS = 30;

    int time = GAME_TIME_SECONDS;
    TimerListener listener;

    public TimeManager(TimerListener listener) {
        this.listener = listener;
    }

    public void tickTimer() {
        time--;

        listener.onTimerUpdated(time);

        if (time <= 0 && listener != null)
            listener.onTimerFinished();
    }


    public interface TimerListener {

        void onTimerFinished();

        void onTimerUpdated(int time);

    }
}
