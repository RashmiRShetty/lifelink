package com.example.lifelink;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RequestStatusActivity extends AppCompatActivity {

    TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_status);

        txtStatus = findViewById(R.id.txtStatus);
        // Dummy status (in real app, fetch from DB)
        txtStatus.setText("Pending: Blood group O+ requested on 1 Aug 2025 at Udupi.");
    }
}
