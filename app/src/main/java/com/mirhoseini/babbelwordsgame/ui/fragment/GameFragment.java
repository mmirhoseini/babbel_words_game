package com.mirhoseini.babbelwordsgame.ui.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mirhoseini.babbelwordsgame.R;
import com.mirhoseini.babbelwordsgame.core.util.TimeManager;
import com.mirhoseini.utils.Utils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by Mohsen on 07/06/16.
 */
public class GameFragment extends Fragment {
    private static final String ARG_GAME_TIME = "game_time";

    private OnGameFragmentInteractionListener listener;
    private TextView answer;
    private AppCompatImageView result;

    @BindView(R.id.timer)
    TextView timer;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.question)
    TextView question;
    @BindView(R.id.game_field)
    FrameLayout gameField;
    @BindView(R.id.correct)
    Button correct;

    Random random;

    @OnClick(R.id.correct)
    void correct() {
        if (listener != null)
            listener.onCorrect();
    }

    @BindView(R.id.wrong)
    Button wrong;

    @OnClick(R.id.wrong)
    void wrong() {
        if (listener != null)
            listener.onWrong();
    }

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_GAME_TIME, time);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGameFragmentInteractionListener) {
            listener = (OnGameFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMainFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            time = getArguments().getInt(ARG_GAME_TIME);
        }

        random = new Random();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(this, view);

        timer.setText(TimeManager.GAME_TIME_SECONDS + "");
        score.setText("0");

        return view;
    }

    public void updateTimer(int newTime) {
        timer.setText(newTime + "");
    }

    public void updateScore(int newScore) {
        score.setText(newScore + "");
    }

    public void showQuestion(String questionText, String answerText) {
        question.setText(questionText);

        if (answer != null)
            answer.clearAnimation();

        answer = new TextView(getActivity());

        answer.setText(answerText);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            answer.setTextAppearance(android.R.style.TextAppearance_Large);
        } else {
            answer.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
        }

        Animation answerAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.answer_translate_fade);
        answer.startAnimation(answerAnimation);

        answer.measure(0, 0);
        int answerWidth = answer.getMeasuredWidth();
        Timber.i("Answer Width: %d", answerWidth);

        int screenWidth = Utils.getDisplayWidth(getActivity());
        Timber.i("Screen Width: %d", screenWidth);

        if (answerWidth > screenWidth)
            answer.setX(0);
        else
            answer.setX(random.nextInt(screenWidth - answerWidth));


        gameField.removeAllViews();
        gameField.addView(answer);
    }

    public void gameOver() {
        if (answer != null) {
            answer.clearAnimation();
            gameField.removeAllViews();
        }

        correct.setEnabled(false);
        wrong.setEnabled(false);
    }

    public void showAnswerWasCorrect() {
        showResultAnimation(R.drawable.ic_check);
    }

    public void showAnswerWasWrong() {
        showResultAnimation(R.drawable.ic_cross);
    }

    private void showResultAnimation(@DrawableRes int drawable) {
        if (result != null) {
            gameField.removeViewInLayout(result);
        }

        result = new AppCompatImageView(getActivity());

        result.setImageResource(drawable);

        Animation resultAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.result_scale_fade);
        result.startAnimation(resultAnimation);

        gameField.addView(result);

//        int width = Utils.getDisplayWidth(getActivity());
//        int height = Utils.getDisplayHeight(getActivity());
//        result.setX(width / 2);
//        result.setY(height / 2);
    }


    public interface OnGameFragmentInteractionListener {

        void onCorrect();

        void onWrong();
    }
}
