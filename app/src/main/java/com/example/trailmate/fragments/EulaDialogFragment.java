package com.example.trailmate.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;
import com.example.trailmate.R;
import com.example.trailmate.Utils;

import timber.log.Timber;

/*
 * Code from Github (Adam Champion):
 * https://github.com/acchampion/WhereAmI
 */
public class EulaDialogFragment extends DialogFragment {
    private final static String TAG = EulaDialogFragment.class.getSimpleName();

    public void setEulaAccepted() {
        Timber.tag(TAG).d("setEulaAccepted");
        Activity activity = requireActivity();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getString(R.string.eula_accepted_key), true).apply();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Timber.tag(TAG).d("onCreateDialog");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.about_app)
                .setMessage(Utils.fromHtml(getString(R.string.eula)))
                .setPositiveButton(R.string.accept, (dialog, id) -> {
                    Timber.tag(TAG).d("EULA Accepted");
                    setEulaAccepted();
                })
                .setNegativeButton(R.string.decline, (dialog, which) -> {
                    Timber.tag(TAG).d("EULA Declined");
                    dialog.cancel();
                    requireActivity().finish();
                    System.exit(1);
                });
        return builder.create();
    }
}
