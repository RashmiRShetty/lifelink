package com.example.lifelink;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class RegisterActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    View dash1, dash2;

    String fullName, email, bloodType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewPager = findViewById(R.id.viewPager);
        dash1 = findViewById(R.id.dash1);
        dash2 = findViewById(R.id.dash2);

        RegisterPagerAdapter adapter = new RegisterPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(true); // SWIPE ENABLED

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    dash1.setBackgroundResource(R.drawable.dash_active);
                    dash2.setBackgroundResource(R.drawable.dash_inactive);
                } else {
                    dash1.setBackgroundResource(R.drawable.dash_inactive);
                    dash2.setBackgroundResource(R.drawable.dash_active);
                }
            }
        });
    }

    public void goToNextPage() {
        viewPager.setCurrentItem(1);
    }

    public void setUserData(String name, String emailText, String selectedBloodType) {
        fullName = name;
        email = emailText;
        bloodType = selectedBloodType;
    }

    public void submitForm(String password) {
        String msg = "Name: " + fullName + "\nEmail: " + email + "\nBlood Type: " + bloodType;
        Toast.makeText(this, "Registered!\n" + msg, Toast.LENGTH_LONG).show();
    }
}
