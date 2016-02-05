package com.hkm.staffvend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by hesk on 5/2/16.
 */
public class SecAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.as_about);
        TextView appv = (TextView) findViewById(R.id.app_version);
        StringBuilder sb = new StringBuilder();
        sb.append(BuildConfig.VERSION_NAME);
        sb.append(" (");
        sb.append(BuildConfig.VERSION_CODE);
        sb.append(")");
        appv.setText(sb.toString());
    }
}
