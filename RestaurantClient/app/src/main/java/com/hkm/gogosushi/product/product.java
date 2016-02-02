package com.hkm.gogosushi.product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hkm.gogosushi.R;

/**
 * Created by hesk on 25/6/15.
 */
public class product extends AppCompatActivity {
    private Toolbar toolbar;

    protected void setCustomActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("deal!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_product);
        setCustomActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.product_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // case R.id.github:
            //    Util.goToGitHub(this);
            //    return true;

            case R.id.add_star:
                Toast.makeText(this, "added to the wallet", Toast.LENGTH_LONG);
                break;

            case android.R.id.home:
                finish();
                return (true);
        }
        return super.onOptionsItemSelected(item);
    }
}
