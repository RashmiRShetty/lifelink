package com.example.lifelink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RequestDetailsActivity extends AppCompatActivity {

    TextView patientName, bloodGroup, units, location, contact, date, status, requester;
    TextView acceptedUserName, acceptedUserContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        // Initialize views
        patientName = findViewById(R.id.patientName);
        bloodGroup = findViewById(R.id.bloodGroup);
        units = findViewById(R.id.units);
        location = findViewById(R.id.location);
        contact = findViewById(R.id.contact);
        date = findViewById(R.id.date);
        status = findViewById(R.id.status);
        requester = findViewById(R.id.requester);
        acceptedUserName = findViewById(R.id.acceptedUserName);
        acceptedUserContact = findViewById(R.id.acceptedUserContact);

        // Get data from Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            patientName.setText("Patient: " + extras.getString("patientName"));
            bloodGroup.setText("Blood Group: " + extras.getString("bloodGroup"));
            units.setText("Units: " + extras.getInt("units"));
            location.setText("Location: " + extras.getString("location"));
            contact.setText("Contact: " + extras.getString("contact"));
            date.setText("Date: " + extras.getString("date"));
            status.setText("Status: " + extras.getString("status"));
            requester.setText("Requested by: " + extras.getString("requesterName"));

            // Donor details (only visible if request accepted)
            String donorName = extras.getString("acceptedUserName", "Not accepted yet");
            String donorContact = extras.getString("acceptedUserContact", "N/A");

            acceptedUserName.setText("Accepted by: " + donorName);
            acceptedUserContact.setText("Donor Contact: " + donorContact);
        }
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_donate);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new android.content.Intent(this, DashboardActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.nav_donate:
                    startActivity(new Intent(this, DonateActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_request:
                    startActivity(new android.content.Intent(this, RequestBloodActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.nav_profile:
                    startActivity(new android.content.Intent(this, ProfileActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }
}
