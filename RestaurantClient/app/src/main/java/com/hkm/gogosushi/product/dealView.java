package com.hkm.gogosushi.product;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hkm.gogosushi.R;

/**
 * Created by hesk on 5/31/2015.
 */
public class dealView extends AppCompatActivity {

    Toolbar toolbar;
    protected void setCustomActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
