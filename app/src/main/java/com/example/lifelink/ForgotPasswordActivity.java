package com.example.lifelink;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText resetEmail, newPassword, confirmPassword;
    private Button resetBtn;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        db = new DatabaseHelper(this);

        resetEmail = findViewById(R.id.resetEmail);
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        resetBtn = findViewById(R.id.resetBtn);

        resetBtn.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        String email = resetEmail.getText().toString().trim();
        String newPass = newPassword.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(email)) {
            resetEmail.setError("Enter your registered email");
            return;
        }
        if (TextUtils.isEmpty(newPass)) {
            newPassword.setError("Enter new password");
            return;
        }
        if (newPass.length() < 6) {
            newPassword.setError("Password must be at least 6 characters");
            return;
        }
        if (!newPass.equals(confirmPass)) {
            confirmPassword.setError("Passwords do not match");
            return;
        }

        // Check if user exists
        User user = db.getUserByEmail(email);
        if (user == null) {
            Toast.makeText(this, "Email not registered", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update password
        boolean updated = db.updateUserPassword(user.getId(), newPass);
        if (updated) {
            Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_LONG).show();
            finish(); // Go back to login
        } else {
            Toast.makeText(this, "Error resetting password", Toast.LENGTH_SHORT).show();
        }
    }
}
