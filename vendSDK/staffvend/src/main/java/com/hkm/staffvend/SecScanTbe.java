package com.hkm.staffvend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.Result;
import com.hkm.layout.Dialog.ErrorMessage;
import com.hkm.staffvend.event.Utils;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by hesk on 1/2/16.
 */
public class SecScanTbe extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    public static final String TAG = "scan";
    public static final String FIELD = "scancontent";
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);
        // Programmatically initialize the scanner view
        mScannerView.setAutoFocus(true);
        setContentView(mScannerView);
        // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        // Register ourselves as a handler for scan results.
        mScannerView.startCamera();
        // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText());
        // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString());
        // Prints the scan format (qrcode, pdf417 etc.)
        // If you would like to resume scanning, call this method below:
        if (rawResult.getText().isEmpty()) {
            ErrorMessage.alert(getString(R.string.note_no_message), getFragmentManager(), new Runnable() {
                @Override
                public void run() {
                    mScannerView.resumeCameraPreview(SecScanTbe.this);
                }
            });
        } else {
            Utils.setFinish(this, FIELD, rawResult.getText());
        }
    }


}
