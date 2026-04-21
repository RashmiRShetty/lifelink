package com.example.lifelink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DonorHomeActivity extends AppCompatActivity {

    Button btnDonateNow, btnDonationHistory, btnProfile, btnNearbyRequests, btnLogout, btnLastDonation, btnQuote;
    boolean isProfileCreated = false; // replace with session or sharedPreferences if needed
    String lastDonationDate = "2025-05-01"; // for example

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_home);

        btnDonateNow = findViewById(R.id.btnDonateNow);
        btnDonationHistory = findViewById(R.id.btnDonationHistory);
        btnProfile = findViewById(R.id.btnProfile);
        btnNearbyRequests = findViewById(R.id.btnNearbyRequests);
        btnLastDonation = findViewById(R.id.btnLastDonation);
        btnQuote = findViewById(R.id.btnQuote);
        btnLogout = findViewById(R.id.btnLogout);

        btnDonateNow.setOnClickListener(v -> startActivity(new Intent(this, DonateNowActivity.class)));

        btnDonationHistory.setOnClickListener(v -> startActivity(new Intent(this, DonationHistoryActivity.class)));

        btnProfile.setOnClickListener(v -> {
            if (isProfileCreated) {
                startActivity(new Intent(this, ViewProfileActivity.class));
            } else {
                Toast.makeText(this, "Profile not found. Please create one.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CreateProfileActivity.class));
            }
        });

        btnNearbyRequests.setOnClickListener(v -> startActivity(new Intent(this, NearbyRequestsActivity.class)));

        btnLastDonation.setOnClickListener(v -> {
            Toast.makeText(this, "Last Donated: " + lastDonationDate + "\nEligible after 3 months", Toast.LENGTH_LONG).show();
        });

        btnQuote.setOnClickListener(v -> {
            String[] quotes = {
                    "Donate blood, save lives!",
                    "Be a hero, donate blood.",
                    "Your blood can give someone another chance at life.",
                    "You don’t have to be a doctor to save lives."
            };
            int index = (int)(Math.random() * quotes.length);
            Toast.makeText(this, quotes[index], Toast.LENGTH_LONG).show();
        });

        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
