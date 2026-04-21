package com.example.lifelink.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.lifelink.DatabaseHelper;
import com.example.lifelink.R;
import com.example.lifelink.RegisterActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.example.lifelink.ForgotPasswordActivity;

public class RegisterPage3Fragment extends Fragment {

    private EditText dobInput;
    private Spinner citySpinner;
    private Button backBtn3, finishBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_page3, container, false);

        dobInput = view.findViewById(R.id.dobInput);
        citySpinner = view.findViewById(R.id.citySpinner);
        backBtn3 = view.findViewById(R.id.backBtn3);
        finishBtn = view.findViewById(R.id.submitBtn);

        // City spinner values
        String[] cities = {"Select City", "Udupi", "Mangalore", "Bangalore", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        backBtn3.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        finishBtn.setOnClickListener(v -> {
            String dob = dobInput.getText().toString().trim();
            String city = citySpinner.getSelectedItem().toString();

            // DOB validation
            if (TextUtils.isEmpty(dob)) {
                dobInput.setError("Enter Date of Birth");
                return;
            }
            if (!isValidDate(dob)) {
                dobInput.setError("Enter valid date in DD/MM/YYYY format");
                return;
            }

            // Optional: check age >= 18
            if (!isAdult(dob)) {
                dobInput.setError("You must be at least 18 years old");
                return;
            }

            if (city.equals("Select City")) {
                Toast.makeText(getContext(), "Select a city", Toast.LENGTH_SHORT).show();
                return;
            }

            RegisterActivity activity = (RegisterActivity) getActivity();
            activity.dob = dob;
            activity.city = city;

            // Save to database
            DatabaseHelper db = new DatabaseHelper(getContext());
            long result = db.registerUser(
                    activity.name,
                    activity.email,
                    activity.password,
                    activity.phone,
                    activity.bloodGroup,
                    activity.dob,   // Pass DOB
                    activity.city   // Pass City
            );

            if (result != -1) {
                Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), "Error: User may already exist", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    // Check valid date format
    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Check if age >= 18
    private boolean isAdult(String dob) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date birthDate = sdf.parse(dob);
            Date today = new Date();
            long age = (today.getTime() - birthDate.getTime()) / (1000L * 60 * 60 * 24 * 365);
            return age >= 18;
        } catch (ParseException e) {
            return false;
        }
    }
}
