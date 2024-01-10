package com.example.dreamland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private boolean passwordshowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameET = findViewById(R.id.usernameEdittext);
        final EditText passwordET = findViewById(R.id.passwordEdittext);
        final ImageView passwordIcon = findViewById(R.id.passwordicon);

        final TextView signUpBtn = findViewById(R.id.signUpBtn);


        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordshowing)
                {
                    passwordshowing = false;
                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.baseline_thumb_down_24);

                }
                else {
                    passwordshowing = true;
                    passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
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