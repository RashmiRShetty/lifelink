package com.example.lifelink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DonateActivity extends AppCompatActivity {

    private EditText unitsInput, locationInput;
    private RadioGroup bloodGroupRG;
    private Button submitBtn;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        // Initialize views
        unitsInput = findViewById(R.id.unitsInput);
        locationInput = findViewById(R.id.locationInput);
        bloodGroupRG = findViewById(R.id.bloodGroupRGDonate);
        submitBtn = findViewById(R.id.submitDonationBtn);
        db = new DatabaseHelper(this);

        // Initialize bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_donate);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(this, DashboardActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                case R.id.nav_donate:
                    return true;
                case R.id.nav_request:
                    startActivity(new Intent(this, RequestBloodActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                case R.id.nav_profile:
                    SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
                    int userId = prefs.getInt("userId", -1);
                    if (userId != -1) {
                        Intent intent = new Intent(this, ProfileActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                    return true;
            }
            return false;
        });

        // Handle donation form submission
        submitBtn.setOnClickListener(v -> submitDonation());
    }

    private void submitDonation() {
        // Get user ID from shared preferences
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected blood group
        int selectedId = bloodGroupRG.getCheckedRadioButtonId();
        String bloodGroup = "";

        if (selectedId == R.id.rbOplus) bloodGroup = "O+";
        else if (selectedId == R.id.rbOminus) bloodGroup = "O-";
        else if (selectedId == R.id.rbAplus) bloodGroup = "A+";
        else if (selectedId == R.id.rbAminus) bloodGroup = "A-";
        else if (selectedId == R.id.rbBplus) bloodGroup = "B+";
        else if (selectedId == R.id.rbBminus) bloodGroup = "B-";
        else if (selectedId == R.id.rbABplus) bloodGroup = "AB+";
        else if (selectedId == R.id.rbABminus) bloodGroup = "AB-";

        if (bloodGroup.isEmpty()) {
            Toast.makeText(this, "Please select blood group", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate units
        String unitsStr = unitsInput.getText().toString().trim();
        if (unitsStr.isEmpty()) {
            Toast.makeText(this, "Please enter units", Toast.LENGTH_SHORT).show();
            return;
        }
        int units = Integer.parseInt(unitsStr);

        // Validate location
        String location = locationInput.getText().toString().trim();
        if (location.isEmpty()) {
            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current date
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                .format(new java.util.Date());

        // Save donation to database
        long result = db.insertDonation(userId, bloodGroup, units, location, date);

        if (result > 0) {
            Toast.makeText(this, "Donation submitted successfully!", Toast.LENGTH_SHORT).show();

            // Get user's name from database
            User user = db.getUserById(userId);
            String userName = user != null ? user.getName() : "Anonymous Donor";

            // Generate certificate
            String fileName = CertificateGenerator.generateCertificate(
                    this,  // context
                    userName // donor name
            );

            if (fileName != null) {
                // Show success message with file name
                Toast.makeText(this,
                        "Certificate saved to Downloads folder: " + fileName,
                        Toast.LENGTH_LONG).show();
            }

            finish();
        } else {
            Toast.makeText(this, "Failed to submit donation", Toast.LENGTH_SHORT).show();
        }
    }
}
