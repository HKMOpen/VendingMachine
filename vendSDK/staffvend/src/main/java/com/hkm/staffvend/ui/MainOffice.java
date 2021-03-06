package com.hkm.staffvend.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hkm.staffvend.R;
import com.hkm.staffvend.content.dish_panel;
import com.hkm.staffvend.content.mainmenu;
import com.hkm.staffvend.content.settings;
import com.hkm.staffvend.content.staffmenu;
import com.hkm.staffvend.event.BS;
import com.squareup.otto.Subscribe;

public class MainOffice extends AppCompatActivity {
    private FloatingActionButton fabbutton;
    private Fragment current;
    private boolean backEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.as_content_vend_frame);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        fabbutton = (FloatingActionButton) findViewById(R.id.fab);
        normal_start(new staffmenu());
    }


    private void normal_start(Fragment location) {
        FragmentManager fm = getFragmentManager();
        current = location;
        if (current instanceof staffmenu) {
            fabbutton.setImageResource(R.drawable.ic_queue_24dp);
            fabbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    staffmenu s = (staffmenu) current;
                    if (s.getOrderReady()) {
                        normal_start(new mainmenu());
                    } else {
                        Snackbar
                                .make(view, "Cannot order yet", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });
            fabbutton.setVisibility(View.VISIBLE);
        } else if (current instanceof mainmenu) {
            fabbutton.setImageResource(R.drawable.ic_domain_24dp);
            fabbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    normal_start(new staffmenu());
                }
            });
            fabbutton.setVisibility(View.VISIBLE);
        } else {
            fabbutton.setVisibility(View.GONE);
        }
        fm.beginTransaction().replace(R.id.fragment, location).commit();
    }

    public void backHome() {
        normal_start(new mainmenu());
        //   getActionBar().setHomeButtonEnabled(false);
    }

    @Subscribe
    public void onEvent(Fragment b) {
        //   getActionBar().setHomeButtonEnabled(true);
        normal_start(b);
        backEnabled = true;
    }


    @Subscribe
    public void ev(BS.EntryFnc fn) {
        normal_start(dish_panel.newInstance(fn.entry, fn.item.getEntry_name_chinese()));
        backEnabled = false;
    }


    @Override
    public void onStart() {
        super.onStart();
        BS.getInstance().register(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BS.onResultFromPrevious(requestCode, resultCode, data, current, this);
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
            if (current instanceof settings) {
                normal_start(new staffmenu());
            } else {
                backHome();
            }
        } else {
            //super.onBackPressed();
        }
    }
}
