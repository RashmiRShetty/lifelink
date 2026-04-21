package com.example.lifelink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DonationCompletionActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    int requestId = 1; // 🔴 Replace with actual requestId passed via Intent

    Button donorConfirmBtn, receiverConfirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_completion);

        dbHelper = new DatabaseHelper(this);

        // Get requestId from previous screen
        requestId = getIntent().getIntExtra("requestId", -1);

        donorConfirmBtn = findViewById(R.id.donorConfirmBtn);
        receiverConfirmBtn = findViewById(R.id.receiverConfirmBtn);

        // Donor confirms
        donorConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.confirmDonationByDonor(requestId);
                checkCompletion();
            }
        });

        // Receiver confirms
        receiverConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.confirmDonationByReceiver(requestId);
                checkCompletion();
            }
        });
    }

    // Check if both donor and receiver confirmed
    private void checkCompletion() {
        if (dbHelper.isDonationCompleted(requestId)) {
            Toast.makeText(this, "Donation completed successfully!", Toast.LENGTH_LONG).show();

            // Go to Certificate screen
            Intent intent = new Intent(this, CertificateActivity.class);
            intent.putExtra("requestId", requestId);
            startActivity(intent);
            finish();
        }
    }
}
