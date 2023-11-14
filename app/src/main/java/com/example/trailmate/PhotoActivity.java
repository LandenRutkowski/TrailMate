package com.example.trailmate;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PhotoActivity extends AppCompatActivity {

    private FirebaseStorage storage;
    private StorageReference storageReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }
}
