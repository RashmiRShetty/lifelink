package com.example.lifelink;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewProfileActivity extends AppCompatActivity {

    TextView txtProfileDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        txtProfileDetails = findViewById(R.id.txtProfileDetails);

        if (!CreateProfileActivity.savedName.isEmpty()) {
            txtProfileDetails.setText(
                    "Name: " + CreateProfileActivity.savedName + "\n" +
                            "Blood Group: " + CreateProfileActivity.savedBloodGroup + "\n" +
                            "Location: " + CreateProfileActivity.savedLocation
            );
        } else {
            txtProfileDetails.setText("No profile found. Please create one.");
        }
    }
}
