package com.example.lifelink;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class DonorProfileActivity extends AppCompatActivity {

    EditText fullName, email, phone, age, address;
    Spinner bloodGroup;
    RadioGroup genderGroup;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile);

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        address = findViewById(R.id.address);
        bloodGroup = findViewById(R.id.bloodGroup);
        genderGroup = findViewById(R.id.genderGroup);
        saveBtn = findViewById(R.id.saveBtn);

        String[] groups = {"Select Blood Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, groups);
        bloodGroup.setAdapter(adapter);

        saveBtn.setOnClickListener(view -> {
            String name = fullName.getText().toString();
            String emailVal = email.getText().toString();
            String mobile = phone.getText().toString();
            String group = bloodGroup.getSelectedItem().toString();
            String userAge = age.getText().toString();
            String addr = address.getText().toString();

            int selectedId = genderGroup.getCheckedRadioButtonId();
            RadioButton selectedGender = findViewById(selectedId);
            String gender = selectedGender != null ? selectedGender.getText().toString() : "";

            Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show();

            // You can now save this to Firebase or SQLite
        });
    }
}
