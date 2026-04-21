package com.example.lifelink;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RequestBloodActivity extends AppCompatActivity {

    EditText patientNameInput, unitsInput, contactInput, locationInput;
    RadioGroup bloodGroupRG;
    Button submitBtn;
    DatabaseHelper db;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        db = new DatabaseHelper(this);

        patientNameInput = findViewById(R.id.patientNameInput);
        unitsInput = findViewById(R.id.unitsInput);
        contactInput = findViewById(R.id.contactInput);
        locationInput = findViewById(R.id.locationInput);
        bloodGroupRG = findViewById(R.id.bloodGroupRGRequest);
        submitBtn = findViewById(R.id.submitRequestBtn);
        userId = getIntent().getIntExtra("userId", -1);

        submitBtn.setOnClickListener(v -> submitRequest());
    }

    private void submitRequest() {
        String patientName = patientNameInput.getText().toString().trim();
        String unitsStr = unitsInput.getText().toString().trim();
        String contact = contactInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();

        // Check all fields
        if (TextUtils.isEmpty(patientName) || TextUtils.isEmpty(unitsStr) ||
                TextUtils.isEmpty(contact) || TextUtils.isEmpty(location)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate blood group selection
        int checked = bloodGroupRG.getCheckedRadioButtonId();
        if (checked == -1) {
            Toast.makeText(this, "Please select a blood group", Toast.LENGTH_SHORT).show();
            return;
        }
        String blood = ((RadioButton) findViewById(checked)).getText().toString();

        // Validate units
        int units;
        try {
            units = Integer.parseInt(unitsStr);
            if (units <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number of units", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate contact number (10 digits)
        if (!contact.matches("\\d{10}")) {
            Toast.makeText(this, "Enter a valid 10-digit contact number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current date
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Insert into database
        long id = db.insertRequest(userId, patientName, blood, units, contact, location, date, this);
        if (id > 0) {
            Toast.makeText(this, "Blood request submitted successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Close activity
        } else {
            Toast.makeText(this, "Failed to submit request.", Toast.LENGTH_SHORT).show();
        }
    }
}
