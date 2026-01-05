package com.example.dreamland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private boolean passwordshowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is already authenticated
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // If the user is already logged in, redirect to the main activity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish(); // Finish the LoginActivity to prevent going back to it
        } else {
            // If the user is not logged in, proceed with the login activity
            setContentView(R.layout.activity_login);

            final EditText usernameET = findViewById(R.id.usernameEdittext);
            final EditText passwordET = findViewById(R.id.passwordEdittext);
            final ImageView passwordIcon = findViewById(R.id.passwordicon);

            final TextView signUpBtn = findViewById(R.id.signUpBtn);

            passwordIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (passwordshowing) {
                        passwordshowing = false;
                        passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passwordIcon.setImageResource(R.drawable.baseline_thumb_down_24);
                    } else {
                        passwordshowing = true;
                        passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passwordIcon.setImageResource(R.drawable.outline_thumb_up_24);
                    }
                    passwordET.setSelection(passwordET.length());
                }
            });

            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, Register.class));
                }
            });
        }
    }
}
