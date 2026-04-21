package com.example.lifelink;

public class Donation {
    private int id;
    private int userId;
    private String bloodGroup;
    private int units;
    private String location;
    private String date;

    public Donation(int id, int userId, String bloodGroup, int units, String location, String date) {
        this.id = id;
        this.userId = userId;
        this.bloodGroup = bloodGroup;
        this.units = units;
        this.location = location;
        this.date = date;
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getBloodGroup() { return bloodGroup; }
    public int getUnits() { return units; }
    public String getLocation() { return location; }
    public String getDate() { return date; }
}
