package com.example.trailmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CoordinateDetailsActivity extends AppCompatActivity {
    private TextView mTitleTextView;
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private TextView mDescriptionTextView;

    private Button mShowOnMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate_details);

        mTitleTextView = findViewById(R.id.title_text_view);
        mLatitudeTextView = findViewById(R.id.latitude_text_view);
        mLongitudeTextView = findViewById(R.id.longitude_text_view);
        mDescriptionTextView = findViewById(R.id.description_text_view);

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
                startActivity(intent);
            }
        });

    }
}
