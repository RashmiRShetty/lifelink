package com.example.lifelink;

// BloodRequestActivity.java

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NormalRequestActivity extends AppCompatActivity {

    EditText etName, etAge, etMedicalHistory, etHospitalLocation, etContactInfo, etQuantity, etDateTime, etPurpose;
    Spinner spinnerBloodGroup, spinnerBloodGroupReq;
    Button btnSubmitRequest;

    String[] bloodGroups = {"Select Blood Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_request);

        initViews();
        setupSpinners();

        btnSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Basic validation
                if (validateInputs()) {
                    // Collect data
                    String name = etName.getText().toString();
                    int age = Integer.parseInt(etAge.getText().toString());
                    String bloodGroup = spinnerBloodGroup.getSelectedItem().toString();
                    String medicalHistory = etMedicalHistory.getText().toString();
                    String hospital = etHospitalLocation.getText().toString();
                    String contact = etContactInfo.getText().toString();

                    String reqBloodGroup = spinnerBloodGroupReq.getSelectedItem().toString();
                    int quantity = Integer.parseInt(etQuantity.getText().toString());
                    String dateTime = etDateTime.getText().toString();
                    String purpose = etPurpose.getText().toString();

                    // Here you can add your logic to save data/send notification

                    Toast.makeText( NormalRequestActivity.this, "Blood Request Submitted!", Toast.LENGTH_LONG).show();

                    // Optionally clear the form
                    clearForm();
                }
            }
        });
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        etMedicalHistory = findViewById(R.id.etMedicalHistory);
        etHospitalLocation = findViewById(R.id.etHospitalLocation);
        etContactInfo = findViewById(R.id.etContactInfo);

        spinnerBloodGroupReq = findViewById(R.id.spinnerBloodGroupReq);
        etQuantity = findViewById(R.id.etQuantity);
        etDateTime = findViewById(R.id.etDateTime);
        etPurpose = findViewById(R.id.etPurpose);

        btnSubmitRequest = findViewById(R.id.btnSubmitRequest);
    }

    private void setupSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, bloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBloodGroup.setAdapter(adapter);
        spinnerBloodGroupReq.setAdapter(adapter);
    }

    private boolean validateInputs() {
        if (etName.getText().toString().trim().isEmpty()) {
            etName.setError("Enter name");
            return false;
        }
        if (etAge.getText().toString().trim().isEmpty()) {
            etAge.setError("Enter age");
            return false;
        }
        if (spinnerBloodGroup.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select blood group", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spinnerBloodGroupReq.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select required blood group", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etQuantity.getText().toString().trim().isEmpty()) {
            etQuantity.setError("Enter quantity");
            return false;
        }
        if (etHospitalLocation.getText().toString().trim().isEmpty()) {
            etHospitalLocation.setError("Enter hospital/location");
            return false;
        }
        if (etContactInfo.getText().toString().trim().isEmpty()) {
            etContactInfo.setError("Enter contact info");
            return false;
        }
        return true;
    }

    private void clearForm() {
        etName.setText("");
        etAge.setText("");
        spinnerBloodGroup.setSelection(0);
        etMedicalHistory.setText("");
        etHospitalLocation.setText("");
        etContactInfo.setText("");

        spinnerBloodGroupReq.setSelection(0);
        etQuantity.setText("");
        etDateTime.setText("");
        etPurpose.setText("");
    }
}
