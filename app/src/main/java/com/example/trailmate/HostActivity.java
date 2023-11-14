package com.example.trailmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.trailmate.fragments.MapsFragment;
import com.google.firebase.auth.FirebaseAuth;

import timber.log.Timber;

/**
 * Based on adamcchampion.
 * Created by Muhammad Abedeljaber and Landen Rutkowski, 2023/10/05.
 */

public class HostActivity extends AppCompatActivity {

    private Button logout, showTrails;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        logout = findViewById(R.id.logout);
        showTrails = findViewById(R.id.showTrails);
        auth = FirebaseAuth.getInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MapsActivity2())
                    .commit();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        showTrails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), PhotoActivity.class));
            }
        });
    }
}
