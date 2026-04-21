package com.example.lifelink;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.lifelink.register.RegisterPage1Fragment;
import com.example.lifelink.register.RegisterPage2Fragment;
import com.example.lifelink.register.RegisterPage3Fragment;

public class RegisterActivity extends AppCompatActivity {

    // Shared data
    public String name = "";
    public String email = "";
    public String password = "";
    public String phone = "";
    public String bloodGroup = "";
    public String dob = "";
    public String city = "";
    public boolean isDonor = false;
    public boolean isRecipient = false;
    public String role; // Store selected role


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (savedInstanceState == null) {
            loadFragment(new RegisterPage1Fragment());
        }
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
