package com.example.trailmate;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

import com.google.android.gms.maps.SupportMapFragment;

import com.example.trailmate.R;

/**
 * Class for Maps Fragment. Sources:
 * - Big Nerd Ranch Guide to Android Programming, Chap. 34
 * - Google: <a href="https://developers.google.com/maps/documentation/android-api/current-place-tutorial">...</a>
 * <p>
 * Based on adamcchampion, 2017/09/24.
 * Created by Muhammad Abedeljaber and Landen Rutkowski, 2023/10/05.
 */

public class MapsFragment extends SupportMapFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpEula();
    }


    @Override
    public void onStart() {
        super.onStart();
        Activity activity = requireActivity();
        activity.invalidateOptionsMenu();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void setUpEula() {
        FragmentActivity activity = requireActivity();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        boolean isEulaAccepted = sharedPrefs.getBoolean(getString(R.string.eula_accepted_key), false);
        if (!isEulaAccepted) {
            DialogFragment eulaDialogFragment = new EulaDialogFragment();
            eulaDialogFragment.show(activity.getSupportFragmentManager(), "eula");
        }
    }



}
