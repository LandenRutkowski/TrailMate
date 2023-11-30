package com.example.trailmate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CoordinatesListActivity extends MenuActivity {

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
                intent.putExtra("description", coordinate.getDescription());
                startActivity(intent);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Coordinate coordinate = mCoordinatesList.get(position);
                showUpdateDeleteDialog(coordinate.getTitle(), coordinate.getLatitude(), coordinate.getLongitude(), coordinate.getDescription());
            return false;
            }
        });
    }

    private void showUpdateDeleteDialog(final String title, final double latitude, final double longitude, final  String description) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextLatitude = (EditText) dialogView.findViewById(R.id.editTextLatitude);
        final EditText editTextLongitude = (EditText) dialogView.findViewById(R.id.editTextLongitude);
        final EditText editTextDescription = (EditText) dialogView.findViewById(R.id.editTextDescription);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateCoordinate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteCoordinate);

        dialogBuilder.setTitle("Updating Coordinate " + title);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle = editTextTitle.getText().toString().trim();
                String newLatitudeText = editTextLatitude.getText().toString().trim();
                String newLongitudeText = editTextLongitude.getText().toString().trim();
                String newDescription = editTextDescription.getText().toString().trim();

                // Check and update individual fields
                if (!TextUtils.isEmpty(newTitle)) {
                    updateCoordinate(title, newTitle, latitude, longitude, description);
                }

                if (!TextUtils.isEmpty(newLatitudeText)) {
                    double newLatitude = Double.parseDouble(newLatitudeText);
                    updateCoordinate(title, title, newLatitude, longitude, description);
                }

                if (!TextUtils.isEmpty(newLongitudeText)) {
                    double newLongitude = Double.parseDouble(newLongitudeText);
                    updateCoordinate(title, title, latitude, newLongitude, description);
                }

                if (!TextUtils.isEmpty(newDescription)) {
                    updateCoordinate(title, title, latitude, longitude, newDescription);
                }

                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCoordinate(title);
                alertDialog.dismiss();
            }
        });
    }

    private boolean updateCoordinate(String oldTitle, String newTitle, double newLatitude, double newLongitude, String newDescription) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Coordinates").child(oldTitle);
        Coordinate coordinate = new Coordinate(newTitle, newLatitude, newLongitude, newDescription);
        dR.setValue(coordinate);
        Toast.makeText(getApplicationContext(), "Coordinate Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteCoordinate(String title) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Coordinates").child(title);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Coordinate Deleted", Toast.LENGTH_LONG).show();
        return true;
    }
}
