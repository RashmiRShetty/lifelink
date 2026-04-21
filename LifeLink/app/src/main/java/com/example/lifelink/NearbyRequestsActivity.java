package com.example.lifelink;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NearbyRequestsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_requests);

        TextView txtRequests = findViewById(R.id.txtRequests);
        txtRequests.setText("🔴 A+ needed at Udupi Hospital\n🟠 B- needed at Manipal\n🟢 AB+ needed at KMC");
    }
}
