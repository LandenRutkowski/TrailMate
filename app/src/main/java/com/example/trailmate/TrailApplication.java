package com.example.trailmate;

import android.app.Application;
import timber.log.Timber;

/**
 * Based on adamcchampion.
 * Created by Muhammad Abedeljaber and Landen Rutkowski, 2023/10/05.
 */



public class TrailApplication extends Application {
    private final static String TAG = TrailApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree()); //Google said to ignore, version issue
        Timber.tag(TAG).d("onCreate");
    }

}
