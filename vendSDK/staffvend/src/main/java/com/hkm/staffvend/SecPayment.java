package com.hkm.staffvend;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Bill.Bill;
import com.hkmvend.sdk.storage.Bill.BillContainer;

import co.hkm.soltag.TagContainerLayout;
import co.hkm.soltag.TagView;
import co.hkm.soltag.ext.LayouMode;

import static com.hkm.staffvend.event.ApplicationConstant.INTENT_BILL_ID;
import static com.hkm.staffvend.event.ApplicationConstant.INTENT_TABLE_FUNCTION;

/**
 * Created by zJJ on 2/5/2016.
 */
public class SecPayment extends AppCompatActivity implements
        TagView.OnTagClickListener {
    private TextView transaction_id, people_count, table_remark, grant, service, average;
    private ImageButton add_button, remove_button, print_slip;
    private FloatingActionButton fabbutton;
    private TagContainerLayout orders;
    private BillContainer instance;
    private Bill target_bill;
    private int intent_function;
    private float btotal, bservice, baverage, bgrant;

    private void offset(int n) {
        if (people_count.getText().toString().isEmpty()) {
            people_count.setText("1");
            instance.updateHeadCount(target_bill, 1);
        } else {
            int kn = Integer.parseInt(people_count.getText().toString());
            int new_n = kn + n;
            if (new_n <= 1) {
                people_count.setText("1");
            } else if (new_n > 99) {
                people_count.setText("99");
            } else {
                people_count.setText(new_n + "");
            }
            instance.updateHeadCount(target_bill, kn);
        }
        cal();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.as_content_view_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fabbutton = (FloatingActionButton) findViewById(R.id.fab);
        add_button = (ImageButton) findViewById(R.id.add_count);
        print_slip = (ImageButton) findViewById(R.id.print_slip);
        remove_button = (ImageButton) findViewById(R.id.remove_count);
        transaction_id = (TextView) findViewById(R.id.transaction_id);
        people_count = (TextView) findViewById(R.id.people_count);
        table_remark = (TextView) findViewById(R.id.field_remark);
        service = (TextView) findViewById(R.id.service_charge);
        average = (TextView) findViewById(R.id.average);
        grant = (TextView) findViewById(R.id.grant_total);
        orders = (TagContainerLayout) findViewById(R.id.item_ul_tag_container);
        orders.setThemeOnActive(R.style.tagactive);
        orders.setTheme(R.style.tagnormal);
        orders.setOnTagClickListener(this);
        orders.setMode(LayouMode.MULTIPLE_CHOICE);
        instance = RestaurantPOS.getInstance(getApplication()).getBillContainer();
        renderData();
        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset(-1);
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset(1);
            }
        });

        fabbutton = (FloatingActionButton) findViewById(R.id.fab);
        fabbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent_function == -1 || target_bill == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("error from this page internally.");
                    Snackbar
                            .make(view, sb.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Collected payment.");
                    Snackbar
                            .make(view, sb.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    instance.settlePayment(target_bill, bgrant);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    private void renderData() {
        Bundle b = getIntent().getExtras();
        int h = b.getInt(INTENT_TABLE_FUNCTION, -1);
        long bill_code = b.getLong(INTENT_BILL_ID, -1L);
        if (h == -1 || bill_code == -1L) return;
        intent_function = h;
        target_bill = instance.findBillById(bill_code);

        StringBuilder sb = new StringBuilder();

        sb.append(target_bill.getTable_id());
        sb.append(" ");
        sb.append("#");
        sb.append(bill_code);
        transaction_id.setText(sb.toString());

        people_count.setText(target_bill.getHeadcount() + "");
        orders.setTags(BillContainer.getOrderedItemsChinese(target_bill));

        sb = new StringBuilder();
        sb.append("Remark");
        sb.append(": ");
        sb.append(target_bill.getTable_remark());
        table_remark.setText(sb.toString());
        cal();
    }

    private String service_charge_tag = "10%";
    private String currency = "HKD";

    private void cal() {
        btotal = BillContainer.getProjectedTotal(target_bill);
        bservice = btotal * 0.1f;
        bgrant = btotal + bservice;
        baverage = bgrant / (float) target_bill.getHeadcount();

        StringBuilder sb = new StringBuilder();
        sb.append("Service charge ");
        sb.append(service_charge_tag);
        sb.append(": ");
        sb.append(currency);
        sb.append(bservice);
        service.setText(sb.toString());

        sb = new StringBuilder();
        sb.append("Grant Total: ");
        sb.append(currency);
        sb.append(bgrant);
        grant.setText(sb.toString());

        sb = new StringBuilder();
        sb.append("Each Person: ");
        sb.append(currency);
        sb.append(baverage);
        average.setText(sb.toString());

    }

    @Override
    public void onTagClick(int position, String text) {

    }

    @Override
    public void onTagLongClick(int position, String text) {

    }
}
