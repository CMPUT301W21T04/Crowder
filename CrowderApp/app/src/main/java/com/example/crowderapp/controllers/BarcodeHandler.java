package com.example.crowderapp.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.example.crowderapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.io.InputStream;
import java.util.List;

public class BarcodeHandler {

    public void BarcodeHandler() {
    }

    public void scanBarcode(Context context) {

        InputStream imgFile = context.getResources().openRawResource(R.raw.barcode);

        if (imgFile != null) {
            BarcodeScanner barcodeScanner = BarcodeScanning.getClient();
            Bitmap bitmapResult = BitmapFactory.decodeResource(context.getResources(), R.raw.barcode);
            InputImage inputImage = InputImage.fromBitmap(bitmapResult, 0);
            Task<List<Barcode>> task = barcodeScanner.process(inputImage);

            task.addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                @Override
                public void onComplete(@NonNull Task<List<Barcode>> task) {
                    List<Barcode> barcodeList = task.getResult();
                    Barcode barcode = barcodeList.get(0);
                    String result = barcode.getRawValue();
                }
            });
        }

    }

    public void scanQR(Context context) {

        InputStream imgFile = context.getResources().openRawResource(R.raw.qr);

        if (imgFile != null) {
            BarcodeScannerOptions options = new BarcodeScannerOptions.Builder().setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE
            ).build();
            BarcodeScanner barcodeScanner = BarcodeScanning.getClient(options);
            Bitmap bitmapResult = BitmapFactory.decodeResource(context.getResources(), R.raw.qr;
            InputImage inputImage = InputImage.fromBitmap(bitmapResult, 0);
            Task<List<Barcode>> task = barcodeScanner.process(inputImage);

            task.addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                @Override
                public void onComplete(@NonNull Task<List<Barcode>> task) {
                    List<Barcode> barcodeList = task.getResult();
                    Barcode barcode = barcodeList.get(0);
                    String result = barcode.getRawValue();
                }
            });
        }

    }

}
