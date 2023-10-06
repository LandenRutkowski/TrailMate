package com.example.trailmate;

import androidx.fragment.app.Fragment;

/**
 * Based on adamcchampion.
 * Created by Muhammad Abedeljaber and Landen Rutkowski, 2023/10/05.
 */

public class MapsActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new MapsFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
