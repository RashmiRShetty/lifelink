package com.example.lifelink;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecipientHomeActivity extends AppCompatActivity {

    Button btnRequestBlood, btnRequestStatus, btnNearbyBanks, btnTips, btnEmergency, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_home);

        btnRequestBlood = findViewById(R.id.btnRequestBlood);
        btnRequestStatus = findViewById(R.id.btnRequestStatus);
        btnNearbyBanks = findViewById(R.id.btnNearbyBanks);
        btnTips = findViewById(R.id.btnTips);
        btnEmergency = findViewById(R.id.btnEmergency);
        btnLogout = findViewById(R.id.btnLogout);

        btnRequestBlood.setOnClickListener(v ->
                startActivity(new Intent(this, RequestBloodActivity.class))
        );

        btnRequestStatus.setOnClickListener(v ->
                startActivity(new Intent(this, RequestStatusActivity.class))
        );

        btnNearbyBanks.setOnClickListener(v -> {
            String locationQuery = Uri.encode("Blood Bank near me");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + locationQuery));
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        btnTips.setOnClickListener(v ->
                startActivity(new Intent(this, DonationTipsActivity.class))
        );

        btnEmergency.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:108")); // 108 = emergency number
            startActivity(callIntent);
        });

        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
