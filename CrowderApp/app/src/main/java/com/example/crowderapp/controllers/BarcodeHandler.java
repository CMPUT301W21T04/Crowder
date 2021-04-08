package com.example.crowderapp.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.callbackInterfaces.addQRCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getBarcodeCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getQRCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.registerBarcodeCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.InputStream;
import java.util.List;

public class BarcodeHandler {

    public void BarcodeHandler() {
    }

    //, registerBarcodeCallBack callback
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

    //, addQRCallBack callback
    public void scanQR(Context context) {

        InputStream imgFile = context.getResources().openRawResource(R.raw.qr);

        if (imgFile != null) {
            BarcodeScannerOptions options = new BarcodeScannerOptions.Builder().setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE
            ).build();
            BarcodeScanner barcodeScanner = BarcodeScanning.getClient(options);
            Bitmap bitmapResult = BitmapFactory.decodeResource(context.getResources(), R.raw.qr);
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

    public void scanQR(Bitmap qr) {

        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder().setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
        ).build();
        BarcodeScanner barcodeScanner = BarcodeScanning.getClient(options);

        InputImage inputImage = InputImage.fromBitmap(qr, 0);
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

    /*
        This QR code generator will take in a string that
        has an action associated with it. Then it will encode that string into a QR code
        for example: 'PASS' or 'FAIL' will be passed into the generator and a corresponding
        QR code will be generated that when read it will output the string that was passed into the generator
     */
    public Bitmap generateQR(String actionText) {

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(actionText.toString(), BarcodeFormat.QR_CODE, 500, 500, null);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            return bitmap;
        } catch (Exception e) {

        }

        return null;
    }

    public void fetchBarcode(getBarcodeCallBack callback) {}

    public void fetchQR(getQRCallBack callback) {}

    public void deleteBarcode() {}

    public void deleteQR(){}

}
