package com.example.trailmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trailmate.models.Coordinate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCoordinatesActivity extends MenuActivity {
    EditText mTitleEditText;
    EditText mLatitudeEditText;
    EditText mLongitudeEditText;
    EditText mDescriptionEditText;
    Button mSaveButton;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coordinates);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Coordinates");


        mTitleEditText = findViewById(R.id.title_edit_text);
        mLatitudeEditText = findViewById(R.id.latitude_edit_text);
        mLongitudeEditText = findViewById(R.id.longitude_edit_text);
        mDescriptionEditText = findViewById(R.id.description_edit_text);
        mSaveButton = findViewById(R.id.save_button);

        Intent intent = getIntent();
        if (intent != null) {
            double latitude = intent.getDoubleExtra("latitude", 0);
            double longitude = intent.getDoubleExtra("longitude", 0);
            String title = intent.getStringExtra("title");

            // Autofill EditText fields
            mLatitudeEditText.setText(String.valueOf(latitude));
            mLongitudeEditText.setText(String.valueOf(longitude));
            mTitleEditText.setText(title);
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mTitleEditText.getText().toString();
                double latitude = Double.parseDouble(mLatitudeEditText.getText().toString());
                double longitude = Double.parseDouble(mLongitudeEditText.getText().toString());
                String description = mDescriptionEditText.getText().toString();

                // Save the title, latitude, and longitude to Firebase
                String id = mRef.push().getKey();
                Coordinate coordinate = new Coordinate(title, latitude, longitude, description);
                mRef.child(title).setValue(coordinate);

                Toast.makeText(AddCoordinatesActivity.this, "Coordinates added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), CoordinatesListActivity.class));
            }
        });
    }
}

