package com.example.lifelink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameInput, phoneInput, dobInput, cityInput;
    private RadioGroup bloodGroupRG;
    private Button updateBtn, logoutBtn;
    private TextView emailText;

    private DatabaseHelper db;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new DatabaseHelper(this);

        // ✅ Get userId from SharedPreferences safely
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "No user ID found. Please login again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        currentUser = db.getUserById(userId);

        if (currentUser == null) {
            Toast.makeText(this, "User not found in database", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initializeViews();
        populateUserData();
        setupListeners();
    }

    private void initializeViews() {
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        dobInput = findViewById(R.id.dobInput);
        cityInput = findViewById(R.id.cityInput);
        bloodGroupRG = findViewById(R.id.bloodGroupRGProfile);
        updateBtn = findViewById(R.id.updateBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        emailText = findViewById(R.id.emailText);
    }

    private void populateUserData() {
        nameInput.setText(currentUser.getName());
        phoneInput.setText(currentUser.getPhone());
        dobInput.setText(currentUser.getDob());
        cityInput.setText(currentUser.getCity());
        emailText.setText(currentUser.getEmail());

        // ✅ Correctly pre-select saved blood group from DB
        String savedGroup = currentUser.getBloodGroup();
        if (savedGroup != null) {
            for (int i = 0; i < bloodGroupRG.getChildCount(); i++) {
                if (bloodGroupRG.getChildAt(i) instanceof LinearLayout) {
                    LinearLayout row = (LinearLayout) bloodGroupRG.getChildAt(i);
                    for (int j = 0; j < row.getChildCount(); j++) {
                        if (row.getChildAt(j) instanceof RadioButton) {
                            RadioButton rb = (RadioButton) row.getChildAt(j);
                            if (rb.getText().toString().equalsIgnoreCase(savedGroup)) {
                                rb.setChecked(true);
                                return; // ✅ Stop after finding match
                            }
                        }
                    }
                } else if (bloodGroupRG.getChildAt(i) instanceof RadioButton) {
                    RadioButton rb = (RadioButton) bloodGroupRG.getChildAt(i);
                    if (rb.getText().toString().equalsIgnoreCase(savedGroup)) {
                        rb.setChecked(true);
                        return;
                    }
                }
            }
        }
    }

    private void setupListeners() {
        updateBtn.setOnClickListener(v -> updateProfile());
        logoutBtn.setOnClickListener(v -> logout());
    }

    private void updateProfile() {
        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String dob = dobInput.getText().toString().trim();
        String city = cityInput.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(dob) || TextUtils.isEmpty(city)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int checkedBloodId = bloodGroupRG.getCheckedRadioButtonId();
        if (checkedBloodId == -1) {
            Toast.makeText(this, "Please select a blood group", Toast.LENGTH_SHORT).show();
            return;
        }

        String blood = ((RadioButton) findViewById(checkedBloodId)).getText().toString();

        int result = db.updateUserProfile(
                currentUser.getId(),
                name,
                phone,
                blood,
                dob,
                city
        );

        if (result > 0) {
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            currentUser = db.getUserById(currentUser.getId()); // Refresh local data
            populateUserData(); // Refresh UI
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }

    private void logout() {
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        prefs.edit().clear().apply(); // clear session

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
