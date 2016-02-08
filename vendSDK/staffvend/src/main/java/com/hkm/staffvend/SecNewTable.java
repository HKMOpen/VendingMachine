package com.hkm.staffvend;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hkm.videosdkui.application.Dialog.ErrorMessage;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Bill.Bill;
import com.hkmvend.sdk.storage.Bill.BillContainer;

import co.hkm.soltag.TagContainerLayout;
import co.hkm.soltag.TagView;
import co.hkm.soltag.ext.LayouMode;

import static com.hkm.staffvend.event.ApplicationConstant.RESULT_NEW_ORDER;

/**
 * Created by hesk on 26/1/16.
 */
public class SecNewTable extends AppCompatActivity implements TagView.OnTagClickListener {
    private EditText table_id, table_remark;
    private ImageButton add_button, remove_button, confirm;
    private TextView people_count, bill_id;
    private BillContainer bc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.as_content_new_table);

        RestaurantPOS pos = RestaurantPOS.getInstance(getApplication());
        bc = pos.getBillContainer();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        long set_bill_transaction_num = bc.getLastestBillNumber();
        if (set_bill_transaction_num < 0) {
            ErrorMessage.alert(getString(R.string.action_set_bill_num), getFragmentManager(), new Runnable() {
                @Override
                public void run() {
                    SecNewTable.this.setResult(Activity.RESULT_CANCELED);
                    SecNewTable.this.finish();
                }
            });
        }

       // table_id = (EditText) findViewById(R.id.table_id);
        people_count = (TextView) findViewById(R.id.actionbar_number_count);
        bill_id = (TextView) findViewById(R.id.actionbar_item_transaction_id);
        bill_id.setText("#" + set_bill_transaction_num);
        //  table_remark = (EditText) findViewById(R.id.table_remark);

        add_button = (ImageButton) findViewById(R.id.actionbar_add_count);

        remove_button = (ImageButton) findViewById(R.id.actionbar_remove_count);

        confirm = (ImageButton) findViewById(R.id.actionbar_confirm);

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

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_new_table();
            }
        });

        TagContainerLayout tc = (TagContainerLayout) findViewById(R.id.item_ul_tag_container);
        tc.setMode(LayouMode.SINGLE_CHOICE);
        tc.setThemeOnActive(R.style.tagactive);
        tc.setTheme(R.style.tagnormal);
        tc.setOnTagClickListener(this);
        tc.setTags(new String[]{"1", "2", "3", "4", "5", "6", "7", "8"});
        TagContainerLayout table = (TagContainerLayout) findViewById(R.id.item_ul_table_container);
        table.setMode(LayouMode.SINGLE_CHOICE);
        table.setThemeOnActive(R.style.tagactive);
        table.setTheme(R.style.tagnormal);
        table.setOnTagClickListener(this);
        tc.setTags(new String[]{
                "T-01",
                "T-02",
                "T-03",
                "T-04",
                "T-05",
                "T-06",
                "T-07",
                "T-08",
                "T-09",
                "T-10",
                "T-11",
                "T-12",
                "T-13",
                "T-14",
                "T-15",
                "T-16"
        });

    }

    private void confirm_new_table() {
        StringBuilder sb = new StringBuilder();
        if (table_id.getText().toString().isEmpty()) {
            sb.append("invalidate table id");
        }
        if (people_count.getText().toString().isEmpty() || Integer.parseInt(people_count.getText().toString()) == 0) {
            if (!sb.toString().isEmpty()) {
                sb.append("\n");
            }
            sb.append("Head count did not specified");
        }
        if (!sb.toString().isEmpty()) {
            Snackbar
                    .make(add_button.getRootView(), sb.toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Bill sucess = bc.newBill(
                    Integer.parseInt(people_count.getText().toString()),
                    table_id.getText().toString(),
                    ""
                    //   table_remark.getText().toString()
            );
            setResult(RESULT_NEW_ORDER);
            finish();
        }
    }

    private void offset(int n) {
        if (people_count.getText().toString().isEmpty()) {
            people_count.setText("1");
        } else {
            int kn = Integer.parseInt(people_count.getText().toString());
            int new_n = kn + n;
            if (new_n <= 0) {
                people_count.setText("0");
            } else
                people_count.setText(new_n + "");
        }
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

    @Override
    public void onTagClick(int position, String text) {
        people_count.setText(text);
    }

    @Override
    public void onTagLongClick(int position, String text) {

    }
}
