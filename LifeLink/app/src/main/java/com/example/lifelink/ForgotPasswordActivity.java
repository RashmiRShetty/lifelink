package com.example.lifelink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailInput;
    Button sendOtpBtn;
    TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailInput = findViewById(R.id.emailInput);
        sendOtpBtn = findViewById(R.id.sendOtpBtn);
        backToLogin = findViewById(R.id.backToLogin);

        // Send OTP button click (currently just shows a Toast)
        sendOtpBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else {
                // In real app: generate & send OTP to email
                Toast.makeText(this, "OTP sent to: " + email, Toast.LENGTH_SHORT).show();

                // TODO: Start OTP verification screen here if needed
            }
        });

        // Back to Login screen
        backToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
