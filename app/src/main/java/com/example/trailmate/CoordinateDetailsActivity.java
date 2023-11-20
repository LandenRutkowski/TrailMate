package com.example.trailmate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CoordinateDetailsActivity extends AppCompatActivity {
    private TextView mTitleTextView;
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private TextView mDescriptionTextView;

    private Uri imgUri;

    private Button mShowOnMapButton, showPhotosButton, addPhotoButton;

    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate_details);

        mTitleTextView = findViewById(R.id.title_text_view);
        mLatitudeTextView = findViewById(R.id.latitude_text_view);
        mLongitudeTextView = findViewById(R.id.longitude_text_view);
        mDescriptionTextView = findViewById(R.id.description_text_view);

        firebaseDatabase = FirebaseDatabase.getInstance();

        String title = getIntent().getStringExtra("title");
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);
        String description = getIntent().getStringExtra("description");

        mTitleTextView.setText(title);
        mLatitudeTextView.setText(String.valueOf(latitude));
        mLongitudeTextView.setText(String.valueOf(longitude));
        mDescriptionTextView.setText(description);

        mShowOnMapButton = findViewById(R.id.show_on_map_button);

        mShowOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoordinateDetailsActivity.this, HostActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
    }
}
