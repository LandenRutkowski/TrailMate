package com.example.trailmate;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maps_menu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int itemId = item.getItemId();

        if (itemId == R.id.menu_english_lang) {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            finish();
            startActivity(new Intent(getApplicationContext(), this.getClass()));
        }

        if (itemId == R.id.menu_arabic_lang) {
            Locale locale = new Locale("ar", "PS");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            finish();
            startActivity(new Intent(getApplicationContext(), this.getClass()));

        }
        return false;
    }
}
