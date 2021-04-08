package com.example.crowderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.crowderapp.controllers.BarcodeHandler;

public class QRCodeActivity extends AppCompatActivity {

    ImageView QRImage;
    BarcodeHandler bcHandler = new BarcodeHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        QRImage = findViewById(R.id.qr_imageView);
        QRImage.setImageBitmap(bcHandler.generateQR("Random"));
    }
}