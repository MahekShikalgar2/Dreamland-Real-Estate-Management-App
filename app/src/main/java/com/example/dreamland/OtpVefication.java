package com.example.dreamland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OtpVefication extends AppCompatActivity {

    private EditText otpEt1,otpEt2,otpEt3,otpEt4;
    private TextView resentBtn;
    private boolean resendEnabled= false;

    private int resendTime = 15;
    private int selectedETPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_vefication);

        otpEt1 = findViewById(R.id.otpET1);
        otpEt2 = findViewById(R.id.otpET2);
        otpEt3 = findViewById(R.id.otpET3);
        otpEt4 = findViewById(R.id.otpET4);

        resentBtn = findViewById(R.id.resendCodeBtn);
        final Button verifybtn = findViewById(R.id.verifyBtn);
        final TextView  otpEmail = findViewById(R.id.otpEmail);
        final TextView  otpMobile = findViewById(R.id.otpmobile);

        final String getEmail  = getIntent().getStringExtra("email");
        final String getMobile  = getIntent().getStringExtra("mobile");

        otpEmail.setText(getEmail);
        otpMobile.setText(getMobile);


        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);


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
                 final String generateOtp = otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt3.getText().toString();
                 if(generateOtp.length()==4)
                 {

                 }
            }
        });

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
            }
        }
    };


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (selectedETPosition == 3) {
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