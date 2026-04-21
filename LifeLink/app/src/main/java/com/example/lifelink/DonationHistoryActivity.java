package com.example.lifelink;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;

public class DonationHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Root ScrollView
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // LinearLayout to hold content
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(32, 32, 32, 32);
        mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Title TextView
        TextView title = new TextView(this);
        title.setText("Donation History");
        title.setTextSize(24);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 32);
        mainLayout.addView(title);

        // Sample data
        addHistoryCard(this, mainLayout, "12 May 2023", "A+", "Udupi Hospital");
        addHistoryCard(this, mainLayout, "25 Jan 2024", "A+", "Manipal Health");
        addHistoryCard(this, mainLayout, "10 Jul 2025", "A+", "KMC Hospital");

        // Attach main layout to ScrollView
        scrollView.addView(mainLayout);

        // Set the final layout
        setContentView(scrollView);
    }

    private void addHistoryCard(Context context, LinearLayout parentLayout, String date, String bloodGroup, String hospital) {
        LinearLayout card = new LinearLayout(context);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        card.setPadding(24, 24, 24, 24);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 24);
        card.setLayoutParams(params);

        TextView dateView = new TextView(context);
        dateView.setText("🗓 Date: " + date);
        dateView.setTextSize(16);
        card.addView(dateView);

        TextView bloodView = new TextView(context);
        bloodView.setText("🩸 Blood Group: " + bloodGroup);
        bloodView.setTextSize(16);
        card.addView(bloodView);

        TextView hospitalView = new TextView(context);
        hospitalView.setText("🏥 Hospital: " + hospital);
        hospitalView.setTextSize(16);
        card.addView(hospitalView);

        parentLayout.addView(card);
    }
}
