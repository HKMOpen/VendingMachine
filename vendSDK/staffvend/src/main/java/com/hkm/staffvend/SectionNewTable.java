package com.hkm.staffvend;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Bill.Bill;
import com.hkmvend.sdk.storage.Bill.BillContainer;
import com.hkmvend.sdk.storage.Menu.MenuEntry;

/**
 * Created by hesk on 26/1/16.
 */
public class SectionNewTable extends AppCompatActivity {
    private EditText table_id, people_count, table_remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_new_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        table_id = (EditText) findViewById(R.id.table_id);
        people_count = (EditText) findViewById(R.id.people_count);
        table_remark = (EditText) findViewById(R.id.table_remark);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                if (table_id.getText().toString().isEmpty()) {
                    sb.append("invalidate table id");
                }
                if (people_count.getText().toString().isEmpty() || Integer.parseInt(people_count.getText().toString()) == 0) {
                    if (!sb.toString().isEmpty()) {
                        sb.append("\n");
                    }
                    sb.append("head count did not specified");
                }
                if (!sb.toString().isEmpty()) {
                    Snackbar
                            .make(view, sb.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    RestaurantPOS pos = RestaurantPOS.getInstance(getApplication());
                    BillContainer bc = pos.getBillContainer();
                    Bill sucess = bc.newBill(
                            Integer.parseInt(people_count.getText().toString()),
                            table_id.getText().toString()
                    );

                }
            }
        });
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

}
