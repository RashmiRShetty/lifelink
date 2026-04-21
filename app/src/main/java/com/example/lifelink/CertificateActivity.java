package com.example.lifelink;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class CertificateActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 100;
    private TextView donorNameText;
    private TextView dateText;
    private String donorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);

        donorNameText = findViewById(R.id.donorName);
        dateText = findViewById(R.id.dateText);

        // Get donor name from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        donorName = prefs.getString("userName", "Anonymous Donor");

        // Set donor name
        donorNameText.setText(donorName);

        // Set current date
        String currentDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
                .format(new Date());
        dateText.setText("Given on this day, " + currentDate + ".");

        // Check and request storage permission
        if (checkStoragePermission()) {
            generatePDFCertificate();
        } else {
            requestStoragePermission();
        }
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generatePDFCertificate();
            } else {
                Toast.makeText(this, "Storage permission is required to save the certificate",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void generatePDFCertificate() {
        CertificateGenerator.generateCertificate(this, donorName);
    }
}
