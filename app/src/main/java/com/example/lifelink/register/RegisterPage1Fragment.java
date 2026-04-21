package com.example.lifelink.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.lifelink.R;
import com.example.lifelink.RegisterActivity;
import com.example.lifelink.ForgotPasswordActivity;

import java.util.regex.Pattern;

public class RegisterPage1Fragment extends Fragment {

    private EditText nameInput, emailInput, passInput;
    private Button nextBtn1;

    // Password pattern: min 6 chars, 1 uppercase, 1 lowercase, 1 digit, 1 special char
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +           // at least 1 digit
                    "(?=.*[a-z])" +           // at least 1 lowercase
                    "(?=.*[A-Z])" +           // at least 1 uppercase
                    "(?=.*[@#$%^&+=!])" +     // at least 1 special char
                    ".{6,}" +                 // at least 6 characters
                    "$");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_page1, container, false);

        nameInput = view.findViewById(R.id.nameInput);
        emailInput = view.findViewById(R.id.emailInput);
        passInput = view.findViewById(R.id.passInput);
        nextBtn1 = view.findViewById(R.id.nextBtn1);

        nextBtn1.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String pass = passInput.getText().toString().trim();

            // Name validation
            if (TextUtils.isEmpty(name)) {
                nameInput.setError("Enter full name");
                return;
            }

            // Email validation
            if (TextUtils.isEmpty(email)) {
                emailInput.setError("Enter email");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailInput.setError("Enter a valid email");
                return;
            }

            // Password validation
            if (TextUtils.isEmpty(pass)) {
                passInput.setError("Enter password");
                return;
            }
            if (!PASSWORD_PATTERN.matcher(pass).matches()) {
                passInput.setError("Password must have:\n- 6+ characters\n- 1 uppercase\n- 1 lowercase\n- 1 number\n- 1 special character");
                return;
            }

            // Pass data to activity
            RegisterActivity activity = (RegisterActivity) getActivity();
            activity.name = name;
            activity.email = email;
            activity.password = pass;

            activity.loadFragment(new RegisterPage2Fragment());
        });

        return view;
    }
}
