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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private FirebaseAuth firebaseAuth;
    private EditText password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        firebaseAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.idLogin);
        password = findViewById(R.id.idPassword);
        email = findViewById(R.id.idEmail);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLogin();
            }
        });
    }

    private void userLogin() {

        String pass = password.getText().toString();
        String em = email.getText().toString();

        if(TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(em)) {
            Toast.makeText(this,"Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(em, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //Start profile Activity
                            startActivity(new Intent(getApplicationContext(),HostActivity.class));
                        } else {
                            loginFailed();
                        }
                    }
                });
    }

    void loginFailed() {

        Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show();
    }
}
