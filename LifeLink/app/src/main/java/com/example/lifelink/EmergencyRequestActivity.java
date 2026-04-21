package com.example.lifelink;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EmergencyRequestActivity extends AppCompatActivity {

    CheckBox cbEmergency;
    EditText etCriticalCondition, etTimeframe;
    Spinner spinnerUrgency;
    Button btnCheckDonors, btnSubmitEmergencyRequest;
    TextView tvDonorStatus;

    String[] urgencyLevels = {"Select Urgency Level", "Critical - Within 1 Hour", "High - Within 3 Hours", "Medium - Within 6 Hours"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_request);

        cbEmergency = findViewById(R.id.cbEmergency);
        etCriticalCondition = findViewById(R.id.etCriticalCondition);
        etTimeframe = findViewById(R.id.etTimeframe);
        spinnerUrgency = findViewById(R.id.spinnerUrgency);
        btnCheckDonors = findViewById(R.id.btnCheckDonors);
        btnSubmitEmergencyRequest = findViewById(R.id.btnSubmitEmergencyRequest);
        tvDonorStatus = findViewById(R.id.tvDonorStatus);

        setupUrgencySpinner();

        // Toggle to enable/disable inputs when emergency checked
        cbEmergency.setOnCheckedChangeListener((buttonView, isChecked) -> {
            etCriticalCondition.setEnabled(isChecked);
            spinnerUrgency.setEnabled(isChecked);
            etTimeframe.setEnabled(isChecked);
            btnCheckDonors.setEnabled(isChecked);
            tvDonorStatus.setVisibility(View.GONE);
        });

        btnCheckDonors.setOnClickListener(v -> {
            // Simulate checking donor availability (in real app: fetch from API)
            checkDonorAvailability();
        });

        btnSubmitEmergencyRequest.setOnClickListener(v -> {
            if (validateInputs()) {
                // Collect data
                boolean isEmergency = cbEmergency.isChecked();
                String conditionDetails = etCriticalCondition.getText().toString();
                String urgency = spinnerUrgency.getSelectedItem().toString();
                String timeframe = etTimeframe.getText().toString();

                // Trigger notifications & logic here (placeholder)
                Toast.makeText(this, "Emergency request submitted!\nUrgency: " + urgency, Toast.LENGTH_LONG).show();

                // Reset form or keep as needed
                clearForm();
            }
        });
    }

    private void setupUrgencySpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, urgencyLevels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUrgency.setAdapter(adapter);
        spinnerUrgency.setEnabled(false);  // Initially disabled until emergency is checked
    }

    private void checkDonorAvailability() {
        // Simulated logic: in real app, query server for donors nearby
        // Here we just show a message after "checking"
        tvDonorStatus.setVisibility(View.VISIBLE);
        tvDonorStatus.setText("3 donors available nearby. Waiting for confirmations...");
        tvDonorStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
    }

    private boolean validateInputs() {
        if (!cbEmergency.isChecked()) {
            Toast.makeText(this, "Please mark request as emergency to submit", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spinnerUrgency.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select urgency level", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etTimeframe.getText().toString().trim().isEmpty()) {
            etTimeframe.setError("Enter timeframe");
            return false;
        }
        return true;
    }

    private void clearForm() {
        cbEmergency.setChecked(false);
        etCriticalCondition.setText("");
        spinnerUrgency.setSelection(0);
        etTimeframe.setText("");
        tvDonorStatus.setVisibility(View.GONE);
    }
}
