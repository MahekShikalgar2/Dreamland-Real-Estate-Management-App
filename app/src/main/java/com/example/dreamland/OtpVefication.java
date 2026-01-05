package com.example.dreamland;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVefication extends AppCompatActivity {

    private EditText otpEt1,otpEt2,otpEt3,otpEt4,otpEt5,otpEt6;
    private TextView resentBtn;
    private boolean resendEnabled= false;
    String verficationId;
    private int resendTime = 40;
    private int selectedETPosition = 0;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_vefication);

        otpEt1 = findViewById(R.id.otpET1);
        otpEt2 = findViewById(R.id.otpET2);
        otpEt3 = findViewById(R.id.otpET3);
        otpEt4 = findViewById(R.id.otpET4);
        otpEt5 = findViewById(R.id.otpET5);
        otpEt6 = findViewById(R.id.otpET6);
        mAuth = FirebaseAuth.getInstance();


        resentBtn = findViewById(R.id.resendCodeBtn);
        final Button verifybtn = findViewById(R.id.verifyBtn);
        final TextView  otpEmail = findViewById(R.id.otpEmail);
        final TextView  otpMobile = findViewById(R.id.otpmobile);

        final String getEmail  = getIntent().getStringExtra("email");
        final String getMobile  = getIntent().getStringExtra("mobile");
        final String getfullname  = getIntent().getStringExtra("fullname");
        final String getpassword  = getIntent().getStringExtra("password");

        otpEmail.setText(getEmail);
        otpMobile.setText(getMobile);


        sendverficationcode(getMobile);

        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);
        otpEt5.addTextChangedListener(textWatcher);
        otpEt6.addTextChangedListener(textWatcher);


        showkeyboard(otpEt1);

        startCountDownTimer();

        resentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(resendEnabled)
                {
                    startCountDownTimer();
                }


            }
        });

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 final String generateOtp = otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString()+otpEt5.getText().toString()+otpEt6.getText().toString();
                 if(generateOtp.length()==6)
                 {
                     Toast.makeText(OtpVefication.this, "OTP : "+generateOtp, Toast.LENGTH_SHORT).show();
                         verifycode(generateOtp);

                 }
                 else {
                     Toast.makeText(OtpVefication.this, "Invalid Otp", Toast.LENGTH_SHORT).show();

                 }
            }
        });

    }
    private void verifycode(String code) {

        PhoneAuthCredential cred = PhoneAuthProvider.getCredential(verficationId,code);
        signinbyCred(cred);

    }
    private void signinbyCred(PhoneAuthCredential cred) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(cred).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(OtpVefication.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OtpVefication.this,MainActivity.class));
                }
                else {
                    Toast.makeText(OtpVefication.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String code = credential.getSmsCode();
            if(code!=null)
            {
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OtpVefication.this, "Verification Failed", Toast.LENGTH_SHORT).show();

            // Show a message and update the UI
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s,token);
            verficationId = s;
            Toast.makeText(OtpVefication.this, "Code Sent. wait for 10 seconds", Toast.LENGTH_SHORT).show();


        }
    };
    private void sendverficationcode(String number) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void showkeyboard(EditText otpET){

        otpET.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET,InputMethodManager.SHOW_IMPLICIT);

    }

    private void startCountDownTimer(){
        resendEnabled = false;
        resentBtn.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime * 1000 , 1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                resentBtn.setText("Resend Code ("+(millisUntilFinished/1000)+")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resentBtn.setText("Resend Code");
                resentBtn.setTextColor(getResources().getColor(R.color.primary));

            }
        }.start();
    }
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {


            if(s.length() > 0)
            {
                if(selectedETPosition == 0)
                {
                    selectedETPosition = 1;
                    showkeyboard(otpEt2);
                }
                else if(selectedETPosition ==1)
                {
                    selectedETPosition = 2;
                    showkeyboard(otpEt3);
                }
                else if(selectedETPosition == 2)
                {
                    selectedETPosition = 3;
                    showkeyboard(otpEt4);
                }
                else if(selectedETPosition == 3)
                {
                    selectedETPosition = 4;
                    showkeyboard(otpEt5);
                }
                else if(selectedETPosition == 4)
                {
                    selectedETPosition = 5;
                    showkeyboard(otpEt6);
                }
            }
        }
    };


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_DEL) {

//            if (selectedETPosition == 3) {
//                selectedETPosition = 2;
//                showkeyboard(otpEt3);
//            } else if (selectedETPosition == 2) {
//                selectedETPosition = 1;
//                showkeyboard(otpEt2);
//            } else if (selectedETPosition == 1) {
//                selectedETPosition = 0;
//                showkeyboard(otpEt1);
//            }
            if (selectedETPosition == 5) {
                selectedETPosition = 4;
                showkeyboard(otpEt5);
            } else if (selectedETPosition == 4) {
                selectedETPosition = 3;
                showkeyboard(otpEt4);
            } else if (selectedETPosition == 3) {
                selectedETPosition = 2;
                showkeyboard(otpEt3);
            } else if (selectedETPosition == 2) {
                selectedETPosition = 1;
                showkeyboard(otpEt2);
            } else if (selectedETPosition == 1) {
                selectedETPosition = 0;
                showkeyboard(otpEt1);
            }
            return true;
        }
        else {
            return super.onKeyUp(keyCode, event);

        }


    }


}