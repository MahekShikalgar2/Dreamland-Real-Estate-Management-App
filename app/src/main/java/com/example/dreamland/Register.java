package com.example.dreamland;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Register extends AppCompatActivity {


    private boolean passwordshow = false;
    private boolean conpasswordshow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText email = findViewById(R.id.emailEdittext);
        EditText mobile = findViewById(R.id.mobileEdittext);
        EditText password = findViewById(R.id.passwordEdittext);
        EditText conPassword = findViewById(R.id.confpasswordEdittext);

        ImageView passwordIcon = findViewById(R.id.passwordicon);
        ImageView conPasswordIcon = findViewById(R.id.confpasswordIcon);

        AppCompatButton signUpBtn = findViewById(R.id.signUpBtn);
        TextView signInBtn = findViewById(R.id.signInBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String getMobiletxt = mobile.getText().toString();
                final String getEmailtxt = email.getText().toString();

                Intent  intent = new Intent(Register.this, OtpVefication.class);
                intent.putExtra("mobile",getMobiletxt);
                intent.putExtra("email",getEmailtxt);
                startActivity(intent);

            }
        });
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(passwordshow)
                {
                    passwordshow = false;
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.baseline_thumb_down_24);

                }
                else {
                    passwordshow = true;
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                    passwordIcon.setImageResource(R.drawable.outline_thumb_up_24);
                }
                password.setSelection(password.length());
            }
        });

        conPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(conpasswordshow)
                {
                    conpasswordshow = false;
                    conPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.baseline_thumb_down_24);

                }
                else {
                    conpasswordshow = true;
                    conPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                    conPasswordIcon.setImageResource(R.drawable.outline_thumb_up_24);
                }
                conPassword.setSelection(conPassword.length());




            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,LoginActivity.class);
                finish();
            }
        });

    }
}