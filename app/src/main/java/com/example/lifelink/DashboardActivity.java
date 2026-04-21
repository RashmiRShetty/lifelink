package com.example.lifelink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Welcome text
        TextView welcomeTxt = findViewById(R.id.welcomeTxt);
        welcomeTxt.setText("Welcome to Dashboard!");

        // Donate Card
        CardView donateCard = findViewById(R.id.donateCard);
        donateCard.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, DonateActivity.class));
        });

        // Request Blood Card
        CardView requestCard = findViewById(R.id.requestCard);
        requestCard.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
            int userId = prefs.getInt("userId", -1);
            Intent intent = new Intent(DashboardActivity.this, RequestBloodActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        // View Requests Card
        CardView viewRequestsCard = findViewById(R.id.viewRequestsCard);
        viewRequestsCard.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, ViewRequestsActivity.class));
        });

        // Profile Card
        CardView profileCard = findViewById(R.id.profileCard);
        profileCard.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
            int userId = prefs.getInt("userId", -1);

            if (userId != -1) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
