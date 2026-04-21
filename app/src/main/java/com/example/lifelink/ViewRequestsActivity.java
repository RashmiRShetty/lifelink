package com.example.lifelink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Locale;

public class ViewRequestsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper db;
    ArrayList<Request> requestsList;
    RequestsAdapter adapter;

    EditText searchInput;
    Button searchBtn, toggleButton;
    boolean showingAccepted = false;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);

        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.requestsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchInput = findViewById(R.id.searchInput);
        searchBtn = findViewById(R.id.searchBtn);
        toggleButton = findViewById(R.id.toggleButton);

        int userId = getSharedPreferences("user_session", MODE_PRIVATE).getInt("userId", -1);
        currentUser = db.getUserById(userId);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_request);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(this, DashboardActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_donate:
                    startActivity(new Intent(this, DonateActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_request:
                    SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
                    int userId1 = prefs.getInt("userId", -1);
                    Intent intent = new Intent(this, RequestBloodActivity.class);
                    intent.putExtra("userId", userId1);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_profile:
                    SharedPreferences prefsProfile = getSharedPreferences("UserSession", MODE_PRIVATE);
                    int profileUserId = prefsProfile.getInt("userId", -1);
                    if (profileUserId != -1) {
                        Intent intentProfile = new Intent(this, ProfileActivity.class);
                        intentProfile.putExtra("userId", profileUserId);
                        startActivity(intentProfile);
                        overridePendingTransition(0, 0);
                    }
                    return true;
            }
            return false;
        });

        loadRequests();

        searchBtn.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim();
            if (TextUtils.isEmpty(query)) {
                Toast.makeText(this, "Enter blood group or location to search", Toast.LENGTH_SHORT).show();
                return;
            }
            filterRequests(query);
        });

        toggleButton.setOnClickListener(v -> {
            showingAccepted = !showingAccepted;
            if (showingAccepted) {
                loadAcceptedRequests();
                toggleButton.setText("Show All Requests");
            } else {
                loadRequests();
                toggleButton.setText("Show Accepted Requests");
            }
        });
    }

    private void loadRequests() {
        requestsList = db.getAllRequests();
        adapter = new RequestsAdapter(requestsList, db, currentUser);
        recyclerView.setAdapter(adapter);
    }

    private void loadAcceptedRequests() {
        requestsList = db.getAcceptedRequests();
        adapter = new RequestsAdapter(requestsList, db, currentUser);
        recyclerView.setAdapter(adapter);
    }

    private void filterRequests(String query) {
        query = query.toLowerCase(Locale.ROOT);
        ArrayList<Request> filteredList = new ArrayList<>();
        for (Request r : showingAccepted ? db.getAcceptedRequests() : db.getAllRequests()) {
            if (r.bloodGroup.toLowerCase(Locale.ROOT).contains(query) ||
                    r.location.toLowerCase(Locale.ROOT).contains(query)) {
                filteredList.add(r);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No requests found", Toast.LENGTH_SHORT).show();
        }

        adapter = new RequestsAdapter(filteredList, db, currentUser);
        recyclerView.setAdapter(adapter);
    }

    private static class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {
        private ArrayList<Request> requests;
        private DatabaseHelper db;
        private User currentUser;

        public RequestsAdapter(ArrayList<Request> requests, DatabaseHelper db, User currentUser) {
            this.requests = requests;
            this.db = db;
            this.currentUser = currentUser;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_request, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Request request = requests.get(position);
            holder.patientName.setText("Patient: " + request.patientName);
            holder.bloodGroup.setText("Blood Group: " + request.bloodGroup);
            holder.units.setText("Units: " + request.units);
            holder.location.setText("Location: " + request.location);
            holder.contact.setText("Contact: " + request.contact);
            holder.date.setText("Date: " + request.date);
            holder.status.setText("Status: " + request.status);

            if ("ACCEPTED".equalsIgnoreCase(request.status)) {
                holder.donorDetails.setVisibility(View.VISIBLE);
                holder.donorDetails.setText(
                        "Donor: " + request.acceptedUserName +
                                "\nContact: " + request.acceptedUserContact +
                                "\nBlood Group: " + request.acceptedUserBloodGroup
                );
                holder.acceptButton.setEnabled(false);
                holder.acceptButton.setText("Already Accepted");
                holder.acceptCard.setCardBackgroundColor(holder.itemView.getResources().getColor(android.R.color.darker_gray));
            } else {
                holder.donorDetails.setVisibility(View.GONE);
                holder.acceptButton.setEnabled(true);
                holder.acceptButton.setText("Accept");
                holder.acceptCard.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.primary_color));
            }

            holder.acceptButton.setOnClickListener(v -> {
                if ("OPEN".equalsIgnoreCase(request.status)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                    View dialogView = LayoutInflater.from(holder.itemView.getContext())
                            .inflate(R.layout.dialog_donor_details, null);

                    EditText nameInput = dialogView.findViewById(R.id.donorNameInput);
                    EditText phoneInput = dialogView.findViewById(R.id.donorPhoneInput);
                    Spinner bloodGroupSpinner = dialogView.findViewById(R.id.donorBloodGroupSpinner);

                    String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
                    ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(holder.itemView.getContext(),
                            android.R.layout.simple_spinner_item, bloodGroups);
                    adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bloodGroupSpinner.setAdapter(adapterSpinner);

                    if (currentUser != null) {
                        nameInput.setText(currentUser.getName());
                        phoneInput.setText(currentUser.getPhone());
                        String userBloodGroup = currentUser.getBloodGroup();
                        for (int i = 0; i < bloodGroups.length; i++) {
                            if (bloodGroups[i].equals(userBloodGroup)) {
                                bloodGroupSpinner.setSelection(i);
                                break;
                            }
                        }
                    }

                    builder.setView(dialogView)
                            .setTitle("Enter Donor Details")
                            .setPositiveButton("Accept", (dialog, which) -> {
                                String donorName = nameInput.getText().toString().trim();
                                String donorPhone = phoneInput.getText().toString().trim();
                                String donorBlood = bloodGroupSpinner.getSelectedItem().toString();

                                if (donorName.isEmpty() || donorPhone.isEmpty() || donorBlood.isEmpty()) {
                                    Toast.makeText(holder.itemView.getContext(),
                                            "Please fill all details", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                db.acceptRequest(
                                        request.id,
                                        donorName,
                                        donorPhone,
                                        donorBlood
                                );

                                request.status = "ACCEPTED";
                                request.acceptedUserName = donorName;
                                request.acceptedUserContact = donorPhone;
                                request.acceptedUserBloodGroup = donorBlood;

                                holder.donorDetails.setVisibility(View.VISIBLE);
                                holder.donorDetails.setText(
                                        "Donor: " + donorName +
                                                "\nContact: " + donorPhone +
                                                "\nBlood Group: " + donorBlood
                                );

                                holder.acceptButton.setEnabled(false);
                                holder.acceptButton.setText("Already Accepted");
                                holder.acceptCard.setCardBackgroundColor(holder.itemView.getResources().getColor(android.R.color.darker_gray));

                                Toast.makeText(holder.itemView.getContext(),
                                        "Request accepted successfully!", Toast.LENGTH_SHORT).show();
                                notifyItemChanged(position);
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return requests.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView patientName, bloodGroup, units, location, contact, date, status;
            TextView donorDetails;
            Button acceptButton;
            androidx.cardview.widget.CardView acceptCard;

            ViewHolder(View itemView) {
                super(itemView);
                patientName = itemView.findViewById(R.id.patientName);
                bloodGroup = itemView.findViewById(R.id.bloodGroup);
                units = itemView.findViewById(R.id.units);
                location = itemView.findViewById(R.id.location);
                contact = itemView.findViewById(R.id.contact);
                date = itemView.findViewById(R.id.date);
                status = itemView.findViewById(R.id.status);
                acceptButton = itemView.findViewById(R.id.acceptButton);
                donorDetails = itemView.findViewById(R.id.donorDetails);
                acceptCard = itemView.findViewById(R.id.acceptCard);
            }
        }
    }
}
