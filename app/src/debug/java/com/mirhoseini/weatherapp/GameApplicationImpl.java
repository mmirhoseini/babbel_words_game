package com.mirhoseini.babbelwordsgame;

import timber.log.Timber;

public class GameApplicationImpl extends GameApplication {

    @Override
    void initApplication() {
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                //adding line number to tag
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });
    }
}
