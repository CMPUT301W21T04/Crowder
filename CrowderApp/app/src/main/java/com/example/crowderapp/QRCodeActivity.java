package com.example.crowderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.crowderapp.controllers.BarcodeHandler;
import com.example.crowderapp.controllers.ScanObjHandler;
import com.example.crowderapp.controllers.callbackInterfaces.ScanObjectCallback;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.ScanObj;

public class QRCodeActivity extends AppCompatActivity {

    ImageView QRImage;
    BarcodeHandler bcHandler = new BarcodeHandler();
    ScanObjHandler soHandler;
    Experiment experiment;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        Bundle bundle = getIntent().getExtras();
        experiment = (Experiment) bundle.getSerializable("Experiment");
        value = bundle.getString("Value");

        soHandler = new ScanObjHandler(experiment.getExperimentID());
        soHandler.createScanObj(value, new ScanObjectCallback() {
            @Override
            public void callback(ScanObj o) {
                QRImage = findViewById(R.id.qr_imageView);
                QRImage.setImageBitmap(bcHandler.generateQR(o.getKey()));
            }
        });

    }
}