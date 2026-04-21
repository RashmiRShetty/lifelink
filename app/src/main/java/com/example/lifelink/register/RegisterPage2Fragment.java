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

import com.example.lifelink.R;
import com.example.lifelink.RegisterActivity;
import com.example.lifelink.ForgotPasswordActivity;

public class RegisterPage2Fragment extends Fragment {

    private EditText phoneInput;
    private Spinner bloodSpinner;
    private Button backBtn2, nextBtn2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_page2, container, false);

        phoneInput = view.findViewById(R.id.phoneInput);
        bloodSpinner = view.findViewById(R.id.bloodSpinner);
        backBtn2 = view.findViewById(R.id.backBtn2);
        nextBtn2 = view.findViewById(R.id.nextBtn2);

        // Set spinner values
        String[] bloodGroups = {"Select Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, bloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodSpinner.setAdapter(adapter);

        backBtn2.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        nextBtn2.setOnClickListener(v -> {
            String phone = phoneInput.getText().toString().trim();
            String blood = bloodSpinner.getSelectedItem().toString();

            if (TextUtils.isEmpty(phone)) {
                phoneInput.setError("Enter phone number");
                return;
            }
            if (blood.equals("Select Blood Group")) {
                Toast.makeText(getContext(), "Select a blood group", Toast.LENGTH_SHORT).show();
                return;
            }

            RegisterActivity activity = (RegisterActivity) getActivity();
            activity.phone = phone;
            activity.bloodGroup = blood;

            activity.loadFragment(new RegisterPage3Fragment());
        });

        return view;
    }
}
