package com.example.lifelink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class StepTwoFragment extends Fragment {

    public StepTwoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_step_two, container, false);

        EditText password = view.findViewById(R.id.passwordInput);
        EditText confirm = view.findViewById(R.id.confirmPasswordInput);
        Button signup = view.findViewById(R.id.signupButton);

        signup.setOnClickListener(v -> {
            String pass = password.getText().toString();
            String confirmPass = confirm.getText().toString();

            if (!pass.equals(confirmPass)) {
                Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            ((RegisterActivity) requireActivity()).submitForm(pass);
        });

        return view;
    }
}
