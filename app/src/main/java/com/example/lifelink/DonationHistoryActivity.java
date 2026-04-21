package com.example.lifelink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.lifelink.Donation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DonationHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ArrayList<Donation> donationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_history);

        recyclerView = findViewById(R.id.historyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        // Get logged-in user ID
        int userId = getSharedPreferences("UserSession", MODE_PRIVATE).getInt("userId", -1);

        if (userId != -1) {
            donationsList = dbHelper.getDonationsByUser(userId);

            // Check if list is empty
            if (donationsList == null) donationsList = new ArrayList<>();

            DonationAdapter adapter = new DonationAdapter(donationsList);
            recyclerView.setAdapter(adapter);
        }
    }

    private static class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {
        private final ArrayList<Donation> donations;

        public DonationAdapter(ArrayList<Donation> donations) {
            this.donations = donations;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Donation donation = donations.get(position);
            holder.bloodGroup.setText("Blood Group: " + donation.getBloodGroup());
            holder.units.setText("Units: " + donation.getUnits());
            holder.date.setText("Date: " + donation.getDate());
            holder.location.setText("Location: " + donation.getLocation());
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_donation, parent, false);
            return new ViewHolder(view);
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView bloodGroup, units, date, location;

            ViewHolder(View itemView) {
                super(itemView);
                bloodGroup = itemView.findViewById(R.id.bloodGroup);
                units = itemView.findViewById(R.id.units);
                date = itemView.findViewById(R.id.date);
                location = itemView.findViewById(R.id.location);
            }
        }

        @Override
        public int getItemCount() {
            return donations.size();
        }
    }
}
