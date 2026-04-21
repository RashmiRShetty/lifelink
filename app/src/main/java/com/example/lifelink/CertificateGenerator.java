package com.example.lifelink;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CertificateGenerator {

    public static String generateCertificate(Context context, String donorName) {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create(); // A4 size
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Background
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, pageInfo.getPageWidth(), pageInfo.getPageHeight(), backgroundPaint);

        // Draw red triangular corners
        Paint cornerPaint = new Paint();
        cornerPaint.setColor(Color.rgb(200, 16, 46)); // Dark red
        cornerPaint.setStyle(Paint.Style.FILL);

        // Top-left corner
        Path topLeftPath = new Path();
        topLeftPath.moveTo(0, 0);
        topLeftPath.lineTo(200, 0);
        topLeftPath.lineTo(0, 200);
        topLeftPath.close();
        canvas.drawPath(topLeftPath, cornerPaint);

        // Bottom-right corner
        Path bottomRightPath = new Path();
        bottomRightPath.moveTo(pageInfo.getPageWidth(), pageInfo.getPageHeight());
        bottomRightPath.lineTo(pageInfo.getPageWidth() - 200, pageInfo.getPageHeight());
        bottomRightPath.lineTo(pageInfo.getPageWidth(), pageInfo.getPageHeight() - 200);
        bottomRightPath.close();
        canvas.drawPath(bottomRightPath, cornerPaint);

        float centerX = pageInfo.getPageWidth() / 2;
        float currentY = 200;

        // Title Paint
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.rgb(200, 16, 46)); // Dark red
        titlePaint.setTextSize(36);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Regular Text Paint
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(18);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // Name Paint
        Paint namePaint = new Paint();
        namePaint.setColor(Color.rgb(200, 16, 46)); // Dark red
        namePaint.setTextSize(28);
        namePaint.setTextAlign(Paint.Align.CENTER);
        namePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Draw Title
        currentY = 300;
        canvas.drawText("BLOOD DONATION", centerX, currentY, titlePaint);
        currentY += 50;
        canvas.drawText("CERTIFICATE", centerX, currentY, titlePaint);
        currentY += 80;

        // Draw Content
        canvas.drawText("This certificate is awarded to", centerX, currentY, textPaint);
        currentY += 60;

        // Draw Name with underline
        canvas.drawText(donorName, centerX, currentY, namePaint);
        float underlineY = currentY + 5;
        float underlineStartX = centerX - namePaint.measureText(donorName) / 2;
        float underlineEndX = centerX + namePaint.measureText(donorName) / 2;
        namePaint.setStyle(Paint.Style.STROKE);
        namePaint.setStrokeWidth(2);
        canvas.drawLine(underlineStartX, underlineY, underlineEndX, underlineY, namePaint);
        currentY += 60;

        // Draw Message
        String message = "to honor their selfless act of donating blood, which";
        canvas.drawText(message, centerX, currentY, textPaint);
        currentY += 30;
        message = "has helped save lives and bring hope to those in need.";
        canvas.drawText(message, centerX, currentY, textPaint);
        currentY += 80;

        // Draw Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String date = dateFormat.format(new Date());
        canvas.drawText("Given on this day, " + date + ".", centerX, currentY, textPaint);
        currentY += 80;

        // Draw Signature
        Paint signaturePaint = new Paint();
        signaturePaint.setColor(Color.rgb(200, 16, 46)); // Dark red
        signaturePaint.setTextSize(24);
        signaturePaint.setTextAlign(Paint.Align.CENTER);
        signaturePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("LifeLink Team", centerX, currentY, signaturePaint);

        pdfDocument.finishPage(page);

        // Save the document
        String fileName = "BloodDonation_Certificate_" + donorName.replaceAll("\\s+", "") + "_" +
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".pdf";

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + fileName;
        File file = new File(path);

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(context, "Certificate saved in Downloads: " + fileName, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error generating certificate", Toast.LENGTH_SHORT).show();
            return null;
        }

        pdfDocument.close();
        return fileName;
    }
}