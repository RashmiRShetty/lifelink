package com.example.lifelink;

public class Request {
    public int id;
    public int userId;
    public String patientName;
    public String bloodGroup;
    public int units;
    public String contact;
    public String location;
    public String date;
    public String status;
    public String requesterName;

    // Info about donor who accepts the request
    public String acceptedUserName;
    public String acceptedUserContact;
    public String acceptedUserBloodGroup;

    // Empty constructor
    public Request() {
    }

    // Constructor for new blood requests (not yet accepted)
    public Request(int id, int userId, String patientName, String bloodGroup, int units,
                   String contact, String location, String date, String status, String requesterName) {
        this.id = id;
        this.userId = userId;
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.units = units;
        this.contact = contact;
        this.location = location;
        this.date = date;
        this.status = status;
        this.requesterName = requesterName;

        this.acceptedUserName = "";
        this.acceptedUserContact = "";
        this.acceptedUserBloodGroup = "";
    }

    // Constructor for accepted requests
    public Request(int id, int userId, String patientName, String bloodGroup, int units,
                   String contact, String location, String date, String status, String requesterName,
                   String acceptedUserName, String acceptedUserContact, String acceptedUserBloodGroup) {
        this.id = id;
        this.userId = userId;
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.units = units;
        this.contact = contact;
        this.location = location;
        this.date = date;
        this.status = status;
        this.requesterName = requesterName;
        this.acceptedUserName = acceptedUserName;
        this.acceptedUserContact = acceptedUserContact;
        this.acceptedUserBloodGroup = acceptedUserBloodGroup;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRequesterName() { return requesterName; }
    public void setRequesterName(String requesterName) { this.requesterName = requesterName; }

    public String getAcceptedUserName() { return acceptedUserName; }
    public void setAcceptedUserName(String acceptedUserName) { this.acceptedUserName = acceptedUserName; }

    public String getAcceptedUserContact() { return acceptedUserContact; }
    public void setAcceptedUserContact(String acceptedUserContact) { this.acceptedUserContact = acceptedUserContact; }

    public String getAcceptedUserBloodGroup() { return acceptedUserBloodGroup; }
    public void setAcceptedUserBloodGroup(String acceptedUserBloodGroup) { this.acceptedUserBloodGroup = acceptedUserBloodGroup; }
}
