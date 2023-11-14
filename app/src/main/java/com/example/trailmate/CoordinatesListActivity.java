package com.example.trailmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CoordinatesListActivity extends AppCompatActivity {

    private ListView mListView;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private ArrayList<Coordinate> mCoordinatesList;
    private ArrayAdapter<Coordinate> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinates_list);

        mListView = findViewById(R.id.list_view);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Coordinates");
        mCoordinatesList = new ArrayList<>();

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCoordinatesList);
        mListView.setAdapter(mAdapter);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCoordinatesList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Coordinate coordinate = ds.getValue(Coordinate.class);
                    mCoordinatesList.add(coordinate);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Coordinate coordinate = mCoordinatesList.get(position);
                Intent intent = new Intent(CoordinatesListActivity.this, CoordinateDetailsActivity.class);
                intent.putExtra("title", coordinate.getTitle());
                intent.putExtra("latitude", coordinate.getLatitude());
                intent.putExtra("longitude", coordinate.getLongitude());
                startActivity(intent);
            }
        });
    }
}
