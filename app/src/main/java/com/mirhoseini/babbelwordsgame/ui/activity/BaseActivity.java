package com.mirhoseini.babbelwordsgame.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mirhoseini.babbelwordsgame.GameApplication;
import com.mirhoseini.babbelwordsgame.di.ApplicationComponent;


/**
 * Created by Mohsen on 07/06/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDependencies(GameApplication.getComponent());

        // can be used for general purpose in all Activities of Application

    }

    protected abstract void injectDependencies(ApplicationComponent component);

}
