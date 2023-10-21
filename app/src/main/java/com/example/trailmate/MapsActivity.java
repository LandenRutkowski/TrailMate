package com.example.trailmate;

import androidx.fragment.app.Fragment;

import com.example.trailmate.fragments.MapsFragment;

import timber.log.Timber;

/**
 * Based on adamcchampion.
 * Created by Muhammad Abedeljaber and Landen Rutkowski, 2023/10/05.
 */

public class MapsActivity extends SingleFragmentActivity{
    private final static String TAG = MapsActivity.class.getSimpleName();
    @Override
    protected Fragment createFragment() {
        Timber.tag(TAG).d("createFragment");
        return new MapsFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.tag(TAG).d("onResume");
    }
}
