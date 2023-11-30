package com.example.trailmate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

/**
 * Based on adamcchampion.
 * Created by Muhammad Abedeljaber and Landen Rutkowski, 2023/10/05.
 */

public class HostActivity extends MenuActivity {

    private Button logout;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        logout = findViewById(R.id.logout);
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
    }



}
