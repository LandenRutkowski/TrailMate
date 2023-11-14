package com.example.trailmate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.startup.AppInitializer;

import com.example.trailmate.models.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;

import com.mapbox.maps.loader.MapboxMapsInitializer;

/**
 * Based on adamcchampion.
 * Created by Muhammad Abedeljaber and Landen Rutkowski, 2023/10/05.
 */



public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private EditText employeeUsernameEdt, employeePasswordEdt, employeeEmailEdt;
    private Button sendData, trails;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree()); //Google said to ignore, version issue
        Timber.tag(TAG).d("onCreate");

        setContentView(R.layout.activity_main);

        employeeUsernameEdt = findViewById(R.id.idEdtUsername);
        employeeEmailEdt = findViewById(R.id.idEdtEmail);
        employeePasswordEdt = findViewById(R.id.idEdtPassword);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");
        auth = FirebaseAuth.getInstance();
        userInfo = new UserInfo();

        sendData = findViewById(R.id.idBtnSendData);
        trails = findViewById(R.id.idBtnTrails);

        // adding on click listener for our button.
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting text from our edittext fields.
                String username = employeeUsernameEdt.getText().toString().trim();
                String password = employeePasswordEdt.getText().toString().trim();
                String email = employeeEmailEdt.getText().toString().trim();

                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {

                    Toast.makeText(MainActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {

                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "SignUp Unsuccessful , Please Try Again...", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onComplete: Failed=" + task.getException().getMessage());
                            }
                            else{
                                addDatatoFirebase(username, password, email);
                                startActivity(new Intent(getApplicationContext(), HostActivity.class));
                            }
                        }
                    });
                }
            }
        });

        // Mapbox
        AppInitializer.getInstance(this)
                .initializeComponent(MapboxMapsInitializer.class);

        trails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Activity activity = requireActivity();
                //startActivity(new Intent(getApplicationContext(), TrailsActivity.class));

                //startActivity(new Intent(getApplicationContext(), MapsActivity2.class));
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                //activity.finish();
            }
        });
    }

    private void addDatatoFirebase(String name, String password, String email) {

        userInfo.setUserame(name);
        userInfo.setPassword(password);
        userInfo.setEmail(email);

        FirebaseUser user = auth.getCurrentUser();
        userInfo.setUserID(Integer.parseInt(user.getUid()));
        String child = String.valueOf(userInfo.getUserID());

        databaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                databaseReference.child("Users").child(child).setValue(userInfo);

                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
