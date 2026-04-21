package com.example.lifelink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class StepOneFragment extends Fragment {

    public StepOneFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_step_one, container, false);

        EditText fullName = view.findViewById(R.id.fullNameInput);
        EditText email = view.findViewById(R.id.emailInput);
        Spinner bloodTypeSpinner = view.findViewById(R.id.bloodTypeSpinner);
        Button next = view.findViewById(R.id.nextButton);

        String[] bloodTypes = {"Select Blood Type", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, bloodTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodTypeSpinner.setAdapter(adapter);

        next.setOnClickListener(v -> {
            String name = fullName.getText().toString().trim();
            String emailText = email.getText().toString().trim();
            String bloodType = bloodTypeSpinner.getSelectedItem().toString();

            if (name.isEmpty() || emailText.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (bloodType.equals("Select Blood Type")) {
                Toast.makeText(getActivity(), "Please select your blood type", Toast.LENGTH_SHORT).show();
                return;
            }

            ((RegisterActivity) requireActivity()).setUserData(name, emailText, bloodType);
            ((RegisterActivity) requireActivity()).goToNextPage();
        });

        return view;
    }
}
