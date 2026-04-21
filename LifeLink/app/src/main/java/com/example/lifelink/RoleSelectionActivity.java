package com.example.lifelink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class RoleSelectionActivity extends AppCompatActivity {

    LinearLayout btnDonor, btnRecipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        btnDonor = findViewById(R.id.btnDonor);
        btnRecipient = findViewById(R.id.btnRecipient);

        // Handle Donor selection
        btnDonor.setOnClickListener(v -> {
            startActivity(new Intent(RoleSelectionActivity.this, DonorHomeActivity.class));
        });

        // Handle Recipient selection (currently disabled)

        btnRecipient.setOnClickListener(v -> {
            startActivity(new Intent(RoleSelectionActivity.this, RequestTypeActivity.class));
        });

    }
}
