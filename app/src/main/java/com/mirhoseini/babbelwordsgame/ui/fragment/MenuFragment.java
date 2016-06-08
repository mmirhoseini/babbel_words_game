package com.mirhoseini.babbelwordsgame.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mirhoseini.babbelwordsgame.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mohsen on 07/06/16.
 */
public class MenuFragment extends Fragment {
    private static final String ARG_HIGH_SCORE = "highScore";

    private OnMainFragmentInteractionListener listener;

    @BindView(R.id.highscore)
    TextView highScore;

    @OnClick(R.id.start)
    void start() {
        if (listener != null)
            listener.onStartGame();
    }

    private int highScoreValue;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance(int highscore) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_HIGH_SCORE, highscore);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainFragmentInteractionListener) {
            listener = (OnMainFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMainFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            highScoreValue = getArguments().getInt(ARG_HIGH_SCORE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);

        highScore.setText(highScoreValue + "");

        return view;
    }

    public interface OnMainFragmentInteractionListener {
        void onStartGame();
    }

}
