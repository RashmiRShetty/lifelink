package com.example.lifelink;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateProfileActivity extends AppCompatActivity {

    public static String savedName = "";
    public static String savedBloodGroup = "";
    public static String savedLocation = "";

    EditText edtName, edtLocation;
    Spinner spinnerBloodGroup;
    Button btnSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        edtName = findViewById(R.id.edtName);
        edtLocation = findViewById(R.id.edtLocation);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        String[] bloodGroups = {"Select", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(adapter);

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedName = edtName.getText().toString().trim();
                savedBloodGroup = spinnerBloodGroup.getSelectedItem().toString();
                savedLocation = edtLocation.getText().toString().trim();

                if (savedName.isEmpty() || savedLocation.isEmpty() || savedBloodGroup.equals("Select")) {
                    Toast.makeText(CreateProfileActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateProfileActivity.this, "Profile saved successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
