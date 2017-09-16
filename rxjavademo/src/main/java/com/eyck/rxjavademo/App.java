package com.eyck.rxjavademo;

import android.app.Application;

/**
 * Created by Eyck on 2017/8/30.
 */

public class App extends Application {
    private static App INSTANCE;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
