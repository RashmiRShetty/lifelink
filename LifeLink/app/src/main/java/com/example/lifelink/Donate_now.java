package com.example.lifelink;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

class DonateNowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_now);

        TextView textView = findViewById(R.id.txtThankYou);
        textView.setText("Thank you for choosing to donate! Please visit your nearest center.");
    }
}
