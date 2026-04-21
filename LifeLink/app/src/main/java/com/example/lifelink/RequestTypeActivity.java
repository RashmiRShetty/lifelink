package com.example.lifelink;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RequestTypeActivity extends AppCompatActivity {

    LinearLayout cardNormal, cardEmergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_type);

        cardNormal = findViewById(R.id.cardNormal);
        cardEmergency = findViewById(R.id.cardEmergency);

        cardNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RequestTypeActivity.this, "Normal Request Selected", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(RequestTypeActivity.this, NormalRequestActivity.class));
            }
        });

        cardEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RequestTypeActivity.this, "Emergency Request Selected", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(RequestTypeActivity.this, EmergencyRequestActivity.class));
            }
        });
    }
}
