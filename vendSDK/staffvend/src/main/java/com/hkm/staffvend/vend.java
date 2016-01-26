package com.hkm.staffvend;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hkm.staffvend.content.mainmenu;
import com.hkm.staffvend.content.staff;
import com.hkm.staffvend.event.BS;
import com.squareup.otto.Subscribe;

public class vend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar
                        .make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        normal_start(new staff());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void normal_start(Fragment location) {
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.fragment, location).commit();
    }


    public void backHome() {
        normal_start(new mainmenu());
        //   getActionBar().setHomeButtonEnabled(false);
    }

    boolean backEnabled = false;

    @Subscribe
    public void onEvent(Fragment b) {
        //   getActionBar().setHomeButtonEnabled(true);
        normal_start(b);
        backEnabled = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        BS.getInstance().register(this);
    }

    /**
     * Called when the Fragment is no longer started.  This is generally
     * tied to {@link Activity#onStop() Activity.onStop} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStop() {
        super.onStop();
        BS.getInstance().unregister(this);
    }


    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        if (backEnabled) {
            backEnabled = false;
            backHome();
        } else {
            //super.onBackPressed();
        }
    }
}
