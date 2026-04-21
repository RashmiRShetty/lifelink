package com.example.lifelink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText loginEmail, loginPassword;
    Button loginBtn;
    TextView goToRegister, forgotPassword;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        // Initialize views
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        goToRegister = findViewById(R.id.goToRegister);
        forgotPassword = findViewById(R.id.forgotPassword);

        // Login button click
        loginBtn.setOnClickListener(v -> {
            String email = loginEmail.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            if (!validateInput(email, password)) return;

            // Attempt login
            User user = db.loginUser(email, password);
            if (user != null) {
                // Save userId in SharedPreferences
                SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
                prefs.edit().putInt("userId", user.getId()).apply();

                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                // Go to Role Selection Activity
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        // Register link
        goToRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );

        // Forgot password link
        forgotPassword.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class))
        );
    }

    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            loginEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
            loginEmail.requestFocus();
            return false;
        }
        if (!isValidPassword(password)) {
            Toast.makeText(this, "Password must be at least 6 characters, " +
                    "contain uppercase, lowercase, digit, and special character", Toast.LENGTH_LONG).show();
            loginPassword.requestFocus();
            return false;
        }
        return true;
    }

    // Password validation method
    private boolean isValidPassword(String password) {
        if (TextUtils.isEmpty(password) || password.length() < 6)
            return false;

        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[^a-zA-Z0-9]");

        return uppercase.matcher(password).find() &&
                lowercase.matcher(password).find() &&
                digit.matcher(password).find() &&
                special.matcher(password).find();
    }
}
