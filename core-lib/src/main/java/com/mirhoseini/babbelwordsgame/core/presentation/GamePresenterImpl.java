package com.mirhoseini.babbelwordsgame.core.presentation;


import com.google.gson.Gson;
import com.mirhoseini.babbelwordsgame.core.model.Word;
import com.mirhoseini.babbelwordsgame.core.util.SchedulerProvider;
import com.mirhoseini.babbelwordsgame.core.util.ScoreManager;
import com.mirhoseini.babbelwordsgame.core.util.TimeManager;
import com.mirhoseini.babbelwordsgame.core.view.MainView;

import java.util.Random;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Mohsen on 07/06/16.
 */
public class GamePresenterImpl implements GamePresenter, TimeManager.TimerListener {

    public static final int NEXT_QUESTION_DELAY = 3;

    private MainView view;
    private Word[] words;

    private boolean isGameRunning = false;
    private boolean isCorrect;

    @Inject
    Gson gson;

    @Inject
    SchedulerProvider scheduler;

    @Inject
    ScoreManager scoreManager;

    @Inject
    Random random;

    TimeManager timeManager;

    private Subscription timerSubscription;
    private Subscription gameSubscription;

    @Inject
    public GamePresenterImpl() {
    }

    @Override
    public void setView(MainView view) {
        this.view = view;

        if (view != null)
            view.loadMainMenu();
    }

    @Override
    public void loadWordsData(String data) {
        words = gson.fromJson(data, Word[].class);
    }

    @Override
    public void startGame() {
        if (view != null)
            view.loadGame();

        isGameRunning = true;

        setupTimer();

        setupScore();

        newWordQuestion();
    }

    private void setupScore() {
        scoreManager.resetScore();
    }

    private void newWordQuestion() {
        Observable.just(random.nextBoolean())
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread())
                .subscribe(correct -> {
                    isCorrect = correct;

                    int r = random.nextInt(words.length);
                    Word word = words[r];

                    String answer;
                    if (isCorrect)
                        answer = word.getTextSpa();
                    else
                        answer = words[random.nextInt(words.length)].getTextSpa();

                    if (view != null)
                        view.revealQuestion(word.getTextEng(), answer);

                    setupQuestionTimeout();
                });
    }

    private void setupQuestionTimeout() {
        if (gameSubscription != null && !gameSubscription.isUnsubscribed())
            gameSubscription.unsubscribe();

        if (isGameRunning)
            gameSubscription = Observable.timer(NEXT_QUESTION_DELAY, SECONDS)
                    .subscribeOn(scheduler.backgroundThread())
                    .observeOn(scheduler.mainThread())
                    .subscribe(aLong -> {
                        if (isGameRunning)
                            newWordQuestion();
                    });
    }

    private void setupTimer() {
        if (timerSubscription != null && !timerSubscription.isUnsubscribed())
            timerSubscription.unsubscribe();

        timerSubscription = Observable.interval(1, SECONDS)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread())
                .subscribe(aLong -> {
                    timeManager.tickTimer();
                });

        timeManager = new TimeManager(this);
    }

    @Override
    public void onCorrectAction() {
        if (isGameRunning && view != null) {
            if (isCorrect) {
                view.showAnswerWasCorrect();
                view.updateScore(scoreManager.incScore());

            } else {
                view.showAnswerWasWrong();
                view.updateScore(scoreManager.decScore());

            }
        }

        newWordQuestion();
    }

    @Override
    public void onWrongAction() {
        if (isGameRunning && view != null) {
            if (isCorrect) {
                view.showAnswerWasWrong();
                view.updateScore(scoreManager.decScore());
            } else {
                view.showAnswerWasCorrect();
                view.updateScore(scoreManager.incScore());
            }
        }

        newWordQuestion();
    }

    @Override
    public void stopGame() {
        isGameRunning = false;

        if (gameSubscription != null && !gameSubscription.isUnsubscribed())
            gameSubscription.unsubscribe();

        if (timerSubscription != null && !timerSubscription.isUnsubscribed())
            timerSubscription.unsubscribe();
    }


    @Override
    public void onTimerFinished() {
        if (view != null)
            view.gameOver(scoreManager.getScore());

        isGameRunning = false;

        if (!timerSubscription.isUnsubscribed())
            timerSubscription.unsubscribe();
    }

    @Override
    public void onTimerUpdated(int time) {
        if (view != null)
            view.updateTimer(time);
    }
}
