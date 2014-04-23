package com.qafeerlabs.pssst.gui.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivity;
import com.qafeerlabs.pssst.gui.R;

public class ScannerView extends CaptureActivity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_scan_view);
    }
    @Override
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor)
    {
    	super.handleDecode(rawResult, barcode, scaleFactor);
    	Toast.makeText(this.getApplicationContext(), "Scanned code " + rawResult.getText(), Toast.LENGTH_LONG).show();
    }
}