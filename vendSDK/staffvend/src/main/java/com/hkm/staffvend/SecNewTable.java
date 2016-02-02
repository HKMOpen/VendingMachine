package com.hkm.staffvend;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Bill.Bill;
import com.hkmvend.sdk.storage.Bill.BillContainer;

/**
 * Created by hesk on 26/1/16.
 */
public class SecNewTable extends AppCompatActivity {
    private EditText table_id, people_count, table_remark;
    private Button add_button, remove_button;


    private void offset(int n) {
        if (people_count.getText().toString().isEmpty()) {
            people_count.setText(1 + "");
        } else {
            int kn = Integer.parseInt(people_count.getText().toString());
            int new_n = kn + n;
            if (new_n <= 0) {
                people_count.setText("0");
            } else
                people_count.setText(new_n + "");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_new_table);
        RestaurantPOS pos = RestaurantPOS.getInstance(getApplication());
        final BillContainer bc = pos.getBillContainer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("#" + bc.getLastestBillNumber() + "");

        table_id = (EditText) findViewById(R.id.table_id);
        people_count = (EditText) findViewById(R.id.people_count);
        table_remark = (EditText) findViewById(R.id.table_remark);
        add_button = (Button) findViewById(R.id.add);
        remove_button = (Button) findViewById(R.id.remove);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset(1);
            }
        });
        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset(-1);
            }
        });
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

                    Bill sucess = bc.newBill(
                            Integer.parseInt(people_count.getText().toString()),
                            table_id.getText().toString(),
                            table_remark.getText().toString()
                    );
                    setResult(RESULT_OK);
                    finish();
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
