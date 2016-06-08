package com.mirhoseini.babbelwordsgame.ui.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.mirhoseini.appsettings.AppSettings;
import com.mirhoseini.babbelwordsgame.R;
import com.mirhoseini.babbelwordsgame.core.presentation.GamePresenter;
import com.mirhoseini.babbelwordsgame.core.view.MainView;
import com.mirhoseini.babbelwordsgame.di.ApplicationComponent;
import com.mirhoseini.babbelwordsgame.di.GameModule;
import com.mirhoseini.babbelwordsgame.ui.fragment.GameFragment;
import com.mirhoseini.babbelwordsgame.ui.fragment.MenuFragment;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Mohsen on 07/06/16.
 */
public class MainActivity extends BaseActivity implements MainView, MenuFragment.OnMainFragmentInteractionListener, GameFragment.OnGameFragmentInteractionListener {
    public static final String KEY_HIGH_SCORE = "highscore";
    private static final String TAG_MAIN_FRAGMENT = "main_fragment";
    private static final String TAG_GAME_FRAGMENT = "game_fragment";

    @Inject
    GamePresenter presenter;

    @BindView(R.id.main_container)
    ViewGroup mainContainer;
    private MenuFragment menuFragment;
    private GameFragment gameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding Views using ButterKnife
        ButterKnife.bind(this);

        Timber.d("Activity Created");

        presenter.loadWordsData(loadWordsJSONFromAsset());
    }

    @Override
    protected void onResume() {
        super.onResume();

        clearBackStack();
    }

    public String loadWordsJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("words.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component
                .plus(new GameModule(this))
                .inject(this);
    }

    @Override
    public void updateTimer(int time) {
        Timber.i("updateTimer %d", time);

        gameFragment.updateTimer(time);
    }

    @Override
    public void loadMainMenu() {
        menuFragment = MenuFragment.newInstance(AppSettings.getInt(this, KEY_HIGH_SCORE, 0));

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, menuFragment, TAG_MAIN_FRAGMENT);
        fragmentTransaction.commit();
    }

    @Override
    public void loadGame() {
        clearBackStack();

        gameFragment = GameFragment.newInstance();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, gameFragment, TAG_GAME_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @NonNull
    private void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0)
            fragmentManager.popBackStack();
    }

    @Override
    public void gameOver(int score) {
        Timber.d("GameOver with score %d", score);

        gameFragment.gameOver();

        boolean newRecord = updateHighScore(score);

        showGameOverDialog(score, newRecord);
    }

    private void showGameOverDialog(int score, boolean newRecord) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.gameover_title)
                .setPositiveButton(R.string.restart, (dialog, which) -> presenter.startGame())
                .setNegativeButton(R.string.back, (dialog, which) -> onBackPressed())
                .setCancelable(false)
                .setMessage(createGameOverDialogMessage(score, newRecord))
                .show();

    }

    @NonNull
    private String createGameOverDialogMessage(int score, boolean newRecord) {
        String message = "";
        if (newRecord)
            message = getString(R.string.new_record) + "\n";
        message += String.format(getString(R.string.gameover_message), score);
        return message;
    }

    private boolean updateHighScore(int score) {
        boolean newRecord = false;
        if (AppSettings.getInt(this, KEY_HIGH_SCORE, 0) < score) {
            AppSettings.setValue(this, KEY_HIGH_SCORE, score);
            newRecord = true;
        }

        return newRecord;
    }

    @Override
    public void revealQuestion(String question, String answer) {
        gameFragment.showQuestion(question, answer);
    }

    @Override
    public void showAnswerWasCorrect() {
//        Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        gameFragment.showAnswerWasCorrect();
    }

    @Override
    public void showAnswerWasWrong() {
//        Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        gameFragment.showAnswerWasWrong();
    }

    @Override
    public void updateScore(int newScore) {
        Timber.d("updateScore %d", newScore);

        gameFragment.updateScore(newScore);
    }

    @Override
    public void onStartGame() {
        presenter.startGame();
    }

    @Override
    public void onCorrect() {
        presenter.onCorrectAction();
    }

    @Override
    public void onWrong() {
        presenter.onWrongAction();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            Timber.i("Popping BackStack");
            fragmentManager.popBackStack();
            presenter.stopGame();
        } else {
            Timber.i("Nothing on BackStack, Calling super to exit");
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Timber.d("Activity Destroyed");

        // call destroy to remove references to objects
        presenter.setView(null);
        presenter = null;
    }

    @Override
    protected void onStop() {
        super.onStop();

        Timber.d("Activity Stopped");

        presenter.stopGame();
    }
}
