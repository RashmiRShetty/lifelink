package com.example.lifelink;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String bloodGroup;
    private String dob;
    private String city;

    public User(int id, String name, String email, String password,
                String phone, String bloodGroup, String dob, String city) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.dob = dob;
        this.city = city;
    }

    // GETTERS
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getBloodGroup() { return bloodGroup; }
    public String getDob() { return dob; }
    public String getCity() { return city; }

    // SETTERS (optional, if you want to update fields)
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public void setDob(String dob) { this.dob = dob; }
    public void setCity(String city) { this.city = city; }
}
