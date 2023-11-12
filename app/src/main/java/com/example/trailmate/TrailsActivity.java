package com.example.trailmate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.trailmate.models.TrailInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TrailsActivity extends AppCompatActivity {

    private EditText titleEdt, lengthEdt;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TrailInfo trailInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trails);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("TrailInfo");

        trailInfo = new TrailInfo();

        Button back = findViewById(R.id.backBtn2);
        Button sendData = findViewById(R.id.idSendData);
        Button deleteData = findViewById(R.id.idDeleteData);
        titleEdt = findViewById(R.id.idTitle);
        lengthEdt = findViewById(R.id.idLength);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting text from our edittext fields.
                String title = titleEdt.getText().toString();
                String length = lengthEdt.getText().toString();

                if (TextUtils.isEmpty(title) && TextUtils.isEmpty(length)) {

                    Toast.makeText(TrailsActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {

                    addDatatoFirebase(title, length);
                }
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEdt.getText().toString();

                if (TextUtils.isEmpty(title)) {

                    Toast.makeText(TrailsActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {

                    deleteDataFromFirebase(title);
                }
            }
        });
    }

    private void deleteDataFromFirebase(String title) {

        String child = title;
        Query query = databaseReference.child("Trails");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().child(child).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addDatatoFirebase(String title, String length) {

        trailInfo.setTrailName(title);
        trailInfo.setTrailLength(length);
        String child = title;

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                databaseReference.child("Trails").child(child).setValue(trailInfo);

                Toast.makeText(TrailsActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(TrailsActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
