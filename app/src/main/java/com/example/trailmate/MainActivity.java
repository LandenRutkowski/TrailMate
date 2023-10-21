package com.example.trailmate;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.trailmate.fragments.EulaDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.trailmate.models.UserInfo;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;

/**
 * Based on adamcchampion.
 * Created by Muhammad Abedeljaber and Landen Rutkowski, 2023/10/05.
 */



public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private EditText employeeUsernameEdt, employeePasswordEdt, employeeEmailEdt;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
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
        databaseReference = firebaseDatabase.getReference("EmployeeInfo");
        userInfo = new UserInfo();

        Button sendDatabtn = findViewById(R.id.idBtnSendData);

        // adding on click listener for our button.
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting text from our edittext fields.
                String username = employeeUsernameEdt.getText().toString();
                String email = employeeEmailEdt.getText().toString();
                String password = employeePasswordEdt.getText().toString();

                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {

                    Toast.makeText(MainActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {

                    addDatatoFirebase(username, email, password);
                }
            }
        });

        Button showDatabtn = findViewById(R.id.idBtnShowData);

        showDatabtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Display data
            }
        });

        Button mapbtn = findViewById(R.id.idBtnMap);

        mapbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setUpEula();

                //Display Map
            }
        });
    }

    private void addDatatoFirebase(String name, String phone, String address) {

        userInfo.setUserame(name);
        userInfo.setPassword(phone);
        userInfo.setEmail(address);

        //Wil need to change to auth when its setup
        userInfo.setUserID(1);
        String child = String.valueOf(userInfo.getUserID());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                databaseReference.child("users").child(child).setValue(userInfo);

                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpEula() {
        Timber.tag(TAG).d("setUpEula");
        //FragmentActivity activity = requireActivity();

        //SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        //boolean isEulaAccepted = sharedPrefs.getBoolean(getString(R.string.eula_accepted_key), false);
        //if (!isEulaAccepted) {
            //DialogFragment eulaDialogFragment = new EulaDialogFragment();
            //eulaDialogFragment.show(activity.getSupportFragmentManager(), "eula");
        //}
    }
}
