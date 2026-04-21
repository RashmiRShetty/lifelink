package com.example.lifelink;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RequestBloodActivity extends AppCompatActivity {

    EditText edtBloodType, edtLocation, edtReason;
    Button btnSubmitRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        edtBloodType = findViewById(R.id.edtBloodType);
        edtLocation = findViewById(R.id.edtLocation);
        edtReason = findViewById(R.id.edtReason);
        btnSubmitRequest = findViewById(R.id.btnSubmitRequest);

        btnSubmitRequest.setOnClickListener(v -> {
            String bloodType = edtBloodType.getText().toString();
            String location = edtLocation.getText().toString();
            String reason = edtReason.getText().toString();

            if (bloodType.isEmpty() || location.isEmpty() || reason.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Request submitted successfully!", Toast.LENGTH_SHORT).show();
                // Save to database logic here (Firebase or SQLite)
            }
        });
    }
}
