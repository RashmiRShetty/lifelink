package com.example.lifelink;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "lifelink.db";
    private static final int DB_VERSION = 3;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_DONATIONS = "donations";
    private static final String TABLE_REQUESTS = "requests";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users
        String createUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "email TEXT UNIQUE," +
                "password TEXT," +
                "phone TEXT," +
                "blood_group TEXT," +
                "dob TEXT," +
                "city TEXT" +
                ")";
        db.execSQL(createUsers);

        // Donations
        String createDonations = "CREATE TABLE " + TABLE_DONATIONS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "blood_group TEXT," +
                "units INTEGER," +
                "location TEXT," +
                "date TEXT," +
                "FOREIGN KEY(user_id) REFERENCES " + TABLE_USERS + "(id)" +
                ")";
        db.execSQL(createDonations);

        // Requests (final single version)
        String createRequests = "CREATE TABLE " + TABLE_REQUESTS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "patient_name TEXT, " +
                "blood_group TEXT, " +
                "units INTEGER, " +
                "contact TEXT, " +
                "location TEXT, " +
                "date TEXT, " +
                "status TEXT DEFAULT 'OPEN', " +
                "accepted_user_name TEXT, " +
                "accepted_user_contact TEXT, " +
                "accepted_user_blood_group TEXT, " +
                "donor_confirmed INTEGER DEFAULT 0, " +
                "receiver_confirmed INTEGER DEFAULT 0, " +
                "FOREIGN KEY(user_id) REFERENCES " + TABLE_USERS + "(id)" +
                ")";
        db.execSQL(createRequests);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables and recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // ------------------ USER OPERATIONS ------------------ //
    public long registerUser(String name, String email, String password, String phone,
                             String bloodGroup, String dob, String city) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("phone", phone);
        cv.put("blood_group", bloodGroup);
        cv.put("dob", dob);
        cv.put("city", city);
        return db.insert(TABLE_USERS, null, cv);
    }

    public User loginUser(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email=? AND password=?",
                new String[]{email, password});
        if (c.moveToFirst()) {
            User u = new User(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getString(c.getColumnIndexOrThrow("name")),
                    c.getString(c.getColumnIndexOrThrow("email")),
                    c.getString(c.getColumnIndexOrThrow("password")),
                    c.getString(c.getColumnIndexOrThrow("phone")),
                    c.getString(c.getColumnIndexOrThrow("blood_group")),
                    c.getString(c.getColumnIndexOrThrow("dob")),
                    c.getString(c.getColumnIndexOrThrow("city"))
            );
            c.close();
            return u;
        }
        c.close();
        return null;
    }
    // Mark request as accepted by a donor
    public boolean acceptRequestByDonor(int requestId, int donorId, String donorName, String donorContact, String donorBloodGroup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", "accepted");
        values.put("acceptedUserName", donorName);
        values.put("acceptedUserContact", donorContact);
        values.put("acceptedUserBloodGroup", donorBloodGroup);

        int rows = db.update("requests", values, "id=?", new String[]{String.valueOf(requestId)});
        return rows > 0;
    }

    public User getUserById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE id=?",
                new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            User u = new User(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getString(c.getColumnIndexOrThrow("name")),
                    c.getString(c.getColumnIndexOrThrow("email")),
                    c.getString(c.getColumnIndexOrThrow("password")),
                    c.getString(c.getColumnIndexOrThrow("phone")),
                    c.getString(c.getColumnIndexOrThrow("blood_group")),
                    c.getString(c.getColumnIndexOrThrow("dob")),
                    c.getString(c.getColumnIndexOrThrow("city"))
            );
            c.close();
            return u;
        }
        c.close();
        return null;
    }

    public int updateUserProfile(int id, String name, String phone, String bloodGroup,
                                 String dob, String city) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("blood_group", bloodGroup);
        cv.put("dob", dob);
        cv.put("city", city);
        return db.update(TABLE_USERS, cv, "id=?", new String[]{String.valueOf(id)});
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email=?",
                new String[]{email});
        if (c.moveToFirst()) {
            User u = new User(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getString(c.getColumnIndexOrThrow("name")),
                    c.getString(c.getColumnIndexOrThrow("email")),
                    c.getString(c.getColumnIndexOrThrow("password")),
                    c.getString(c.getColumnIndexOrThrow("phone")),
                    c.getString(c.getColumnIndexOrThrow("blood_group")),
                    c.getString(c.getColumnIndexOrThrow("dob")),
                    c.getString(c.getColumnIndexOrThrow("city"))
            );
            c.close();
            return u;
        }
        c.close();
        return null;
    }

    public boolean updateUserPassword(int userId, String newPassword) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("password", newPassword);
        int rows = db.update(TABLE_USERS, cv, "id=?", new String[]{String.valueOf(userId)});
        return rows > 0;
    }

    // ------------------ DONATION OPERATIONS ------------------ //
    public long insertDonation(int userId, String bloodGroup, int units, String location, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", userId);
        cv.put("blood_group", bloodGroup);
        cv.put("units", units);
        cv.put("location", location);
        cv.put("date", date);
        return db.insert(TABLE_DONATIONS, null, cv);
    }

    public boolean addDonation(int userId, String bloodGroup, int units, String location, String date) {
        if (!isUserEligible(userId)) {
            return false;
        }
        long result = insertDonation(userId, bloodGroup, units, location, date);
        return result != -1;
    }

    public void confirmDonationByDonor(int requestId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("donor_confirmed", 1);
        db.update(TABLE_REQUESTS, values, "id=?", new String[]{String.valueOf(requestId)});
    }

    public void confirmDonationByReceiver(int requestId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("receiver_confirmed", 1);
        db.update(TABLE_REQUESTS, values, "id=?", new String[]{String.valueOf(requestId)});
    }

    public boolean isDonationCompleted(int requestId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT donor_confirmed, receiver_confirmed FROM " + TABLE_REQUESTS + " WHERE id=?",
                new String[]{String.valueOf(requestId)});
        if (c.moveToFirst()) {
            int donor = c.getInt(0);
            int receiver = c.getInt(1);
            c.close();
            return donor == 1 && receiver == 1;
        }
        c.close();
        return false;
    }

    public boolean isUserEligible(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT date FROM " + TABLE_DONATIONS + " WHERE user_id=? ORDER BY date DESC LIMIT 1",
                new String[]{String.valueOf(userId)}
        );

        if (c.moveToFirst()) {
            String lastDonationDate = c.getString(c.getColumnIndexOrThrow("date"));
            c.close();
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Date lastDate = sdf.parse(lastDonationDate);
                java.util.Date currentDate = new java.util.Date();
                long diffDays = (currentDate.getTime() - lastDate.getTime()) / (1000 * 60 * 60 * 24);
                return diffDays >= 90;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            c.close();
            return true;
        }
    }

    public ArrayList<Donation> getDonationsByUser(int userId) {
        ArrayList<Donation> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_DONATIONS + " WHERE user_id=?",
                new String[]{String.valueOf(userId)}
        );

        if (cursor.moveToFirst()) {
            do {
                Donation donation = new Donation(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("blood_group")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("units")),
                        cursor.getString(cursor.getColumnIndexOrThrow("location")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date"))
                );
                list.add(donation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<User> getUsersByBloodGroup(String bloodGroup) {
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE blood_group=?",
                new String[]{bloodGroup});
        while (c.moveToNext()) {
            User u = new User(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getString(c.getColumnIndexOrThrow("name")),
                    c.getString(c.getColumnIndexOrThrow("email")),
                    c.getString(c.getColumnIndexOrThrow("password")),
                    c.getString(c.getColumnIndexOrThrow("phone")),
                    c.getString(c.getColumnIndexOrThrow("blood_group")),
                    c.getString(c.getColumnIndexOrThrow("dob")),
                    c.getString(c.getColumnIndexOrThrow("city"))
            );
            list.add(u);
        }
        c.close();
        return list;
    }

    // ------------------ REQUEST OPERATIONS ------------------ //
    public long insertRequest(int userId, String patientName, String bloodGroup, int units,
                              String contact, String location, String date, Context context) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", userId);
        cv.put("patient_name", patientName);
        cv.put("blood_group", bloodGroup);
        cv.put("units", units);
        cv.put("contact", contact);
        cv.put("location", location);
        cv.put("date", date);
        cv.put("status", "OPEN");
        long id = db.insert(TABLE_REQUESTS, null, cv);

        ArrayList<User> donors = getUsersByBloodGroup(bloodGroup);
        for (User d : donors) {
            NotificationHelper.showNotification(
                    context,
                    "Urgent " + bloodGroup + " Blood Needed",
                    "Patient: " + patientName + ", Location: " + location,
                    R.drawable.ic_blood_drop
            );
        }

        return id;
    }

    public int acceptRequest(int requestId, String donorName, String donorContact, String donorBloodGroup) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", "ACCEPTED");
        cv.put("accepted_user_name", donorName);
        cv.put("accepted_user_contact", donorContact);
        cv.put("accepted_user_blood_group", donorBloodGroup);
        return db.update(TABLE_REQUESTS, cv, "id=?", new String[]{String.valueOf(requestId)});
    }

    public ArrayList<Request> getAllRequests() {
        ArrayList<Request> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT r.*, u.name as requester FROM " + TABLE_REQUESTS +
                        " r LEFT JOIN " + TABLE_USERS + " u ON r.user_id = u.id ORDER BY r.id DESC",
                null
        );
        while (c.moveToNext()) {
            Request r = new Request(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getInt(c.getColumnIndexOrThrow("user_id")),
                    c.getString(c.getColumnIndexOrThrow("patient_name")),
                    c.getString(c.getColumnIndexOrThrow("blood_group")),
                    c.getInt(c.getColumnIndexOrThrow("units")),
                    c.getString(c.getColumnIndexOrThrow("contact")),
                    c.getString(c.getColumnIndexOrThrow("location")),
                    c.getString(c.getColumnIndexOrThrow("date")),
                    c.getString(c.getColumnIndexOrThrow("status")),
                    c.getString(c.getColumnIndexOrThrow("requester")),
                    c.getString(c.getColumnIndexOrThrow("accepted_user_name")),
                    c.getString(c.getColumnIndexOrThrow("accepted_user_contact")),
                    c.getString(c.getColumnIndexOrThrow("accepted_user_blood_group"))
            );
            list.add(r);
        }
        c.close();
        return list;
    }

    public ArrayList<Request> getAcceptedRequests() {
        ArrayList<Request> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT r.*, u.name as requester FROM " + TABLE_REQUESTS +
                        " r LEFT JOIN " + TABLE_USERS + " u ON r.user_id = u.id " +
                        "WHERE r.status = 'ACCEPTED' ORDER BY r.id DESC",
                null
        );
        while (c.moveToNext()) {
            Request r = new Request(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getInt(c.getColumnIndexOrThrow("user_id")),
                    c.getString(c.getColumnIndexOrThrow("patient_name")),
                    c.getString(c.getColumnIndexOrThrow("blood_group")),
                    c.getInt(c.getColumnIndexOrThrow("units")),
                    c.getString(c.getColumnIndexOrThrow("contact")),
                    c.getString(c.getColumnIndexOrThrow("location")),
                    c.getString(c.getColumnIndexOrThrow("date")),
                    c.getString(c.getColumnIndexOrThrow("status")),
                    c.getString(c.getColumnIndexOrThrow("requester")),
                    c.getString(c.getColumnIndexOrThrow("accepted_user_name")),
                    c.getString(c.getColumnIndexOrThrow("accepted_user_contact")),
                    c.getString(c.getColumnIndexOrThrow("accepted_user_blood_group"))
            );
            list.add(r);
        }
        c.close();
        return list;
    }

    public int updateRequestStatus(int id, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        return db.update(TABLE_REQUESTS, cv, "id=?", new String[]{String.valueOf(id)});
    }

    // ------------------ SEARCH & FILTER ------------------ //
    public ArrayList<Request> searchRequests(Context context, String bloodGroup, String location, String patientName) {
        ArrayList<Request> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // Base query
        StringBuilder query = new StringBuilder(
                "SELECT r.*, u.name as requester FROM " + TABLE_REQUESTS +
                        " r LEFT JOIN " + TABLE_USERS + " u ON r.user_id = u.id WHERE 1=1"
        );

        ArrayList<String> argsList = new ArrayList<>();

        if (bloodGroup != null && !bloodGroup.trim().isEmpty()) {
            query.append(" AND LOWER(r.blood_group) LIKE ?");
            argsList.add("%" + bloodGroup.trim().toLowerCase() + "%");
        }

        if (location != null && !location.trim().isEmpty()) {
            query.append(" AND LOWER(r.location) LIKE ?");
            argsList.add("%" + location.trim().toLowerCase() + "%");
        }

        if (patientName != null && !patientName.trim().isEmpty()) {
            query.append(" AND LOWER(r.patient_name) LIKE ?");
            argsList.add("%" + patientName.trim().toLowerCase() + "%");
        }

        query.append(" ORDER BY r.id DESC");

        String[] args = argsList.toArray(new String[0]);
        Cursor c = db.rawQuery(query.toString(), args);

        while (c.moveToNext()) {
            Request r = new Request(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getInt(c.getColumnIndexOrThrow("user_id")),
                    c.getString(c.getColumnIndexOrThrow("patient_name")),
                    c.getString(c.getColumnIndexOrThrow("blood_group")),
                    c.getInt(c.getColumnIndexOrThrow("units")),
                    c.getString(c.getColumnIndexOrThrow("contact")),
                    c.getString(c.getColumnIndexOrThrow("location")),
                    c.getString(c.getColumnIndexOrThrow("date")),
                    c.getString(c.getColumnIndexOrThrow("status")),
                    c.getString(c.getColumnIndexOrThrow("requester")),
                    c.getString(c.getColumnIndexOrThrow("accepted_user_name")),
                    c.getString(c.getColumnIndexOrThrow("accepted_user_contact")),
                    c.getString(c.getColumnIndexOrThrow("accepted_user_blood_group"))
            );
            list.add(r);
        }
        c.close();

        if (list.isEmpty()) {
            Toast.makeText(context, "No matching blood requests found.", Toast.LENGTH_SHORT).show();
        }

        return list;
    }
}
