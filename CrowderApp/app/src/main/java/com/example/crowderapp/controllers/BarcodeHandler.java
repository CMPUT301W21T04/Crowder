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

/**
 * Barcode handler that is meant to generate QR code and scan QR and barcode
 */
public class BarcodeHandler {

    public void BarcodeHandler() {
    }

    /**
     * scans barcode using mlkit. This has been depricated, but kept for backup
     * @param barcode
     */
    public void scanBarcode(Bitmap barcode) {
        /*
            Reference : https://zeeshan-elahi.medium.com/how-to-use-googles-ml-kit-to-scan-barcodes-8a009ab491d2
            Author : Zeeshan Elahi
            Details : Showed how to scan QR and barcode using mlkit
         */
        BarcodeScanner barcodeScanner = BarcodeScanning.getClient();
        InputImage inputImage = InputImage.fromBitmap(barcode, 0);
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

    /**
     * scans qr using mlkit. This has been depricated, but kept for backup
     * @param qr
     */
    public void scanQR(Bitmap qr) {
        /*
            Reference : https://zeeshan-elahi.medium.com/how-to-use-googles-ml-kit-to-scan-barcodes-8a009ab491d2
            Author : Zeeshan Elahi
            Details : Showed how to scan QR and barcode using mlkit
         */
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

    /**
     * generates a QR based on a string
     * @param actionText : an action string
     * @return QR code bitmap
     */
    public Bitmap generateQR(String actionText) {
        /*
            Reference: https://medium.com/@aanandshekharroy/generate-barcode-in-android-app-using-zxing-64c076a5d83a
            Author : Aanand Shekhar Roy
            Details : Showed how to use zxing to generate barcode
         */
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(actionText.toString(), BarcodeFormat.QR_CODE, 500, 500, null);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            return bitmap;
        } catch (Exception e) {

        }

        return null;
    }

}
